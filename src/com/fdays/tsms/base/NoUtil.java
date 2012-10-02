package com.fdays.tsms.base;

import java.util.Random;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.neza.base.Hql;
import com.neza.database.SelectDataBean;
import com.neza.tool.DateUtil;

public class NoUtil
{

	private HibernateTemplate hibernateTemplate;
	private final String whoftable[][] = { { "AirticketOrder", "airOrderNo" },
	    { "AirticketOrder", "groupMarkNo" }, { "Statement", "statementNo" } };

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
	{
		this.hibernateTemplate = hibernateTemplate;
	}

	public String getAirTicketOrderNo()
	{
		return getNewNo("O", "yyyyMMdd", 6); // O
	}

	public String getAirticketGroupNo()
	{
		return getNewNo("G", "yyyyMMdd", 6);// A
	}

	public String getStatementNo()
	{
		return getNewNo("S", "yyyyMMdd", 6);// G
	}

	public String getNewNo(String c, String pattern, int length)
	{

		String temp = c + DateUtil.getDateString(pattern);
		try
		{
			Hql hql = new Hql("select next_no from no where type='" + c + "'");
			hql.add(" and  substr(next_no,0,9)='" + temp + "'");
			SelectDataBean sdb = new SelectDataBean();
			sdb.setQuerySQL(hql.getSql());
			sdb.executeQuery();
			String oldNoticeNo = "";
			String newNumber = "";

			if (sdb == null)
			{
				return temp + "FF" + NoUtil.getRandom(5);
			}
			else if (sdb.getRowCount() < 1)
			{
				newNumber = temp + "000001";
				hql.clear();
				hql.add("insert into no(id,next_no,type) values(seq_no.nextval,'"
				    + temp + "000001" + "','" + c + "')");
				sdb.executeUpdateSQL(hql.getSql());
			}
			else
			{
				int num = 0;
				oldNoticeNo = sdb.getColValue(1, "next_no");

				if (oldNoticeNo.length() >= 9)
				{
					oldNoticeNo = oldNoticeNo.substring(9);
					num = com.neza.base.Constant.toInt(oldNoticeNo);
					if (num == 0)
						oldNoticeNo = "1";
					else
						oldNoticeNo = String.valueOf(++num);
				}
				else
					oldNoticeNo = "1";
				newNumber = temp + transValue(oldNoticeNo, length);
				hql.clear();
				hql.add("update no set next_no='" + newNumber + "' where type='" + c
				    + "'");
				sdb.executeUpdateSQL(hql.getSql());
			}

			sdb.close();

			return newNumber;
		}
		catch (Exception ex)
		{
			return temp + "0" + NoUtil.getRandom(5);
		}
	}



	public static String transValue(String value, int size)
	{
		int temp = com.neza.base.Constant.toInt(value);
		int tempNum = String.valueOf(temp).length();
		value = String.valueOf(temp);
		if (temp == 0)
			value = "1";
		for (int i = 0; i < size - tempNum; i++)
		{
			value = String.valueOf("0") + value;
		}
		return value;
	}

	private static int getNoOfArray(int[] intNo)
	{
		for (int i = 0; i < intNo.length; i++)
		{
			if (intNo[i] != intNo[0] + i)
				return intNo[0] + i;
			else
				return intNo[0] + i + 1;
		}
		return 0;
	}

	private static String getNoOfArray(String[] strNo)
	{
		try
		{
			int temp = Integer.parseInt(strNo[0]);
			for (int i = 0; i < strNo.length; i++)
			{
				if (Integer.parseInt(strNo[i]) != temp + i)
					return Integer.toString(temp + i);
			}
			return Integer.toString(temp + strNo.length);
		}
		catch (Exception ex)
		{
			return "00";
		}
	}

	public static String getRandom(int num)
	{
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < num; i++)
		{
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}

		return sRand;
	}

	public static void main(String arg[])
	{
		try
		{
			System.out.println(NoUtil.getRandom(10));
		}
		catch (Exception ex)
		{

		}
	}
}