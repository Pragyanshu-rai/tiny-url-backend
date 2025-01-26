package com.webtools.tinyurl.service;

import java.util.List;
import org.mockito.Mock;
import java.util.ArrayList;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import com.webtools.tinyurl.domain.TinyUrl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.webtools.tinyurl.dto.OriginalUrlObjectDTO;
import com.webtools.tinyurl.repository.TinyUrlRepository;
import org.springframework.test.annotation.DirtiesContext;
import com.webtools.tinyurl.exception.CannotCreateUrlException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GenerateTinyUrlServiceTests {

    @Mock
    private TinyUrlRepository tinyUrlRepository;

    @InjectMocks
    private GenerateTinyUrlService generateTinyUrlService;

    private final TinyUrl tinyUrl;

    {
        tinyUrl = new TinyUrl("shortUrlKey", "OriginalLongUrlKey");
    }

    @Test
    public void testThatGenerateTinyUrlReturnsValidTinyUrlObject() {
        when(tinyUrlRepository.findTinyUrlObjectByOriginalUrl(tinyUrl.getOriginalUrlKey())).thenReturn(new ArrayList<TinyUrl>(List.of(tinyUrl)));
        TinyUrl newTinyUrl = generateTinyUrlService.generateTinyUrl(new OriginalUrlObjectDTO(tinyUrl.getOriginalUrlKey()));

        Assertions.assertThat(newTinyUrl).isEqualTo(tinyUrl);
    }

    @Test
    public void testThatGenerateTinyUrlReturnsNewValidTinyUrlObject() {

        try{
            when(tinyUrlRepository.findTinyUrlObjectByOriginalUrl(tinyUrl.getOriginalUrlKey())).thenReturn(new ArrayList<TinyUrl>());
            lenient().when(tinyUrlRepository.save(tinyUrl)).thenReturn(tinyUrl);
            TinyUrl newTinyUrl = generateTinyUrlService.generateTinyUrl(new OriginalUrlObjectDTO(tinyUrl.getOriginalUrlKey()));

            Assertions.assertThat(newTinyUrl.isActive()).isEqualTo(tinyUrl.isActive());
            Assertions.assertThat(newTinyUrl.getOriginalUrlKey()).isEqualTo(tinyUrl.getOriginalUrlKey());
        } catch (CannotCreateUrlException e) {
            Assertions.fail(e.getMessage());
        }
    }
}