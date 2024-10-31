package com.dreamx.kosha.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String sahamatiUrl;

    // Getters and Setters
    public String getSahamatiUrl() {
        return sahamatiUrl;
    }

    public void setSahamatiUrl(String url) {
        this.sahamatiUrl = url;
    }
}
