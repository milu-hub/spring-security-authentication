package com.example.result.request;

import lombok.Data;

@Data
public class BasePageRequest {
    private Integer page = 1;
    private Integer size = 10;
}
