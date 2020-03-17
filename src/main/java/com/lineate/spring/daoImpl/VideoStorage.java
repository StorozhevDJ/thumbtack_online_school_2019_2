package com.lineate.spring.daoImpl;

import com.lineate.spring.dao.DataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VideoStorage implements DataStorage {

    private final static Logger LOGGER = LoggerFactory.getLogger(VideoStorage.class);

    public String save(String path) {
        if (path == null) return null;
        LOGGER.info("Save video to path {}", path);
        return path;
    }
}
