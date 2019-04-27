package cn.FlowerShopSys.biz;

import javax.swing.JTable;
import cn.FlowerShopSys.entity.Customs;
import cn.FlowerShopSys.entity.Goods;
import cn.FlowerShopSys.entity.Host;
import cn.FlowerShopSys.entity.Orders;
import cn.FlowerShopSys.entity.shCar;
import cn.FlowerShopSys.service.Window;

import java.util.ArrayList;
import java.util.List;
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
import javax.swing.JPasswordField;
/**
 * @author 杨欣禹
 *  于2018_7_16
 */
public class customPage implements Window {
	
	public JDialog frame;
	private JTabbedPane tabbedPane;
	private JPanel addGoods;
	private JPanel viewShList;
	private JPanel myOrders;
	private JPanel checkOrders;
	private JTable goodsTable;
	private JTextField buyNumber;
	private JTable checkTable;
	private JLabel lblCheckOrder;
	private JButton btnCheckOrder;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_3;
	private JTextField textNewAddr;
	private JTable myOrdtable;
	private JTable shCarTable;
	private JLabel sumPriceLabel;
	private JLabel realPriceLabel;
	private JLabel sumPriceLabel_1;
	private JLabel realPriceLabel_1;
	private JLabel yuanLabel;
	private JLabel yuanLabel_1;
	private double total;//购物车金额显示记录
	private JButton delButton;
	private JTextField newAddField;
	private JButton NewAddButton;
	private JLabel lblNewLabel;
	private JPasswordField oldPasswdField;
	private JLabel lblNewLabel_1;
	private JPasswordField newPasswordField;
	private JLabel lblNewLabel_2;
	private JPasswordField newPasswordField_1;
	private JButton newPsswdButton;
	/*
	 *
	 * Create the application.
	 */
	public customPage(Map<String, Goods> mapG, Map<String, Orders> mapO,Customs person) {//多加入了一个参数
		initializeCustom(mapG,mapO,person);//改参数，传user
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initializeCustom(Map<String, Goods> mapG, Map<String, Orders> mapO,Customs person) {
		Font font = new Font("黑体",Font.PLAIN,20);
		Font font1 = new Font("宋体",Font.PLAIN,20);
		frame = new JDialog();
		frame.setAlwaysOnTop(true);
		frame.setTitle("大黑山鲜花销售中心");
		frame.setResizable(false);
		frame.setFont(font);
		frame.setBounds(100, 100, 1238, 693);
		frame.setModal(true);
		frame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		tabbedPane.setFont(font);
		tabbedPane.setBounds(24, 42, 1196, 572);
		tabbedPane.setForeground(new Color(153, 51, 0));
		frame.getContentPane().add(tabbedPane);
		
		//查看货物标签页
		addGoods = new JPanel();
		tabbedPane.addTab("购买商品", null, addGoods, null);
		String head[]=new String[] { "货品名称", "单价", "库存","组成/高度 ","累计销售"};
		String headCar[]=new String[] { "名称","数量","总价"};//购物车表头
		String headO[]=new String[] { "ID", "订单信息", "地址","总价","完成情况"};//订单签收表头
		addGoods.setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 13, 843, 539);
		addGoods.setForeground(new Color(153, 51, 0));
		scrollPane.setFont(new Font("宋体", Font.PLAIN, 25));
		addGoods.add(scrollPane);
		
		goodsTable = new JTable();
		person.freshTable(mapG,head,goodsTable);
		goodsTable.setBackground(Color.WHITE); 
		scrollPane.setViewportView(goodsTable);
		
		
		//建立物件组
		List<shCar> shList=new ArrayList<shCar>();
		
		//（购物车）
		//List<shCar> shList=new ArrayList<shCar>();移到上面
		viewShList = new JPanel();
		tabbedPane.addTab("购物车", null, viewShList, null);
		viewShList.setLayout(null);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(14, 13, 825, 276);
		viewShList.add(scrollPane_1);
		shCarTable = new JTable();//购物车内信息表
		//编写购物车表信息
		person.freshCarTable(shList,headCar,shCarTable);
		scrollPane_1.setViewportView(shCarTable);
		
		//输入新地址文本框
		textNewAddr = new JTextField();
		textNewAddr.setBounds(123, 503, 335, 35);
		viewShList.add(textNewAddr);
		textNewAddr.setColumns(10);
		textNewAddr.setForeground(new Color(153, 51, 0));
		
		JRadioButton rdbtnNewAddress = new JRadioButton("启用新地址");
		rdbtnNewAddress.setBounds(492, 506, 157, 27);
		rdbtnNewAddress.setForeground(new Color(153, 51, 0));
		viewShList.add(rdbtnNewAddress);
		
		//默认收货地址标签
		JLabel lblDefaultAddr = new JLabel(person.getAddress());
		lblDefaultAddr.setFont(new Font("黑体", Font.PLAIN, 20));
		lblDefaultAddr.setBounds(123, 455, 320, 28);
		lblDefaultAddr.setForeground(new Color(153, 51, 0));
		viewShList.add(lblDefaultAddr);
		
		JLabel lblDefaultAddr0 = new JLabel("默认地址：");
		lblDefaultAddr0.setFont(new Font("黑体", Font.PLAIN, 20));
		lblDefaultAddr0.setBounds(14, 452, 102, 35);
		lblDefaultAddr0.setForeground(new Color(153, 51, 0));
		viewShList.add(lblDefaultAddr0);
		
		
		JLabel lblAddress = new JLabel("收货地址：");
		lblAddress.setBounds(14, 492, 128, 50);
		lblAddress.setForeground(new Color(153, 51, 0));
		lblAddress.setFont(font);
		viewShList.add(lblAddress);
		
		JButton btnMakeOrd = new JButton("提交订单");
		btnMakeOrd.setBounds(900, 499, 158, 39);
		btnMakeOrd.setForeground(new Color(153, 51, 0));
		btnMakeOrd.setFont(font);
		btnMakeOrd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addr=person.getAddress();//默认地址
				//判断是否启用新地址
				if(rdbtnNewAddress.isSelected()) {
					addr=textNewAddr.getText();
				}
				person.putInOrd(mapO, shList, addr);
				//删除列表所有信息
				shList.clear();
				person.freshCarTable(shList, headCar, shCarTable);//刷新购物车
				person.freshUnChOrdersTable(mapO,headO,checkTable);//刷新订单签收
				//hostDo.freshTable(mapG,head,goodsTable_2);
		
			}
		});
		viewShList.add(btnMakeOrd);
		
		sumPriceLabel = new JLabel("总价格：");
		sumPriceLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		sumPriceLabel.setBounds(581, 317, 80, 35);
		sumPriceLabel.setForeground(new Color(153, 51, 0));
		viewShList.add(sumPriceLabel);
		
		realPriceLabel = new JLabel("折扣价：");
		realPriceLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		realPriceLabel.setBounds(581, 366, 84, 35);
		realPriceLabel.setForeground(new Color(153, 51, 0));
		viewShList.add(realPriceLabel);
				
		
		//动态价格标签，默认初始为0
		sumPriceLabel_1 = new JLabel("0");
		sumPriceLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		sumPriceLabel_1.setBounds(675, 320, 95, 28);
		sumPriceLabel_1.setForeground(new Color(153, 51, 0));
		viewShList.add(sumPriceLabel_1);
		
		realPriceLabel_1 = new JLabel("0");
		realPriceLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		realPriceLabel_1.setBounds(675, 369, 91, 28);
		realPriceLabel_1.setForeground(new Color(153, 51, 0));
		viewShList.add(realPriceLabel_1);
		
		yuanLabel = new JLabel("元");
		yuanLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		yuanLabel.setBounds(787, 325, 72, 18);
		yuanLabel.setForeground(new Color(153, 51, 0));
		viewShList.add(yuanLabel);
		
		yuanLabel_1 = new JLabel("元");
		yuanLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		yuanLabel_1.setBounds(787, 374, 72, 18);
		yuanLabel_1.setForeground(new Color(153, 51, 0));
		viewShList.add(yuanLabel_1);
		
		
		//输入名称
		JLabel lblnewName = new JLabel("点击商品行称选中");
		lblnewName.setBounds(867, 258, 218, 31);
		lblnewName.setForeground(new Color(153, 51, 0));
		lblnewName.setFont(font);
		addGoods.add(lblnewName);
		
		
		//输入数量标签
		JLabel lblnewNumber = new JLabel("输入需要加入购物车数量:");
		lblnewNumber.setBounds(867, 336, 239, 28);
		lblnewNumber.setForeground(new Color(153, 51, 0));
		lblnewNumber.setFont(font);
		addGoods.add(lblnewNumber);
		
		//获取输入的购买数量
		buyNumber = new JTextField();
		buyNumber.setBounds(867, 405, 145, 42);
		buyNumber.setForeground(new Color(153, 51, 0));
		buyNumber.setFont(font1);
		buyNumber.setColumns(10);
		addGoods.add(buyNumber);
		
		JLabel Dw2 = new JLabel("件");
		Dw2.setBounds(1040, 405, 39, 42);
		Dw2.setForeground(new Color(153, 51, 0));
		Dw2.setFont(font1);
		addGoods.add(Dw2);
		
		//获取文本框内信息并将数据存进泛型集合
		//用于响应上架按钮
		JButton btnAddGoods = new JButton("确认");
		btnAddGoods.setBounds(867, 515, 111, 34);
		btnAddGoods.setForeground(new Color(153, 51, 0));
		btnAddGoods.setFont(font);
		//点击后所执行的操作*****
		btnAddGoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int number=Integer.parseInt(buyNumber.getText());
				total+=person.buy(number, goodsTable, mapG, shList);//记录购物车金额
				person.freshTable(mapG,head,goodsTable);
				sumPriceLabel_1.setText(String.valueOf(total));//刷新原价标签
				double real=person.discount(total);
				realPriceLabel_1.setText(String.valueOf(real));//刷新实际价格
				System.out.println("hit botton");
				person.freshCarTable(shList,headCar,shCarTable);//刷新购物车
				buyNumber.setText(null);
			}
		});
		addGoods.add(btnAddGoods);
		
		JButton btnNotNew = new JButton("取消");
		btnNotNew.setBounds(995, 515, 111, 34);
		btnNotNew.setForeground(new Color(153, 51, 0));
		btnNotNew.setFont(font);
		btnNotNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyNumber.setText(null);
			}
		});
		addGoods.add(btnNotNew);
		
		//移出购物车逻辑
		delButton = new JButton("移出购物车");
		delButton.setFont(new Font("黑体", Font.PLAIN, 20));
		delButton.setBounds(923, 246, 135, 43);
		delButton.setForeground(new Color(153, 51, 0));
		viewShList.add(delButton);
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				total=person.delShCar(mapG, shCarTable, shList);//返回总价
				//刷新表格
				person.freshCarTable(shList, headCar, shCarTable);
				person.freshTable(mapG, head, goodsTable);
				//刷新价格
				sumPriceLabel_1.setText(String.valueOf(total));
				realPriceLabel_1.setText(String.valueOf(person.discount(total)));
			}
		});
		
		
		
		//进货标签页
		myOrders = new JPanel();
		tabbedPane.addTab("历史订单", null, myOrders, null);
		myOrders.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(14, 13, 1067, 400);
		scrollPane_2.setForeground(new Color(153, 51, 0));
		myOrders.add(scrollPane_2);
		//所有已签收订单列表
		myOrdtable = new JTable();
		scrollPane_2.setViewportView(myOrdtable);
		person.freshChOrdersTable(mapO,headO,myOrdtable);
		
		
		
		//订单管理标签页
		checkOrders = new JPanel();
		tabbedPane.addTab("订单签收", null, checkOrders, null);
		checkOrders.setLayout(null);
		//String headO[]=new String[] { "ID", "订单信息", "地址","总价","完成情况"};
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 10, 1071, 486);
		scrollPane_3.setForeground(new Color(153, 51, 0));
		checkOrders.add(scrollPane_3);
		
		
		//未完成订单列表
		checkTable = new JTable();
		scrollPane_3.setViewportView(checkTable);
		person.freshUnChOrdersTable(mapO,headO,checkTable);
		
		lblCheckOrder = new JLabel("可选择订单进行签收");
		lblCheckOrder.setFont(font);
		lblCheckOrder.setBounds(14, 514, 681, 32);
		lblCheckOrder.setForeground(new Color(153, 51, 0));
		checkOrders.add(lblCheckOrder);
		
		btnCheckOrder = new JButton("确认签收");
		btnCheckOrder.setFont(font);
		btnCheckOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				person.checkOrder(mapO, checkTable);
				//同时更新已签收和未签收订单列表
				person.freshChOrdersTable(mapO,headO,myOrdtable);
				person.freshUnChOrdersTable(mapO,headO,checkTable);
				//刷新价格标签
				total=0;
				sumPriceLabel_1.setText("0");
				realPriceLabel_1.setText("0");
			}
		});
		btnCheckOrder.setBounds(910, 502, 195, 44);
		btnCheckOrder.setForeground(new Color(153, 51, 0));
		checkOrders.add(btnCheckOrder);
		
		
		//修改个人信息
		
		JPanel message = new JPanel();
		tabbedPane.addTab("信息修改", null, message, null);
		message.setLayout(null);
		
		JLabel newAddLabel = new JLabel("默认配送地址：");
		newAddLabel.setFont(new Font("黑体", Font.PLAIN, 20));
		newAddLabel.setBounds(66, 36, 158, 47);
		newAddLabel.setForeground(new Color(153, 51, 0));
		message.add(newAddLabel);
		
		newAddField = new JTextField();
		newAddField.setBounds(287, 35, 363, 38);
		newAddField.setForeground(new Color(153, 51, 0));
		message.add(newAddField);
		newAddField.setColumns(10);
		
		NewAddButton = new JButton("确认更新");
		NewAddButton.setFont(new Font("黑体", Font.PLAIN, 20));
		NewAddButton.setBounds(696, 34, 125, 37);
		NewAddButton.setForeground(new Color(153, 51, 0));
		message.add(NewAddButton);
		
		NewAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newAdd=null;
				newAdd=newAddField.getText();
				newAdd.trim();//去掉首空格
				person.newAddress(newAdd);
				lblDefaultAddr.setText(person.getAddress());//更新购物车中的默认地址标签
			}
		});
		
		
		lblNewLabel = new JLabel("原密码：");
		lblNewLabel.setFont(new Font("黑体", Font.PLAIN, 20));
		lblNewLabel.setBounds(130, 189, 94, 32);
		lblNewLabel.setForeground(new Color(153, 51, 0));
		message.add(lblNewLabel);
		
		oldPasswdField = new JPasswordField();
		oldPasswdField.setBounds(287, 188, 349, 38);
		oldPasswdField.setForeground(new Color(153, 51, 0));
		message.add(oldPasswdField);
		
		lblNewLabel_1 = new JLabel("新密码：");
		lblNewLabel_1.setFont(new Font("黑体", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(130, 271, 94, 27);
		lblNewLabel_1.setForeground(new Color(153, 51, 0));
		message.add(lblNewLabel_1);
		
		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(287, 267, 349, 38);
		newPasswordField.setForeground(new Color(153, 51, 0));
		message.add(newPasswordField);
		
		lblNewLabel_2 = new JLabel("确认新密码：");
		lblNewLabel_2.setFont(new Font("黑体", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(104, 353, 120, 27);
		lblNewLabel_2.setForeground(new Color(153, 51, 0));
		message.add(lblNewLabel_2);
		
		newPasswordField_1 = new JPasswordField();
		newPasswordField_1.setBounds(287, 342, 349, 38);
		rdbtnNewAddress.setForeground(new Color(153, 51, 0));
		message.add(newPasswordField_1);
		
		newPsswdButton = new JButton("更新密码");
		newPsswdButton.setFont(new Font("黑体", Font.PLAIN, 20));
		newPsswdButton.setBounds(386, 430, 146, 38);
		newPsswdButton.setForeground(new Color(153, 51, 0));
		message.add(newPsswdButton);
		
		newPsswdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String oldPd=oldPasswdField.getText();
				String newPd1=newPasswordField.getText();
				String newPd2=newPasswordField_1.getText();
				person.newPd(oldPd, newPd1, newPd2);
				oldPasswdField.setText(null);
				newPasswordField.setText(null);
				newPasswordField_1.setText(null);
			}
		});
		
		
		//顶部身份标签
		JLabel welcomNewLabel = new JLabel();
		String welcome=person.welcomWd();		
		welcomNewLabel.setText(welcome);
		welcomNewLabel.setBounds(14, 0, 656, 37);
		welcomNewLabel.setForeground(new Color(153, 51, 0));
		frame.getContentPane().add(welcomNewLabel);
		
	}
	


	@Override
	public void initializeHost(Map<String, Goods> mapG, Map<String, Orders> mapO, Host hostDo) {
		// TODO 自动生成的方法存根
		
	}

	
}
