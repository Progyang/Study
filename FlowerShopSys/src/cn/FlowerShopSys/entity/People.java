package cn.FlowerShopSys.entity;

/**
 * 店主和客户的共同父类
 *@author 马志圆
 *2018-07-14
 */
public class People {
	private String id;//唯一标识一个人
	private String passWd;//登录密码
	private int rand;//等级标识  0店长    1 普通客户 2普通会员  3白金会员  4黄金会员   5钻石会员 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassWd() {
		return passWd;
	}
	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}
	public int getRand() {
		return rand;
	}
	public void setRand(int rand) {
		this.rand = rand;
	}
	//构造函数
	public People() {

	}
	public People(String id,String passWd,int rand) {
		this.id=id;
		this.passWd=passWd;
		this.rand=rand;
	}
}
