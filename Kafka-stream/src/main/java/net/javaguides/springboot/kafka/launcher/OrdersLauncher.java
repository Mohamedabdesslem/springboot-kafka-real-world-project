package net.javaguides.springboot.kafka.launcher;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.kafka.exceptionHandler.StreamsDeserializationExceptionHandler;
import net.javaguides.springboot.kafka.exceptionHandler.StreamsProcessorCustomErrorHandler;
import net.javaguides.springboot.kafka.exceptionHandler.StreamsSerializationExceptionHandler;
import net.javaguides.springboot.kafka.topology.OrdersTopology;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class OrdersLauncher {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.streams.orders-id}")
    private String ordersId;


    public void ordersLauncher () {
        Properties properties = new Properties();

        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,this.ordersId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,this.bootstrapServers);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        properties.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, StreamsDeserializationExceptionHandler.class);
        properties.put(StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG, StreamsSerializationExceptionHandler.class);


        KafkaStreams streams = new KafkaStreams(OrdersTopology.buildTopology(),properties);
        streams.setUncaughtExceptionHandler(new StreamsProcessorCustomErrorHandler());


        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        try{
            streams.start();
        } catch (Exception e){
            log.error("Exception démarage stream topolgy {}",e.getMessage(),e);
        }
    }
}
