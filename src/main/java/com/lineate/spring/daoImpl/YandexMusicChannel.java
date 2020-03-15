package com.lineate.spring.daoImpl;

import com.lineate.spring.dao.PublishingChannels;
import com.lineate.spring.models.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Period;
import java.time.ZonedDateTime;

public class YandexMusicChannel implements PublishingChannels {

    private final static Logger LOGGER = LoggerFactory.getLogger(YandexMusicChannel.class);


    @Override
    public void publish(Recording recording, ZonedDateTime publishAvailableDate) {
        LOGGER.info("Publish recording {} on Yandex Music {}", recording, publishAvailableDate);
        publishAvailableDate.plus(Period.ofWeeks(1));
    }
}
