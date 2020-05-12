package com.tonytaotao.hadoop.listfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;

/**
 * Copyright (C), 2014-2018, 深圳市云之讯网络技术有限公司
 * FileName: ListHdfsFile
 * <p>
 * 〈〉
 *
 * @author tonytaotao
 * @create 2018/3/13
 * @since 1.0.0
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名          修改时间         版本号             描述
 */
public class ListHdfsFile {

    public static void main(String[] args) throws Exception{
        String url = "hdfs://192.168.23.128:9000/";

        System.setProperty("hadoop.home.dir", "E:\\Program Files\\hadoop-2.6.0-cdh5.14.0\\hadoop-common-2.2.0");

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(url), conf);

        FileStatus[] statuses = fs.listStatus(new Path("/input"));
        for (FileStatus status:statuses){
            System.out.println(status);
        }

    }



}
