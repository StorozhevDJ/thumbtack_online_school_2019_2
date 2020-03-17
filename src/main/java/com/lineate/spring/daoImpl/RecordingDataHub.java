package com.lineate.spring.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lineate.spring.dao.DataStorage;

@Service
public class RecordingDataHub {

    private List<DataStorage> dataStorage;

    @Autowired
    public RecordingDataHub(List<DataStorage> dataStorage) {
        this.dataStorage = dataStorage;
    }

    public String save(String path) {
        StringBuilder sb = new StringBuilder();
        for (DataStorage ds : dataStorage) {
            sb.append(ds.save(path)).append("/r/n");
        }
        return sb.toString();
    }
}
