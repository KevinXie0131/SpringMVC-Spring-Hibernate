package rml.model.vo;

import java.math.BigDecimal;

import rml.model.po.Tauth;

public class Auth implements java.io.Serializable {

	private String id;
	private String pid;
	private String pname;
	private String state = "open";// (open,closed)

	private String cid;
	private Tauth tauth;
	private String cdesc;
	private String cname;
	private BigDecimal cseq;
	private String curl;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Tauth getTauth() {
		return tauth;
	}

	public void setTauth(Tauth tauth) {
		this.tauth = tauth;
	}

	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public BigDecimal getCseq() {
		return cseq;
	}

	public void setCseq(BigDecimal cseq) {
		this.cseq = cseq;
	}

	public String getCurl() {
		return curl;
	}

	public void setCurl(String curl) {
		this.curl = curl;
	}

}
