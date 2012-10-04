package com.fdays.tsms.right.biz;

import java.util.List;

import com.fdays.tsms.right.RoleRight;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.right.dao.RightDAO;
import com.neza.exception.AppException;

public class RightBizImpl implements RightBiz
{

	private RightDAO rightDAO;

	public void setRightDAO(RightDAO rightDAO)
	{
		this.rightDAO = rightDAO;
	}

	public void setRights(UserRightInfo uri, long userId) throws AppException
	{
		List list = rightDAO.listRoleRightsByUserId(userId);
		long b = System.currentTimeMillis();

		for (int i = 0; i < list.size(); i++)
		{
			RoleRight rr = (RoleRight) list.get(i);
			// if (rr.getRightCode().equals("se01"))
			// System.out.println("---------00-------------------------4.3==========");
			if (rr.getRightCode().equals("sa01"))
			{
				List list1 = rightDAO.listRoleRights();
				uri.clear();
				for (int j = 0; j < list1.size(); j++)
				{

					RoleRight rrx = (RoleRight) list1.get(j);
				//	if (rrx.getRightCode().equals("sb85"))
				//		System.out.println(rrx.getRightCode() + "|");
					uri.addRight(rrx.getRightCode(), rrx.getRightAction());
				}
			//	System.out.println(uri.hasRight("sb86"));
				return;
			}
			uri.addRight(rr.getRightCode(), rr.getRightAction());
		}

	}

}
