package com.lineate.spring.daoImpl.channel;

import com.lineate.spring.dao.PublishingChannels;
import com.lineate.spring.models.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class YandexMusicChannel implements PublishingChannels {

    private final static Logger LOGGER = LoggerFactory.getLogger(YandexMusicChannel.class);

    @Override
    public void publish(Recording recording, ZonedDateTime publishAvailableDate) {
        LOGGER.info("Publish recording {} on Yandex Music {}", recording, publishAvailableDate);
    }
}
