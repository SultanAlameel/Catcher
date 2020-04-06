package com.ksu.catcher.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TBL_DOMAIN")

public class Domain {
	
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private long id;
private String name;
@ManyToOne
private User user;
@OneToMany(mappedBy = "domain")
private List<Report> reports ;




public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public List<Report> getReports() {
	return reports;
}

public void setReports(List<Report> reports) {
	this.reports = reports;
}

public long getId() {
	return id;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}


	
	

}
