package com.jim_jam.jim_jam_url_shortener.repositories;

import com.jim_jam.jim_jam_url_shortener.data.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository layer to interact with unused_keys collection
 */
@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {
}
