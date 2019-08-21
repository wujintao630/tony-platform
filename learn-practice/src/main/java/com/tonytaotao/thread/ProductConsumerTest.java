package com.tonytaotao.thread;

import java.io.Serializable;

public class ProductConsumerTest {
	public static void main(String[] args) {
		Bag bag=new Bag();
		Thread consumer=new Thread(new Consumer(bag), "consumer");
		Thread producer=new Thread(new Producer(bag), "producer");
		consumer.start();
		producer.start();
	}
}

class Consumer implements Runnable{

	private Bag bag;

	public Consumer() {

	}
	public Consumer(Bag bag){
		this.bag=bag;
	}

	@Override
	public void run() {
		while(true){
			bag.put();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Producer implements Runnable{

	private Bag bag;

	public Producer() {

	}
	public Producer(Bag bag){
		this.bag=bag;
	}

	@Override
	public void run() {
		while(true){
			bag.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}



class Apple implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public Apple() {
		super();
	}

	public Apple(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

class Bag {

	public final int count=10;

	public int sum=0;

	public synchronized void put(){
		while(sum >= count){
			System.out.println("背包已满");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sum++;
		System.out.println("当前线程："+Thread.currentThread().getName()+",往背包中放了一个苹果,"+"sum="+sum);
		notifyAll();
	}

	public synchronized void get(){
		while(sum <= 0){
			System.out.println("背包已空");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sum--;
		System.out.println("当前线程："+Thread.currentThread().getName()+",往背包中拿出一个苹果,"+"sum="+sum);
		notifyAll();
	}
}


