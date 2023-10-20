package com.example.shopshoejavaspring.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenderEnum {
    M("Nam"),
    F("Nữ"),
    O("Khác");

    private final String name;
}
