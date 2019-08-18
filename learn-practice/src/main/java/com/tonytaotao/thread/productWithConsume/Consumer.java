package com.tonytaotao.thread.productWithConsume;

public class Consumer implements Runnable{
	
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
