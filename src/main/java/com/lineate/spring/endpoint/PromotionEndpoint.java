package com.lineate.spring.endpoint;

import com.lineate.spring.service.PromotionService;
import com.lineate.spring.service.RecordingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/promotion")
public class PromotionEndpoint {

    private PromotionService promotionService;
    private RecordingService recordingService;

    @Autowired
    public PromotionEndpoint(PromotionService promotionService, RecordingService recordingService) {
        this.promotionService = promotionService;
        this.recordingService = recordingService;
    }
}
