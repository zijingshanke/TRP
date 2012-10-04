package com.fdays.tsms.airticket.util;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AirticketOrderRemoteBizImp extends UnicastRemoteObject implements
		AirticketOrderRemoteBiz {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AirticketOrderRemoteBizImp() throws RemoteException {
		super();
	}

	public String getRemoteObjName() throws RemoteException {
		String objName = "/airticketOrder";
		return objName;
	}

	public void addAirticketOrder(long orderId) throws RemoteException {
		AirticketOrderStore.addOrderString(orderId);
	}

	public boolean containsAirticketOrder(long orderId) throws RemoteException {
		boolean result = false;
		try {
			result = AirticketOrderStore
					.containsExistOrder_String(orderId + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void removeAirticketOrder(long orderId) throws RemoteException {
		AirticketOrderStore.removeOrderId(orderId);
	}
}
