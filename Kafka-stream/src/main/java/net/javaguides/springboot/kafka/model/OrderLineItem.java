package net.javaguides.springboot.kafka.model;

import java.math.BigDecimal;

public record OrderLineItem (
        String iteam,

        Integer count,

        BigDecimal amount){
}
