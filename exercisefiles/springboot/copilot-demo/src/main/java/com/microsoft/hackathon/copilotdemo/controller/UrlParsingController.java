package com.microsoft.hackathon.copilotdemo.controller;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UrlParsingController {

    @GetMapping("/parse-url")
    public Map<String, String> parseUrl(@RequestParam("url") @Nullable String urlString) {
        Map<String, String> response = new HashMap<>();
        try {
            URL url = new URL(urlString);
            response.put("protocol", url.getProtocol());
            response.put("host", url.getHost());
            response.put("port", url.getPort() == -1 ? "default" : Integer.toString(url.getPort()));
            response.put("path", url.getPath());
            response.put("query", url.getQuery() == null ? "none" : url.getQuery());
        } catch (Exception e) {
            response.put("error", "Invalid URL, use url request param in parse-url endpoint.");
        }
        return response;
    }
}