package com.ksu.catcher.web.rest;

import java.util.List;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksu.catcher.ApplicationProperties;
import com.ksu.catcher.domain.Domain;

import com.ksu.catcher.repository.DomainRepository;
import com.ksu.catcher.repository.UserRepository;
import com.ksu.catcher.web.rest.vo.DomainVO;

@RestController
@RequestMapping("/domains")
public class DomainResource {
	
	private final DomainRepository domainRepository ;
	
	private final UserRepository userRepository ;
	
	private final ApplicationProperties applicationProperties;
	
	public DomainResource(DomainRepository domainRepository, UserRepository userRepository, ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties ;
		this.userRepository = userRepository;
		this.domainRepository = domainRepository ;
	}
	
	@PostMapping
	public void createDomain(@RequestBody DomainVO domainVO) {
		
		//User user = userRepository.findById(id)
		
		Domain domain = new Domain();
		
		
	//	user = userRepository.findAll();
		
		domain.setName(domainVO.getDomainName());
		
		
		domain = domainRepository.save(domain);
		
		System.out.println(applicationProperties.getZap().getAddress());
		//
		
	}
	
	@GetMapping("/param")
	public String getProperty() {
		System.out.println(applicationProperties);
		System.out.println(applicationProperties.getZap());
		System.out.println(applicationProperties.getZap().getAddress());
		return "Done";
	}
	
	@GetMapping("/{id}")
	public Domain getDomain(@PathVariable Long id) {
		return domainRepository.findById(id).get();
		
	}
	
	
	//@GetMapping("/{id}")
//	public User getUser(@PathVariable Long id) {
	//	return domainRepository.findById(id).get();
		
	//}
	
	
	@GetMapping
	public List<Domain> getAll(){
		
		return null ;
	}
}
