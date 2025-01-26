package com.webtools.tinyurl.domain;


import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "tiny_url", indexes = @Index(name = "original_url_key_is_active_idx", columnList = "original_url_key, is_active", unique = false))
public class TinyUrl {

    @Id
    private String shortUrlKey;

    private String originalUrlKey;

    private boolean isActive = true;

    public TinyUrl(String shortUrlKey, String originalUrlKey) {
        this.shortUrlKey = shortUrlKey;
        this.originalUrlKey = originalUrlKey;
    }

    public TinyUrl() {

    }

    public String getShortUrlKey() {
        return shortUrlKey;
    }

    public String getOriginalUrlKey() {
        return originalUrlKey;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setShortUrlKey(String shortUrlKey) {
        this.shortUrlKey = shortUrlKey;
    }

    public void setOriginalUrlKey(String originalUrlKey) {
        this.originalUrlKey = originalUrlKey;
    }

    @Override
    public String toString() {
        return "TinyUrl{" +
                "shortUrlKey='" + shortUrlKey + '\'' +
                ", originalUrlKey='" + originalUrlKey + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    /**
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TinyUrl tinyUrl)) return false;
        return isActive == tinyUrl.isActive && Objects.equals(shortUrlKey, tinyUrl.shortUrlKey) && Objects.equals(originalUrlKey, tinyUrl.originalUrlKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShortUrlKey(), getOriginalUrlKey(), isActive());
    }
}
