package cn.FlowerShopSys.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import cn.FlowerShopSys.entity.*;
/**
 * 数据库操作类，包含所有对数据库的操作
 * @author 程硕淇
 *
 */
public class DBWork {
	
	/**
	 * 建立连接函数操作conn
	 */
	public void connect() {
		//加载驱动
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException err) {
			err.getMessage();
			System.out.println("加载驱动失败");
		}
		
		try {
			//建立连接
			StartFrame.conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop?&useSSL=false&serverTimezone=UTC", "root", "19980905yxy");
			System.out.println("建立连接成功！");
		}catch(Exception err){
			err.printStackTrace();
			System.out.println("建立连接失败");
		}
		
	}
	
	/**
	 * 游客登陆，拥有 公共账号
	 */
	public People touLogin() {
		PreparedStatement pstmt=null;
		
		String id="**tourist**";//默认账号
		String password="0";//默认密码
		String address="请填写配送地址";//默认地址
		int count=0;//购物次数
		double consume=0;
		int rand=1;//权限等级初值为-1表示登陆不成功
		String sql="select*from users WHERE id=?";
		try {
			pstmt=StartFrame.conn.prepareStatement(sql);
			pstmt.setString(1, "**tourist**");
			ResultSet rs=pstmt.executeQuery();
			//更新count consume信息
			if(rs.next()) {
				count=rs.getInt("count");
				consume=rs.getDouble("consume");
			}
		}catch(SQLException err) {
			err.getMessage();
			System.out.println("游客信息获取失败");
		}
		People person=new Customs(id,password,rand,consume,count,address);
		return person;
	}
	
	/**
	 * 登陆系统函数,返回实例化一个people对象
	 */
	public  People login(String idd,String pass) {
//		if(idd==null&&pass==null || "".equals("")) {
//			return null;
//		}
		//Connection conn=null;
		Statement stmt=null;
		
		//People person=null;
		
		String id=null;//账号
		String password=null;//密码
		String address=null;//默认地址
		int count=0;//购物次数
		double consume=0;
		int rand=-1;//权限等级初值为-1表示登陆不成功
		
		
		try {
			//读取信息
			stmt=StartFrame.conn.createStatement();//创建statement，执行sql语句		
			int flag=0;//判断标志位,是否成功登陆
			do {
				//用户输入
				
				id=idd;
				password=pass;
				
				//ResultSet rs=stmt.executeQuery("select name form login where name='"+name+"'");
				ResultSet rs=stmt.executeQuery("select*from users");
				//int flag=0;//判断标志位,是否成功登陆
				while(rs.next()) {
					if(rs.getString("id").equals(id)) {
						if(rs.getString("password").equals(password)) {
							System.out.println("登陆成功");
							flag=1;
							rand=rs.getInt("rand");
							//读入信息
							address=rs.getString("address");
							consume=rs.getDouble("consume");
							count=rs.getInt("count");
							
							break;
						}
						//密码错误直接退出
						else {
							break;
						}
					}
				}
				if(flag==0) {
					System.out.println("用户名或密码错误");
				}
			rs.close();
			}while(flag==0);
			
			
		}catch(SQLException err) {
			err.getMessage();
			System.out.println("err2");
		}finally {
			try {
				if(stmt!=null) {
					stmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(Exception err) {
				err.getMessage();
				System.out.println("err3");
			}
		}
		//创建实例
		//依据用户权限等级，生成实例化对象
		if(rand>0) {
		People person=new Customs(id,password,rand,consume,count,address);
		return person;
		}
		else {//店长（待完成）
			People person=new Host();
			return person;
		}
		
	}

	/**
	 * 用户注册函数
	 */
	public  int register(String s1,String s2,String s4) {
		//Connection conn=null;
		PreparedStatement pstmt=null;
		int i=0;
		//记录变量
		String id;//账号
		String password;//密码
		int rand=2;//等级,客户默认从1级开始
		int count=0;//购买次数，默认0
		double consume=0;//累计消费，默认0
		String address;//默认地址
		id=s1;
		password=s2;
		address=s4;
		
		//sql待执行语句
		//账号，密码，等级，购买次数，累计消费，默认地址
		String sql="INSERT INTO users(id,password,rand,count,consume,address) VALUES(?,?,?,?,?,?)";
		try {
			//建立连接
			//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop","root",
					//"123456");
			System.out.println("连接完成");
			// 注意此处需要传sql
			pstmt = StartFrame.conn.prepareStatement(sql);
			//设置占位符对应的值：1，2，3，表示占位符的位置
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setInt(3, rand);
			pstmt.setInt(4, count);
			pstmt.setDouble(5, consume);
			pstmt.setString(6, address);
			// 注意这个地方不需要传sql
			int resultNum=pstmt.executeUpdate();
			if(resultNum>0) {
				i=1;
				System.out.println("注册成功");
				return i;
			}else {
				System.out.println("增加失败");
				return i;
			}
		}catch(Exception err) {
			err.getMessage();
		}finally {
			//资源的释放也需要存放在try语句中
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(SQLException err) {
				System.out.println("资源释放异常");
			}
		}
		return i;
	}

	/**
	 * 读入商品信息函数
	 */
	public  Map<String,Goods> putGoods(){
		//Connection conn=null;
		Statement stmt=null;
		
		//People person=null;
		Map<String,Goods> allGoods=new HashMap<String,Goods>();
		Goods good;//临时记录多态
		String name=null;//商品名称
		double price;//单价
		int number;//库存数量
		int history;//已售出
		double height;//高度
		String form;//组成

		try {
			//建立连接
			//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop", "root", "123456");
			System.out.println("建立连接成功！");
			//读取花商品信息信息
			stmt=StartFrame.conn.createStatement();//创建statement，执行sql语句
			//加入单花信息
			ResultSet rs=stmt.executeQuery("select*from flowers");
			while(rs.next()) {
				name=rs.getString("name");
				price=rs.getDouble("price");
				number=rs.getInt("number");
				history=rs.getInt("history");
				good=new Flowers(name,price,number,history);
				//加入Map中
				allGoods.put(name, good);
			}
			System.out.println("读入单花信息");
			//加入花篮信息
			rs=stmt.executeQuery("select*from baskets");
			while(rs.next()) {
				name=rs.getString("name");
				price=rs.getDouble("price");
				number=rs.getInt("number");
				history=rs.getInt("history");
				height=rs.getDouble("height");
				good=new Baskets(name,price,number,history,height);
				//加入Map中
				allGoods.put(name, good);
			}
			System.out.println("读入花篮信息");
			//加入花束信息
			rs=stmt.executeQuery("select*from bouquets");
			while(rs.next()) {
				name=rs.getString("name");
				price=rs.getDouble("price");
				number=rs.getInt("number");
				history=rs.getInt("history");
				form=rs.getString("form");
				good=new Bouquets(name,price,number,history,form);
				//加入Map中
				allGoods.put(name, good);
			}
			System.out.println("读入花束信息");
			rs.close();
			
		}catch(SQLException err) {
			err.getMessage();
			System.out.println("err2");
		}finally {
			try {
				if(stmt!=null) {
					stmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(Exception err) {
				err.getMessage();
				System.out.println("err3");
			}
		}
		//返回map
		return allGoods;
	}

	/**
	 * 读入订单信息函数
	 */
	public  Map<String,Orders> putOrders(String userId){
		//Connection conn=null;
		//Statement stmt=null;
		PreparedStatement pstmt=null;
		
		//People person=null;
		Map<String,Orders> myOrders=new HashMap<String,Orders>();
		String id;//订单id
		String information;//所含商品
		String address;//送货地址
		Orders ord=null;
		int flag;//订单是否已完成，默认false未完成
		double sumPrice;//订单总价格

		try {
			//建立连接
			//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop", "root", "123456");
			System.out.println("建立连接成功！");
			//读取花商品信息信息
			String sql="SELECT*FROM orders WHERE order_id LIKE ?";
			pstmt=StartFrame.conn.prepareStatement(sql);//创建statement，执行sql语句
			pstmt.setString(1,userId+"%");
			ResultSet rs=pstmt.executeQuery();
			System.out.println(userId);
			System.out.println(sql);
			while(rs.next()) {
				id=rs.getString("order_id");
				information=rs.getString("information");
				flag=rs.getInt("flag");
				sumPrice=rs.getDouble("sumPrice");
				address=rs.getString("address");
				System.out.println(flag);
				ord=new Orders(id,information,flag,sumPrice,address);
				//加入Map中
				myOrders.put(id, ord);
			}
			rs.close();
			
		}catch(SQLException err) {
			System.out.println("err2");
			err.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(Exception err) {
				err.getMessage();
				System.out.println("err3");
			}
		}
		//返回map
		return myOrders;
	}

	/**
	 * 读入订单所有信息函数
	 */
	public  Map<String,Orders> putAllOrders(){
		//Connection conn=null;
		//Statement stmt=null;
		PreparedStatement pstmt=null;
		
		//People person=null;
		Map<String,Orders> myOrders=new HashMap<String,Orders>();
		String id;//订单id
		String information;//所含商品
		String address;//送货地址
		Orders ord=null;
		int flag;//订单是否已完成，默认false未完成
		double sumPrice;//订单总价格
		
		Scanner input=new Scanner(System.in);
		try {
			//建立连接
			//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop", "root", "123456");
			System.out.println("建立连接成功！");
			//读取花商品信息信息
			String sql="SELECT * FROM orders ";
			pstmt=StartFrame.conn.prepareStatement(sql);//创建statement，执行sql语句
			ResultSet rs=pstmt.executeQuery();
			System.out.println(sql);
			while(rs.next()) {
				id=rs.getString("order_id");
				information=rs.getString("information");
				flag=rs.getInt("flag");
				sumPrice=rs.getDouble("sumPrice");
				address=rs.getString("address");
				System.out.println(flag);
				ord=new Orders(id,information,flag,sumPrice,address);
				//加入Map中
				myOrders.put(id, ord);
			}
			rs.close();
			
		}catch(SQLException err) {
			System.out.println("err2");
			err.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(Exception err) {
				err.getMessage();
				System.out.println("err3");
			}
		}
		//返回map
		return myOrders;
	}
	
	/**
	 * 店长更新用户信息,写回数据库
	 */
	public void updtCustom(Customs cus) {
		PreparedStatement pstmt=null;
		
		
		Scanner input=new Scanner(System.in);
		try {
			//建立连接
			//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop", "root", "123456");
			System.out.println("建立连接成功！");
			//读取花商品信息信息
			String sql="UPDATE users set password=?,rand=?,consume=?,count=?,address=? where id=?";
			pstmt=StartFrame.conn.prepareStatement(sql);//创建statement，执行sql语句
			//设置占位符对应的值
			pstmt.setString(1, cus.getPassWd());
			pstmt.setInt(2, cus.getRand());
			pstmt.setDouble(3, cus.getConsume());
			pstmt.setInt(4, cus.getCount());
			pstmt.setString(5, cus.getAddress());
			pstmt.setString(6, cus.getId());
			int rs=pstmt.executeUpdate();
			if(rs<=0) {
				System.out.println("更新数据用户失败");
			}
			
		}catch(SQLException err) {
			System.out.println("err2");
			err.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(Exception err) {
				err.getMessage();
				System.out.println("err3");
			}
		}
		//返回map
	}

	/**
	 * 店长更新货物，最高权限
	 */
	public void updtHostGoods(Map<String,Goods> allGoods) {
		PreparedStatement pstmt=null;
		String sql;

		try {
			//建立连接
			//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop", "root", "123456");
			System.out.println("建立连接成功！");
			//删除所有
			sql="DELETE FROM flowers";
			pstmt=StartFrame.conn.prepareStatement(sql);
			pstmt.executeLargeUpdate();
			sql="DELETE FROM bouquets";
			pstmt=StartFrame.conn.prepareStatement(sql);
			pstmt.executeLargeUpdate();
			sql="DELETE FROM baskets";
			pstmt=StartFrame.conn.prepareStatement(sql);
			pstmt.executeLargeUpdate();
			//迭代判断,加入信息
			for(Goods g:allGoods.values()) {
				if(g instanceof Flowers) {
					sql="INSERT INTO Flowers(name,price,number,history) VALUES(?,?,?,?)";
					pstmt=StartFrame.conn.prepareStatement(sql);
					pstmt.setString(1, g.getName());
					pstmt.setDouble(2, g.getPrice());
					pstmt.setInt(3, g.getNumber());
					pstmt.setInt(4, g.getHistory());
					int rs=pstmt.executeUpdate();
					if(rs<=0) {
						System.out.println(g.getName()+"数据更新失败");
					}
				}
				else if(g instanceof Bouquets) {
					sql="INSERT INTO bouquets(name,price,number,history,form) VALUES(?,?,?,?,?)";
					pstmt=StartFrame.conn.prepareStatement(sql);
					pstmt.setString(1, g.getName());
					pstmt.setDouble(2, g.getPrice());
					pstmt.setInt(3, g.getNumber());
					pstmt.setInt(4, g.getHistory());
					//上方为通用属性
					pstmt.setString(5, ((Bouquets) g).getForm());//类型强转
					int rs=pstmt.executeUpdate();
					if(rs<=0) {
						System.out.println(g.getName()+"数据更新失败");
					}
				
				}
				else {
					sql="INSERT INTO baskets(name,price,number,history,height) VALUES(?,?,?,?,?)";
					pstmt=StartFrame.conn.prepareStatement(sql);
					pstmt.setString(1, g.getName());
					pstmt.setDouble(2, g.getPrice());
					pstmt.setInt(3, g.getNumber());
					pstmt.setInt(4, g.getHistory());
					//上方为通用属性
					pstmt.setDouble(5, ((Baskets) g).getHeight());//类型强转
					int rs=pstmt.executeUpdate();
					if(rs<=0) {
						System.out.println(g.getName()+"数据更新失败");
					}
				}
			}	
		}catch(SQLException err) {
			System.out.println("err2");
			err.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(Exception err) {
				err.getMessage();
				System.out.println("err3");
			}
		}
	}

	/**
	 * 店长更新店内订单，最高权限
	 */
	public void updtHostOrd(Map<String,Orders> allOrds) {
		PreparedStatement pstmt=null;
		String sql;

		try {
			//建立连接
			//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop", "root", "123456");
			System.out.println("建立连接成功！");
			//删除所有
			sql="DELETE FROM orders";
			pstmt=StartFrame.conn.prepareStatement(sql);
			pstmt.executeUpdate();
			//迭代判断,加入信息
			for(Orders g:allOrds.values()) {
				sql="INSERT INTO orders(order_id,information,address,flag,sumPrice) VALUES(?,?,?,?,?)";
				pstmt=StartFrame.conn.prepareStatement(sql);
				pstmt.setString(1, g.id);
				pstmt.setString(2, g.information);
				pstmt.setString(3, g.address);
				pstmt.setInt(4, g.flag);
				pstmt.setDouble(5, g.sumPrice);
				int rs=pstmt.executeUpdate();//报错了
				if(rs<=0) {
					System.out.println(g.id+"数据更新失败");
				}
			}	
		}catch(SQLException err) {
			System.out.println("err2");
			err.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(Exception err) {
				err.getMessage();
				System.out.println("err3");
			}
		}
	}
	
	
	/**
	 * 客户更新商品，写回数据库
	 */
	public void updtCusGoods(Map<String,Goods> allGoods) {
		PreparedStatement pstmt=null;
		String sql;
		Scanner input=new Scanner(System.in);
		try {
			//建立连接
			//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop", "root", "123456");
			System.out.println("建立连接成功！");
			//迭代判断,加入信息
			for(Goods g:allGoods.values()) {
				if(g instanceof Flowers) {
					sql="REPLACE INTO flowers (name,price,number,history) VALUES(?,?,?,?)";
					pstmt=StartFrame.conn.prepareStatement(sql);
					pstmt.setString(1, g.getName());
					pstmt.setDouble(2, g.getPrice());
					pstmt.setInt(3, g.getNumber());
					pstmt.setInt(4, g.getHistory());
					pstmt.execute();
				}
				else if(g instanceof Bouquets) {
					sql="REPLACE INTO bouquets (name,price,number,history,form) VALUES(?,?,?,?,?)";
					pstmt=StartFrame.conn.prepareStatement(sql);
					pstmt.setString(1, g.getName());
					pstmt.setDouble(2, g.getPrice());
					pstmt.setInt(3, g.getNumber());
					pstmt.setInt(4, g.getHistory());
					//上方为通用属性
					pstmt.setString(5, ((Bouquets) g).getForm());//类型强转
					pstmt.execute();
				}
				else {
					sql="REPLACE INTO baskets (name,price,number,history,height) VALUES(?,?,?,?,?)";
					pstmt=StartFrame.conn.prepareStatement(sql);
					pstmt.setString(1, g.getName());
					pstmt.setDouble(2, g.getPrice());
					pstmt.setInt(3, g.getNumber());
					pstmt.setInt(4, g.getHistory());
					//上方为通用属性
					pstmt.setDouble(5, ((Baskets) g).getHeight());//类型强转
					pstmt.execute();
				}
			}	
		}catch(SQLException err) {
			System.out.println("err2");
			err.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(Exception err) {
				err.getMessage();
				System.out.println("err3");
			}
		}
		//返回map
	}
	/**
	 * sql刷新购物订单
	 */
	public void insertOrd(Map<String,Orders> myOrders) {
		PreparedStatement pstmt=null;
		

		try {
			//建立连接
			//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myrshop", "root", "123456");
			System.out.println("建立连接成功！");
			//读取订单信息信息
			//value遍历
			for(Orders x:myOrders.values()) {
				String sql="REPLACE INTO orders (order_id,information,flag,sumprice,address) VALUES(?,?,?,?,?)"; 
				pstmt=StartFrame.conn.prepareStatement(sql);//创建statement，执行sql语句
				//设置占位符对应的值
				pstmt.setString(1, x.id);
				pstmt.setString(2, x.information);
				pstmt.setInt(3, x.flag);
				pstmt.setDouble(4, x.sumPrice);
				pstmt.setString(5, x.address);
				pstmt.execute();
			}
			
		}catch(SQLException err) {
			System.out.println("err2");
			err.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				/*
				if(conn!=null) {
					conn.close();
				}*/
			}catch(Exception err) {
				err.getMessage();
				System.out.println("err3");
			}
		}
		//返回map
	}
	
	
}


