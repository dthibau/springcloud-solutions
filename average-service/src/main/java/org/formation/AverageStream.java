package org.formation;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface AverageStream {

	String INPUT = "positions-in";
	String OUTPUT = "averages-out";
	
	@Input(INPUT)
	SubscribableChannel inboundPositions();
	
	@Output(OUTPUT)
	MessageChannel outboundAverages();
}
