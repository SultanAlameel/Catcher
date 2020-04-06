package com.ksu.catcher.web.rest;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ApiResponseList;
import org.zaproxy.clientapi.core.ApiResponseSet;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import com.ksu.catcher.ApplicationProperties;
import com.ksu.catcher.domain.Report;
import com.ksu.catcher.repository.DomainRepository;
import com.ksu.catcher.repository.ReportRepository;
import com.ksu.catcher.web.rest.vo.ReportVO;


@RestController
@RequestMapping("/report")

//@RequestMapping(value = "/report", method = RequestMethod.POST)
public class ReportResource  {
	
	
	private final ReportRepository reportRepository ;
	
	private final DomainRepository domainRepository;
			

	public ReportResource (ReportRepository reportRepository,DomainRepository domainRepository) {
		
		this.domainRepository = domainRepository;
		this.reportRepository= reportRepository;
	}

	
	@PostMapping("/{reportId}")
	public Report getReport(@PathVariable long reportId) {
		
		return reportRepository.findById(reportId).get(); 
		
	}
	
}





