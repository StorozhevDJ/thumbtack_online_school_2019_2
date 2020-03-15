package com.lineate.spring.daoImpl;

import com.lineate.spring.dao.DataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoStorage implements DataStorage {

    private final static Logger LOGGER = LoggerFactory.getLogger(VideoStorage.class);

    public String save(String path) {
        LOGGER.info("Save video to path {}", path);
        return null;
    }
}
