package net.javaguides.springboot.kafka.model;

public record Store(String locationId,
                    Address address,
                    String contactNum) {
}
