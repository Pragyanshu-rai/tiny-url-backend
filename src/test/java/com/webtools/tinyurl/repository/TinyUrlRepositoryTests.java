package com.webtools.tinyurl.repository;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import com.webtools.tinyurl.domain.TinyUrl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TinyUrlRepositoryTests {

    @Autowired
    private TinyUrlRepository tinyUrlRepository;

    private final TinyUrl tinyUrl;
    private TinyUrl savedSecondTinyUrl;

    {
        tinyUrl = new TinyUrl("shortUrlKey", "longOriginalUrl");
    }

    /**
     * this test must run first
     */
    @Test
    public void testThatTinyUrlRepositorySavesData() {
        TinyUrl savedTinyUrl = tinyUrlRepository.save(tinyUrl);

        Assertions.assertThat(savedTinyUrl).isNotNull();
        Assertions.assertThat(savedTinyUrl.isActive()).isTrue();
        Assertions.assertThat(savedTinyUrl).isEqualTo(tinyUrl);
    }

    /**
     * test 1 must run before this one
     *
     */
    @Test
    public void testThatTinyUrlRepositoryFetchesData() {
        TinyUrl savedTinyUrl = tinyUrlRepository.save(tinyUrl);
        savedSecondTinyUrl = tinyUrlRepository.save(new TinyUrl("OtherShortUrlKey", tinyUrl.getOriginalUrlKey()));
        ArrayList<TinyUrl> fetchedTinyUrl = (ArrayList<TinyUrl>)tinyUrlRepository.findTinyUrlObjectByOriginalUrl(tinyUrl.getOriginalUrlKey());

        Assertions.assertThat(fetchedTinyUrl).isNotNull();
        Assertions.assertThat(fetchedTinyUrl.size()).isEqualTo(2);
        Assertions.assertThat(fetchedTinyUrl).containsExactly(tinyUrl, savedSecondTinyUrl);
    }

    @Test
    public void testThatTinyUrlRepositoryDeletesData() {
        TinyUrl savedTinyUrl = tinyUrlRepository.save(tinyUrl);
        savedSecondTinyUrl = tinyUrlRepository.save(new TinyUrl("OtherShortUrlKey", tinyUrl.getOriginalUrlKey()));

        tinyUrlRepository.deleteTinyUrlObjectByHashKey(tinyUrl.getShortUrlKey());
        Optional<TinyUrl> deletedTinyUrl = tinyUrlRepository.findById(tinyUrl.getShortUrlKey());
        System.out.println(deletedTinyUrl.get().toString());

        Assertions.assertThat(deletedTinyUrl).isPresent();
        Assertions.assertThat(deletedTinyUrl.get()).isNotEqualTo(tinyUrl);
        Assertions.assertThat(deletedTinyUrl.get().isActive()).isFalse();
        tinyUrl.setActive(false);
        Assertions.assertThat(deletedTinyUrl.get()).isEqualTo(tinyUrl);
    }

    @Test
    public void testThatTinyUrlRepositoryFetchesActiveData() {
        tinyUrlRepository.save(tinyUrl);
        savedSecondTinyUrl = tinyUrlRepository.save(new TinyUrl("OtherShortUrlKey", tinyUrl.getOriginalUrlKey()));

        tinyUrlRepository.deleteTinyUrlObjectByHashKey(tinyUrl.getShortUrlKey());
        tinyUrlRepository.deleteTinyUrlObjectByHashKey(tinyUrl.getShortUrlKey());
        Optional<TinyUrl> tinyUrlShouldBeNull = tinyUrlRepository.findActiveTinyUrlObjectById(tinyUrl.getShortUrlKey());
        Assertions.assertThat(tinyUrlShouldBeNull).isNotPresent();

        Optional<TinyUrl> tinyUrlShouldNotBeNull = tinyUrlRepository.findActiveTinyUrlObjectById(savedSecondTinyUrl.getShortUrlKey());
        Assertions.assertThat(tinyUrlShouldNotBeNull).isPresent();
        Assertions.assertThat(tinyUrlShouldNotBeNull.get()).isEqualTo(savedSecondTinyUrl);
    }

    @Test
    public void testThatTinyUrlRepositoryActivatesData() {
        tinyUrlRepository.save(tinyUrl);
        tinyUrlRepository.deleteTinyUrlObjectByHashKey(tinyUrl.getShortUrlKey());
        Optional<TinyUrl> deletedTinyUrl = tinyUrlRepository.findById(tinyUrl.getShortUrlKey());

        Assertions.assertThat(deletedTinyUrl).isPresent();
        Assertions.assertThat(deletedTinyUrl.get().getShortUrlKey()).isEqualTo(tinyUrl.getShortUrlKey());

        tinyUrlRepository.activateTinyUrlObjectByHashKey(deletedTinyUrl.get().getShortUrlKey());
        Optional<TinyUrl> activatedTinyUrl = tinyUrlRepository.findActiveTinyUrlObjectById(deletedTinyUrl.get().getShortUrlKey());

        Assertions.assertThat(activatedTinyUrl).isPresent();
        Assertions.assertThat(activatedTinyUrl.get()).isEqualTo(tinyUrl);
    }
}
