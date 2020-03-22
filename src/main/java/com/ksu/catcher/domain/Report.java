 
package com.ksu.catcher.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TBL_REPORT")
public class Report {
	
	
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private long id;
private Date date;
private String scanId;
private String target;
public String getTarget() {
	return target;
}

public void setTarget(String target) {
	this.target = target;
}
private boolean executed;
@ManyToOne
private Domain domain;


public boolean isExecuted() {
	return executed;
}

public void setExecuted(boolean executed) {
	this.executed = executed;
}

public String getScanId() {
	return scanId;
}

public void setScanId(String scanId) {
	this.scanId = scanId;
}

public long getId() {
	return id;
}

public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}




}
