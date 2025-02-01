package com.webtools.tinyurl.service;

import org.mockito.Mock;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import com.webtools.tinyurl.domain.TinyUrl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.webtools.tinyurl.repository.TinyUrlRepository;
import com.webtools.tinyurl.exception.UrlNotFoundException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TinyUrlServiceTests {

    @Mock
    private TinyUrlRepository tinyUrlRepository;

    @InjectMocks
    private TinyUrlService tinyUrlService;

    private final TinyUrl tinyUrl;

    {
        tinyUrl = new TinyUrl("shortUrlKey", "OriginalLongUrlKey");
    }

    @Test
    public void testThatFetchOriginalUrlReturnsTinyUrlObject() {
        when(tinyUrlRepository.findActiveTinyUrlObjectById(tinyUrl.getShortUrlKey())).thenReturn(Optional.of(tinyUrl));

        try {
            TinyUrl fetchedTinyUrl = tinyUrlService.fetchOriginalUrl(tinyUrl.getShortUrlKey());
            Assertions.assertThat(fetchedTinyUrl).isNotNull();
            Assertions.assertThat(fetchedTinyUrl).isEqualTo(tinyUrl);
        } catch (UrlNotFoundException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
