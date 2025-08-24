package net.javaguides.springboot.kafka.Serders;

import net.javaguides.springboot.kafka.model.AlphabetWordAggregate;
import net.javaguides.springboot.kafka.model.Order;
import net.javaguides.springboot.kafka.model.Revenue;
import net.javaguides.springboot.kafka.model.TotalRevenue;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class SerdesFactory {

    public static Serde<Order> orderSerdes() {

        JsonSerializer<Order> jsonSerializer = new JsonSerializer<>();

        JsonDeserializer<Order> jsonDeSerializer = new JsonDeserializer<>(Order.class);
        return  Serdes.serdeFrom(jsonSerializer, jsonDeSerializer);
    }

    public static Serde<Revenue> revenueSerdes() {

        JsonSerializer<Revenue> jsonSerializer = new JsonSerializer<>();

        JsonDeserializer<Revenue> jsonDeSerializer = new JsonDeserializer<>(Revenue.class);
        return  Serdes.serdeFrom(jsonSerializer, jsonDeSerializer);
    }

    public static Serde<AlphabetWordAggregate> alphabetWordSerdes() {

        JsonSerializer<AlphabetWordAggregate> jsonSerializer = new JsonSerializer<>();

        JsonDeserializer<AlphabetWordAggregate> jsonDeSerializer = new JsonDeserializer<>(AlphabetWordAggregate.class);
        return  Serdes.serdeFrom(jsonSerializer, jsonDeSerializer);
    }

    public static Serde<TotalRevenue> totalRevenueSerdes() {

        JsonSerializer<TotalRevenue> jsonSerializer = new JsonSerializer<>();

        JsonDeserializer<TotalRevenue> jsonDeSerializer = new JsonDeserializer<>(TotalRevenue.class);
        return  Serdes.serdeFrom(jsonSerializer, jsonDeSerializer);
    }
}
