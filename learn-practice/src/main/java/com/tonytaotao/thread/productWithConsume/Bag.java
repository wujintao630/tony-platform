package com.tonytaotao.thread.productWithConsume;

public class Bag {
	
	public final int count=10;
	
	public int sum=0;
	
	public synchronized void put(){
		while(sum>=count){
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
		while(sum<=0){
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
