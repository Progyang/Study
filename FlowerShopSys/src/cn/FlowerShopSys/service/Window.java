package cn.FlowerShopSys.service;

import java.util.Map;

import cn.FlowerShopSys.entity.Customs;
import cn.FlowerShopSys.entity.Goods;
import cn.FlowerShopSys.entity.Host;
import cn.FlowerShopSys.entity.Orders;

/**
 * 窗口接口
 *
 */
public interface Window {
	public void initializeHost(Map<String, Goods> mapG, Map<String, Orders> mapO,Host hostDo);
	public void initializeCustom(Map<String, Goods> mapG, Map<String, Orders> mapO,Customs cumtomDo);
	//待实现
}
