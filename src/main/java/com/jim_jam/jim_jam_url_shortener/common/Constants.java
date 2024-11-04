package com.jim_jam.jim_jam_url_shortener.common;

import lombok.Getter;

@Getter
public enum Constants {
    UNUSED_KEYS_COLLECTION("unused_keys"),
    USED_KEYS_COLLECTION_NAME("used_keys");

    private final String value;

    Constants(String value) {
        this.value = value;
    }
}
