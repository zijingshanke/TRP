package com.fdays.tsms.airticket.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import com.fdays.tsms.base.rmi.RMINode;
import com.fdays.tsms.base.rmi.RMIServer;
/**
 * 订单库 远程方法调用,适应服务器集群
 */
public class AirticketOrderStoreRemote {

	public static void addAirticketOrder(long orderId) {
		List<RMINode> nodeList = RMINode.getRMINodeList("airticketOrder");
		for (int i = 0; i < nodeList.size(); i++) {
			RMINode rmiNode = (RMINode) nodeList.get(i);

			String rmiUrl = RMINode.getRMIURL(rmiNode);

			// 调用在RMI服务注册表中查找对象，并调用其上的方法
			AirticketOrderRemoteBiz remoteBiz;
			try {
				remoteBiz = (AirticketOrderRemoteBiz) Naming.lookup(rmiUrl);
				remoteBiz.addAirticketOrder(orderId);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
				try {
					remoteBiz=new AirticketOrderRemoteBizImp();
					System.out.println(rmiUrl+"访问异常,手动启动RMI监听");
					RMIServer.startListener(rmiNode, remoteBiz);
				} catch (RemoteException e1) {					
					e1.printStackTrace();
				}	
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean containsAirticketOrder(long orderId) {
		boolean result = false;

		List<RMINode> nodeList = RMINode.getRMINodeList("airticketOrder");
		for (int i = 0; i < nodeList.size(); i++) {
			RMINode rmiNode = (RMINode) nodeList.get(i);
			String rmiUrl = RMINode.getRMIURL(rmiNode);

			// 调用在RMI服务注册表中查找对象，并调用其上的方法
			AirticketOrderRemoteBiz remoteBiz;
			try {
				remoteBiz = (AirticketOrderRemoteBiz) Naming.lookup(rmiUrl);
				result = remoteBiz.containsAirticketOrder(orderId);
			} catch (MalformedURLException e) {
				System.out.println("URL格式错误");
				e.printStackTrace();					
			} catch (RemoteException e) {
				System.out.println("创建远程对象发生异常");
				e.printStackTrace();				
				try {
					remoteBiz=new AirticketOrderRemoteBizImp();
					System.out.println(rmiUrl+"访问异常,手动启动RMI监听");
					RMIServer.startListener(rmiNode, remoteBiz);
				} catch (RemoteException e1) {					
					e1.printStackTrace();
				}			
			} catch (NotBoundException e) {
				System.out.println("没有找到远程对象");
				e.printStackTrace();
			}

			if (result == true) {
				return true;
			}
		}
		return result;
	}	

	public static void removeAirticketOrder(long orderId) {
		try {
			List<RMINode> nodeList = RMINode.getRMINodeList("airticketOrder");
			for (int i = 0; i < nodeList.size(); i++) {
				RMINode rmiNode = (RMINode) nodeList.get(i);

				String rmiUrl = RMINode.getRMIURL(rmiNode);

				// 调用在RMI服务注册表中查找对象，并调用其上的方法
				AirticketOrderRemoteBiz remoteBiz = (AirticketOrderRemoteBiz) Naming
						.lookup(rmiUrl);

				remoteBiz.removeAirticketOrder(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
