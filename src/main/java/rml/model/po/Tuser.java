package rml.model.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Tuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "QUSER", schema = "")
public class Tuser implements java.io.Serializable {

	// Fields

	private String cid;
	private Date ccreatedatetime;
	private Date cmodifydatetime;
	private String cname;
	private String cpwd;
	private Set<Tusertrole> tusertroles = new HashSet<Tusertrole>(0);

	// Constructors

	/** default constructor */
	public Tuser() {
	}

	/** minimal constructor */
	public Tuser(String cid, String cname, String cpwd) {
		this.cid = cid;
		this.cname = cname;
		this.cpwd = cpwd;
	}

	/** full constructor */
	public Tuser(String cid, Date ccreatedatetime, Date cmodifydatetime, String cname, String cpwd, Set<Tusertrole> tusertroles) {
		this.cid = cid;
		this.ccreatedatetime = ccreatedatetime;
		this.cmodifydatetime = cmodifydatetime;
		this.cname = cname;
		this.cpwd = cpwd;
		this.tusertroles = tusertroles;
	}

	// Property accessors
	@Id
	@Column(name = "CID",  nullable = false, length = 36)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CCREATEDATETIME", length = 7)
	public Date getCcreatedatetime() {
		return this.ccreatedatetime;
	}

	public void setCcreatedatetime(Date ccreatedatetime) {
		this.ccreatedatetime = ccreatedatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CMODIFYDATETIME", length = 7)
	public Date getCmodifydatetime() {
		return this.cmodifydatetime;
	}

	public void setCmodifydatetime(Date cmodifydatetime) {
		this.cmodifydatetime = cmodifydatetime;
	}

	@Column(name = "CNAME", unique = true, nullable = false, length = 100)
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "CPWD", nullable = false, length = 100)
	public String getCpwd() {
		return this.cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tuser")
	public Set<Tusertrole> getTusertroles() {
		return this.tusertroles;
	}

	public void setTusertroles(Set<Tusertrole> tusertroles) {
		this.tusertroles = tusertroles;
	}

}