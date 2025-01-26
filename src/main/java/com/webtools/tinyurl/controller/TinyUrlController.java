package com.webtools.tinyurl.controller;

import com.webtools.tinyurl.domain.TinyUrl;
import com.webtools.tinyurl.dto.MessageRunnerDTO;
import com.webtools.tinyurl.dto.TinyUrlObjectDTO;
import com.webtools.tinyurl.exception.CannotDeleteUrlException;
import com.webtools.tinyurl.exception.FailedToDeleteUrlException;
import com.webtools.tinyurl.exception.UrlNotFoundException;
import com.webtools.tinyurl.dto.OriginalUrlObjectDTO;
import com.webtools.tinyurl.redis.RedisService;
import com.webtools.tinyurl.service.DeleteTinyUrlService;
import com.webtools.tinyurl.service.GenerateTinyUrlService;
import com.webtools.tinyurl.service.TinyUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class TinyUrlController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private TinyUrlService tinyUrlService;

    @Autowired
    private DeleteTinyUrlService deleteTinyUrlService;

    @Autowired
    private GenerateTinyUrlService generateTinyUrlService;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TinyUrlObjectDTO> generateTinyUrl(@RequestBody final OriginalUrlObjectDTO request) {
        TinyUrl tinyUrl = generateTinyUrlService.generateTinyUrl(request);
        redisService.saveObjectToCache(tinyUrl.getShortUrlKey(), tinyUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TinyUrlObjectDTO(tinyUrl.getShortUrlKey()));
    }

    @GetMapping("{hashCode}")
    @ResponseBody
    public ResponseEntity<OriginalUrlObjectDTO> fetchOriginalUrl(@PathVariable("hashCode") final String hashKey) throws UrlNotFoundException {
        Optional<TinyUrl> tinyUrlCache = redisService.fetchObjectFromCache(hashKey);

        if(tinyUrlCache.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new OriginalUrlObjectDTO(tinyUrlCache.get().getOriginalUrlKey()));
        }
        TinyUrl tinyUrl  = tinyUrlService.fetchOriginalUrl(hashKey);
        redisService.saveObjectToCache(hashKey, tinyUrl);
        return ResponseEntity.status(HttpStatus.OK).body(new OriginalUrlObjectDTO(tinyUrl.getOriginalUrlKey()));
    }

    @DeleteMapping("delete/{hashCode}")
    @ResponseBody
    public ResponseEntity<MessageRunnerDTO> deleteTinyUrlObject(@PathVariable("hashCode") final String urlHashKey) throws CannotDeleteUrlException, FailedToDeleteUrlException {
        redisService.deleteObjectFromCache(urlHashKey);
        deleteTinyUrlService.deleteTinyUrlObjectById(urlHashKey);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageRunnerDTO("Delete Operation Successful"));
    }
}
