package com.ksu.catcher.zap;

import com.ksu.catcher.service.DomainService;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class ScanningStatusThread implements Runnable  {
	private String scanID;
	private ZapClient zapClient;
	private DomainService domainService ;
	
	public ScanningStatusThread(String scanID, ZapClient zapClient, DomainService domainService) {
		this.scanID = scanID;
		this.zapClient = zapClient;
		this.domainService = domainService ;
	}

	@Override
	public void run() {
		int progress = 0;

		try {
			while(progress < 100){
				Thread.sleep(1000000);
				progress = zapClient.checkScan(scanID);
			}
		}catch (ClientApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (InterruptedException ie){
			//todo handle exception
		}

		if(progress == 100){
			domainService.completeScan(scanID);
		}
	}
}

