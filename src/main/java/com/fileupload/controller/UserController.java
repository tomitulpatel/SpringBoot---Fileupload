package com.fileupload.controller;

import com.fileupload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin("*")
@RestController
public class UserController {

    private static final String SCHEME_NAME = "Nippon India Growth Mid Cap Fund - Direct Plan Growth Plan - Growth Option";
   // "Nippon India Growth Mid Cap Fund - Direct Plan Growth Plan - Growth Option"

    private BigDecimal previousNav = new BigDecimal("5100"); // example baseline NAV


    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;




    @GetMapping("/getUser")
    public String getUser(@RequestParam  String emailId) {
        return userService.getUserdetails(emailId) != null ? "User exists with emailId::"+ emailId : "User does not exists";
    }

    @GetMapping("/getNAV")
    public  String getCurrentNAV(@RequestParam  String schemeName) {
        String url = "https://www.amfiindia.com/spages/NAVAll.txt";
        String data = restTemplate.getForObject(url, String.class);

       // String schemeName = "Nippon India Growth Mid Cap Fund - Direct Plan Growth Plan - Growth Option";
        String[] lines = data.split("\n");

        for (String line : lines) {
                if (line.contains(schemeName)) {
                  //  return "Latest NAV for " + schemeName + ": " + line;
                    String[] parts = line.split(";");
                    if (parts.length >= 5) {
                        return parts[4];  // NAV is the 4th column
                    }
                }

        }
        return "NAV not found for " + schemeName;

    }

@Scheduled(cron = "0 0 10 * * ?") // every day at 10 AM
 @GetMapping("/check-drop")
    public String checkNavDrop() {
        String url = "https://www.amfiindia.com/spages/NAVAll.txt";
        String data = restTemplate.getForObject(url, String.class);
      BigDecimal previousNav = new BigDecimal("5300"); // example baseline NAV

     if (data != null) {
            String[] lines = data.split("\n");
            for (String line : lines) {
                if (line.contains(SCHEME_NAME)) {
                    String[] parts = line.split(";");
                    if (parts.length >= 5) {
                        BigDecimal latestNav = new BigDecimal(parts[4]);

                        BigDecimal dropPercentage = previousNav
                                .subtract(latestNav)
                                .divide(previousNav, 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(BigDecimal.valueOf(100));

                        if (dropPercentage.compareTo(BigDecimal.TEN) > 0) {
                            sendSms("Alert: NAV dropped by " +SCHEME_NAME+"  "+ dropPercentage + "%. Current NAV: " + latestNav);
                        }

                        // Optional: Update previous NAV after alerting
                        previousNav = latestNav;
                        return "Checked NAV: " + latestNav + ", Drop: " + dropPercentage + "%";
                    }
                }
            }
        }

        return "NAV not found";
    }

    private void sendSms(String message) {
        String smsUrl = "https://api.msg91.com/api/v2/sendsms";

        // Replace with your actual values
        String authKey = "459102ALCBTGnzB686a43e8P1";
        String senderId = "test284";
        String route = "4";
        String mobileNumber = "919986148047"; // country code + number

        String requestBody = "{\n" +
                "  \"sender\": \"" + senderId + "\",\n" +
                "  \"route\": \"" + route + "\",\n" +
                "  \"country\": \"91\",\n" +
                "  \"sms\": [\n" +
                "    {\n" +
                "      \"message\": \"" + message + "\",\n" +
                "      \"to\": [\"" + mobileNumber + "\"]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authkey", authKey);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(smsUrl, entity, String.class);
            System.out.println("SMS sent: " + response);
        } catch (Exception e) {
            System.out.println("SMS failed: " + e.getMessage());
        }
    }
}



