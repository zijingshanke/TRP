package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.transaction.PlatformbankCardPaymentListForm;
import com.neza.exception.AppException;

public interface PlatformbankCardPaymentBiz {
	public void createPlaBankCardPayment(PlatformbankCardPaymentListForm pbplistForm,String sessionId)throws AppException;
	public List list(PlatformbankCardPaymentListForm pbplistForm)throws AppException;
	public ArrayList<ArrayList<Object>> getDownloadPlatformbankCardPayment(PlatformbankCardPaymentListForm pbplistForm) throws AppException;

}
