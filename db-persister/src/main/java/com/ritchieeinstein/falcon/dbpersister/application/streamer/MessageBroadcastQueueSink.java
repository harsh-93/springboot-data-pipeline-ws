package com.ritchieeinstein.falcon.dbpersister.application.streamer;

import com.google.gson.Gson;
import com.ritchieeinstein.falcon.dbpersister.application.request.PayloadRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validation;
import java.util.Set;

@Component
@Validated
@EnableBinding(Sink.class)
public class MessageBroadcastQueueSink {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBroadcastQueueSink.class);
    private static final Gson gson = new Gson();

    @StreamListener(target = Sink.INPUT)
    public void processIncomingPayload(String payload) throws Exception {
        LOGGER.debug(payload);
        PayloadRequest req = gson.fromJson(payload, PayloadRequest.class);
        Set constraints = Validation.buildDefaultValidatorFactory().usingContext().getValidator().validate(req);
        if(constraints.stream().count() > 0){
            for(Object violation: constraints){
                LOGGER.error(violation.toString());
            }
            throw new Exception("Validation Failed");
        }
    }

}