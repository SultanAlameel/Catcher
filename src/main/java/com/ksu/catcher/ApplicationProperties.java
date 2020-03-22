package com.ksu.catcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;




//@Component
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
public class ApplicationProperties {

	private Zap zap = new Zap();


	public Zap getZap() {
		return zap;
	}

	public void setZap(Zap zap) {
		this.zap = zap;
	}
	
	
	public static class Zap {
		private String address ;
		private int port ;
		private String apiKey ;
		//private List<String> blackListPlugins;
		
		
		
		
		
		
	
		//public List<String> getBlackListPlugins() {
	//		return blackListPlugins;
		//}
		//public void setBlackListPlugins(List<String> blackListPlugins) {
		//	this.blackListPlugins = blackListPlugins;
		//}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public String getApiKey() {
			return apiKey;
		}
		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}
		
		
	}
}

