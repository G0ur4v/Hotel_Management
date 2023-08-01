package com.service.inventory.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    @Builder.Default
    private boolean success = false;
    @Builder.Default
    private Staff staff = null;
    @Builder.Default
    private Room room = null;
    @Builder.Default
    private Item item = null;
    @Builder.Default
    private String message = null;
    private int code;
}
