package com.webtools.tinyurl.service;

import com.webtools.tinyurl.domain.TinyUrl;
import com.webtools.tinyurl.exception.CannotDeleteUrlException;
import com.webtools.tinyurl.exception.FailedToDeleteUrlException;
import org.assertj.core.api.Assertions;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.webtools.tinyurl.repository.TinyUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class DeleteTinyUrlServiceTests {

    private static final Logger log = LoggerFactory.getLogger(DeleteTinyUrlServiceTests.class);

    @Mock
    private TinyUrlRepository tinyUrlRepository;

    @InjectMocks
    private DeleteTinyUrlService deleteTinyUrlService;

    private final TinyUrl tinyUrl;

    {
        tinyUrl = new TinyUrl("shortUrlKey", "OriginalLongUrlKey");
    }

    @Test
    public void testThatDeleteTinyUrlObjectByIdSetsIsActiveFalse() {
        when(tinyUrlRepository.findActiveTinyUrlObjectById(tinyUrl.getShortUrlKey())).thenReturn(Optional.of(tinyUrl));
        doNothing().when(tinyUrlRepository).deleteTinyUrlObjectByHashKey(tinyUrl.getShortUrlKey());

        try {
            deleteTinyUrlService.deleteTinyUrlObjectById(tinyUrl.getShortUrlKey());
        } catch (CannotDeleteUrlException | FailedToDeleteUrlException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
