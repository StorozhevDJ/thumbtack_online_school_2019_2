package com.lineate.spring;

import com.lineate.spring.models.Recording;
import com.lineate.spring.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.ZonedDateTime;

@Component
public class CmdOperations implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(CmdOperations.class);

    private final PromotionService promotionService;

    @Autowired
    public CmdOperations(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Hello, runner was started");
        Recording recording = new Recording("artist", "name", "albumName", 2020, new URL("urlCover"), "genre", 100, new URL("urlAudio"), null);
        promotionService.createCampaign(recording, ZonedDateTime.now());
        LOGGER.info("Hello, runner was stopped");
    }
}
