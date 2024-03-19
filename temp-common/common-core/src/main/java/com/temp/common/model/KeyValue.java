package com.temp.common.model;

import lombok.Data;

/**
 * key value
 *
 * @Author Administrator
 * @Date 2023/9/15 18:18
 **/
@Data
public class KeyValue<T> {

    private String key;

    private T value;

    private T value2;

    public KeyValue() {
    }

    public KeyValue(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue(T value) {
        this.value = value;
    }

    public KeyValue(String key, T value, T value2) {
        this.key = key;
        this.value = value;
        this.value2 = value2;
    }
}
