package com.imantou.store.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class OssMessage implements Serializable {

    /**
     * 文件路径
     */
    private String url;

    /**
     * 文件名
     */
    private String filename;
}
