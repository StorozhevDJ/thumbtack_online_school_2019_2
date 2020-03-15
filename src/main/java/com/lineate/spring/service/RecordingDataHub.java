package com.lineate.spring.service;

import com.lineate.spring.dao.DataStorage;
import com.lineate.spring.models.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordingDataHub {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordingDataHub.class);

    private DataStorage dataStorage;

    @Autowired
    public RecordingDataHub(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public String save (Recording recording) {
        dataStorage.save("Path to save");
        LOGGER.info("Save recording {}", recording);
        return null;
    }
}
