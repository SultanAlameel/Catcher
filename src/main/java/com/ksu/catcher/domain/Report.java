 
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
private String crawlId;
private String scanId;
private String target;
private boolean executing;
@ManyToOne
private Domain domain;





public String getCrawlId() {
	return crawlId;
}

public void setCrawlId(String crawlId) {
	this.crawlId = crawlId;
}

public Domain getDomain() {
	return domain;
}

public void setDomain(Domain domain) {
	this.domain = domain;
}




public boolean isExecuting() {
	return executing;
}

public void setExecuting(boolean executing) {
	this.executing = executing;
}

public String getTarget() {
	return target;
}

public void setTarget(String target) {
	this.target = target;
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
