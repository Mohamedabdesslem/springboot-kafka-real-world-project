package net.javaguides.springboot.kafka.model;

import java.math.BigDecimal;

public record Revenue (String locationId, BigDecimal finalAmount) {
}
