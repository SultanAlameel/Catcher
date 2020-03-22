package com.ksu.catcher.zap;

import java.util.*;

import org.zaproxy.clientapi.core.*;

public class Alerts {

    private static final String ZAP_ADDRESS = "localhost";
    private static final int ZAP_PORT = 8080;
    // Change to match the API key set in ZAP, or use NULL if the API key is disabled
    private static final String ZAP_API_KEY = "6gmd5am8fu72grgtds3lto5scr";
    // The URL of the application to be tested
    private static final String TARGET = "https://public-firing-range.appspot.com";

    private static List<String> blackListPlugins = Arrays.asList("1000", "1025");


    public static void main(String[] args) {
        ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);

        try {
            // TODO: Check if the scanning has completed

            // Retrieve the alerts using paging in case there are lots of them
            int start = 0;
            int count = 5000;
            int alertCount = 0;
            ApiResponse resp = api.alert.alerts(TARGET, String.valueOf(start), String.valueOf(count), null);

            while (((ApiResponseList) resp).getItems().size() != 0) {
                System.out.println("Reading " + count + " alerts from " + start);
                alertCount += ((ApiResponseList) resp).getItems().size();

                for (ApiResponse l : (((ApiResponseList) resp).getItems())) {

                    Map<String, ApiResponse> element = ((ApiResponseSet) l).getValuesMap();
                    if (blackListPlugins.contains(element.get("pluginId").toString())) {
                        // TODO: Trigger any relevant postprocessing
                    } else if ("High".equals(element.get("risk").toString())) {
                        // TODO: Trigger any relevant postprocessing
                    } else if ("Informational".equals(element.get("risk").toString())) {
                        // TODO: Ignore all info alerts - some of them may have been downgraded by security annotations
                    }
                }
                start += count;
                resp = api.alert.alerts(TARGET, String.valueOf(start), String.valueOf(count), null);
            }
            System.out.println("Total number of Alerts: " + alertCount);

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}