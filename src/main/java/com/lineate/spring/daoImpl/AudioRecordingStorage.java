package com.lineate.spring.daoImpl;

import com.lineate.spring.dao.DataStorage;
import com.lineate.spring.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioRecordingStorage implements DataStorage {

    private final static Logger LOGGER = LoggerFactory.getLogger(AudioRecordingStorage.class);

    @Override
    public String save(String path) {
        LOGGER.info("Save audio recording to path {}", path);
        return null;
    }
}
