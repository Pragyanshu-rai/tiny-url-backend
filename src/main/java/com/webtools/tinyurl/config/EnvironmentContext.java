package com.webtools.tinyurl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class EnvironmentContext {

    @Value("${environmentContext.shortUrlKeyName}")
    private String shortUrlKeyName;

    @Value("${environmentContext.originalUrlName}")
    private String originalUrlName;

    @Value("${environmentContext.inDevMode}")
    private String inDevMode;

    public String getShortUrlKeyName() {
        return shortUrlKeyName;
    }

    public String getOriginalUrlName() {
        return originalUrlName;
    }

    public boolean isInDevMode() { return Boolean.parseBoolean(inDevMode); }
}
