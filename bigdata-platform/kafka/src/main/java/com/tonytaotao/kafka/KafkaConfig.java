package com.tonytaotao.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConfig {


    public static final String KAFKA_SERVER_CLUSTER = "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094";

    public static final String KAFKA_TOPIC = "test";

    public static final String KAFKA_GROUP_ID = "test_group";


    public static Properties getProducerConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_CLUSTER);

        // 0表示producer不需要等待任何确认收到的信息。副本将立即加到socket buffer并认为已经发送。没有任何保障可以保证此种情况下server已经成功接收数据，同时重试配置不会发生作用（因为客户端不知道是否失败）回馈的offset会总是设置为-1
        // 1表示至少要等待leader已经成功将数据写入本地log，但是并没有等待所有follower是否成功写入。这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失
        // all表示leader需要等待所有备份都成功写入日志，这种策略会保证只要有一个备份存活就不会丢失数据，这是最强的保证
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        // retry > 0 如果发送失败，会自动尝试重新发送数据，发送次数为retries设置的值。如果设定了retries但没有把max.in.flight.requests.per.connection 设置成 1则可能会改变数据的顺序，因为如果这两个batch都是发送到同一个partition，并且第一个batch发送失败而第二个发送成功，则第二个batch中的消息记录会比第一个的达到得早
        props.put(ProducerConfig.RETRIES_CONFIG, 0);

        // 批处理，增大吞吐量，缓存一批数据一起传到服务器
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        // 若存储在 缓存中的数据没有达到 batch_size，如果设置了linger.ms, 则会等待这个设置的时间，收集更多的数据请求，一起发送，减少请求次数
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        // 缓存大小
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554422);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return props;
    }

    public static Properties getConsumerConfig() {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_CLUSTER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID);

        // true：自动commit offset, 配合 auto.commit.interval.ms 使用
        // false: 手动提交，业务处理完后才提交offset
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        //  配合 enable.auto.commit 使用，若 enable.auto.commit 为false， 则该配置不生效
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);

        // 告诉Kafka Broker在发现kafka在没有初始offset，或者当前的offset是一个不存在的值（如果一个record被删除，就肯定不存在了）时，该如何处理。它有4种处理方式：
        // earliest：自动重置到最早的offset。
        // latest：看上去重置到最晚的offset,  默认值。
        // none：如果边更早的offset也没有的话，就抛出异常给consumer，告诉consumer在整个consumer group中都没有发现有这样的offset。
        // 如果不是上述3种，只抛出异常给consumer。
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        // 与broker的session时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);

        // 心跳是在consumer与coordinator之间进行的。心跳是确定consumer存活，加入或者退出group的有效手段
        // 当Consumer由于某种原因不能发Heartbeat到coordinator时，并且时间超过session.timeout.ms时，就会认为该consumer已退出，它所订阅的partition会分配到同一group 内的其它的consumer上。
        // 通常设置的值要低于session.timeout.ms的1/3
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        return props;

    }

}
