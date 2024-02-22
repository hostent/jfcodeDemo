package com.jfcore.frame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

 
public class PagePars {

	public PagePars() {
		_pars = new Hashtable<String, Object>();
	}

	public PagePars(HttpServletRequest request) {
		_pars = new Hashtable<String, Object>();

		if (request == null) {
			return;
		}

		if (request.getParameter("pageSize") != null) {
			_pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if (request.getParameter("pageIndex") != null) {
			_pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		}
		if (request.getParameter("order") != null) {
			_order = request.getParameter("order");
		}

		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();

			if (paraName.equals("pageSize") || paraName.equals("pageIndex") || paraName.equals("order")) {
				continue;
			}

			_pars.put(paraName, request.getParameter(paraName));
		}

	}

	public int getFrom() {
		return (_pageIndex - 1) * _pageSize;
	}

	public String getStringPar(String key) {
		if (!_pars.containsKey(key)) {
			return null;
		}
		return _pars.get(key).toString();
	}

	public Integer getIntPar(String key) {
		if (!_pars.containsKey(key)) {
			return null;
		}
		return Integer.parseInt(_pars.get(key).toString());
	}

	public Date getDatePar(String key, String format) throws ParseException {
		if (!_pars.containsKey(key)) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(_pars.get(key).toString());
	}

	public Double getDoublePar(String key) {
		if (!_pars.containsKey(key)) {
			return null;
		}
		return Double.parseDouble(_pars.get(key).toString());
	}

	public Boolean getBooleanPar(String key) {
		if (!_pars.containsKey(key)) {
			return null;
		}
		return Boolean.parseBoolean(_pars.get(key).toString());
	}

	Hashtable<String, Object> _pars;

	int _pageSize = 10000;

	int _pageIndex = 1;

	String _order;

	public Hashtable<String, Object> getPars() {
		return _pars;
	}

	public void setPars(Hashtable<String, Object> pars) {
		_pars = pars;
	}

	public int getPageSize() {
		return _pageSize;
	}

	public void setPageSize(int pageSize) {
		_pageSize = pageSize;
	}

	public int getPageIndex() {
		return _pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		_pageIndex = pageIndex;
	}

	public String getOrder() {
		return _order;
	}

	public void setOrder(String order) {
		_order = order;
	}
}
