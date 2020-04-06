package com.ksu.catcher.zap;

import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class CrawlingStatusThread implements Runnable {

	private String scanID;
	private ZapClient zapClient;
	
	final String ZAP_ADDRESS = zapClient.getApplicationProperties().getZap().getAddress();
	final int ZAP_PORT = zapClient.getApplicationProperties().getZap().getPort();
	final String ZAP_API_KEY = zapClient.getApplicationProperties().getZap().getApiKey();
	
	 ClientApi api = new ClientApi(ZAP_ADDRESS,ZAP_PORT ,ZAP_API_KEY);

	
	public CrawlingStatusThread(String scanID, ZapClient zapClient) {
		
		this.scanID = scanID;
		this.zapClient = zapClient;
	}
	
	@Override
	public void run() {
			
		
		int progress;
		
		try {
		while (true) {
            
				Thread.sleep(1000);
				 progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanID)).getValue());
		            System.out.println("preparing your website : " + progress + "%");
		            
		            if (progress >= 100) {
		                break;
		            }
			}
		}
		catch (InterruptedException | NumberFormatException | ClientApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	
	
	}