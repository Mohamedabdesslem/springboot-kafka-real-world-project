package net.javaguides.springboot.kafka.topology;

import net.javaguides.springboot.kafka.Serders.SerdesFactory;
import net.javaguides.springboot.kafka.model.AlphabetWordAggregate;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

public class AgreggateToplogy {

    public static final String AGGREGATE ="aggregate";

    public static Topology buildAgreggateTopolgy(){

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        var inputStream = streamsBuilder.stream(AGGREGATE, Consumed.with(Serdes.String(),Serdes.String()));

        inputStream.print(Printed.<String,String>toSysOut().withLabel(AGGREGATE));

        var groupedString = inputStream
                .groupByKey(Grouped.with(Serdes.String(),Serdes.String()));
                        //.groupBy((key,value)-> value,Grouped.with(Serdes.String(),Serdes.String())); // changer la clé au lieu de celui envoyé

        //exploreCount(groupedString);
        exploreReduce(groupedString);
        //exploreAgreggate(groupedString);
        return streamsBuilder.build();
    }

    private static void exploreAgreggate(KGroupedStream<String, String> groupedString) {
        Initializer<AlphabetWordAggregate>   alphabetWordAggregateInitializer = AlphabetWordAggregate::new;
        Aggregator<String,String,AlphabetWordAggregate> aggregator
                =(key,value,aggregate) -> aggregate.updateNewEvents(key,value);
        var aggregatedStream = groupedString.aggregate(alphabetWordAggregateInitializer,
                aggregator,
                Materialized.<String,AlphabetWordAggregate, KeyValueStore<Bytes,byte[]>>as("aggregated-store")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(SerdesFactory.alphabetWordSerdes())

        );

        aggregatedStream
                .toStream()
                .print(Printed.<String,AlphabetWordAggregate>toSysOut().withLabel("aggregated-words"));
    }

    private static void exploreReduce(KGroupedStream<String, String> groupedString) {

        var modifiedStream = groupedString
                .reduce((value1,value2) -> value1.toUpperCase()+"-"+value2.toUpperCase(),
                        Materialized.as("reduced-words"));

        modifiedStream.toStream().print(Printed.<String,String>toSysOut().withLabel("reduced-words"));
    }

    private static void exploreCount(KGroupedStream<String, String> groupedStream) {

        var countByAlphabet = groupedStream.count(Named.as("count-per-alphabet")
        , Materialized.as("words-count-per-alphabet"));

        countByAlphabet.toStream().print(Printed.<String, Long>toSysOut().withLabel("words-count-per-alphabet"));
    }

}
