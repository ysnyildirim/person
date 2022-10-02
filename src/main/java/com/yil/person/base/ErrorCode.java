/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */
package com.yil.person.base;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PersonJobNotFound(3000004, "Kişinin Meslek bilgisi bulunamadı!"),
    PersonEducationNotFound(3000003, "Kişinin Eğitim bilgisi bulunamadı!"),
    EducationNotFound(3000002, "Eğitim durumu bulunamadı!"),
    PersonNotFound(3000001, "Kişi bulunamadı!"),
    GenderNotFound(3000000, "Cinsiyet bulunamadı!"),
    ;
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
