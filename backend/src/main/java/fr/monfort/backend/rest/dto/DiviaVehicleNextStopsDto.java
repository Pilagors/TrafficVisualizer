package fr.monfort.backend.rest.dto;

import java.util.List;

public record DiviaVehicleNextStopsDto(
    String vehicleId,
    String vehicleLabel,
    String tripId,
    String routeId,
    int directionId,
    long timestamp,
    List<DiviaStopEtaDto> nextStops
) {

}
