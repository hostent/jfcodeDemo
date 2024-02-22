package com.jfcore.frame;

import java.util.*;

public class PageData {


	long total;
	List<Map<String, Object>> rows;

	Map<String, Object> stats;

	public PageData() {
	}

	public PageData(long count, List<Map<String, Object>> page) {
		total = count;
		rows = page;
	}

	public PageData(long count, List<Map<String, Object>> page, Map<String, Object> map) {
		total = count;
		rows = page;
		stats = map;
	}
	
	
	
	
	
	
	
	
	

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	public Map<String, Object> getStats() {
		return stats;
	}

	public void setStats(Map<String, Object> stats) {
		this.stats = stats;
	}
}
