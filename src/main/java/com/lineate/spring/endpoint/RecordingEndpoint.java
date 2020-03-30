package com.lineate.spring.endpoint;

import com.lineate.spring.CmdOperations;
import com.lineate.spring.models.Recording;
import com.lineate.spring.service.PromotionService;
import com.lineate.spring.service.RecordingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
//@RequestMapping("/api/recording")
public class RecordingEndpoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(RecordingEndpoint.class);

    private RecordingService recordingService;

    @Autowired
    public RecordingEndpoint(RecordingService recordingService) {
        this.recordingService = recordingService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String tradeIn(@Valid @RequestBody Recording recording) {
        /*Optional<CarForTradeIn> optResult = validator.validate(recording);
        if (!optResult.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect parameters");
        } else*/
        {
            return recordingService.save(recording);
        }
    }

    @RequestMapping(value = "/{delId}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String delete(@PathVariable("delId") short delId) {
        System.out.println("URL open dev " + delId);

        return "asd " + delId;
    }
}
