package com.webtools.tinyurl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OriginalUrlObjectDTO {

    @JsonProperty("url")
    private String originalUrl;

    public OriginalUrlObjectDTO() {
    }

    public OriginalUrlObjectDTO(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
