package com.ksu.catcher.zap;

import java.nio.charset.StandardCharsets;

import org.zaproxy.clientapi.core.*;

public class ActiveScan {

    private static final int ZAP_PORT = 8081;
    private static final String ZAP_API_KEY = "6gmd5am8fu72grgtds3lto5scr";
    private static final String ZAP_ADDRESS = "localhost";
    private static final String TARGET = "https://public-firing-range.appspot.com";

    public static void main(String[] args) {

        ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);

        try {
            // TODO : explore the app (Spider, etc) before using the Active Scan API, Refer the explore section
            System.out.println("Active Scanning target : " + TARGET);
            ApiResponse resp = api.ascan.scan(TARGET, "True", "False", null, null, null);
            String scanid;
            int progress;

            // The scan now returns a scan id to support concurrent scanning
            scanid = ((ApiResponseElement) resp).getValue();
            // Poll the status until it completes
            while (true) {
                Thread.sleep(5000);
                progress =
                        Integer.parseInt(
                                ((ApiResponseElement) api.ascan.status(scanid)).getValue());
                System.out.println("Active Scan progress : " + progress + "%");
                if (progress >= 100) {
                    break;
                }
            }

            System.out.println("Active Scan complete");
            // Print vulnerabilities found by the scanning
            System.out.println("Alerts:");
            System.out.println(new String(api.core.xmlreport(), StandardCharsets.UTF_8));

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }
}