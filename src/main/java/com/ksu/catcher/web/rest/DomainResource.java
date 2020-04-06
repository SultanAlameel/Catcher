package com.ksu.catcher.web.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksu.catcher.ApplicationProperties;
import com.ksu.catcher.domain.Domain;
import com.ksu.catcher.domain.User;
import com.ksu.catcher.repository.DomainRepository;
import com.ksu.catcher.repository.UserRepository;
import com.ksu.catcher.web.rest.vo.DomainVO;

@RestController
@RequestMapping("/domains")
public class DomainResource {
	
	private final DomainRepository domainRepository ;
	
	private final UserRepository userRepository ;
	
	
	
	public DomainResource(DomainRepository domainRepository, UserRepository userRepository, ApplicationProperties applicationProperties) {
		
		this.userRepository = userRepository;
		this.domainRepository = domainRepository ;
	}
	
	@PostMapping
	public void addDomain(@RequestBody DomainVO domainVO) {
		Domain domain = new Domain();

		domain.setName(domainVO.getDomainName());
		domain.setUser(userRepository.findById(domainVO.getUserId()).get());

		domain = domainRepository.save(domain);
	}

	@PostMapping("/scan")
	public void scanDomain(@RequestBody DomainVO domainVO){

	}

	@GetMapping("/{id}")
	public Domain getDomain(@PathVariable Long id) {
		return domainRepository.findById(id).get();
	}

	@GetMapping
	public List<Domain> getAll(){
		return null ;
	}
}
