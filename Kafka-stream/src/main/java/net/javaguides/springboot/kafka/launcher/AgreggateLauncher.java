package net.javaguides.springboot.kafka.launcher;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.kafka.exceptionHandler.StreamsDeserializationExceptionHandler;
import net.javaguides.springboot.kafka.exceptionHandler.StreamsProcessorCustomErrorHandler;
import net.javaguides.springboot.kafka.exceptionHandler.StreamsSerializationExceptionHandler;
import net.javaguides.springboot.kafka.topology.AgreggateToplogy;
import net.javaguides.springboot.kafka.topology.OrdersTopology;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class AgreggateLauncher {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.streams.aggregate}")
    private String aggregateId;

    public void aggregateLauncher () {
        Properties properties = new Properties();

        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,this.aggregateId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,this.bootstrapServers);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        properties.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, StreamsDeserializationExceptionHandler.class);
        properties.put(StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG, StreamsSerializationExceptionHandler.class);


        KafkaStreams streams = new KafkaStreams(AgreggateToplogy.buildAgreggateTopolgy(),properties);
        streams.setUncaughtExceptionHandler(new StreamsProcessorCustomErrorHandler());


        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        try{
            streams.start();
        } catch (Exception e){
            log.error("Exception démarage stream topolgy {}",e.getMessage(),e);
        }
    }

}
