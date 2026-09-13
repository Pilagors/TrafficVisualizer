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
import com.google.transit.realtime.GtfsRealtime.TripUpdate.StopTimeEvent;
import com.google.transit.realtime.GtfsRealtime.TripUpdate.StopTimeUpdate;

import fr.monfort.backend.rest.controller.TrafficVisualizerController;
import fr.monfort.backend.rest.dto.DiviaStopEtaDto;
import fr.monfort.backend.rest.dto.DiviaVehicleNextStopsDto;

@Service
public class DiviaVehicleNextStopService {

  private static final Logger logger = LoggerFactory.getLogger(TrafficVisualizerController.class);

  public List<DiviaVehicleNextStopsDto> getNextStops() {
    List<DiviaVehicleNextStopsDto> response = new ArrayList<>();

    try {
      URI uri = new URI("https://proxy.transport.data.gouv.fr/resource/divia-dijon-gtfs-rt-trip-update");
      URL url = uri.toURL();
      FeedMessage feed = FeedMessage.parseFrom(url.openStream());

      for (FeedEntity f : feed.getEntityList()) {
        response.add(parseNextStop(f));
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

  private DiviaVehicleNextStopsDto parseNextStop(FeedEntity feed) {
    return new DiviaVehicleNextStopsDto(
      feed.getTripUpdate().getVehicle().getId(),
      feed.getTripUpdate().getVehicle().getLabel(),
      feed.getTripUpdate().getTrip().getTripId(),
      feed.getTripUpdate().getTrip().getRouteId(),
      feed.getTripUpdate().getTrip().getDirectionId(),
      feed.getTripUpdate().getTimestamp(),
      mapNextStops(feed.getTripUpdate().getStopTimeUpdateList())
    );
  }

  private List<DiviaStopEtaDto> mapNextStops(List<StopTimeUpdate> list) {
    return list.stream()
      .map(this::mapStopTimeUpdate)
      .toList();
  }

  private DiviaStopEtaDto mapStopTimeUpdate(StopTimeUpdate stopTimeUpdate) {
    StopTimeEvent arrival = stopTimeUpdate.hasArrival() ? stopTimeUpdate.getArrival() : null;
    StopTimeEvent departure = stopTimeUpdate.hasDeparture() ? stopTimeUpdate.getDeparture() : null;

    return new DiviaStopEtaDto(
      stopTimeUpdate.getStopId(),
      arrival != null && arrival.hasTime() ? arrival.getTime() : null,
      arrival != null && arrival.hasDelay() ? arrival.getDelay() : null,
      departure != null && departure.hasTime() ? departure.getTime() : null,
      departure != null && departure.hasDelay() ? departure.getDelay() : null,
      stopTimeUpdate.getScheduleRelationship().name()
    );
  }
}
