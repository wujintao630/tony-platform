package com.tonytaotao.hadoop.commonfriend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Copyright (C), 2014-2018, 深圳市云之讯网络技术有限公司
 * FileName: StepFirst
 * <p>
 * 〈〉
 *
 * @author tonytaotao
 * @create 2018/3/17
 * @since 1.0.0
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名          修改时间         版本号             描述
 */
public class StepFirst {

    static class FirstMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException{
            String line = value.toString();
            String[] array = line.split(":");
            String user = array[0];
            String[] friends = array[1].split(",");
            for (int i = 0; i < friends.length ; i++) {
                context.write(new Text(friends[i]), new Text(user));
            }
        }
    }

    static class FirstReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text friend, Iterable<Text> users, Context context) throws IOException, InterruptedException {
            StringBuffer buf = new StringBuffer();
            for(Text user : users) {
                buf.append(user).append(",");
            }
            context.write(new Text(friend), new Text(buf.toString()));
        }
    }

    public static void main(String[] args) throws Exception {
        //创建任务
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(StepFirst.class);

        //任务输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //指定map reduce
        job.setMapperClass(FirstMapper.class);
        job.setReducerClass(FirstReducer.class);

        //输入文件路径、输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //提交任务
        job.waitForCompletion(true);
    }
}
