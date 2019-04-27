package cn.FlowerShopSys.entity;

import java.util.Map;

/**
 * @author 马志圆
 *2018-07-14
 *订单类，记录订单信息
 */
public class Orders {
	public String id;//订单id
	public String information;//所含商品
	public String address;//送货地址
	public int flag;//订单是否已完成，默认0未完成
	public double sumPrice;//订单总价格
	//构造
	public Orders() {
		this.flag=0;
	}
	public Orders(String id,String information,int flag,double sumPrice,String address) {
		this.id=id;
		this.information=information;
		this.flag=flag;
		this.sumPrice=sumPrice;
		this.address=address;
	}
	/**
	 * 订单展示函数
	 * @author 程硕淇 7-14
	 */
	public void show() {
		System.out.print("订单号:"+id+"   ");
		System.out.print("商品信息:"+information);
		System.out.print("   价格:￥"+sumPrice);
		System.out.print("  配送地址:"+address);
		if(flag==1) {
			System.out.println("  状态:已签收");
		}else {
			System.out.print("  状态:未签收");
		}
		
	}
}
