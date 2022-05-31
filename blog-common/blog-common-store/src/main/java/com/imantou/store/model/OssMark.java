package com.imantou.store.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <achao1441470436@gmail.com>
 * @since 2022/3/22 19:29
 */
@Getter
@Setter
@Builder
public class OssMark implements Serializable {
    private static final long serialVersionUID = -7203883080353694939L;
    private String accessid;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private String expire;

}
