package com.ksu.catcher.web.rest;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ApiResponseList;
import org.zaproxy.clientapi.core.ApiResponseSet;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import com.ksu.catcher.ApplicationProperties;
import com.ksu.catcher.domain.Report;
import com.ksu.catcher.repository.ReportRepository;
import com.ksu.catcher.web.rest.vo.ReportVO;


@RestController
@RequestMapping("/report")
public class ReportResource {
	
	
	private final ReportRepository reportRepository;
	private final ApplicationProperties applicationProperties;
			

	public ReportResource (ReportRepository reportRepository,ApplicationProperties applicationProperties) {
		
		this.reportRepository= reportRepository;
		this.applicationProperties = applicationProperties;
		
	}
	
	

	
	
	
	
	
	
	
	@PostMapping("/add")
	public void addReprt(@RequestBody ReportVO reportVO ) {
		
		
		Report report = new Report();
		
		Date d1 = new Date();
		
		report.setDate(d1);
		

		String TARGET = reportVO.getUrl();
		
		
		
		String ZAP_ADDRESS = applicationProperties.getZap().getAddress();
		int ZAP_PORT = applicationProperties.getZap().getPort();
		String ZAP_API_KEY = applicationProperties.getZap().getApiKey();

		
		
		 ClientApi api = new ClientApi(ZAP_ADDRESS,ZAP_PORT ,ZAP_API_KEY);

	        try {
	            // Start spidering the target
	            System.out.println("prepargin : " + TARGET+" to be scanned");
	            
	            ApiResponse resp = api.spider.scan(TARGET, null, null, null, null);
	            
	            String scanID;
	           

	            // The scan returns a scan id to support concurrent scanning
	            scanID = ((ApiResponseElement) resp).getValue();
	            System.out.println("the scan id is :"+scanID);
	            
	            report.setScanId(scanID);
	            report.setTarget(reportVO.getUrl());
	            
	            
	            checkSpider(report.getScanId(),report.getTarget());
	            // Poll the status until it completes
	            
	            // If required post process the spider results
	            List<ApiResponse> spiderResults = ((ApiResponseList) api.spider.results(scanID)).getItems();
	            
	           report = reportRepository.save(report);
	            

	            // TODO: Explore the Application more with Ajax Spider or Start scanning the application for vulnerabilities

	        } catch (Exception e) {
	            System.out.println("Exception : " + e.getMessage());
	            e.printStackTrace();
	        }	
		
	}
	
	
	
	
	
	
public void checkSpider(String scanId,String TARGET) throws InterruptedException, NumberFormatException, ClientApiException {
		
	

	String ZAP_ADDRESS = applicationProperties.getZap().getAddress();
		int ZAP_PORT = applicationProperties.getZap().getPort();
		String ZAP_API_KEY = applicationProperties.getZap().getApiKey();

		
		
		 ClientApi api = new ClientApi(ZAP_ADDRESS,ZAP_PORT ,ZAP_API_KEY);

		
		
		int progress;
		
		
		while (true) {
            Thread.sleep(1000);
            progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanId)).getValue());
            System.out.println("preparing your website : " + progress + "%");
            
            
            if (progress >= 100) {
                break;
            }
        }
        System.out.println("your website scan is gona start now");
       
        startScan(TARGET);
		
	}
	
	
	
	
	
	
	
	public void startScan(String TARGET) {
		
		String ZAP_ADDRESS = applicationProperties.getZap().getAddress();
		int ZAP_PORT = applicationProperties.getZap().getPort();
		String ZAP_API_KEY = applicationProperties.getZap().getApiKey();


        ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);

        try {
            // TODO : explore the app (Spider, etc) before using the Active Scan API, Refer the explore section
            System.out.println("Active Scanning target : " + TARGET);
            ApiResponse resp = api.ascan.scan(TARGET, "True", "False", null, null, null);
            String scanid;
            int progress;

            // The scan now returns a scan id to support concurrent scanning
            scanid = ((ApiResponseElement) resp).getValue();
           System.out.println("the scan id is :"+scanid);
            // Poll the status until it completes
            checkScan(scanid);

            System.out.println("Active Scan complete");
            // Print vulnerabilities found by the scanning
            System.out.println("Alerts:");
            System.out.println(new String(api.core.xmlreport(), StandardCharsets.UTF_8));

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }
		
		
		
	
		
	
	public void checkScan(String scanid) throws InterruptedException, NumberFormatException, ClientApiException {
		
		String ZAP_ADDRESS = applicationProperties.getZap().getAddress();
		int ZAP_PORT = applicationProperties.getZap().getPort();
		String ZAP_API_KEY = applicationProperties.getZap().getApiKey();

		
		
		 ClientApi api = new ClientApi(ZAP_ADDRESS,ZAP_PORT ,ZAP_API_KEY);

		
		int progress;
		
		while (true) {
            Thread.sleep(30000);
            
            progress =Integer.parseInt(((ApiResponseElement) api.ascan.status(scanid)).getValue());
            System.out.println("Active Scan progress : " + progress + "%");
            if (progress >= 100) {
                break;
            }
        }
		
		
		
		
		
	}
	
	
	
	public void getAlerts(ReportVO reportVO) {
		
		List<String> blackListPlugins = Arrays.asList("1000", "1025");
		String ZAP_ADDRESS = applicationProperties.getZap().getAddress();
		int ZAP_PORT = applicationProperties.getZap().getPort();
		String ZAP_API_KEY = applicationProperties.getZap().getApiKey();
		
		String TARGET = reportVO.getUrl();

		
		 ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);

	        try {
	            // TODO: Check if the scanning has completed

	            // Retrieve the alerts using paging in case there are lots of them
	            int start = 0;
	            int count = 5000;
	            int alertCount = 0;
	            ApiResponse resp = api.alert.alerts(TARGET, String.valueOf(start), String.valueOf(count), null);

	            while (((ApiResponseList) resp).getItems().size() != 0) {
	                System.out.println("Reading " + count + " alerts from " + start);
	                alertCount += ((ApiResponseList) resp).getItems().size();

	                for (ApiResponse l : (((ApiResponseList) resp).getItems())) {

	                    Map<String, ApiResponse> element = ((ApiResponseSet) l).getValuesMap();
	                    if (blackListPlugins.contains(element.get("pluginId").toString())) {
	                        // TODO: Trigger any relevant postprocessing
	                    } else if ("High".equals(element.get("risk").toString())) {
	                        // TODO: Trigger any relevant postprocessing
	                    } else if ("Informational".equals(element.get("risk").toString())) {
	                        // TODO: Ignore all info alerts - some of them may have been downgraded by security annotations
	                    }
	                }
	                start += count;
	                resp = api.alert.alerts(TARGET, String.valueOf(start), String.valueOf(count), null);
	            }
	            System.out.println("Total number of Alerts: " + alertCount);

	        } catch (Exception e) {
	            System.out.println("Exception : " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
		
		
		
	
	
	
	
	
	
	
	
}	


