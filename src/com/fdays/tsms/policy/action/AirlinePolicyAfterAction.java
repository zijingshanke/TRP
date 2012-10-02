package com.fdays.tsms.policy.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;

import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

/**
 * AirlinePolicyAfterAction
 * @author chenqx
 *
 */
public class AirlinePolicyAfterAction extends BaseAction {
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;

	//新增操作
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirlinePolicyAfter airlinePolicyAfter = (AirlinePolicyAfter) form;
		Inform inf = new Inform();
		if(!isCheckDate(airlinePolicyAfter.getBeginDate(),airlinePolicyAfter.getEndDate())){
			inf.setMessage("增加后返政策信息出错！错误信息是：结束日期不能早于起始日期");
			inf.setForwardPage("/policy/airlinePolicyAfterList.do?thisAction=list");
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		try {
			AirlinePolicyAfter tempAirlinePolicyAfter = new AirlinePolicyAfter();
			tempAirlinePolicyAfter.setName(airlinePolicyAfter.getName());
			tempAirlinePolicyAfter.setCarrier(airlinePolicyAfter.getCarrier().toUpperCase());
			tempAirlinePolicyAfter.setBeginDate(airlinePolicyAfter.getBeginDate());
			tempAirlinePolicyAfter.setEndDate(airlinePolicyAfter.getEndDate());
			tempAirlinePolicyAfter.setMemo(airlinePolicyAfter.getMemo());
			tempAirlinePolicyAfter.setQuota(airlinePolicyAfter.getQuota());
			tempAirlinePolicyAfter.setStatus(airlinePolicyAfter.getStatus());
			inf.setMessage("成功增加后返政策信息！");
			airlinePolicyAfterBiz.save(tempAirlinePolicyAfter);
			request.setAttribute("airlinePolicyAfter", tempAirlinePolicyAfter);
			inf.setForwardPage("/policy/airlinePolicyAfterList.do?thisAction=list");
			
		} catch (Exception ex) {
			inf.setMessage("增加后返政策信息出错！错误信息：" + ex.getMessage());
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirlinePolicyAfter airlinePolicyAfter = (AirlinePolicyAfter) form;
		Inform inf = new Inform();
		if(!isCheckDate(airlinePolicyAfter.getBeginDate(),airlinePolicyAfter.getEndDate())){
			inf.setMessage("修改后返政策信息出错！错误信息：结束日期不能早于起始日期");
			inf.setForwardPage("/policy/airlinePolicyAfterList.do?thisAction=list");
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		try {
			AirlinePolicyAfter tempAirlinePolicyAfter = new AirlinePolicyAfter();
			tempAirlinePolicyAfter.setName(airlinePolicyAfter.getName());
			tempAirlinePolicyAfter.setCarrier(airlinePolicyAfter.getCarrier().toUpperCase());
			tempAirlinePolicyAfter.setBeginDate(airlinePolicyAfter.getBeginDate());
			tempAirlinePolicyAfter.setEndDate(airlinePolicyAfter.getEndDate());
			tempAirlinePolicyAfter.setMemo(airlinePolicyAfter.getMemo());
			tempAirlinePolicyAfter.setQuota(airlinePolicyAfter.getQuota());
			tempAirlinePolicyAfter.setStatus(airlinePolicyAfter.getStatus());
			tempAirlinePolicyAfter.setId(airlinePolicyAfter.getId());
			inf.setMessage("成功修改后返政策信息！");
			airlinePolicyAfterBiz.update(tempAirlinePolicyAfter);
			request.setAttribute("airlinePolicyAfter", tempAirlinePolicyAfter);
			inf.setForwardPage("/policy/airlinePolicyAfterList.do?thisAction=list");
			
		} catch (Exception ex) {
			inf.setMessage("修改后返政策信息出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 开始日期是否早于结束日期
	 * @param begin
	 * @param end
	 * @return
	 */
	private boolean isCheckDate(Timestamp begin,Timestamp end){
		if(begin == null || end == null){
			return true;
		}
		if(begin.compareTo(end) <= 0){
			return true;
		}
		return false;
	}
	//----------------------------set get-------------------------//

	public void setAirlinePolicyAfterBiz(AirlinePolicyAfterBiz airlinePolicyAfterBiz) {
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}

}