package com.ksu.catcher.zap;

import com.ksu.catcher.service.DomainService;
import org.zaproxy.clientapi.core.ClientApiException;

public class CrawlingStatusThread implements Runnable {
	private String crawlingId;
	private ZapClient zapClient;
	private DomainService domainService ;

	public CrawlingStatusThread(String scanID, ZapClient zapClient, DomainService domainService) {
		this.crawlingId = scanID;
		this.zapClient = zapClient ;
		this.domainService = domainService ;
	}
	
	@Override
	public void run() {
		int progress = 0;

		while (progress < 100) {
			try{
				Thread.sleep(10000);
				progress = zapClient.checkCrawler(crawlingId);
			}catch (ClientApiException e){

			}catch(InterruptedException e){

			}
		}

		if(progress == 100){
			domainService.scan(crawlingId);
		}
	}

}