package com.tonytaotao.thread.productWithConsume;

import java.io.Serializable;

public class Apple implements Serializable {

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
