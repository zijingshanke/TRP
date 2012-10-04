package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class PlatformDAOImp extends BaseDAOSupport implements PlatformDAO {

	// 分页查询
	public List list(PlatformListForm platformListForm) throws AppException {
		Hql hql = new Hql();
		hql.add("from Platform p where 1=1");
		if (platformListForm.getName() != null
				&& (!(platformListForm.getName().equals("")))) {
			hql.add(" and p.name like '%" + platformListForm.getName().trim() + "%'");
		}
		if (platformListForm.getType() != null
				&& (!(platformListForm.getType().equals("")))) {
			hql.add(" and p.type=" + platformListForm.getType());
		}
		if (platformListForm.getDrawType() != null
				&& (!(platformListForm.getDrawType().equals("")))) {
			hql.add(" and p.drawType=" + platformListForm.getDrawType());
		}
		
		
		
		
		hql.add("and p.status not in("+Platform.STATES_1+")");//过滤无效
		hql.add(" order by p.name");
		return this.list(hql, platformListForm);
	}

	// 删除
	public void delete(long id) throws AppException {
		if (id > 0) {
			Platform platform = (Platform) this.getHibernateTemplate().get(
					Platform.class, new Long(id));
			this.getHibernateTemplate().delete(platform);
		}
	}

	// 添加保存
	public long save(Platform platform) throws AppException {
		this.getHibernateTemplate().save(platform);
		return platform.getId();
	}

	public long update(Platform platform) throws AppException {
		if (platform.getId() > 0) {
			this.getHibernateTemplate().update(platform);
			return platform.getId();
		} else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public Platform getPlatformById(long platformId) throws AppException {
		Hql hql = new Hql();
		hql.add("from Platform p where p.id=" + platformId);
		Query query = this.getQuery(hql);
		Platform platform = null;
		if (query != null && query.list() != null&& query.list().size() > 0) {
			platform = (Platform) query.list().get(0);
		}
		return platform;
	}

	// 查询 返回一个list集合
	public List<Platform> getPlatformList() throws AppException {
		List<Platform> list = new ArrayList<Platform>();
		Hql hql = new Hql();
		hql.add(" from Platform p ");
		hql.add(" order by p.name ");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null) {
			list = query.list();
		}
		return list;
	}
	
	// 查询 返回一个list集合
	public List<Platform> getValidPlatformList() throws AppException {
		List<Platform> list = new ArrayList<Platform>();
		Hql hql = new Hql();
		hql.add(" from Platform p where 1=1 and p.status=0 ");
		hql.add(" order by p.name ");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null) {
			list = query.list();
		}
		return list;
	}
}
