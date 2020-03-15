package com.lineate.spring.dao;

import com.lineate.spring.models.Recording;

import java.time.ZonedDateTime;

public interface PublishingChannels {

    void publish(Recording recording, ZonedDateTime publishAvailableDate);


}
