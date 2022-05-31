package com.imantou.store.platform;

import com.imantou.store.FileStorage;
import com.imantou.store.autoconfigure.OssProperties;
import com.imantou.store.model.OssMark;
import com.imantou.store.model.OssMessage;

import java.io.InputStream;

public class LocalStorage implements FileStorage {

    @Override
    public FileStorage init(OssProperties ossProperties) {
        return null;
    }

    @Override
    public OssMessage upload(byte[] data) {
        return null;
    }

    @Override
    public OssMessage upload(InputStream inputStream) {
        return null;
    }

    @Override
    public void delete(String pathName) {

    }

    @Override
    public String getEndpointLink() {
        return null;
    }

    @Override
    public OssMark getMark() {
        return null;
    }
}
