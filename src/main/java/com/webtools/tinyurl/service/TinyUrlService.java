package com.webtools.tinyurl.service;

import com.webtools.tinyurl.domain.TinyUrl;
import com.webtools.tinyurl.exception.UrlNotFoundException;
import com.webtools.tinyurl.repository.TinyUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TinyUrlService {

    private static final Logger log = LoggerFactory.getLogger(TinyUrlService.class);
    @Autowired
    private TinyUrlRepository tinyUrlRepository;

    public TinyUrlService() {
    }

    public TinyUrlService(TinyUrlRepository tinyUrlRepository) {
        this.tinyUrlRepository = tinyUrlRepository;
    }

    public TinyUrl fetchOriginalUrl(String shortUrlHash) throws UrlNotFoundException {
        Optional<TinyUrl> tinyUrl = this.tinyUrlRepository.findActiveTinyUrlObjectById(shortUrlHash);

        if (tinyUrl.isEmpty()) {
            throw new UrlNotFoundException("Oops, looks like the url you are looking for does not exist.");
        }
        log.info("Successfully retrieved original url - {}", tinyUrl.get().getOriginalUrlKey());
        return tinyUrl.get();
    }
}
