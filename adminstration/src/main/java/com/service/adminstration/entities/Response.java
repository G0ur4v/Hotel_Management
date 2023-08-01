package com.service.adminstration.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    @Builder.Default
    private boolean success = false;
    @Builder.Default
    private Department department = null;
    @Builder.Default
    private String message = null;
    private int code;
}
