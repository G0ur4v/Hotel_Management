package com.service.authentication.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Builder.Default
    private boolean success = false;

    @Builder.Default
    private String message = null;
    @Builder.Default
    private Object department = null;
    @Builder.Default
    private Object item = null;
    @Builder.Default
    private Object room = null;
    @Builder.Default
    private Object staff = null;
    @Builder.Default
    private Object roomService = null;
    @Builder.Default
    private Object reservation = null;
    @Builder.Default
    private Object bill = null;
    @Builder.Default
    private String token = null;
    private int code;
}