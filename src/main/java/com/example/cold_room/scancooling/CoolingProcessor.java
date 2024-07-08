package com.example.cold_room.scancooling;

import com.example.cold_room.model.Cooling;
import com.example.cold_room.repository.CoolingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
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

        coolingStream.filter((k, v) -> v.getTemperature() >= -20 || !v.getIsCooling() && v.getTemperature() > -35)
                .map((k,v) -> KeyValue.pair(k, v.toString()))
                .to("alert-topic");

        coolingStream.filter((k, v) -> v.getIsCooling() && -35 <= v.getTemperature() && v.getTemperature() <=-21 )
                .map((k,v) -> KeyValue.pair(k, v.toString()))
                .to("warning-topic");
    }

}
