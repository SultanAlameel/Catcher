package com.ksu.catcher.web.rest;

import java.util.List;

import com.ksu.catcher.service.DomainService;
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

	private final DomainService domainService ;

	public DomainResource(DomainRepository domainRepository,
						  UserRepository userRepository,
						  DomainService domainService) {
		
		this.userRepository = userRepository;
		this.domainRepository = domainRepository ;
		this.domainService = domainService;
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
		domainService.crawle(domainVO);
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
