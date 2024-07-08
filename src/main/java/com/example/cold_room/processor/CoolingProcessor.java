package com.example.cold_room.processor;

import com.example.cold_room.CoolingRepository;
import com.example.cold_room.model.Cooling;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CoolingProcessor {

    private static final Serde<String> STRING_SERDE = Serdes.String();

    private final CoolingRepository repository;

    private final CoolingParser coolingParser;

    @Autowired
    void process(StreamsBuilder streamsBuilder) {
        KStream<String, String> coolingMessageStream = streamsBuilder
                .stream("cooling-room-topic", Consumed.with(STRING_SERDE, STRING_SERDE));

        KStream<String, Cooling> coolingStream = coolingMessageStream.map((k, v) -> {
            Cooling cooling = coolingParser.parse(v);
            repository.save(cooling);
            return KeyValue.pair(cooling.getIdRoom().toString(), cooling);
        });
        messageStream.to("alert-topic", Produced.with(STRING_SERDE, STRING_SERDE));
    }
}
