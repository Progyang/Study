package cn.FlowerShopSys.entity;

/**
 * @author 马志圆
 *2018-07-14
 *花店所售花束类
 */
public class Bouquets extends Goods {
	private String form;//花束的组成
	//构造函数
	public Bouquets() {}
	public Bouquets(String name,double price,int number,int history,String form) {
		super(name,price,number,history);
		this.form=form;
	}
	//计算价格函数重写
	public double calculate(int num) {
		return num*super.getPrice();
	}
	//show函数重写
	public void show() {
		System.out.println("花束：  "+super.getName()+" \t单价: "+super.getPrice()+"￥"+"\t库存: "+super.getNumber()+"\t已售出: "+super.getHistory()+" \t组成:"+this.getForm());
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}

}
