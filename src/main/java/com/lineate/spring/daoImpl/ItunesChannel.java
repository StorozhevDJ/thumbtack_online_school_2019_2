package com.lineate.spring.daoImpl;

import com.lineate.spring.dao.PublishingChannels;
import com.lineate.spring.models.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Period;
import java.time.ZonedDateTime;

public class ItunesChannel implements PublishingChannels {

    private final static Logger LOGGER = LoggerFactory.getLogger(ItunesChannel.class);

    @Override
    public void publish(Recording recording, ZonedDateTime publishAvailableDate) {
        publishAvailableDate.plus(Period.ofWeeks(1));
    }
}
