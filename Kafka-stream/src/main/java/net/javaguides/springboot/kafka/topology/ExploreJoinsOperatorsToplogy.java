package net.javaguides.springboot.kafka.topology;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.kafka.model.Alphabet;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.ValueJoiner;

@Slf4j
public class ExploreJoinsOperatorsToplogy {

    public static final String ALPHABETS ="alphabets"; // A: First letter in the english alphabet

    public static final String ALPHABETS_ABBREVATIONS ="alphabets_abbrevations"; // A=>Apple

    public  static Topology buildTopology(){

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        joinKStreamWithKTable(streamsBuilder);

        return streamsBuilder.build();
    }

    private static void joinKStreamWithKTable(StreamsBuilder streamsBuilder) {

       var alphabetsAbbrevations= streamsBuilder
               .stream(ALPHABETS_ABBREVATIONS,
                       Consumed.with(Serdes.String(),Serdes.String()));

       alphabetsAbbrevations
               .print(Printed.<String,String>toSysOut().withLabel("alphabets_abbrevations"));

       var alphabetsTable= streamsBuilder
                .table(ALPHABETS,
                        Consumed.with(Serdes.String(),Serdes.String()),
                        Materialized.as("alphabets-store"));


        alphabetsTable
                .toStream()
                .print(Printed.<String,String>toSysOut().withLabel("alphabets"));

        ValueJoiner<String, String, Alphabet> valueJoiner = Alphabet::new;



        var joinStream = alphabetsAbbrevations
                .join(alphabetsTable,valueJoiner);

        joinStream.print(Printed.<String,Alphabet>toSysOut().withLabel("alphabets-with-abbreviation"));

    }
}
