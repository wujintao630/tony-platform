package com.tonytaotao.thread.productWithConsume;

public class Producer implements Runnable{
	
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
