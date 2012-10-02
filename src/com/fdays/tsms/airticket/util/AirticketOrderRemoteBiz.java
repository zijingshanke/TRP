package com.fdays.tsms.airticket.util;

import java.rmi.RemoteException;

import com.fdays.tsms.base.rmi.RemoteBiz;

public interface AirticketOrderRemoteBiz extends RemoteBiz {

	public void addAirticketOrder(long orderId) throws RemoteException;

	public boolean containsAirticketOrder(long orderId) throws RemoteException;

	public void removeAirticketOrder(long orderId) throws RemoteException;
}
