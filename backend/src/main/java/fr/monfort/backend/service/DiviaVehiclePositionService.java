package fr.monfort.backend.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;

import fr.monfort.backend.rest.controller.TrafficVisualizerController;
import fr.monfort.backend.rest.dto.DiviaVehiclePositionDto;

@Service
public class DiviaVehiclePositionService {

  private static final Logger logger = LoggerFactory.getLogger(TrafficVisualizerController.class);

  public List<DiviaVehiclePositionDto> getPositions() {
    List<DiviaVehiclePositionDto> response = new ArrayList<>();

    try {
      URI uri = new URI("https://proxy.transport.data.gouv.fr/resource/divia-dijon-gtfs-rt-vehicle-position");
      URL url = uri.toURL();
      FeedMessage feed = FeedMessage.parseFrom(url.openStream());

      for (FeedEntity f : feed.getEntityList()) {
        response.add(parsePosition(f));
      }
    } catch (URISyntaxException e) {
      logger.error("Invalid URI {}", e.getMessage());
    } catch (MalformedURLException e){
      logger.error("Invalid URL {}", e.getMessage());
    } catch (IOException e) {
      logger.error("Erreur lors de lecture stream du feed", e);
    }

    return response;
  }

  private DiviaVehiclePositionDto parsePosition(FeedEntity entity) {
    return new DiviaVehiclePositionDto(
      entity.getVehicle().getVehicle().getId(),
      entity.getVehicle().getVehicle().getLabel(),
      entity.getVehicle().getPosition().getLatitude(),
      entity.getVehicle().getPosition().getLongitude(),
      entity.getVehicle().getPosition().getBearing(),
      entity.getVehicle().getPosition().getSpeed(),
      entity.getVehicle().getCurrentStatus().name(),
      entity.getVehicle().getStopId(),
      entity.getVehicle().getTrip().getTripId(),
      entity.getVehicle().getTrip().getRouteId(),
      entity.getVehicle().getTrip().getDirectionId(),
      entity.getVehicle().getTimestamp()
    );
  }
}
