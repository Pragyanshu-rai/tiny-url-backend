package com.webtools.tinyurl.service;

import com.webtools.tinyurl.domain.TinyUrl;
import com.webtools.tinyurl.exception.UrlNotFoundException;
import com.webtools.tinyurl.repository.TinyUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TinyUrlService {

    @Autowired
    private TinyUrlRepository tinyUrlRepository;

    public TinyUrlService() {
    }

    public TinyUrlService(TinyUrlRepository tinyUrlRepository) {
        this.tinyUrlRepository = tinyUrlRepository;
    }

    public TinyUrl fetchOriginalUrl(String shortUrlHash) throws UrlNotFoundException {
        Optional<TinyUrl> tinyUrl = this.tinyUrlRepository.findById(shortUrlHash);

        if (tinyUrl.isEmpty()) {
            throw new UrlNotFoundException("Oops, looks like the url you are looking for does not exist.");
        }
        return tinyUrl.get();
    }
}
