package cn.FlowerShopSys.biz;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import cn.FlowerShopSys.run.DBWork;

import java.awt.Color;
import java.awt.Font;




/**会员注册窗口
 * @author 马志圆
 *2018-07-15
 * @param <DBwork>
 */
public class SignFrame extends JFrame {
	    private JLabel label1;//id标签
	    private JLabel label2;//密码标签
	    private JLabel label3;//确认密码标签
	    private JLabel label4;//地址标签
	    private JButton button1;//提交 按钮
	    private JTextField text1;//id输入的文本框
	    private JPasswordField text2;//密码输入的文本框
	    private JPasswordField text3;//确认密码的文本框
	    private JTextField text4;//填写地址
	    DBWork DBwork;
	    public SignFrame(){
	        super();
	        DBwork=new DBWork();
	        DBwork.connect();
	        this.setSize(427,268);
	        this.getContentPane().setLayout(null);//设置布局控制器,null表示清空布局管理器
	        getContentPane().add(this.getTextField_1(),null);//添加文本框
	        getContentPane().add(this.getTextField_2(),null);
	        getContentPane().add(this.getTextField_3(),null);
	        getContentPane().add(this.getTextField_4(),null);
	 
	        getContentPane().add(this.getButton(),null);//添加按钮
	        
	 
	        getContentPane().add(this.getLabel(),null);//添加标签
	        getContentPane().add(this.getLabel_2(), null);
	        getContentPane().add(this.getLabel_3(), null);
	        getContentPane().add(this.getLabel_4(), null);
	        this.setTitle("注册新用户");//设置窗口标题
	        this.setLocationRelativeTo(null); //中心显示
	        this.setVisible(true);//可视化
	 
	    }
	    //设置标签
	    private JLabel getLabel(){
	        if(label1==null){
	            label1 = new JLabel();
	            label1.setFont(new Font("黑体", Font.PLAIN, 20));
	            label1.setBounds(10,49,77,18);
	            label1.setForeground(new Color(153, 51, 0));
	            label1.setText("新账号:");
	            label1.setToolTipText("JLabel");
	        }
	        return label1;
	    }
	    private JLabel getLabel_2() {
	    	if(label2==null) {
	    		label2=new JLabel();
	    		label2.setFont(new Font("黑体", Font.PLAIN, 20));
	    		label2.setBounds(10, 80,77,18);
	    		label2.setForeground(new Color(153, 51, 0));
	    		label2.setText("新密码:");
	    		label2.setToolTipText("JLabel");
	    	}
	    	return label2;
	    }
	    private JLabel getLabel_3() {
	    	if(label3==null) {
	    		label3=new JLabel();
	    		label3.setFont(new Font("黑体", Font.PLAIN, 20));
	    		label3.setBounds(10, 111,94,18);
	    		label3.setForeground(new Color(153, 51, 0));
	    		label3.setText("确认密码:");
	    		label3.setToolTipText("JLabel");
	    	}
	    	return label3;
	    }
	    private JLabel getLabel_4() {
	    	if(label4==null) {
	    		label4=new JLabel();
	    		label4.setFont(new Font("黑体", Font.PLAIN, 20));
	    		label4.setBounds(10, 142,94,18);
	    		label4.setForeground(new Color(153, 51, 0));
	    		label4.setText("默认地址:");
	    		label4.setToolTipText("JLabel");
	    	}
	    	return label4;
	    }
	    //添加按钮
	    private JButton getButton(){
	        if(button1==null){
	            button1 = new JButton();
	            button1.setFont(new Font("黑体", Font.PLAIN, 20));
	            button1.setBounds(142,182,130,37);
	            button1.setForeground(new Color(153, 51, 0));
	            button1.setText("提交");
	            button1.setToolTipText("提交");
	            button1.addActionListener(new signButton());//添加监听器类，其主要的响应都由监听器类的方法实现
	 
	        }
	        return button1;
	    }
	    //监听
	    private class signButton implements ActionListener{
	        public void actionPerformed(ActionEvent e){
	        	//获取文本框中输入信息
	    		String s1=text1.getText();
	    		String s2=text2.getText();
	    		//判断两次重复还没有写
	    		String s3=text3.getText();
	    		String s4=text4.getText();
	    		if(s2.equals(s3)) {
	            System.out.println("提交");
	            //todo 注册成功判断，如果成功弹出提醒页面
	            if(DBwork.register(s1,s2,s4)==1) {
	            JDialog jd=new JDialog();
	    		jd.setTitle("sign");
	    		jd.setSize(200, 100);
	    		jd.getContentPane().setLayout(null);
	    		JLabel jl=new JLabel("            注册成功！");  
	            jl.setBounds(0,-20,100,100);
	            jl.setForeground(new Color(153, 51, 0));
	            jd.getContentPane().add(jl);
	    		jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    		jd.setLocationRelativeTo(null);
	    		jd.setVisible(true);
	    		text1.setText(null);
	    		text2.setText(null);
	    		text3.setText(null);
	    		text4.setText(null);
	    		}else {
	    			JDialog jd=new JDialog();
		    		jd.setTitle("sign");
		    		jd.setSize(200,200);
		    		jd.getContentPane().setLayout(null);
		    		JLabel jl=new JLabel("   注册失败了！");  
		            jl.setBounds(0,-20,100,100);
		            jl.setForeground(new Color(153, 51, 0));
		            jd.getContentPane().add(jl);
		    		jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		    		jd.setLocationRelativeTo(null);
		    		jd.setVisible(true);
	    		}
	    		}
	    		else {
	    			JDialog jd=new JDialog();
		    		jd.setTitle("sign");
		    		jd.setSize(200, 200);
		    		jd.getContentPane().setLayout(null);
		    		JLabel jl=new JLabel("密码不一样");  
		            jl.setBounds(0,-20,100,100);
		            jl.setForeground(new Color(153, 51, 0));
		            jd.getContentPane().add(jl);
		    		jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		    		jd.setLocationRelativeTo(null);
		    		jd.setVisible(true);
	    		} 
	    		
	    		
	        }
	    }
	    //设定文本区域
	    private JTextField getTextField_1(){
	        if(text1==null){
	            text1 = new JTextField();
	            text1.setFont(new Font("宋体", Font.PLAIN, 20));
	            text1.setBounds(106,49,254,21);
	            text1.setForeground(new Color(153, 51, 0));
	        }
	        return text1;
	    }
	    private JPasswordField getTextField_2(){
	        if(text2==null){
	            text2 = new JPasswordField();
	            text2.setFont(new Font("宋体", Font.PLAIN, 20));
	            text2.setBounds(106,80,254,20);
	            text2.setForeground(new Color(153, 51, 0));
	        }
	        return text2;
	    }
	    private JPasswordField getTextField_3(){
	        if(text3==null){
	            text3 = new JPasswordField();
	            text3.setFont(new Font("宋体", Font.PLAIN, 20));
	            text3.setBounds(106,110,254,21);
	            text3.setForeground(new Color(153, 51, 0));
	        }
	        return text3;
	    }
	    private JTextField getTextField_4(){
	        if(text4==null){
	            text4 = new JTextField();
	            text4.setFont(new Font("宋体", Font.PLAIN, 20));
	            text4.setBounds(106,140,254,21);
	            text4.setForeground(new Color(153, 51, 0));
	        }
	        return text4;
	    }
}


