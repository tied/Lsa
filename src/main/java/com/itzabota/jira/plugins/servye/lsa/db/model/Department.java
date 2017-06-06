package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.itzabota.jira.plugins.utils.SqlTimestampAdapter;

@Entity
@Table(name = "jira_tab_department")
public class Department implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5290333777838710176L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "department_code", columnDefinition = "NVARCHAR(100)")
	public String getDptcod() {
		return dptcod;
	}
	public void setDptcod(String dptcod) {
		this.dptcod = dptcod;
	}
	
	@Column(name = "department_name", columnDefinition = "NVARCHAR(1024)")
	public String getDptname() {
		return dptname;
	}
	public void setDptname(String dptname) {
		this.dptname = dptname;
	}
	
	@XmlElement
	private Long id;
	
	@XmlElement
	private String dptcod;
	
	@XmlElement
	private String dptname;
	
	@XmlElement
	@XmlJavaTypeAdapter(SqlTimestampAdapter.class)
	private Timestamp tsBeg;

	@XmlElement
	@XmlJavaTypeAdapter(SqlTimestampAdapter.class)
	private Timestamp tsEnd;
	
	
	@Basic(optional = true)
	@Column(name = "ts_beg")
	@Temporal(TemporalType.TIMESTAMP)		
	public Timestamp getTsBeg() {
		return tsBeg;
	}
	public void setTsBeg(Timestamp tsBeg) {
		this.tsBeg = tsBeg;
	}
	
	@Basic(optional = true)
	@Column(name = "ts_end")
	@Temporal(TemporalType.TIMESTAMP)			
	public Timestamp getTsEnd() {
		return tsEnd;
	}
	public void setTsEnd(Timestamp tsEnd) {
		this.tsEnd = tsEnd;
	}

	@XmlElement
	private final List<Employee> employees = new ArrayList<Employee>();

	@OneToMany(mappedBy = "dptcod")
	public List<Employee> getEmployees() {
		return employees;
	}	
				
	public Department() {
		
	}
}

