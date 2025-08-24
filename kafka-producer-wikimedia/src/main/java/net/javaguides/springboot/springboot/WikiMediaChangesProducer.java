package net.javaguides.springboot.springboot;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;

import static java.lang.Thread.sleep;

@Service
@Slf4j
public class WikiMediaChangesProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public WikiMediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        EventHandler eventHandler = new WikiMediaChangesHandler(kafkaTemplate,topicName);
        String url ="https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(eventHandler,URI.create(url));
        EventSource eventSource = builder.build();

        eventSource.start();

        sleep(50000);

    }

}
