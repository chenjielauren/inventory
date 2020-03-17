package com.silgan.inventory.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageQueryUtil extends LinkedHashMap<String, Object> {
    //当前页码
    private int page;
    //每页条数
    private int limit;    
    //排列字段
    private String sidx;
    //顺序
    private String sord;
    
    private Map<String, Object> map;
    
    public PageQueryUtil() {
    	
    }

    public PageQueryUtil(Map<String, Object> params) {
        this.putAll(params);
        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.sidx = (null!=params.get("sidx"))?params.get("sidx").toString():"";
        this.sord = (null!=params.get("sord"))?params.get("sord").toString():"";
        this.put("start", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
        this.put("sidx", sidx);
        this.put("sord", sord);
    }

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}


	public String getSord() {
		return sord;
	}


	public void setSord(String sord) {
		this.sord = sord;
	}


	@Override
    public String toString() {
        return "PageUtil{" +
                "page=" + page +
                ", limit=" + limit +
                ", sidx=" + sidx +
                ", sord=" + sord +
                '}';
    }
	
}
