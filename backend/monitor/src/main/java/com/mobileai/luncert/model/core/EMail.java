package com.mobileai.luncert.model.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EMail {

    private String target;
    
    private String subject;

    private String content;

}