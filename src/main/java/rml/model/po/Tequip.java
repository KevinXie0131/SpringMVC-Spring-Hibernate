package rml.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tequip entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "QEQUIP", schema = "")
public class Tequip implements java.io.Serializable {

	// Fields

	private String cid;
	private String cmodel;
	private String cname;
	private String cproducer;
	private String cdesc;
	private Integer cno;

	// Constructors

	/** default constructor */
	public Tequip() {
	}

	/** minimal constructor */
	public Tequip(String cid, String cmodel) {
		this.cid = cid;
		this.cmodel = cmodel;
	}

	/** full constructor */
	public Tequip(String cid, String cmodel, String cname, String cproducer,
			String cdesc, Integer cno) {
		this.cid = cid;
		this.cmodel = cmodel;
		this.cname = cname;
		this.cproducer = cproducer;
		this.cdesc = cdesc;
		this.cno = cno;
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

	@Column(name = "CMODEL", nullable = false, length = 100)
	public String getCmodel() {
		return this.cmodel;
	}

	public void setCmodel(String cmodel) {
		this.cmodel = cmodel;
	}

	@Column(name = "CNAME", length = 100)
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "CPRODUCER", length = 100)
	public String getCproducer() {
		return this.cproducer;
	}

	public void setCproducer(String cproducer) {
		this.cproducer = cproducer;
	}

	@Column(name = "CDESC", length = 100)
	public String getCdesc() {
		return this.cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	@Column(name = "CNO", precision = 8, scale = 0)
	public Integer getCno() {
		return this.cno;
	}

	public void setCno(Integer cno) {
		this.cno = cno;
	}

}