package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.itzabota.jira.plugins.utils.SqlTimestampAdapter;

@Entity
@Table(name = "jira_tab_accesshistory")
public class AccessHistory implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8768232619584010730L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Basic(optional = false)
	@Column(name = "Date_created")
	@Temporal(TemporalType.TIMESTAMP)
	public Timestamp getTs() {
		return ts;
	}
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	
	@Basic(optional = true)
	@Column(name = "Date_end")
	@Temporal(TemporalType.TIMESTAMP)	
	public Timestamp getTsEnd() {
		return tsEnd;
	}
	public void setTsEnd(Timestamp tsEnd) {
		this.tsEnd = tsEnd;
	}
	
	@Column(name = "Structural_code", columnDefinition = "NVARCHAR(1024)")
	public String getCdStr() {
		return cdStr;
	}
	public void setCdStr(String cdStr) {
		this.cdStr = cdStr;
	}
	
	@Column(name = "Name", columnDefinition = "NVARCHAR(512)")
	public String getNameResourceLast() {
		return nameResourceLast;
	}
	public void setNameResourceLast(String nameResourceLast) {
		this.nameResourceLast = nameResourceLast;
	}

	@XmlElement
	private Long id;
	
	@XmlElement
	@XmlJavaTypeAdapter(SqlTimestampAdapter.class)
	private Timestamp ts;
	
	@XmlElement
	@XmlJavaTypeAdapter(SqlTimestampAdapter.class)
	private Timestamp tsEnd;
	
	@XmlElement
	private String cdStr;
	
	@XmlElement
	private String nameResourceLast;
	
	@XmlElement
	private Employee employee;

	@ManyToOne
	@JoinColumn(name="ID_Employee", referencedColumnName="ID")
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@XmlElement
	private AccessStatus accessStatus;

	@ManyToOne
	@JoinColumn(name="Status_ID", referencedColumnName="ID")
	public AccessStatus getAccessStatus() {
		return accessStatus;
	}
	public void setAccessStatus(AccessStatus accessStatus) {
		this.accessStatus = accessStatus;
	}
	
	public AccessHistory() {
		
	}
	
	public AccessHistory(String cdStr, Timestamp ts, 
			Employee employee,
			AccessStatus accessStatus,
			String nameResourceLast,
			Timestamp tsEnd) {
		this.employee = employee;
		this.ts = ts;
		this.cdStr = cdStr;
		this.accessStatus = accessStatus;
		this.nameResourceLast = nameResourceLast;
		this.tsEnd = tsEnd;	
	}		
	
	
}
