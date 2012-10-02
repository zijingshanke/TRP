package com.fdays.tsms.transaction.dao;

import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.transaction.PaymentTool;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;
import com.fdays.tsms.transaction.PaymentToolListForm;

public class PaymentToolDAOImp extends BaseDAOSupport implements PaymentToolDAO {

	// 分页查询
	public List list(PaymentToolListForm paymentToolForm) throws AppException {
		Hql hql = new Hql();
		hql.add("from PaymentTool p where 1=1");
		if (paymentToolForm.getName() != null
				&& (!(paymentToolForm.getName().equals("")))) {
			hql.add(" and p.name like '%" + paymentToolForm.getName().trim()
					+ "%'");
		}
		if (paymentToolForm.getType() != null
				&& (!(paymentToolForm.getType().equals("")))) {
			hql.add(" and p.type=" + paymentToolForm.getType());
		}
		hql.add("and p.status not in(" + PaymentTool.STATES_1 + ")");// 过滤无效
		hql.add(" order by p.name");
		return this.list(hql, paymentToolForm);
	}

	// 查询 返回一个list集合
	public List<PaymentTool> getPaymentToolList() throws AppException {
		Hql hql = new Hql();
		hql.add("from PaymentTool where 1=1 ");
		return this.list(hql);
	}

	// 查询 返回一个list集合
	public List<PaymentTool> getValidPaymentToolList() throws AppException {
		Hql hql = new Hql();
		hql.add("from PaymentTool p where 1=1 and p.status=0");
		return this.list(hql);
	}

	// 获取支付工具list(付款/收款类型)
	public List getPaymentToolListByType(long type) throws AppException {
		Hql hql = new Hql();
		hql.add("from PaymentTool pt where 1=1 ");
		hql.add("and  exists(from Account ac where ac.tranType in(" + type
				+ ",3)  and ac.paymentTool.id=pt.id)");
		return this.list(hql);
	}

	// 删除
	public void delete(long id) throws AppException {
		if (id > 0) {
			PaymentTool paymentTool = (PaymentTool) this.getHibernateTemplate()
					.get(PaymentTool.class, new Long(id));
			this.getHibernateTemplate().delete(paymentTool);
		}
	}

	// 添加保存
	public long save(PaymentTool paymentTool) throws AppException {
		this.getHibernateTemplate().save(paymentTool);
		return paymentTool.getId();
	}

	// 修改
	public long update(PaymentTool paymentTool) throws AppException {
		if (paymentTool.getId() > 0) {
			this.getHibernateTemplate().update(paymentTool);
			return paymentTool.getId();
		} else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	// 根据id查询
	public PaymentTool getPaymentToolByid(long paymentToolId)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from PaymentTool p where p.id=" + paymentToolId);
		Query query = this.getQuery(hql);
		PaymentTool paymentTool = null;
		if (query != null && query.list() != null) {
			paymentTool = (PaymentTool) query.list().get(0);
		}
		return paymentTool;
	}

}
