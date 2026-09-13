package fr.monfort.backend.rest.dto;

public record DiviaStopEtaDto(
    String stopId,
    Long arrivalTime,
    Integer arrivalDelay,
    Long departureTime,
    Integer departureDelay,
    String scheduleRelationship
) {

}
