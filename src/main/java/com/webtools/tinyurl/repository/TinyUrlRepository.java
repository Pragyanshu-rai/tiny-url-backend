package com.webtools.tinyurl.repository;

import java.util.List;
import java.util.Optional;
import com.webtools.tinyurl.domain.TinyUrl;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface TinyUrlRepository extends CrudRepository<TinyUrl, String> {

    @Query("SELECT url FROM TinyUrl url WHERE url.originalUrlKey like :originalUrlKey")
    List<TinyUrl> findTinyUrlObjectByOriginalUrl(@Param("originalUrlKey") String originalUrlKey);

    @Query("SELECT url FROM TinyUrl url WHERE url.shortUrlKey = :shortUrlHashKey AND url.isActive = true")
    Optional<TinyUrl> findActiveTinyUrlObjectById(@Param("shortUrlHashKey") String shortUrlHashKey);

    @Transactional
    @Modifying
    @Query("UPDATE TinyUrl SET isActive = true WHERE shortUrlKey = :shortUrlHashKey")
    void activateTinyUrlObjectByHashKey(@Param("shortUrlHashKey") String shortUrlHashKey);

    @Transactional
    @Modifying
    @Query("UPDATE TinyUrl SET isActive = false WHERE shortUrlKey = :shortUrlHashKey")
    void deleteTinyUrlObjectByHashKey(@Param("shortUrlHashKey") String shortUrlHashKey);
}
