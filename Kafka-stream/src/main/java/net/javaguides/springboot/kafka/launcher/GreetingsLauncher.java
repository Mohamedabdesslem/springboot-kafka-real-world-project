package net.javaguides.springboot.kafka.launcher;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.kafka.topology.GreetingsTopology;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class GreetingsLauncher {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.streams.application-id}")
    private String greetingsId;

    public void exploreKTableLauncher()  {

        Properties properties = new Properties();

        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,this.greetingsId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,this.bootstrapServers);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");

        KafkaStreams streams = new KafkaStreams(GreetingsTopology.buildTopology(),properties);

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        try{
            streams.start();
        } catch (Exception e){
            log.error("Exception démarage stream topolgy {}",e.getMessage(),e);
        }
    }
}
