package com.tonytaotao.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.TimeUnit;

public class KafkaProducerMain {

    public static void main(String[] args) {

        Producer<String, String> producer = new KafkaProducer<>(KafkaConfig.getProducerConfig());

        int i = 0;

        while (true) {

            String msg = "welcom tony " + (i++);

            System.out.println("生产者生产消息：" + msg);

            producer.send(new ProducerRecord<>(KafkaConfig.KAFKA_TOPIC, msg));

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
