package com.ksu.catcher.zap;

import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class CrawlingStatusThread implements Runnable {
	private String scanID;
	private ZapClient zapClient;

	public CrawlingStatusThread(String scanID, ZapClient zapClient) {
		this.scanID = scanID;
		this.zapClient = zapClient ;
	}
	
	@Override
	public void run() {
		int progress = 0;

		while (progress < 100) {
			try{
				Thread.sleep(10000);
				progress = zapClient.checkCrawler(scanID);
			}catch (ClientApiException e){

			}catch(InterruptedException e){

			}
		}

		if(progress == 100){
			//start scan
		}
	}

}