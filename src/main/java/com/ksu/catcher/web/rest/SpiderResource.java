package com.ksu.catcher.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ApiResponseList;
import org.zaproxy.clientapi.core.ClientApi;

import com.ksu.catcher.web.rest.vo.SpiderVO;

@RequestMapping("/spider")
public class SpiderResource {

	
	
	@GetMapping("/{url}")
	public void goSpider( SpiderVO spiderVO,@PathVariable String url) {
		
		
		spiderVO.setTARGET(url);
		
		ClientApi api = new ClientApi(spiderVO.getZapAddress(), spiderVO.getZapPort(), spiderVO.getZapApiKey());

        try {
            // Start spidering the target
            System.out.println("Spidering target : " + spiderVO.getTARGET());
            ApiResponse resp = api.spider.scan(spiderVO.getTARGET(), null, null, null, null);
            String scanID;
            int progress;

            // The scan returns a scan id to support concurrent scanning
            scanID = ((ApiResponseElement) resp).getValue();
            // Poll the status until it completes
            while (true) {
                Thread.sleep(1000);
                progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanID)).getValue());
                System.out.println("Spider progress : " + progress + "%");
                if (progress >= 100) {
                    break;
                }
            }
            System.out.println("Spider completed");
            // If required post process the spider results
            List<ApiResponse> spiderResults = ((ApiResponseList) api.spider.results(scanID)).getItems();

            // TODO: Explore the Application more with Ajax Spider or Start scanning the application for vulnerabilities

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }
		
		
		
	}
	
	
	
	


