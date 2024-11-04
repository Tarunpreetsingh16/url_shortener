package com.jim_jam.jim_jam_url_shortener.models;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Model to store data sent in body when creating a short URL
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GetShortUrlRequest", description = "Model to store data sent in body when creating a short URL")
public class GetShortUrlRequest {
    @Schema(name = "actualUrl", description = "Actual url for which we want short url", example = "http://jim-jam/an1238asd/as91")
    private String actualUrl;
}
