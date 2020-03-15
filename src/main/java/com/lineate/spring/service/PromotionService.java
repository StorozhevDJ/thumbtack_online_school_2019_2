package com.lineate.spring.service;

import com.lineate.spring.dao.PublishingChannels;
import com.lineate.spring.models.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

public class PromotionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionService.class);

    private PublishingChannels publishingChannels;

    @Autowired
    public PromotionService (PublishingChannels publishingChannels) {
        this.publishingChannels = publishingChannels;
    }

    public void createCampaign(Recording recording, ZonedDateTime campaignCreateDate) {
        LOGGER.info("createCampaign, recording {}, campaignCreateDate {}", recording, campaignCreateDate);
        publishingChannels.publish(recording, campaignCreateDate);
    }


}
