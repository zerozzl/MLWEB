package com.zerozzl.mlweb.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.common.paging.QueryParameter;
import com.zerozzl.mlweb.dao._GenericDao;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class _GenericDaoImpl<T, PK extends Serializable> implements _GenericDao<T, PK> {

	private HibernateTemplate hibernateTemplate;
	private Class<T> persistentClass;

	public _GenericDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public PK save(T entity) {
		if (entity != null) {
			return (PK) hibernateTemplate.save(entity);
		} else {
			return null;
		}
	}

	@Override
	public void delete(PK id) {
		if (id != null) {
			T entity = this.get(id);
			if (entity != null) {
				hibernateTemplate.delete(entity);
			}
		}
	}
	
	@Override
	public void delete(T entity) {
		if (entity != null) {
			hibernateTemplate.delete(entity);
		}
	}

	@Override
	public void update(T entity) {
		if (entity != null) {
			hibernateTemplate.update(entity);
		}
	}

	@Override
	public T get(PK id) {
		if (id != null) {
			return (T) hibernateTemplate.get(persistentClass, id);
		} else {
			return null;
		}
	}

	@Override
	public List<T> find(String HQL) {
		List<T> list = new ArrayList<T>();
		if (HQL != null) {
			list = (List<T>) hibernateTemplate.find(HQL);
		}
		return list;
	}

	protected List<T> _find(final String HQL, final PagedBean pagedBean) {
		return _find(HQL, null, pagedBean);
	}

	protected List<T> _find(String HQL, Map parameters) {
		return this._find(HQL, parameters, null, false);
	}

	protected List<T> _find(String HQL, Map parameters, PagedBean pagedBean) {
		return this._find(HQL, parameters, pagedBean, false);
	}

	protected List<T> _find (final String HQL, final Map parameters,
			final PagedBean pagedBean, final boolean useCache) {
		return (List<T>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(HQL);
				query.setCacheable(useCache);
				if (parameters != null && !parameters.isEmpty()) {
					Iterator iter = parameters.keySet().iterator();
					while (iter.hasNext()) {
						String parameterName = (String) iter.next();
						if (parameters.get(parameterName) instanceof Collection<?>) {
							query.setParameterList(parameterName, (Collection) parameters.get(parameterName));
						} else if (parameters.get(parameterName) instanceof Object[]) {
							query.setParameterList(parameterName, (Object[]) parameters.get(parameterName));
						} else {
							query.setParameter(parameterName, parameters.get(parameterName));
						}
					}
				}
				if (pagedBean != null && pagedBean.doPaged()) {
					query.setFirstResult(pagedBean.getFirstResult());
					if (pagedBean.getPageSize() != -1) {
						query.setMaxResults(pagedBean.getPageSize());
					}
					query.setFetchSize(getFetchSize(pagedBean.getFirstResult()));
				}
				List results = query.getResultList();
				return results;
			}
		});
	}

	private static int getFetchSize(int firstRow) {
		if (firstRow > 150) {
			return firstRow > 10000 ? 150 : firstRow > 5000 ? 100 : firstRow > 1000 ? 50 : 30;
		}
		if (firstRow > 100)
			return 15;
		if (firstRow > 50)
			return 10;
		return 0;
	}

	protected List<T> _find(List<QueryParameter> queryParams) {
		return this._find(null, queryParams, null);
	}

	protected List<T> _find(List<QueryParameter> queryParams, List<OrderByParameter> orderBys) {
		return this._find(null, queryParams, orderBys);
	}

	protected List<T> _find(final String property, final List<QueryParameter> queryParams,
			final List<OrderByParameter> orderBys) {
		StringBuilder buf = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(property)) {
			buf.append("select o." + property + " ");
		}
		buf.append("from " + persistentClass.getSimpleName() + " o where 1=1 ");

		if (queryParams != null && queryParams.size() > 0) {
			for (QueryParameter qp : queryParams) {
				buf.append(qp.buildStatement());
				params.put(qp.getParameterName(), qp.getValue());
			}
		}

		if (orderBys != null && orderBys.size() > 0) {
			String orderByStat = " order by ";
			for (OrderByParameter obp : orderBys) {
				orderByStat = orderByStat + obp.buildStatement();
			}
			orderByStat = orderByStat.substring(0, orderByStat.length() - 1);
			buf.append(orderByStat);
		}
		
		return this._find(buf.toString(), params);
	}

	protected PagedList _findByPage(List<QueryParameter> queryParams, PagedBean pageBean) {
		return this._findByPage(queryParams, null, pageBean);
	}

	protected PagedList _findByPage(List<QueryParameter> queryParams, List<OrderByParameter> orderBys,
			PagedBean pageBean) {
		StringBuilder countHql = new StringBuilder(), hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		countHql.append("select count(*) from " + persistentClass.getSimpleName() + " o where 1=1 ");
		hql.append("from " + persistentClass.getSimpleName() + " o where 1=1 ");

		if (queryParams != null && queryParams.size() > 0) {
			for (QueryParameter qp : queryParams) {
				countHql.append(qp.buildStatement());
				hql.append(qp.buildStatement());
				params.put(qp.getParameterName(), qp.getValue());
			}
		}

		if (orderBys != null && orderBys.size() > 0) {
			String orderByStat = " order by ";
			for (OrderByParameter obp : orderBys) {
				orderByStat = orderByStat + obp.buildStatement();
			}
			orderByStat = orderByStat.substring(0, orderByStat.length() - 1);
			hql.append(orderByStat);
		}
		
		return this._findByPage(countHql.toString(), hql.toString(), params, pageBean);
	}

	protected PagedList _findByPage(String HQLStringForCount, String HQLString, Map parameters, PagedBean pageBean) {
		return _findByPage(1, HQLStringForCount, HQLString, parameters, pageBean);
	}

	private long findRowCount(String HQLString, Map parameters) {
		long rowCount = 0;
		List list = this._find(HQLString, parameters);
		if (list.size() > 0) {
			rowCount = (Long) list.get(0);
		}
		return rowCount;
	}

	/**
	 * type 1: HQL 2: SQL
	 */
	private PagedList _findByPage(int type, String QLStringForCount, String QLString, Map parameters,
			PagedBean pagedBean) {
		long rowCount = 0;

		if (type == 2) {
			// rowCount = this.findRowCountBySQL(QLStringForCount, parameters);
		} else {
			rowCount = this.findRowCount(QLStringForCount, parameters);
		}

		PagedList pagedList = new PagedList();
		if (rowCount < 1) {
			pagedList.setEmpty(true);
			return pagedList;
		}
		Long pageCount = (rowCount + pagedBean.getPageSize() - 1) / pagedBean.getPageSize();
		int currentPage = 1;
		int fwdPage = 0;
		int backPage = 0;
		int pageSize = pagedBean.getPageSize();
		int lastPage = pageCount.intValue();

		if (pagedBean.getPageNo() > lastPage) {
			currentPage = lastPage;
		} else if (pagedBean.getPageNo() < 1) {
			currentPage = 1;
		} else {
			currentPage = pagedBean.getPageNo();
		}
		pagedBean.setPageNo(currentPage);
		
		fwdPage = currentPage + 1;
		backPage = currentPage - 1;

		if (fwdPage < 1) {
			fwdPage = 1;
		}
		if (fwdPage > lastPage) {
			fwdPage = lastPage;
		}

		if (backPage < 1) {
			backPage = 1;
		}
		if (backPage > lastPage) {
			backPage = lastPage;
		}

		int _start = 0;
		int _stop = 0;
		int nPagesDisplayed = pagedBean.getPagesDisplayed();
		
		{
			int nCurrent = currentPage;
			int nRightMargin = nPagesDisplayed / 2;
			int nStop = nCurrent + nRightMargin;
			int nLastPage = lastPage;

			int nLeftAddon = 0;
			if (nStop > nLastPage)
				nLeftAddon = nStop - nLastPage;

			int nLeftMargin = (nPagesDisplayed - 1) / 2 + nLeftAddon;
			int nStart = nCurrent - nLeftMargin;
			int nFirstPage = 1;
			if (nStart < nFirstPage) {
				nStart = nFirstPage;
			}
			_start = nStart;
		}

		{
			int nCurrent = currentPage;
			int nLeftMargin = (nPagesDisplayed - 1) / 2;
			int nStart = nCurrent - nLeftMargin;
			int nFirstPage = 1;

			int nRightAddon = 0;
			if (nStart < nFirstPage)
				nRightAddon = nFirstPage - nStart;

			int nRightMargin = nPagesDisplayed / 2 + nRightAddon;
			int nStop = nCurrent + nRightMargin;
			int nLastPage = lastPage;
			if (nStop > nLastPage) {
				nStop = nLastPage;
			}
			_stop = nStop;
		}

		List currentPageList = new ArrayList();
		if (type == 2) {
			// currentPageList = this._findBySQL(QLString, parameters, _pagingAndSorting);
		} else {
			currentPageList = this._find(QLString, parameters, pagedBean);
		}

		List<String> pagesDisplayedList = new ArrayList<String>();
		for (int i = _start; i <= _stop; i++) {
			pagesDisplayedList.add(String.valueOf(i));
		}
		String[] xxx = (String[]) pagesDisplayedList.toArray(new String[pagesDisplayedList.size()]);

		pagedList.setPageCount(String.valueOf(pageCount));
		pagedList.setCurrentPage(String.valueOf(currentPage));
		pagedList.setPagesDisplayed(xxx);
		pagedList.setCurrentPageList(currentPageList);
		pagedList.setRowCount(rowCount);
		pagedList.setBackPage(String.valueOf(backPage));
		pagedList.setFwdPage(String.valueOf(fwdPage));
		pagedList.setPageSize(String.valueOf(pageSize));

		return pagedList;
	}

	/********** 执行SQL的方法已经暂时停用，有需要再打开 **********/

//	protected List<T> _findBySQL(String SQLString) {
//		return _findBySQL(SQLString, null, null);
//	}
//
//	protected List<T> _findBySQL(String SQLString, Map parameters) {
//		return _findBySQL(SQLString, parameters, null, false);
//	}
//
//	protected List<T> _findBySQL(String SQLString, Map parameters, PagedBean pagingAndSorting) {
//		return this._findBySQL(SQLString, parameters, pagingAndSorting, false);
//	}
//
//	protected List<T> _findBySQL(final String SQLString, final Map parameters, final PagedBean pagingAndSorting,
//			final boolean useCache) {
//		Object obj = hibernateTemplate.execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException {
//				SQLQuery query = session.createSQLQuery(SQLString);
//				query.setCacheable(useCache);
//				if (parameters != null && !parameters.isEmpty()) {
//					Iterator iter = parameters.keySet().iterator();
//					while (iter.hasNext()) {
//						String parameterName = (String) iter.next();
//						if (parameters.get(parameterName) instanceof Date) {
//							query.setDate(parameterName, (Date) parameters.get(parameterName));
//						} else if (parameters.get(parameterName) instanceof Collection<?>) {
//							query.setParameterList(parameterName, (Collection) parameters.get(parameterName));
//						} else if (parameters.get(parameterName) instanceof Object[]) {
//							query.setParameterList(parameterName, (Object[]) parameters.get(parameterName));
//						} else {
//							query.setParameter(parameterName, parameters.get(parameterName));
//						}
//					}
//				}
//
//				if (pagingAndSorting != null && pagingAndSorting.doPaged()) {
//					query.setFirstResult(pagingAndSorting.getFirstResult());
//					if (pagingAndSorting.getPageSize() != -1) {
//						query.setMaxResults(pagingAndSorting.getPageSize());
//					}
//					query.setFetchSize(getFetchSize(pagingAndSorting.getFirstResult()));
//				}
//				return query.list();
//			}
//
//		});
//		return (List) obj;
//	}
//
//	protected PagedList<T> _findByPageBySQL(String SQLStringForCount, String SQLString, Map parameters, PagedBean pageBean) {
//		return this._findByPage(2, SQLStringForCount, SQLString, parameters, pageBean);
//	}
//	
//	private long findRowCountBySQL(String SQLString, Map parameters) {
//		long rowCount = 0;
//		List list = this._findBySQL(SQLString, parameters);
//		if (list.size() > 0) {
//			Integer size = (Integer) list.get(0);
//			if (size != null) {
//				rowCount = Long.parseLong(size.toString());
//			}
//		}
//		return rowCount;
//	}
	
}
