package com.tonytaotao.algorithm;

public class SnowFlake {

    /**
     * 开始时间戳 2019-01-01 00:00:00
     */
    private final long startTimestamp = 1546272000000L;

    /**
     * 机器ID所占位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据中心ID所占位数
     */
    private final long datacenterIdBits = 5L;

    /**
     * 序列所占位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID左移12位
     */
    private final long workIdLeftShift = sequenceBits;

    /**
     * 数据中心ID左移17位(12+5)
     */
    private final long datacenterIdLeftShift = workIdLeftShift + workerIdBits;

    /**
     * 时间戳左移22位(12+5+5)
     */
    private final long timestampLeftShift = datacenterIdLeftShift + datacenterIdBits;

    /**
     * 序列掩码 4095 : 0b111111111111=0xfff=4095
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 当前机器ID（0~31）
     */
    private long currentWorkId;

    /**
     * 当前数据中心ID(0~31)
     */
    private long currentDatacenterId;

    /**
     * 初始化序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上一次生成序列号的时间戳
     */
    private long lastTimestamp = -1L;


    /**
     * 获取序列号
     * @return
     */
    public synchronized long nextId() {

        long currentTimestamp = System.currentTimeMillis();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException(String.format("System clock move backwards. Refusing to generate id for %d milliseconds", lastTimestamp - currentTimestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内超过序列最大值
            if (sequence == 0) {

                // 阻塞到下一个毫秒，直到获得新的时间戳
                currentTimestamp = System.currentTimeMillis();
                while (currentTimestamp <= lastTimestamp) {
                    currentTimestamp = System.currentTimeMillis();
                }
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = currentTimestamp;

        //  生成序列号
        return ((currentTimestamp - startTimestamp) << timestampLeftShift) | (currentDatacenterId << datacenterIdLeftShift) | (currentWorkId << workIdLeftShift) | sequence;

    }

    /**
     * 初始化workId 和 datacenterId
     */
    private void initSnowFlake() {

        // 多节点启动的时候，就需要设置不同的workId 和 datacenterId, 防止生成重复序列号

        // eg：
        // 1、使用数据库表，记录已有的节点 workId、datacenterId
        // 2、当多节点同时启动的时候，可以使用redis分布式锁或者数据库悲观锁，防止 workId、datacenterId重复
        // 3、设置workId、datacenterId，并保证不与其他节点重复，且不超过maxWorkId、maxDatacenterId

    }

}

