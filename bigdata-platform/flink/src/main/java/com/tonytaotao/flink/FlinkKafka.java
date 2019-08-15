package com.tonytaotao.flink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import javax.annotation.Nullable;
import java.util.Properties;

public class FlinkKafka {

    public static void main(String[] args) throws Exception{

        // 引入Flink StreamExecutionEnvironment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置监控数据流时间间隔（官方叫状态与检查点）
        env.enableCheckpointing(1000);

        // 配置kafka和zookeeper的ip和端口
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.56.101:9092");
        properties.setProperty("zookeeper.connect", "192.168.56.101:2181");
        properties.setProperty("group.id", "test");

        // 记载kafka和zookeeper的配置
        FlinkKafkaConsumer011<String> consumer = new FlinkKafkaConsumer011<String>("test", new SimpleStringSchema(), properties);

        consumer.assignTimestampsAndWatermarks(new LineSplitter());

        // 转换kafka数据类型为flink的dataStream类型
        DataStream<String> stream = env.addSource(consumer);
        stream.print();
        env.execute("WordCount from kafka data");
   }

    public static final class LineSplitter implements AssignerWithPunctuatedWatermarks<String> {

        private static final long serialVersionUID = 1L;


        @Nullable
        @Override
        public Watermark checkAndGetNextWatermark(String arg0, long arg1) {
            if (null != arg0 && arg0.contains(",")) {
                String parts[] = arg0.split(",");
                return new Watermark(Long.parseLong(parts[0]));
            }
            return null;
        }

        @Override
        public long extractTimestamp(String arg0, long arg1) {
            if (null != arg0 && arg0.contains(",")) {
                String parts[] = arg0.split(",");
                return Long.parseLong(parts[0]);
            }
            return 0;
        }
    }

}


