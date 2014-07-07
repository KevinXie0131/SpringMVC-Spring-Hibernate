package rml.model.vo;

import java.util.Date;

public class Online implements java.io.Serializable {

	//pagination
	private int page;// current page
	private int rows;// number of records in each page
	private String sort;// sort field
	private String order;// sort order (asc,desc)

	private String cid;
	private Date cdatetime;
	private String cip;
	private String cname;
	
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Date getCdatetime() {
		return cdatetime;
	}

	public void setCdatetime(Date cdatetime) {
		this.cdatetime = cdatetime;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
