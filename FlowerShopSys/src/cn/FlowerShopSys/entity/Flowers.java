package cn.FlowerShopSys.entity;

/**
 * @author 马志圆
 * 2018-07-14
 * 花店所销售的花类
 * 
 * 
 */
public class Flowers extends Goods {
	//构造函数
	public Flowers() {}
	public Flowers(String name,double price,int number,int history) {
		super(name,price,number,history);
	}
	//重写抽象方法
	public double calculate(int num) {
		return num*super.getPrice();
	}
	public void show() {
		System.out.println("单花：  "+super.getName()+"  \t单价: "+super.getPrice()+"￥"+"\t库存: "+super.getNumber()+"\t已售出: "+super.getHistory());
	}

}
