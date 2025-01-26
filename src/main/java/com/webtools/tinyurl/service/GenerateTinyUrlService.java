package com.webtools.tinyurl.service;

import com.webtools.tinyurl.domain.TinyUrl;
import com.webtools.tinyurl.exception.CannotCreateUrlException;
import com.webtools.tinyurl.dto.OriginalUrlObjectDTO;
import com.webtools.tinyurl.repository.TinyUrlRepository;
import com.webtools.tinyurl.util.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class GenerateTinyUrlService {

    @Autowired
    private TinyUrlRepository tinyUrlRepository;

    public TinyUrl generateTinyUrl(OriginalUrlObjectDTO originalUrlObject){

        try {
            ArrayList<TinyUrl> haystack = (ArrayList<TinyUrl>) tinyUrlRepository.findTinyUrlObjectByOriginalUrl(originalUrlObject.getOriginalUrl());

            if(!haystack.isEmpty()) {
                tinyUrlRepository.activateTinyUrlObjectByHashKey(haystack.get(0).getShortUrlKey());
                return haystack.get(0);
            }

            String shortHash = Transformer.generateHash(originalUrlObject.getOriginalUrl(), true, true);
            TinyUrl tinyUrl = new TinyUrl(shortHash, originalUrlObject.getOriginalUrl());
            tinyUrlRepository.save(tinyUrl);
            return tinyUrl;
        } catch (NoSuchAlgorithmException e) {
            throw new CannotCreateUrlException(e.getMessage());
        } catch ( Exception e ) {
            throw e;
        }
    }
}
