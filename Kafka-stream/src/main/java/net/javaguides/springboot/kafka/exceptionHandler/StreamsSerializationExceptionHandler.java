package net.javaguides.springboot.kafka.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;

import java.util.Map;

@Slf4j
public class StreamsSerializationExceptionHandler implements ProductionExceptionHandler {
    int errorCounter;


    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public ProductionExceptionHandlerResponse handle(ProducerRecord<byte[], byte[]> producerRecord, Exception e) {
        log.error("Exception is : {} , and the kafka record is : {} ",e.getMessage(),producerRecord,e);
        log.error("errorCounter : {}",errorCounter);

        if (errorCounter <2){
            errorCounter++;
            return ProductionExceptionHandlerResponse.CONTINUE;
        }
        return ProductionExceptionHandlerResponse.FAIL;
    }

    @Override
    public ProductionExceptionHandlerResponse handleSerializationException(ProducerRecord record, Exception exception) {
        return ProductionExceptionHandler.super.handleSerializationException(record, exception);
    }
}
