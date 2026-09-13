package fr.monfort.backend.rest.dto;

public record DiviaVehiclePositionDto(
  String vehicleId,
  String vehicleLabel,
  double latitude,
  double longitude,
  double bearing,
  double speed,
  String currentStatus,
  String stopId,
  String tripId,
  String routeId,
  int directionId,
  long timestamp
) {

}
