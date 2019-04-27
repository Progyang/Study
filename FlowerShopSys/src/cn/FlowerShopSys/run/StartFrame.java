package cn.FlowerShopSys.run;
import javax.swing.*;

import cn.FlowerShopSys.biz.SignFrame;
import cn.FlowerShopSys.biz.customPage;
import cn.FlowerShopSys.biz.hostPage;
import cn.FlowerShopSys.entity.Customs;
import cn.FlowerShopSys.entity.Goods;
import cn.FlowerShopSys.entity.Host;
import cn.FlowerShopSys.entity.Orders;
import cn.FlowerShopSys.entity.People;

import java.awt.event.*;
import java.sql.Connection;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
/**
 * @author 马志圆
 *2018-07-15
 *官网主界面
 */
public class StartFrame extends JFrame{

    private JLabel label1;
    private JLabel label2;
    private JButton button1;//登录按钮
    private JButton button2;//注册按钮
    private JButton button3;//游客按钮
    private JTextField text1;//id输入栏
    private JPasswordField pField;//密码输入栏
    DBWork DBwork;
    People person;
    public static Connection conn;
    public StartFrame(){
    	
        super();
        setResizable(false);
        DBwork=new DBWork();
        person=null;
        DBwork.connect();
        this.setSize(300,207);
        this.getContentPane().setLayout(null);//设置布局控制器,null表示清空布局管理器
        getContentPane().add(this.getTextField(),null);//添加文本框
        getContentPane().add(this.getPasswordField(),null);
 
        getContentPane().add(this.getButton(),null);//添加按钮
        getContentPane().add(this.getButton_2(),null);
        getContentPane().add(this.getButton_3(), null);
       
        
        
        
        getContentPane().add(this.getLabel(),null);//添加标签
        JFrame.setDefaultLookAndFeelDecorated(false);
        getContentPane().add(this.getLabel_2(), null);//去掉标题栏
        this.setTitle("花店登录系统");
       // this.setUndecorated(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//一定要设置关闭
        this.setLocationRelativeTo(null); //中心显示
        this.setVisible(true);
 
    } 
   public static void main(String[] args) {
	    
           StartFrame sF=new StartFrame();
}
//		
//	}
    /**
     * 设置标签
     */
    private JLabel getLabel(){
        if(label1==null){
            label1 = new JLabel();
            label1.setFont(new Font("黑体", Font.PLAIN, 20));
            label1.setBounds(34,49,53,18);
            label1.setForeground(new Color(153, 51, 0));
            label1.setText("账号:");
            label1.setToolTipText("JLabel");
        }
        return label1;
    }
    private JLabel getLabel_2() {
    	if(label2==null) {
    		label2=new JLabel();
    		label2.setFont(new Font("黑体", Font.PLAIN, 20));
    		label2.setBounds(34, 80,53,18);
    		label2.setForeground(new Color(153, 51, 0));
    		label2.setText("密码:");
    		label2.setToolTipText("JLabel");
    	}
    	return label2;
    }
    /**
     * 设置按钮
     */
    private JButton getButton(){
        if(button1==null){
            button1 = new JButton();
            button1.setFont(new Font("黑体", Font.PLAIN, 20));
            button1.setBounds(104,131,80,27);
            button1.setForeground(new Color(153, 51, 0));
            button1.setText("登录");
            button1.setToolTipText("登录");
            button1.addActionListener(new LaunchButton());//添加监听器类，其主要的响应都由监听器类的方法实现
 
        }
        return button1;
    }
    private JButton getButton_2(){
        if(button2==null){
            button2 = new JButton();
            button2.setFont(new Font("黑体", Font.PLAIN, 20));
            button2.setBounds(194,131,80,27);
            button2.setForeground(new Color(153, 51, 0));
            button2.setText("注册");
            button2.setToolTipText("注册");
            button2.addActionListener(new SignButton());//添加监听器类，其主要的响应都由监听器类的方法实现
 
        }
        return button2;
    }
    private JButton getButton_3(){
        if(button3==null){
            button3 = new JButton();
            button3.setFont(new Font("黑体", Font.PLAIN, 20));
            button3.setBounds(10,131,80,27);
            button3.setForeground(new Color(153, 51, 0));
            button3.setText("游客");
            button3.setToolTipText("游客");
            button3.addActionListener(new PassengerButton());//添加监听器类，其主要的响应都由监听器类的方法实现
 
        }
        return button3;
    }
    /**
     * 监听器类实现ActionListener接口，主要实现actionPerformed方法
     */
    private class LaunchButton implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	String s1=text1.getText(); 	
       		String s=pField.getText();//获取文本框中输入信息
    		text1.setText(null);
    		pField.setText(null);
    		System.out.println(s1);
    		System.out.println(s);
            System.out.println("登录");
            person=DBwork.login(s1,s);
            if(person==null) {
            	JDialog jd=new JDialog();
 	    		jd.setTitle("sign");
 	    		jd.setSize(50, 130);
 	    		jd.getContentPane().setLayout(null);
 	    		JLabel jl=new JLabel("   用户名或密码错误！");  
 	            jl.setBounds(0,-20,100,100);
 	            jl.setForeground(new Color(153, 51, 0));
 	            jd.getContentPane().add(jl);
 	    		jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
 	    		jd.setLocationRelativeTo(null);
 	    		jd.setVisible(true);
            }
            else if(person.getRand()>1) {
            	Customs cus=(Customs)person;
				Map<String,Goods> allGoods=DBwork.putGoods();
				Map<String,Orders> myOrders=DBwork.putOrders(cus.getId());
				try {
					customPage window = new customPage(allGoods,myOrders,cus);
					window.frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				cus.levelUp();//升级判断
				//回写数据
				DBwork.updtCustom(cus);
				DBwork.insertOrd(myOrders);
				DBwork.updtCusGoods(allGoods);
            }
            else if(person.getRand()==0) {
            	Host host=(Host)person;
				Map<String,Goods> allGoods=DBwork.putGoods();
				Map<String,Orders> allOrders=DBwork.putAllOrders();
				try {
					hostPage window = new hostPage(allGoods,allOrders,host);
					window.frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				//写回数据
				//店长信息没有被更新
				DBwork.updtHostOrd(allOrders);
				DBwork.updtHostGoods(allGoods);
            }
            //todo 登录成功判断，如果成功弹出,根据id弹出不同的操作页面
        }
    }
    private class SignButton implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		//注册成功就弹出一个dialog提醒
    	
	       SignFrame sF=new SignFrame(); 
   		    
    		System.out.println("注册");
    	}
    }
    private class PassengerButton implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		System.out.println("游客");
    		person=DBwork.touLogin();
    		Customs cus=(Customs)person;
    		Map<String,Goods> allGoods=DBwork.putGoods();
			Map<String,Orders> myOrders=DBwork.putOrders(cus.getId());
			try {
				customPage window = new customPage(allGoods,myOrders,cus);
				window.frame.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			cus.levelUp();//升级判断
			//回写数据
			DBwork.updtCustom(cus);
			DBwork.insertOrd(myOrders);
			DBwork.updtCusGoods(allGoods);
    }
    	}
    /**
     * 设定文本域
     */
    private JTextField getTextField(){
        if(text1==null){
            text1 = new JTextField();
            text1.setFont(new Font("宋体", Font.PLAIN, 20));
            text1.setBounds(96,49,160,20);
        }
        return text1;
    }
    private JPasswordField getPasswordField() {
    	if(pField==null) {
    		pField=new JPasswordField();
    		pField.setFont(new Font("宋体", Font.PLAIN, 20));
    		pField.setBounds(96,80,160,20);
    	}
    	return pField;
    }
	public JTextField getText1() {
		return text1;
	}
	public void setText1(JTextField text1) {
		this.text1 = text1;
	}
}
