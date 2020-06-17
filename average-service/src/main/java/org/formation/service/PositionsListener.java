package org.formation.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.formation.AverageStream;
import org.formation.model.Position;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PositionsListener {
	Logger log = Logger.getLogger(PositionsListener.class.getSimpleName());
	
	private final AverageService averageService;
	
	public PositionsListener(AverageService averageService) {
		this.averageService = averageService;
	}
	
	private Map<Long,Calendar> lastDateTimes = new HashMap<>();
	private Map<Long,List<Position>> lastPositions = new HashMap<>();
	
    @StreamListener(AverageStream.INPUT)
    public void handlePosition(@Payload Position position) {
        log.info("Received position: " + position);
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(position.getTimestamp());

        if ( lastDateTimes.get(position.getId()) == null ) {
        	lastDateTimes.put(position.getId(),_truncateToMinutes(cal));
        }
        log.info("Minute is " + cal.get(Calendar.MINUTE) + " lastMinute was " + lastDateTimes.get(position.getId()).get(Calendar.MINUTE));
        
        if ( lastDateTimes.get(position.getId()).get(Calendar.MINUTE) != cal.get(Calendar.MINUTE) ) {
        	double averageLatitude = lastPositions.get(position.getId()).stream().map(p -> p.getLatitude())
        			.mapToDouble(d->d.doubleValue())
        			.average()
            .orElse(Double.NaN);
        	double averageLongitude = lastPositions.get(position.getId()).stream().map(p -> p.getLongitude())
        			.mapToDouble(d->d.doubleValue())
                    .average()
                    .orElse(Double.NaN);
        	Position averagePosition = new Position().id(position.getId()).latitude(averageLatitude).longitude(averageLongitude).noCommande(position.getNoCommande());
        	        	
        	averagePosition.setTimestamp(lastDateTimes.get(position.getId()).getTimeInMillis());
        	
        	averageService.sendAverage(averagePosition);
        	
        	lastDateTimes.put(position.getId(),_truncateToMinutes(cal));
        	log.info("Last Date time minute : " + lastDateTimes.get(position.getId()));
        	
        	_registerPosition(position,true);
        	
        	
        } else {
        	log.info("Registering position: " + position);
        	_registerPosition(position, false);
        }
        
    }
    
    private Calendar _truncateToMinutes(Calendar cal) {
    	Calendar ret = Calendar.getInstance();
    	ret.setTime(cal.getTime());
    	ret.set(Calendar.SECOND, 0);
    	ret.set(Calendar.MILLISECOND, 0);
    	return ret;
    }
    
    private void _registerPosition(Position position, boolean isFirst) {
    	List<Position> positions = lastPositions.get(position.getId());
    	if ( isFirst || positions == null ) {
    		positions = new ArrayList<>();
    	}
    	positions.add(position);
    	lastPositions.put(position.getId(), positions);
    }
}
