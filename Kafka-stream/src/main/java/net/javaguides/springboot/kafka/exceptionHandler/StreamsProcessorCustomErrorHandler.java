package net.javaguides.springboot.kafka.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;

@Slf4j
public class StreamsProcessorCustomErrorHandler implements StreamsUncaughtExceptionHandler {
    @Override
    public StreamThreadExceptionResponse handle(Throwable throwable) {
        log.error("Exception in the Application {} ",throwable.getMessage());

        if ( throwable instanceof StreamsException){
            var cause = throwable.getCause();
            if ( cause.getMessage().equals("Transient Error")){
                return StreamThreadExceptionResponse.REPLACE_THREAD;
            }
        }
        return StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
    }
}
