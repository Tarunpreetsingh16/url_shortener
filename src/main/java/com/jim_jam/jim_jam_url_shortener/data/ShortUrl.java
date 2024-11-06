package com.jim_jam.jim_jam_url_shortener.data;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Collection "short_url"
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "short_url")
public class ShortUrl {
    @Id
    private String id;

    private String actualUrl;

    @CreatedDate
    private Instant createdAt;
}
