package com.util.lambda;
import java.math.BigDecimal;

public class AObject {

	private String a;
	
	private String b;
	
	private BigDecimal money;
	
	private Integer num;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
	
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public String toString(){
		return a+" "+b;
	}
	
	
}
