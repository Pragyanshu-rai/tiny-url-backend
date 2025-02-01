package com.webtools.tinyurl.service;

import com.webtools.tinyurl.domain.TinyUrl;
import com.webtools.tinyurl.exception.CannotDeleteUrlException;
import com.webtools.tinyurl.exception.FailedToDeleteUrlException;
import com.webtools.tinyurl.repository.TinyUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteTinyUrlService {

    private static final Logger log = LoggerFactory.getLogger(DeleteTinyUrlService.class);
    @Autowired
    private TinyUrlRepository tinyUrlRepository;

    public DeleteTinyUrlService() {
    }

    public DeleteTinyUrlService(TinyUrlRepository tinyUrlRepository) {
        this.tinyUrlRepository = tinyUrlRepository;
    }

    public void deleteTinyUrlObjectById(String shortUrlKey) throws CannotDeleteUrlException, FailedToDeleteUrlException {
        Optional<TinyUrl> tinyUrl = tinyUrlRepository.findActiveTinyUrlObjectById(shortUrlKey);

        if(tinyUrl.isEmpty()) {
            throw new CannotDeleteUrlException("Object Does Not Exists Or Has Been Permanently Deleted");
        }

        try{
            tinyUrlRepository.deleteTinyUrlObjectByHashKey(shortUrlKey);
            log.info("Successfully delete object from db");
        } catch (Exception e) {
            throw new FailedToDeleteUrlException(e.getMessage());
        }
    }
}
