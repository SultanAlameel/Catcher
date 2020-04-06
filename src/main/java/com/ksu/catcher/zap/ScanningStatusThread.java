package com.ksu.catcher.zap;

import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class ScanningStatusThread implements Runnable  {
	
	private String scanID;
	private ZapClient zapClient;
	
	String ZAP_ADDRESS = zapClient.getApplicationProperties().getZap().getAddress();
	int ZAP_PORT = zapClient.getApplicationProperties().getZap().getPort();
	String ZAP_API_KEY = zapClient.getApplicationProperties().getZap().getApiKey();
	
	 ClientApi api = new ClientApi(ZAP_ADDRESS,ZAP_PORT ,ZAP_API_KEY);
	
	
	public ScanningStatusThread(String scanID, ZapClient zapClient) {
		super();
		this.scanID = scanID;
		this.zapClient = zapClient;
	}

	
	@Override
	public void run() {
		
		int progress;

		try {
		while (true) {
            
				Thread.sleep(30000);
				 progress = Integer.parseInt(((ApiResponseElement) api.ascan.status(scanID)).getValue());
				 System.out.println("Active Scan progress : " + progress + "%");
		            
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



/*
	public String getScanID() {
		return scanID;
	}




	public void setScanID(String scanID) {
		this.scanID = scanID;
	}




	public ZapClient getZapClient() {
		return zapClient;
	}




	public void setZapClient(ZapClient zapClient) {
		this.zapClient = zapClient;
	}
	*/		
		
	}

