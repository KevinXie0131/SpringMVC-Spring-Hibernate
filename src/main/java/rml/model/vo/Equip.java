package rml.model.vo;

import java.util.Date;

public class Equip implements java.io.Serializable {
	
	private String ids;
	//pagination
	private int page;// current page
	private int rows;// number of records in each page
	private String sort;// sort field
	private String order;// sort order (asc,desc)
	//search
	private String q;
	
	private String cid;
	private String cmodel;
	private String cname;
	private String cproducer;
	private String cdesc;
	private Integer cno;
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCmodel() {
		return cmodel;
	}
	public void setCmodel(String cmodel) {
		this.cmodel = cmodel;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCproducer() {
		return cproducer;
	}
	public void setCproducer(String cproducer) {
		this.cproducer = cproducer;
	}
	public String getCdesc() {
		return cdesc;
	}
	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}
	public Integer getCno() {
		return cno;
	}
	public void setCno(Integer cno) {
		this.cno = cno;
	}

}
