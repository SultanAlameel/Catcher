package com.ksu.catcher.zap;



import org.springframework.stereotype.Component;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import com.ksu.catcher.ApplicationProperties;
import com.ksu.catcher.factory.Tool;

import javax.annotation.PostConstruct;


@Component
public class ZapClient implements Tool{
	private final ApplicationProperties applicationProperties;
	private ClientApi api ;

	public ZapClient(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	@PostConstruct
	private void postConstruct(){
		ApplicationProperties.Zap zapConfig = this.applicationProperties.getZap() ;
		api = new ClientApi(zapConfig.getAddress(), zapConfig.getPort(), zapConfig.getApiKey());
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

	public int checkCrawler(String scanId) throws ClientApiException {
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

	public int checkScan(String scanId) throws  ClientApiException {
		
		int progress;
		
		progress =Integer.parseInt(((ApiResponseElement) api.ascan.status(scanId)).getValue());
           
		return progress;
   
	}
}
