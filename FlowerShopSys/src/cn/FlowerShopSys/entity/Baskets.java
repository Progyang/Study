package cn.FlowerShopSys.entity;

/**
 * @author 马志圆
 *2018-07-14
 *花店所售花篮类
 */
public class Baskets extends Goods {
	private double height;
	public Baskets() {}
	public Baskets(String name,double price,int number,int history,double height) {
		super(name,price,number,history);
		this.height=height;
	}
	@Override
	public double calculate(int num) {
		return num*super.getPrice();
	}

	@Override
	public void show() {
		System.out.println("花篮：  "+super.getName()+" \t单价: "+super.getPrice()+"￥"+"\t库存: "+super.getNumber()+"  \t已售出: "+super.getHistory()+" \t高度:"+this.getHeight()+"cm");
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}

}
