package com.tonytaotao.thread.productWithConsume;

public class Test {
	public static void main(String[] args) {
		Bag bag=new Bag();
		Thread consumer=new Thread(new Consumer(bag), "consumer");
		Thread producer=new Thread(new Producer(bag), "producer");
		consumer.start();
		producer.start();
	}
}
