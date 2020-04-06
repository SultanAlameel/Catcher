package com.ksu.catcher.zap;



import org.springframework.stereotype.Component;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import com.ksu.catcher.ApplicationProperties;
import com.ksu.catcher.factory.Tool;


@Component
public class ZapClient implements Tool{

	private final ApplicationProperties applicationProperties;
	
	
	final String ZAP_ADDRESS = getApplicationProperties().getZap().getAddress();
	final int ZAP_PORT = getApplicationProperties().getZap().getPort();
	final String ZAP_API_KEY = getApplicationProperties().getZap().getApiKey();

	ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);
	

	
	public ZapClient(ApplicationProperties applicationProperties) {
		
		this.applicationProperties = applicationProperties;
	}


	public String getZAP_ADDRESS() {
		return ZAP_ADDRESS;
	}


	public int getZAP_PORT() {
		return ZAP_PORT;
	}


	public String getZAP_API_KEY() {
		return ZAP_API_KEY;
	}


	public String startCrawling(String domain) {
		

		try {
			ApiResponse resp = api.spider.scan(domain, null, null, null, null);
			String scanID = ((ApiResponseElement) resp).getValue();
			return scanID;

		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	public int checkCrawler(String scanId) throws InterruptedException, NumberFormatException, ClientApiException {
		

			    int progress;
			
	            progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanId)).getValue());
	            System.out.println("preparing your website : " + progress + "%");
	            
	            return progress;      
	}
	
	
	public String startScan(String domain) {

        try {
           
            ApiResponse resp = api.ascan.scan(domain, "True", "False", null, null, null);
            
            String scanID = ((ApiResponseElement) resp).getValue();
            
            return scanID;
           
                   } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
            
            return null;
                   }
			}
	
	
	
	public int checkScan(String scanId) throws InterruptedException, NumberFormatException, ClientApiException {
		
		int progress;
		
            progress =Integer.parseInt(((ApiResponseElement) api.ascan.status(scanId)).getValue());
           
            return progress;
   
	}

	public ApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}
	
	
	
	
	
	
}
