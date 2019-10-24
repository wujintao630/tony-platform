package com.tonytaotao.flink;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 计算实时热门商品
 */
public class HotItem {

    public static void main(String[] args) throws Exception{

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 告诉系统按照 EventTime 处理
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 为了打印到控制台的结果不乱序，我们配置全局的并发为1，改变并发对结果正确性没有影响
        env.setParallelism(1);

        // UserBehavior.csv 的本地文件路径, 在 resources 目录下
        URL fileUrl = HotItem.class.getClassLoader().getResource("UserBehavior.csv");
        Path filePath = Path.fromLocalFile(new File(fileUrl.toURI()));

        // 抽取 UserBehavior 的 TypeInformation，是一个 PojoTypeInfo
        PojoTypeInfo<UserBehavior> pojoType = (PojoTypeInfo<UserBehavior>) TypeExtractor.createTypeInfo(UserBehavior.class);

        // 由于 Java 反射抽取出的字段顺序是不确定的，需要显式指定下文件中字段的顺序
        String[] fieldOrder = new String[]{"userId", "itemId", "categoryId", "behavior", "timestamp"};

        // 创建 PojoCsvInputFormat
        PojoCsvInputFormat<UserBehavior> csvInput = new PojoCsvInputFormat<>(filePath, pojoType, fieldOrder);

            // 创建数据源，得到 UserBehavior 类型的 DataStream
        env.createInput(csvInput, pojoType)
            // 抽取出时间和生成 watermark
           .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<UserBehavior>() {
               @Override
               public long extractAscendingTimestamp(UserBehavior userBehavior) {
                   // 原始数据单位秒，将其转成毫秒
                   return userBehavior.timestamp * 1000;
               }
           })
           // 过滤出只有点击的数据
          .filter(new FilterFunction<UserBehavior>() {
              @Override
              public boolean filter(UserBehavior userBehavior) throws Exception {
                  // 过滤出只有点击的数据
                  return userBehavior.behavior.equals("pv");
              }
          })
          .keyBy("itemId")
          .timeWindow(Time.minutes(60), Time.minutes(5))
          .aggregate(new CountAggregation(), new WindowResultFunction())
          .keyBy("windowEnd")
          .process(new TopHotItem(3))
          .print();

        env.execute("Hot Items Job");

    }

    public static class TopHotItem extends KeyedProcessFunction<Tuple, ItemViewCount, String> {

        private final int topSize;

        private ListState<ItemViewCount> itemStateList;

        public TopHotItem(int topSize) {
            this.topSize = topSize;
        }

        @Override
        public void processElement(ItemViewCount itemViewCount, Context context, Collector<String> collector) throws Exception {

            // 每条数据都保存到状态中
            itemStateList.add(itemViewCount);

            // 注册 windowEnd+1 的 EventTime Timer, 当触发时，说明收齐了属于windowEnd窗口的所有商品数据
            context.timerService().registerEventTimeTimer(itemViewCount.windowEnd + 1);

        }

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);

            ListStateDescriptor<ItemViewCount> itemStateDescList = new ListStateDescriptor<ItemViewCount>("itemState-state", ItemViewCount.class);

            itemStateList = getRuntimeContext().getListState(itemStateDescList);
        }

        @Override
        public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {

            // 获取收到的所有商品点击量
            List<ItemViewCount> itemViewCountList = new ArrayList<>();
            for(ItemViewCount itemViewCount : itemStateList.get()) {
                itemViewCountList.add(itemViewCount);
            }

            // 提前清除状态中的数据，释放空间
            itemStateList.clear();

            // 按照点击量从大到小排序
            itemViewCountList.sort(new Comparator<ItemViewCount>() {
                @Override
                public int compare(ItemViewCount o1, ItemViewCount o2) {
                    return (int)(o2.viewCount - o1.viewCount);
                }
            });

            // 将排名信息格式化成 String, 便于打印
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("====================================\n");
            stringBuilder.append("时间: ").append(new Timestamp(timestamp-1)).append("\n");
            for (int i=0; i<itemViewCountList.size() && i < topSize; i++) {
                ItemViewCount currentItem = itemViewCountList.get(i);
                // No1:  商品ID=12224  浏览量=2413
                stringBuilder.append("No").append(i).append(":")
                        .append("  商品ID=").append(currentItem.itemId)
                        .append("  浏览量=").append(currentItem.viewCount)
                        .append("\n");
            }
            stringBuilder.append("====================================\n\n");

            // 控制输出频率，模拟实时滚动结果
            Thread.sleep(1000);

            out.collect(stringBuilder.toString());
        }
    }

    /**
     * COUNT 统计的聚合函数实现，每出现一条记录加一
     */
    public static class CountAggregation implements AggregateFunction<UserBehavior, Long, Long> {

        @Override
        public Long createAccumulator() {
            return 0L;
        }

        @Override
        public Long add(UserBehavior userBehavior, Long aLong) {
            return aLong + 1;
        }

        @Override
        public Long getResult(Long aLong) {
            return aLong;
        }

        @Override
        public Long merge(Long aLong, Long acc1) {
            return aLong + acc1;
        }
    }

    public static class WindowResultFunction implements WindowFunction<Long, ItemViewCount, Tuple, TimeWindow> {

        @Override
        public void apply(Tuple tuple, TimeWindow timeWindow, Iterable<Long> iterable, Collector<ItemViewCount> collector) throws Exception {
            Long itemId = ((Tuple1<Long>)tuple).f0;
            Long count = iterable.iterator().next();
            collector.collect(ItemViewCount.of(itemId, timeWindow.getEnd(), count));
        }
    }

    /**
     * 商品点击量(窗口操作的输出类型)
     */
    public static class ItemViewCount {

        public long itemId; // 商品ID

        public long windowEnd; // 窗口结束时间戳

        public long viewCount; // 商品的点击量

        public static ItemViewCount of(long itemId, long windowEnd, long viewCount) {
            ItemViewCount result = new ItemViewCount();
            result.itemId = itemId;
            result.windowEnd = windowEnd;
            result.viewCount = viewCount;
            return result;
        }

    }

    /**
     * 数据实体类
     */
    public static class UserBehavior {

        public long userId;         // 用户ID
        public long itemId;         // 商品ID
        public int categoryId;      // 商品类目ID
        public String behavior;     // 用户行为, 包括("pv", "buy", "cart", "fav")
        public long timestamp;      // 行为发生的时间戳，单位秒


    }

}




