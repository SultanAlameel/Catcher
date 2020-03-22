package com.ksu.catcher.zap;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ApiResponseList;
import org.zaproxy.clientapi.core.ApiResponseSet;
import org.zaproxy.clientapi.core.ClientApi;

import com.ksu.catcher.ApplicationProperties;
import com.ksu.catcher.ApplicationProperties.Zap;
import com.ksu.catcher.web.rest.vo.ZapVO;

@Component
//@RequestMapping("/client")
public class ZapClient {
	//@Autowired
	ApplicationProperties applicationProperties ;
	
	
	public ZapClient( ApplicationProperties applicationProperties ) {
		this.applicationProperties = applicationProperties;
		
	}

	//private static final String ZAP_ADDRESS = "localhost";
	//    private static final int ZAP_PORT = 8081;
	    ///Change to match the API key set in ZAP, or use NULL if the API key is disabled
	//    private static final String ZAP_API_KEY = "6gmd5am8fu72grgtds3lto5scr";
	    //
	 private static String TARGET	;
	private static List<String> blackListPlugins = Arrays.asList("1000", "1025");
	
	@PostMapping("/go")
public void goSpider(String url) {
		
		TARGET = url;
		
		
		 ClientApi api = new ClientApi(applicationProperties.getZap().getAddress(),applicationProperties.getZap().getPort(),applicationProperties.getZap().getApiKey());

	        try {
	            // Start spidering the target
	            System.out.println("prepargin : " + TARGET+" to be scanned");
	            ApiResponse resp = api.spider.scan(TARGET, null, null, null, null);
	            String scanID;
	            int progress;

	            // The scan returns a scan id to support concurrent scanning
	            scanID = ((ApiResponseElement) resp).getValue();
	            // Poll the status until it completes
	            while (true) {
	                Thread.sleep(1000);
	                progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanID)).getValue());
	                System.out.println("preparing your website : " + progress + "%");
	                if (progress >= 100) {
	                    break;
	                }
	            }
	            System.out.println("your website scan is gona start now");
	            // If required post process the spider results
	            List<ApiResponse> spiderResults = ((ApiResponseList) api.spider.results(scanID)).getItems();

	            // TODO: Explore the Application more with Ajax Spider or Start scanning the application for vulnerabilities

	        } catch (Exception e) {
	            System.out.println("Exception : " + e.getMessage());
	            e.printStackTrace();
	        }
	        
	        
	    
        
        
        try {
            // TODO : explore the app (Spider, etc) before using the Active Scan API, Refer the explore section
            System.out.println("Active Scanning target : " + TARGET);
            ApiResponse resp = api.ascan.scan(TARGET, "True", "False", null, null, null);
            String scanid;
            int progress;

            // The scan now returns a scan id to support concurrent scanning
            scanid = ((ApiResponseElement) resp).getValue();
            // Poll the status until it completes
            while (true) {
                Thread.sleep(5000);
                progress =
                        Integer.parseInt(
                                ((ApiResponseElement) api.ascan.status(scanid)).getValue());
                System.out.println("Active Scan progress : " + progress + "%");
                if (progress >= 100) {
                    break;
                }
            }

            System.out.println("Active Scan complete");
            // Print vulnerabilities found by the scanning
            System.out.println("Alerts:");
            System.out.println(new String(api.core.xmlreport(), StandardCharsets.UTF_8));

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
        
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
