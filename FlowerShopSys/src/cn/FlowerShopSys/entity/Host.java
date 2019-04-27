package cn.FlowerShopSys.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
/**
 * @author 杨欣禹
 *  于2018_7_14
 */
public class Host extends People{
	
	public void Tsk(String worn){
		JDialog wo=new JDialog();
		wo.setAlwaysOnTop(true);
		wo.setTitle(worn);
		wo.setBounds(720, 650, 300,0);
		wo.setModal(true);
		wo.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		wo.getContentPane().setLayout(null);
		wo.setVisible(true);
		
	}
	//商品上架
	public void putOn(Map<String,Goods> goodsMap,String name,double price,int number,int history,String special,int goodsType) {
		if(goodsType==1) {
			Goods newOne = new Bouquets(name,price,number,0,special);
			goodsMap.put(newOne.getName(), newOne);
		}
		else if(goodsType==2) {
			Goods newOne = new Baskets(name,price,number,0,Double.parseDouble(special));
			goodsMap.put(newOne.getName(), newOne);
		}
		else {
			Goods newOne = new Flowers(name,price,number,0);
			goodsMap.put(newOne.getName(), newOne);
		}
		//todo
	}
	
	//商品下架
	public void pullOff(Map<String,Goods> goodsMap,JTable goodsTable_1) {
		//获取所点击的货品的name属性,可多选
		for(int i=goodsTable_1.getSelectedRows().length-1;i>=0;i--) {
			String a = goodsTable_1.getValueAt(goodsTable_1.getSelectedRows()[i],0).toString();
			if(a!=null) {
				goodsMap.remove(a);
			}
		}
		//todo
	}
	
	//进货
	public void fresh(Map<String,Goods> goodsMap,JTable goodsTable_2,JTextField num) {
		String a = goodsTable_2.getValueAt(goodsTable_2.getSelectedRow(),0).toString();
		int ago=goodsMap.get(a).getNumber();
		int newNum=ago+Integer.parseInt(num.getText());
		goodsMap.get(a).setNumber(newNum);
		//todo
	}
	
	//处理订单
	public void checkOrder(Map<String,Orders> ordersMap,JTable ordersTable) {
		String a = ordersTable.getValueAt(ordersTable.getSelectedRow(),0).toString();
		if(ordersMap.get(a).flag==1) {
			ordersMap.remove(a);
		}
		else {
			Tsk("该订单尚未完成,删除失败");
		}
		//todo
	}
	
	//销量排行
	public void historyOrder(Map<String,Goods> goodsMap,String [] headH,JTable hisTable) {
		List<Goods> list=new ArrayList<Goods>();
		for (Map.Entry<String,Goods> en:goodsMap.entrySet()){
			list.add(en.getValue());
		}
		//排序
		Collections.sort(list, new Comparator<Goods>(){  
	           /*  
	            * int compare(Student o1, Student o2) 返回一个基本类型的整型，  
	            * 返回负数表示：o1 小于o2，  
	            * 返回0 表示：o1和o2相等，  
	            * 返回正数表示：o1大于o2。  
	            */  
	         public int compare(Goods o1, Goods o2) {  
	               //按照销量大小进行降序排列  
	             if(o1.getHistory() < o2.getHistory()){  
	                  return 1;  
	             }  
	             if(o1.getHistory() == o2.getHistory()){  
	                 return 0;  
	             }  
	             return -1;  
	         }  
	    });
		Map<String,Goods> listMap=new LinkedHashMap<String,Goods>();
		for(int i=0;i<list.size();i++) {
			listMap.put(list.get(i).getName(), list.get(i));
		}
		freshTable(listMap,headH,hisTable);
	}
	//获取货物数据
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
	//获取订单数据
	public Object[][] OrdersData(Map<String,Orders> mapO,String headO[]){
		
		Object[][] data=new Object[mapO.size()][headO.length];
		int i=0;
		for (Map.Entry<String,Orders> en:mapO.entrySet()){
            data[i][0]=en.getValue().id;
            data[i][1]=en.getValue().information;
            data[i][2]=en.getValue().address;
            data[i][3]=en.getValue().sumPrice;
            if(en.getValue().flag==1) {
            	data[i][4]="已完成";
            }
            else {
            	data[i][4]="未完成";
            }
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
		tableHead.setForeground(new Color(102, 51, 0));
		tableHead.setBackground(new Color(102, 153, 153)); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
	        for (int i = 0; i < head.length; i++) {
	        	TableColumn column = table.getColumnModel().getColumn(i);
	            if (i == 3) {
	                column.setPreferredWidth(500);
	            }
	        }
	}
	//用于界面中的订单列表更新函数
	public void freshOrdersTable(Map<String,Orders> mapO,String [] head,JTable table) {
		Font font = new Font("宋体",Font.PLAIN,20);
		table.setModel(new DefaultTableModel(OrdersData(mapO,head),head));//形成表格
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
	                column.setPreferredWidth(500);
	            }
	            if (i == 2) {
	                column.setPreferredWidth(300);
	            }
	        }
	}
}
