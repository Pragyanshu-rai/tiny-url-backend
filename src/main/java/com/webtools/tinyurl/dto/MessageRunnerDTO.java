package com.webtools.tinyurl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRunnerDTO {

    @JsonProperty("msg")
    private String message;

    public MessageRunnerDTO() {
    }

    public MessageRunnerDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
