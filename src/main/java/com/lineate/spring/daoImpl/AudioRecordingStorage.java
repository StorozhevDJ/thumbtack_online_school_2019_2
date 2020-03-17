package com.lineate.spring.daoImpl;

import com.lineate.spring.dao.DataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AudioRecordingStorage implements DataStorage {

    private final static Logger LOGGER = LoggerFactory.getLogger(AudioRecordingStorage.class);

    @Override
    public String save(String path) {
        if (path == null) return null;
        LOGGER.info("Save Audio recording to path {}", path);
        return path;
    }
}
