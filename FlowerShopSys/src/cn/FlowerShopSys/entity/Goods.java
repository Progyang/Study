package cn.FlowerShopSys.entity;

/**
 * @author 马志圆
 * 2018-07-14
 *花店商品父类，抽象类
 */
public abstract class Goods {
	private String name;//商品名称
	private double price;//单价
	private int number;//库存数量
	private int history;//已售出
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getHistory() {
		return history;
	}
	public void setHistory(int history) {
		this.history = history;
	}
	//构造函数
	public Goods() {}
	public Goods(String name,double price,int number,int history) {
		this.name=name;
		this.price=price;
		this.number=number;
		this.history=history;
	}
	//计算购买此种商品价格的抽象函数
	public abstract double calculate(int num) ;
	//输出当前商品信息
	public abstract void show();
}
