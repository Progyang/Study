package cn.FlowerShopSys.entity;

public class shCar {
	private String name;
	private int buyNum;
	private double sumprice;//该项总价
	public shCar(String name,int buyNum,double sumprice) {
		this.name=name;
		this.buyNum=buyNum;
		this.sumprice=sumprice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public double getSumprice() {
		return sumprice;
	}
	public void setSumprice(double sumprice) {
		this.sumprice = sumprice;
	}
	
}
