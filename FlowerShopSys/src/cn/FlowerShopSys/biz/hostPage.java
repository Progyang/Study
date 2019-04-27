package cn.FlowerShopSys.biz;

import javax.swing.ButtonGroup;
import javax.swing.JTable;

import cn.FlowerShopSys.entity.Customs;
import cn.FlowerShopSys.entity.Goods;
import cn.FlowerShopSys.entity.Host;
import cn.FlowerShopSys.entity.Orders;
import cn.FlowerShopSys.entity.People;
import cn.FlowerShopSys.service.Window;

import java.util.Map;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
/**
 * @author 杨欣禹
 *  于2018_7_16至2018_7_18
 */
public class hostPage implements Window{
	
	public JDialog frame;
	private JTabbedPane tabbedPane;
	private JPanel putOn;
	private JPanel pullOff;
	private JPanel fresh;
	private JPanel checkOrders;
	private JTable goodsTable;
	private JRadioButton flowers;
	private JRadioButton bouquets;
	private JRadioButton baskets;
	private JTextField newName;
	private JTextField newPrice;
	private JTextField newNumber;
	private JTextField newSpecial;
	private JTable goodsTable_1;
	private JTable goodsTable_2;
	private JLabel lplfresh;
	private JTextField num;
	private JTable ordersTable;
	private JLabel lbldeleteOrder;
	private JButton btnDeleteOrder;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JPanel historyTable;
	private JTable hisTable;
	private JScrollPane scrollPane_4;
	private JTextField history;
	/*
	 *
	 * Create the application.
	 */
	public hostPage(Map<String, Goods> mapG,Map<String, Orders> mapO,Host hostDo) {
		initializeHost(mapG,mapO,hostDo);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initializeHost(Map<String, Goods> mapG, Map<String, Orders> mapO,Host hostDo) {
		Font font = new Font("黑体",Font.PLAIN,20);
		Font font1 = new Font("宋体",Font.PLAIN,20);
		frame = new JDialog();
		frame.setAlwaysOnTop(true);
		frame.setTitle("花店管理系统");
		frame.setResizable(false);
		frame.setFont(font);
		frame.setBounds(100, 100, 1238, 658);
		frame.setModal(true);
		frame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		tabbedPane.setForeground(new Color(153, 0, 0));
		tabbedPane.setFont(font);
		tabbedPane.setBounds(0, 0, 1222, 576);
		frame.getContentPane().add(tabbedPane);
		
		//上架标签页
		putOn = new JPanel();
		putOn.setForeground(new Color(0, 0, 0));
		tabbedPane.addTab("上架新品", null, putOn, null);
		putOn.setLayout(null);
		String head[]=new String[] { "货品名称", "单价", "库存","组成/高度 ","已售出"};
		scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(102, 153, 153));
		scrollPane.setForeground(new Color(102, 51, 0));
		scrollPane.setBounds(10, 13, 843, 539);
		scrollPane.setFont(new Font("宋体", Font.PLAIN, 25));
		putOn.add(scrollPane);
		
		goodsTable = new JTable();
		hostDo.freshTable(mapG,head,goodsTable);
		scrollPane.setViewportView(goodsTable);
		
		
		//判断类型
		JLabel lblGoodsType = new JLabel("请选择上架商品类型");
		lblGoodsType.setForeground(new Color(153, 51, 0));
		lblGoodsType.setFont(font);
		lblGoodsType.setBounds(867, 18, 221, 28);
		putOn.add(lblGoodsType);
		
		flowers = new JRadioButton("花");
		flowers.setForeground(new Color(153, 51, 0));
		flowers.setFont(font);
		flowers.setSelected(true);
		flowers.setBounds(867, 55, 60, 28);
		putOn.add(flowers);
		
		bouquets = new JRadioButton("花束");
		bouquets.setForeground(new Color(153, 51, 0));
		bouquets.setFont(font);
		bouquets.setBounds(933, 55, 77, 28);
		putOn.add(bouquets);
		
		baskets = new JRadioButton("花篮");
		baskets.setForeground(new Color(153, 51, 0));
		baskets.setFont(font);
		baskets.setBounds(1016, 55, 81, 28);
		putOn.add(baskets);
		
		// 创建一个按钮组
		ButtonGroup btnGroup = new ButtonGroup();

		// 添加单选按钮到按钮组
		btnGroup.add(flowers);
		btnGroup.add(bouquets);
		btnGroup.add(baskets);
		
		
		//输入名称
		JLabel lblnewName = new JLabel("输入需要上架的商品名称:");
		lblnewName.setForeground(new Color(153, 51, 0));
		lblnewName.setFont(font);
		lblnewName.setBounds(867, 92, 252, 31);
		putOn.add(lblnewName);
		
		newName = new JTextField();
		newName.setForeground(new Color(153, 51, 0));
		newName.setFont(font1);
		newName.setBounds(867, 136, 238, 42);
		putOn.add(newName);
		newName.setColumns(10);
		
		//输入单价
		JLabel lblnewPrice = new JLabel("输入需要上架的商品单价:");
		lblnewPrice.setForeground(new Color(153, 51, 0));
		lblnewPrice.setFont(font);
		lblnewPrice.setBounds(867, 191, 238, 31);
		putOn.add(lblnewPrice);
		
		newPrice = new JTextField();
		newPrice.setForeground(new Color(153, 51, 0));
		newPrice.setFont(font1);
		newPrice.setColumns(10);
		newPrice.setBounds(867, 235, 185, 42);
		putOn.add(newPrice);
		
		JLabel Dw = new JLabel("元");
		Dw.setForeground(new Color(153, 51, 0));
		Dw.setFont(font);
		Dw.setBounds(1054, 235, 51, 42);
		putOn.add(Dw);
		
		
		//输入数量
		JLabel lblnewNumber = new JLabel("输入需要上架的商品数量:");
		lblnewNumber.setForeground(new Color(153, 51, 0));
		lblnewNumber.setFont(font);
		lblnewNumber.setBounds(867, 290, 238, 28);
		putOn.add(lblnewNumber);
		
		newNumber = new JTextField();
		newNumber.setForeground(new Color(153, 51, 0));
		newNumber.setFont(font1);
		newNumber.setColumns(10);
		newNumber.setBounds(867, 331, 145, 42);
		putOn.add(newNumber);
		
		JLabel Dw2 = new JLabel("朵/捧/个");
		Dw2.setForeground(new Color(153, 51, 0));
		Dw2.setFont(new Font("黑体", Font.PLAIN, 20));
		Dw2.setBounds(1016, 331, 92, 42);
		putOn.add(Dw2);
		
		//最后一个文本框
		JLabel lblnewSpecial = new JLabel("单纯上架花朵无需填此项");
		lblnewSpecial.setForeground(new Color(153, 51, 0));
		lblnewSpecial.setFont(font);
		lblnewSpecial.setBounds(867, 398, 230, 28);
		putOn.add(lblnewSpecial);
		
		flowers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblnewSpecial.setText("单纯上架花朵无需填此项");
			}
		});
		bouquets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblnewSpecial.setText("需要上架的花束组成:");
			}
		});
		baskets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblnewSpecial.setText("需要上架的花篮高度(cm):");
			}
		});
		
		newSpecial = new JTextField();
		newSpecial.setForeground(new Color(153, 51, 0));
		newSpecial.setColumns(10);
		newSpecial.setFont(font1);
		newSpecial.setBounds(867, 439, 238, 42);
		putOn.add(newSpecial);
		
		//获取文本框内信息并将数据存进泛型集合
		//用于响应上架按钮
		JButton btnNewGoods = new JButton("确认");
		btnNewGoods.setForeground(new Color(153, 51, 0));
		btnNewGoods.setFont(font);
		btnNewGoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int goodsType=0;
				if(bouquets.isSelected()) {
					goodsType=1;
				}
				else if(baskets.isSelected()) {
					goodsType=2;
				}
				else {
					goodsType=0;
				}
				String name=newName.getText();
				double price=Double.parseDouble(newPrice.getText());
				int number=Integer.parseInt(newNumber.getText());
				int history=0;
				String special=newSpecial.getText();
				hostDo.putOn(mapG, name, price, number, history, special, goodsType);
				hostDo.freshTable(mapG,head,goodsTable);
				hostDo.freshTable(mapG,head,goodsTable_1);
				hostDo.freshTable(mapG,head,goodsTable_2);
				newName.setText(null);
				newPrice.setText(null);
				newNumber.setText(null);
				newSpecial.setText(null);
			}
		});
		btnNewGoods.setBounds(867, 515, 111, 34);
		putOn.add(btnNewGoods);
		
		JButton btnNotNew = new JButton("取消");
		btnNotNew.setForeground(new Color(153, 51, 0));
		btnNotNew.setFont(font);
		btnNotNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newName.setText(null);
				newPrice.setText(null);
				newNumber.setText(null);
				newSpecial.setText(null);
			}
		});
		btnNotNew.setBounds(995, 515, 111, 34);
		putOn.add(btnNotNew);
		
		
		//下架标签页
		pullOff = new JPanel();
		tabbedPane.addTab("下架货品", null, pullOff, null);
		pullOff.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 1095, 480);
		pullOff.add(scrollPane_1);
		
		
		goodsTable_1 = new JTable();
		scrollPane_1.setViewportView(goodsTable_1);
		hostDo.freshTable(mapG,head,goodsTable_1);
		
		
		JLabel lblpullOff = new JLabel("请选中想要下架的货品,然后点击下架按钮");
		lblpullOff.setForeground(new Color(153, 51, 0));
		lblpullOff.setFont(font);
		lblpullOff.setBounds(10, 503, 538, 43);
		pullOff.add(lblpullOff);
		
		JButton btnpullOff = new JButton("下架");
		btnpullOff.setForeground(new Color(153, 51, 0));
		btnpullOff.setFont(font);
		btnpullOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hostDo.pullOff(mapG, goodsTable_1);
				hostDo.freshTable(mapG,head,goodsTable);
				hostDo.freshTable(mapG,head,goodsTable_1);
				hostDo.freshTable(mapG,head,goodsTable_2);
		
			}
		});
		btnpullOff.setBounds(923, 503, 182, 43);
		pullOff.add(btnpullOff);
		
		
		
		//进货标签页
		fresh = new JPanel();
		tabbedPane.addTab("补充货物", null, fresh, null);
		fresh.setLayout(null);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 10, 1095, 483);
		fresh.add(scrollPane_2);
		
		goodsTable_2 = new JTable();
		scrollPane_2.setViewportView(goodsTable_2);
		hostDo.freshTable(mapG,head,goodsTable_2);
		
		
		lplfresh = new JLabel("请选择需要补货的货物,输入进货数量后点击补货.                    数量:");
		lplfresh.setForeground(new Color(153, 51, 0));
		lplfresh.setFont(font);
		lplfresh.setBounds(0, 506, 696, 40);
		fresh.add(lplfresh);
		
		num = new JTextField();
		num.setForeground(new Color(153, 51, 0));
		num.setBounds(710, 506, 166, 40);
		num.setFont(font1);
		fresh.add(num);
		num.setColumns(10);
		
		JButton btnfresh = new JButton("补货");
		btnfresh.setForeground(new Color(153, 51, 0));
		btnfresh.setFont(font);
		btnfresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hostDo.fresh(mapG, goodsTable_2, num);
				hostDo.freshTable(mapG,head,goodsTable);
				hostDo.freshTable(mapG,head,goodsTable_1);
				hostDo.freshTable(mapG,head,goodsTable_2);
				num.setText(null);
				hostDo.Tsk("补货成功!");
			}
		});
		btnfresh.setBounds(939, 506, 166, 40);
		fresh.add(btnfresh);
		
		
		
		//订单管理标签页
		checkOrders = new JPanel();
		tabbedPane.addTab("订单管理", null, checkOrders, null);
		checkOrders.setLayout(null);
		String headO[]=new String[] { "ID", "订单信息", "地址","总价","完成情况"};
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 10, 1095, 486);
		checkOrders.add(scrollPane_3);
		
		
		//订单列表
		ordersTable = new JTable();
		scrollPane_3.setViewportView(ordersTable);
		hostDo.freshOrdersTable(mapO,headO,ordersTable);
		
		lbldeleteOrder = new JLabel("可选择订单进行删除处理(即结账)");
		lbldeleteOrder.setForeground(new Color(153, 51, 0));
		lbldeleteOrder.setFont(font);
		lbldeleteOrder.setBounds(14, 514, 681, 32);
		checkOrders.add(lbldeleteOrder);
		
		btnDeleteOrder = new JButton("删除订单");
		btnDeleteOrder.setForeground(new Color(153, 51, 0));
		btnDeleteOrder.setFont(font);
		btnDeleteOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hostDo.checkOrder(mapO, ordersTable);
				hostDo.freshOrdersTable(mapO,headO,ordersTable);
				//TODO
			}
		});
		btnDeleteOrder.setBounds(910, 502, 195, 44);
		checkOrders.add(btnDeleteOrder);
		
		
		//销量榜单方法
		historyTable = new JPanel();
		tabbedPane.addTab("销量榜单", null, historyTable, null);
		historyTable.setLayout(null);
		
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(14, 5, 1091, 502);
		historyTable.add(scrollPane_4);
		
		hisTable = new JTable();
		scrollPane_4.setViewportView(hisTable);
		hostDo.historyOrder(mapG,head,hisTable);
		
		JLabel lblfirst = new JLabel("点击按钮,查看人气商品或气人商品");
		lblfirst.setForeground(new Color(102, 51, 0));
		lblfirst.setFont(font);
		lblfirst.setBounds(14, 517, 337, 32);
		historyTable.add(lblfirst);
		
		history = new JTextField();
		history.setForeground(new Color(153, 51, 0));
		history.setBounds(387, 518, 256, 32);
		historyTable.add(history);
		history.setColumns(10);
		history.setFont(font1);
		
		JButton btnlast = new JButton("查看销量最少商品");
		btnlast.setForeground(new Color(102, 102, 255));
		btnlast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = hisTable.getValueAt(hisTable.getRowCount()-1,0).toString();
				history.setText(a);
				hostDo.historyOrder(mapG,head,hisTable);
			}
		});
		btnlast.setFont(font);
		btnlast.setBounds(903, 517, 202, 32);
		historyTable.add(btnlast);
		
		JButton btnfirst = new JButton("查看销量最多商品");
		btnfirst.setForeground(new Color(255, 0, 51));
		btnfirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String a = hisTable.getValueAt(0,0).toString();
				history.setText(a);
				hostDo.historyOrder(mapG,head,hisTable);
			}
		});
		btnfirst.setFont(font);
		btnfirst.setBounds(695, 517, 198, 32);
		historyTable.add(btnfirst);

	}

	@Override
	public void initializeCustom(Map<String, Goods> mapG, Map<String, Orders> mapO, Customs cumtomDo) {
		// TODO 自动生成的方法存根
	}
}
	
	
