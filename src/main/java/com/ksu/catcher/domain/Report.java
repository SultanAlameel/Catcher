 
package com.ksu.catcher.domain;

import java.time.LocalDate;
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
private LocalDate date;
private String crawlId;
private String scanId;
private String target;
private Boolean executing;
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




public Boolean isExecuting() {
	return executing;
}

public void setExecuting(Boolean executing) {
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

public LocalDate getDate() {
	return date;
}
public void setDate(LocalDate date) {
	this.date = date;
}




}
