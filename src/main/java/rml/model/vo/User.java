package rml.model.vo;

import java.util.Date;

public class User implements java.io.Serializable {

	private String ids;
	private String roleIds;
	private String roleNames;
	private String authIds;
	private String authNames;
	private String authUrls;
	//pagination
	private int page;// current page
	private int rows;// number of records in each page
	private String sort;// sort field
	private String order;// sort order (asc,desc)
	//user search
	//private Date ccreatedatetimeStart;
	//private Date cmodifydatetimeStart;
	//private Date ccreatedatetimeEnd;
	//private Date cmodifydatetimeEnd;
	private String q;
	
	private String cid;
	private Date ccreatedatetime;
	private Date cmodifydatetime;
	private String cname;
	private String cpwd;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getAuthUrls() {
		return authUrls;
	}

	public void setAuthUrls(String authUrls) {
		this.authUrls = authUrls;
	}

	public String getAuthNames() {
		return authNames;
	}

	public void setAuthNames(String authNames) {
		this.authNames = authNames;
	}

	public String getAuthIds() {
		return authIds;
	}

	public void setAuthIds(String authIds) {
		this.authIds = authIds;
	}

/*	public Date getCcreatedatetimeStart() {
		return ccreatedatetimeStart;
	}

	public void setCcreatedatetimeStart(Date ccreatedatetimeStart) {
		this.ccreatedatetimeStart = ccreatedatetimeStart;
	}

	public Date getCmodifydatetimeStart() {
		return cmodifydatetimeStart;
	}

	public void setCmodifydatetimeStart(Date cmodifydatetimeStart) {
		this.cmodifydatetimeStart = cmodifydatetimeStart;
	}

	public Date getCcreatedatetimeEnd() {
		return ccreatedatetimeEnd;
	}

	public void setCcreatedatetimeEnd(Date ccreatedatetimeEnd) {
		this.ccreatedatetimeEnd = ccreatedatetimeEnd;
	}

	public Date getCmodifydatetimeEnd() {
		return cmodifydatetimeEnd;
	}

	public void setCmodifydatetimeEnd(Date cmodifydatetimeEnd) {
		this.cmodifydatetimeEnd = cmodifydatetimeEnd;
	}
*/
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

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Date getCcreatedatetime() {
		return ccreatedatetime;
	}

	public void setCcreatedatetime(Date ccreatedatetime) {
		this.ccreatedatetime = ccreatedatetime;
	}

	public Date getCmodifydatetime() {
		return cmodifydatetime;
	}

	public void setCmodifydatetime(Date cmodifydatetime) {
		this.cmodifydatetime = cmodifydatetime;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCpwd() {
		return cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}

}
