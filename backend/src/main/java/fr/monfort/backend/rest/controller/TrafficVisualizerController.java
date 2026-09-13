package fr.monfort.backend.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.monfort.backend.rest.dto.DiviaVehicleNextStopsDto;
import fr.monfort.backend.rest.dto.DiviaVehiclePositionDto;
import fr.monfort.backend.service.DiviaVehicleNextStopService;
import fr.monfort.backend.service.DiviaVehiclePositionService;

@RestController
@RequestMapping("/traffic/visualizer/v1")
public class TrafficVisualizerController {

  private DiviaVehiclePositionService positionService;
  private DiviaVehicleNextStopService nextStopService;

  @Autowired
  public void setPositionService(DiviaVehiclePositionService service) {
      this.positionService = service;
  }

  @Autowired
  public void setNextStopService(DiviaVehicleNextStopService nextStopService) {
      this.nextStopService = nextStopService;
  }

  @GetMapping("/positions")
  public List<DiviaVehiclePositionDto> getVehiclesPositions() {
    return positionService.getPositions();
  }

  @GetMapping("/next-stop")
  public List<DiviaVehicleNextStopsDto> getVehiclesNextStops() {
    return nextStopService.getNextStops();
  }
}
