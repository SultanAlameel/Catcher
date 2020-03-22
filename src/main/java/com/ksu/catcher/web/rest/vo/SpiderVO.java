package com.ksu.catcher.web.rest.vo;




public class SpiderVO {
	
	
	
	 private static final String ZAP_ADDRESS = "localhost";
	    private static final int ZAP_PORT = 8081;
	    // Change to match the API key set in ZAP, or use NULL if the API key is disabled
	    private static final String ZAP_API_KEY = "6gmd5am8fu72grgtds3lto5scr";
	    // The URL of the application to be tested
	    private static String TARGET ;
		
	    
	    
	    public static String getTARGET() {
			return TARGET;
		}



		public static void setTARGET(String tARGET) {
			TARGET = tARGET;
		}



		public static String getZapAddress() {
			return ZAP_ADDRESS;
		}



		public static int getZapPort() {
			return ZAP_PORT;
		}



		public static String getZapApiKey() {
			return ZAP_API_KEY;
		}
			    	
	   //"https://public-firing-range.appspot.com";

}
