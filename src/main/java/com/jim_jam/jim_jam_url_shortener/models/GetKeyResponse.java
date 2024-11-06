package com.jim_jam.jim_jam_url_shortener.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Model to store data to be returned when a new key needs to be generated
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GetKeyResponse", description = "Model to store data to be returned when a new key needs to be generated")
public class GetKeyResponse {
    @Schema(name = "key", description = "Random key generated", example = "ae34fc")
    private String key;
}
