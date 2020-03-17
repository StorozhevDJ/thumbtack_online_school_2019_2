package com.lineate.spring.service;

import com.lineate.spring.dao.PublishingChannels;
import com.lineate.spring.models.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class PromotionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionService.class);

    private List<PublishingChannels> publishingChannels;

    @Autowired
    public PromotionService(List<PublishingChannels> publishingChannels) {
        this.publishingChannels = publishingChannels;
    }

    public void createCampaign(Recording recording, ZonedDateTime campaignCreateDate) {
        LOGGER.info("createCampaign, recording {}, campaignCreateDate {}", recording, campaignCreateDate);
        for (PublishingChannels pc : publishingChannels) {
            pc.publish(recording, campaignCreateDate);
        }
    }

}
