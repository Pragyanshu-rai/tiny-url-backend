package com.webtools.tinyurl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class TinyUrlObjectDTO {

    @JsonProperty("tinyurl")
    private String shortUrlKey;

    public TinyUrlObjectDTO() {

    }

    public TinyUrlObjectDTO(String shortUrlKey) {
        this.shortUrlKey = shortUrlKey;
    }

    public String getShortUrlKey() {
        return shortUrlKey;
    }

    public void setShortUrlKey(String shortUrlKey) {
        this.shortUrlKey = shortUrlKey;
    }
}
