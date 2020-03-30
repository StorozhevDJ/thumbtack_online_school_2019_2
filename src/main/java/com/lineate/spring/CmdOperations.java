package com.lineate.spring;

import com.lineate.spring.models.Recording;
import com.lineate.spring.service.PromotionService;
import com.lineate.spring.service.RecordingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.ZonedDateTime;

//@Component
public class CmdOperations implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(CmdOperations.class);

    private PromotionService promotionService;
    private RecordingService recordingService;

    //    @Autowired
    public CmdOperations(PromotionService promotionService, RecordingService recordingService) {
        this.promotionService = promotionService;
        this.recordingService = recordingService;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Hello, runner was started");
        Recording recording = new Recording(
                "artist",
                "name",
                "albumName",
                2020,
                new URL("http://asdasd.ru/cover"),
                "genre",
                100,
                new URL("http://asdasd.ru/audio"),
                null);
        recordingService.save(recording);
        promotionService.createCampaign(recording, ZonedDateTime.now());

        recording = new Recording(
                "artist",
                "name",
                "albumName",
                2020,
                new URL("http://asdasd.ru/cover"),
                "genre",
                100,
                null,
                new URL("http://asdasd.ru/video"));
        recordingService.save(recording);
        promotionService.createCampaign(recording, ZonedDateTime.now());

        LOGGER.info("Goodbye, runner was stopped");
    }
}
