package com.lineate.spring.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lineate.spring.dao.PublishingChannels;
import com.lineate.spring.daoImpl.RecordingDataHub;
import com.lineate.spring.daoImpl.channel.YoutubeMusicChannel;
import com.lineate.spring.models.Recording;

@Component
public class RecordingService {

	private final RecordingDataHub recordingDataHub;
	private final List<PublishingChannels> publishingChannels;

	@Autowired
	public RecordingService(RecordingDataHub recordingDataHubs, List<PublishingChannels> publishingChannels) {
		this.recordingDataHub = recordingDataHubs;
		this.publishingChannels = publishingChannels;
	}

	public String save(Recording recording) {
		String savePath = null;

		if (recording.getUrlAudio() != null) {
			savePath = recording.getUrlAudio().getPath();
			ZonedDateTime publishAvailableDate = ZonedDateTime.now().plusWeeks(1);
			for (PublishingChannels pc : publishingChannels) {
				pc.publish(recording, publishAvailableDate);
			}
		} else if (recording.getUrlVideo() != null) {
			savePath = recording.getUrlVideo().getPath();
			for (PublishingChannels pc : publishingChannels) {
				if (pc.getClass() == YoutubeMusicChannel.class) {
					pc.publish(recording, ZonedDateTime.now().plusWeeks(2));
				}
			}
		}
		if (savePath == null) {
			return null;
		}

		return recordingDataHub.save(savePath);
	}

}
