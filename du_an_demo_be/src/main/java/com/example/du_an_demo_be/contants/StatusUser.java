package com.example.du_an_demo_be.contants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusUser {

    IN_ACTIVE(0),
    ACTIVE(1);

    private final int value;
}
