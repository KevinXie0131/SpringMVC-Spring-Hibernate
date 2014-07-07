package rml.model.po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Tlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "QLOG", schema = "")
public class Tlog implements java.io.Serializable {

	// Fields

	private String cid;
	private String cname;
	private String cip;
	private Date cdatetime;
	private String cmsg;

	// Constructors

	/** default constructor */
	public Tlog() {
	}

	/** minimal constructor */
	public Tlog(String cid, String cname) {
		this.cid = cid;
		this.cname = cname;
	}

	/** full constructor */
	public Tlog(String cid, String cname, Date cdatetime, String cmsg) {
		this.cid = cid;
		this.cname = cname;
		this.cdatetime = cdatetime;
		this.cmsg = cmsg;
	}

	// Property accessors
	@Id
	@Column(name = "CID", unique = true, nullable = false, length = 36)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "CNAME", nullable = false, length = 100)
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	
	@Column(name = "CIP", nullable = false, length = 50)
	public String getCip() {
		return this.cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CDATETIME", length = 7)
	public Date getCdatetime() {
		return this.cdatetime;
	}

	public void setCdatetime(Date cdatetime) {
		this.cdatetime = cdatetime;
	}

	@Column(name = "CMSG", length = 200)
	public String getCmsg() {
		return this.cmsg;
	}

	public void setCmsg(String cmsg) {
		this.cmsg = cmsg;
	}

}