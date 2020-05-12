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
import java.util.Arrays;

/**
 * Copyright (C), 2014-2018, 深圳市云之讯网络技术有限公司
 * FileName: StepSecond
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
public class StepSecond {

    static class SecondMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] friendUsers = line.split("\t");
            String friend = friendUsers[0];
            String[] users = friendUsers[1].split(",");

            Arrays.sort(users);

            for (int i = 0; i < users.length - 1; i++) {
                for (int j = i + 1 ; j < users.length; j++) {
                    context.write(new Text(users[i] + "-" + users[j]), new Text(friend));
                }
            }
        }
    }

    static class SecondReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text userUser, Iterable<Text> friends, Context context) throws IOException, InterruptedException {
            StringBuffer buf = new StringBuffer();
            for (Text friend : friends) {
                buf.append(friend).append(" ");
            }
            context.write(userUser, new Text(buf.toString()));
        }
    }

    public static void main(String[] args) throws Exception {
        //创建任务
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(StepSecond.class);

        //任务输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //指定map reduce
        job.setMapperClass(SecondMapper.class);
        job.setReducerClass(SecondReducer.class);

        //输入文件路径、输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //提交任务
        job.waitForCompletion(true);
    }
}
