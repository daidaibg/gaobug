package com.imantou.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author gaobug
 */
@Getter
@Setter
@ToString
public class PlatformLoginForm {
    private String userIdentification;
    private String identification;
    private Integer loginType;
}
