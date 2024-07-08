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

    @Autowired
    void process(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream("cooling-room-topic", Consumed.with(STRING_SERDE, STRING_SERDE));
        messageStream.foreach((k,v) -> {
            System.out.printf("%s%n",v);
            Cooling c = new Cooling();
            repository.save(c);
        });
        messageStream.to("alert-topic", Produced.with(STRING_SERDE, STRING_SERDE));
    }
}
