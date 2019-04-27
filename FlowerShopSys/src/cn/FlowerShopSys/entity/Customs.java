package cn.FlowerShopSys.entity;
import cn.FlowerShopSys.biz.customPage;
import cn.FlowerShopSys.entity.*;
//import cn.FlowerShopSys.run.DBWork;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * 顾客类继承peoplel类和menu接口
 * @author 程硕淇 2018-7-14
 */
public class Customs extends People{
	double consume;//累计消费
	int count;//累计购物次数
	String address;//默认地址
	
	//构造函数
	public Customs() {}
	public Customs(String id,String passWd,int rand,double consume,int count,String address) {
		super(id,passWd,rand);
		this.consume=consume;
		this.count=count;
		this.address=address;
	}
	
	//获取货物数据，并改变成表格输出形式
	public Object[][] GoodsData(Map<String,Goods> mapG,String head[]){
		Object[][] data=new Object[mapG.size()][head.length];
		int i=0;
		for (Map.Entry<String,Goods> en:mapG.entrySet()){
            data[i][0]=en.getValue().getName();
            data[i][1]=en.getValue().getPrice();
            data[i][2]=en.getValue().getNumber();
            if(en.getValue() instanceof Baskets) {
            	data[i][3]=((Baskets)en.getValue()).getHeight();
            }
            else if(en.getValue() instanceof Bouquets) {
            	data[i][3]=((Bouquets)en.getValue()).getForm();
            }
            else {
            	data[i][3]=null;
            }
            data[i][4]=en.getValue().getHistory();
            i++;
        }
        return data;
    }
	//用于界面中的商品列表更新函数
	public void freshTable(Map<String, Goods> mapG,String [] head,JTable table) {
		Font font = new Font("宋体",Font.PLAIN,20);
		table.setModel(new DefaultTableModel(GoodsData(mapG,head),head));//形成表格
		table.setFont(font);//设置字体
		table.setRowHeight(25);//设置行高
		JTableHeader tableHead = table.getTableHeader(); // 创建表格标题对象
		tableHead.setPreferredSize(new Dimension(tableHead.getWidth(), 35));// 设置表头大小
		tableHead.setFont(new Font("黑体", Font.PLAIN, 20));// 设置表格字体
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
	        for (int i = 0; i < head.length; i++) {
	        	TableColumn column = table.getColumnModel().getColumn(i);
	            if (i == 3) {
	                column.setPreferredWidth(500);
	            }
	        }
	}
	/**
	 * 用户等级自动升级
	 */
	public void levelUp() {
		if(consume<200) {
			this.setRand(1);//普通用户或游客
		}
		else if(consume>200 &&consume<=500) {
			this.setRand(2);//普通会员
		}
		else if(consume>500 && consume<=1000) {
			this.setRand(3);//白金会员
		}
		else if(consume>1000 && consume<=2000) {
			this.setRand(4);//黄金会员
		}
		else {
			this.setRand(5);//钻石会员
		}
	}
	
	/**
	 * 返回分身信息开头
	 */
	public String welcomWd() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR);
		String welcome;
		switch(this.getRand()) {
		case 2:
			welcome=new String("尊敬的普通会员，您好！");
			break;
		case 3:
			welcome=new String("尊贵的白金会员，您好！");
			break;
		case 4:
			welcome=new String("尊贵的黄金会员，您好！");
			break;
		case 5:
			welcome=new String("尊贵的钻石会员，您好！");
			break;
		default:
			welcome=new String("亲爱的顾客，您好！");
		}
		
		if(hour>4 && hour<12) {
			welcome+="  愿您有个美好的清晨";
		}
		else if(hour>=12 && hour<=15) {
			welcome+="  愿您有个安详的午后";
		}
		else if(hour>15 && hour<=19) {
			welcome+="  愿您有个悠闲的黄昏";
		}
		else {
			welcome+="  愿您有个浪漫的夜晚";
		}
		return welcome;
	}
	
	/**
	 * 获取折扣价格
	 */
	public double discount(double price) {
		double dis=price;
		switch(this.getRand()) {
		case 2:
			dis*=0.95;
			break;
		case 3:
			dis*=0.9;
			break;
		case 4:
			dis*=0.8;
			break;
		case 5:
			dis*=0.75;
			break;
			default:
		}
		return dis;//其余等级返回原价
	}
	//获取表用已签收订单数据
	public Object[][] OrdersCheckedData(Map<String,Orders> mapO,String headO[]){
		int i=0;
		for (Map.Entry<String,Orders> en:mapO.entrySet()){
			if(en.getValue().flag==1) {//计算已签收签收数量
	            i++;
			}
        }
		Object[][] data=new Object[i][headO.length];//动态
		i=0;//清零
		for (Map.Entry<String,Orders> en:mapO.entrySet()){
			if(en.getValue().flag==1) {//若订单已签收则写入
				data[i][0]=en.getValue().id;
	            data[i][1]=en.getValue().information;
	            data[i][2]=en.getValue().address;
	            data[i][3]=en.getValue().sumPrice;
	            data[i][4]="已完成";
	            i++;
			}
            
        }
        return data;
    }	
	
	//获取表用未签收订单数据
	public Object[][] OrdersUnCheckedData(Map<String,Orders> mapO,String headO[]){
		int i=0;
		for (Map.Entry<String,Orders> en:mapO.entrySet()){
			if(en.getValue().flag!=1) {//寻找未签收数量
	            i++;
			}
            
        }
		Object[][] data=new Object[i][headO.length];
		i=0;//清零
		for (Map.Entry<String,Orders> d:mapO.entrySet()){
			if(d.getValue().flag!=1) {//若订单已签收则写入
				data[i][0]=d.getValue().id;
	            data[i][1]=d.getValue().information;
	            data[i][2]=d.getValue().address;
	            data[i][3]=d.getValue().sumPrice;
	            data[i][4]="待签收";
	            i++;
			}
            
        }
        return data;
	}	
	
	//获取购物车临时数据
	public Object[][] shCarData(List<shCar> shList,String headO[]){
		Object[][] data=new Object[shList.size()][headO.length];
		for (int i=0;i<shList.size();i++){
			data[i][0]=shList.get(i).getName();
            data[i][1]=shList.get(i).getBuyNum();
            data[i][2]=shList.get(i).getSumprice();
        }
        return data;
	}
	
	
	/**
	 * 更新已签收历史订单
	 */
	public void freshChOrdersTable(Map<String,Orders> mapO,String [] head,JTable table) {
		Font font = new Font("宋体",Font.PLAIN,20);
		table.setModel(new DefaultTableModel(OrdersCheckedData(mapO,head),head));//形成表格
		table.setFont(font);//设置字体
		table.setRowHeight(25);//设置行高
		JTableHeader tableHead = table.getTableHeader(); // 创建表格标题对象
		tableHead.setPreferredSize(new Dimension(tableHead.getWidth(), 35));// 设置表头大小
		tableHead.setFont(new Font("黑体", Font.PLAIN, 20));// 设置表格字体
		tableHead.setForeground(new Color(102, 51, 0));
		tableHead.setBackground(new Color(102, 153, 153)); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
	        for (int i = 0; i < head.length; i++) {
	        	TableColumn column = table.getColumnModel().getColumn(i);
	        	if (i == 0) {
	                column.setPreferredWidth(160);
	            }
	            if (i == 1) {
	                column.setPreferredWidth(400);
	            }
	            if (i == 2) {
	                column.setPreferredWidth(250);
	            }
	        }
	}
	
	//更新未签收历史订单
	public void freshUnChOrdersTable(Map<String,Orders> mapO,String [] head,JTable table) {
		Font font = new Font("宋体",Font.PLAIN,20);
		table.setModel(new DefaultTableModel(OrdersUnCheckedData(mapO,head),head));//形成表格
		table.setFont(font);//设置字体
		table.setRowHeight(25);//设置行高
		JTableHeader tableHead = table.getTableHeader(); // 创建表格标题对象
		tableHead.setPreferredSize(new Dimension(tableHead.getWidth(), 35));// 设置表头大小
		tableHead.setFont(new Font("黑体", Font.PLAIN, 20));// 设置表格字体
		tableHead.setForeground(new Color(102, 51, 0));
		tableHead.setBackground(new Color(102, 153, 153)); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
	        for (int i = 0; i < head.length; i++) {
	        	TableColumn column = table.getColumnModel().getColumn(i);
	        	if (i == 0) {
	                column.setPreferredWidth(160);
	            }
	            if (i == 1) {
	                column.setPreferredWidth(450);
	            }
	            if (i == 2) {
	                column.setPreferredWidth(250);
	            }
	        }
	}
	
	//更新购物车表
	public void freshCarTable(List<shCar> shList,String [] head,JTable table) {
		Font font = new Font("宋体",Font.PLAIN,20);
		table.setModel(new DefaultTableModel(shCarData(shList,head),head));//形成表格
		table.setFont(font);//设置字体
		table.setRowHeight(25);//设置行高
		JTableHeader tableHead = table.getTableHeader(); // 创建表格标题对象
		tableHead.setPreferredSize(new Dimension(tableHead.getWidth(), 35));// 设置表头大小
		tableHead.setFont(new Font("黑体", Font.PLAIN, 20));// 设置表格字体
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
	        for (int i = 0; i < head.length; i++) {
	        	TableColumn column = table.getColumnModel().getColumn(i);
	        	if (i == 0) {
	                column.setPreferredWidth(160);
	            }
	            if (i == 1) {
	                column.setPreferredWidth(500);
	            }
	            if (i == 2) {
	                column.setPreferredWidth(300);
	            }
	        }
	}
	
	//订单签收
	public void checkOrder(Map<String,Orders> ordersMap,JTable ordersTable) {
		int row=ordersTable.getSelectedRow();//当表为空时返回-1组合在一起会越界，所以单独拿出
		System.out.println(row);
		if(row<0) {
			return ;
		}
		String a = ordersTable.getValueAt(row,0).toString();
		if(ordersMap.containsKey(a)) {//如果未签收
			ordersMap.get(a).flag=1;
		}
	}

	//提交订单
	public void putInOrd(Map<String,Orders> mapO,List<shCar> shList,String address) {
		//订单为空直接跳过
		if(shList.isEmpty()) {
			System.out.println("空购物车");
			return ;
		}
		String id;//订单号
		String information=new String();//订单信息
		double sumPrice=0;//金额
		Orders ord;//临时记录本次订单信息
		shCar car;
		for(int i=0;i<shList.size();i++) {
			car=shList.get(i);
			information+=car.getName()+"  "+car.getBuyNum()+"  件";
			sumPrice+=car.getSumprice();
		}
		
		//修改用户信息
		this.consume+=discount(sumPrice);//加上实际金额
		this.count++;
		id=this.getId()+"|"+this.count;
		ord=new Orders(id,information,0,sumPrice,address);
		mapO.put(id, ord);
		
		//todo
	}
	
	
	//购买,参数map，商品名，数量，返回价格
	public double buy(int num,JTable ordersTable,Map<String,Goods> goods,List<shCar> shList) {
		String a = ordersTable.getValueAt(ordersTable.getSelectedRow(),0).toString();
		String name;
		double price=0;
		if(goods.containsKey(a) && goods.get(a).getNumber()>=num) {//货物选中正确且存量充足
			name=a;
			//创建car实例
			price=goods.get(a).calculate(num);//price代表这一项总价
			shCar car=new shCar(name,num,price);
			shList.add(car);
			//货物数量减小
			int history=goods.get(a).getHistory();
			int lastNum=goods.get(a).getNumber();
			goods.get(a).setNumber(lastNum-num);
			goods.get(a).setHistory(history+num);
		}
		else {
			
			JDialog wo=new JDialog();
			wo.setAlwaysOnTop(true);
			wo.setTitle("添加失败");
			wo.setBounds(100, 100, 600, 200);
			wo.setModal(true);
			wo.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			wo.getContentPane().setLayout(null);
			JLabel wornO = new JLabel("选择错误或数量不足!");
			wornO.setFont(new Font("黑体", Font.PLAIN, 20));
			wornO.setBounds(75, 65, 250, 30);
			wo.getContentPane().add(wornO);
			wo.show();
		}
		return price;//成功则价格，失败则返回0（默认）
	}
	
	//移出购物车,返回当前购物车内物品总价
	public double delShCar(Map<String,Goods> goodsMap,JTable shCarTable,List<shCar> shList) {
		int row=shCarTable.getSelectedRow();
		if(row<0) {//表为空
			return 0;
		}
		//String a = ordersTable.getValueAt(get,0).toString();
		//归还物品
		String name;
		int returnNum;//归还数量
		int restNum;//剩余数量
		int history;//累计销售
		double sumPrice=0;//购物车物品总价
		returnNum=shList.get(row).getBuyNum();
		name=shList.get(row).getName();
		restNum=goodsMap.get(name).getNumber();
		history=goodsMap.get(name).getHistory();
		goodsMap.get(name).setHistory(history-returnNum);
		goodsMap.get(name).setNumber(returnNum+restNum);
		shList.remove(row);
		//计算价格
		for(int i=0;i<shList.size();i++) {
			sumPrice+=shList.get(i).getSumprice();
		}
		return sumPrice;
	}
		
	//更新地址
	public void newAddress(String newAdd) {
		if(newAdd.equals(null)||newAdd.length()<4) {//为空或长度不足
			return ;
		}
		this.address=newAdd;
		JDialog wo=new JDialog();
		wo.setAlwaysOnTop(true);
		wo.setTitle("修改成功");
		wo.setBounds(100, 100, 600, 200);
		wo.setModal(true);
		wo.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		wo.getContentPane().setLayout(null);
		JLabel wornO = new JLabel("修改成功");
		wornO.setFont(new Font("黑体", Font.PLAIN, 20));
		wornO.setBounds(75, 65, 250, 30);
		wo.getContentPane().add(wornO);
		wo.show();
	}
	
	//更新密码
	public void newPd(String old,String new1,String new2) {
		if(!old.equals(this.getPassWd()) || !new1.equals(new2)) {
			JDialog wo=new JDialog();
			wo.setAlwaysOnTop(true);
			wo.setTitle("更新失败");
			wo.setBounds(100, 100, 600, 200);
			wo.setModal(true);
			wo.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			wo.getContentPane().setLayout(null);
			JLabel wornO = new JLabel("原密码输入错误或两次新密码输入不符");
			wornO.setFont(new Font("黑体", Font.PLAIN, 20));
			wornO.setBounds(75, 65, 400, 30);
			wo.getContentPane().add(wornO);
			wo.show();
		}
		else {
			this.setPassWd(new1);
			JDialog wo=new JDialog();
			wo.setAlwaysOnTop(true);
			wo.setTitle("修改成功");
			wo.setBounds(100, 100, 600, 200);
			wo.setModal(true);
			wo.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			wo.getContentPane().setLayout(null);
			JLabel wornO = new JLabel("修改成功");
			wornO.setFont(new Font("黑体", Font.PLAIN, 20));
			wornO.setBounds(75, 65, 250, 30);
			wo.getContentPane().add(wornO);
			wo.show();
			return;
		}
		
	}
	
	//get 函数
	public double getConsume() {
		return consume;
	}
	public int getCount() {
		return count;
	}
	public String getAddress() {
		return address;
	}

}
