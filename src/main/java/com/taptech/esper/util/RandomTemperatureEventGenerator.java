package com.taptech.esper.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.taptech.esper.event.TemperatureEvent;

/**
 * Just a simple class to create a number of Random TemperatureEvents and pass them off to the
 * TemperatureEventHandler.
 */
@Component
public class RandomTemperatureEventGenerator {

    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(RandomTemperatureEventGenerator.class);

    /**
     * Creates simple random Temperature events and lets the implementation class handle them.
     */
    public List<TemperatureEvent> startSendingTemperatureReadings(final long noOfTemperatureEvents) {
        final List<TemperatureEvent> events = new ArrayList<TemperatureEvent>();
        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();
        xrayExecutor.submit(new Runnable() {
            public void run() {
                LOG.debug(getStartingMessage());
                int count = 0;
                while (count < noOfTemperatureEvents) {
                    TemperatureEvent ve = new TemperatureEvent(new Random().nextInt(500), new Date());
                    events.add(ve);
                    count++;
                    /*
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        LOG.error("Thread Interrupted", e);
                    }
                    */
                }
            }
        });
        return events;
    }


    private String getStartingMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* STARTING - ");
        sb.append("\n* PLEASE WAIT - TEMPERATURES ARE RANDOM SO MAY TAKE");
        sb.append("\n* A WHILE TO SEE WARNING AND CRITICAL EVENTS!");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}
