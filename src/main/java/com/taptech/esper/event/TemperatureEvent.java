package com.taptech.esper.event;

import java.util.Date;

/**
 * Immutable Temperature Event class. The process control system creates these events.
 */
public class TemperatureEvent {

    /** Temperature in Celcius. */
    private int temperature;
    
    /** Time temerature reading was taken. */
    private Date timeOfReading;
    
    /**
     * Single value constructor.
     * @param value Temperature in Celsius.
     */
    /**
     * Temeratur constructor.
     * @param temperature Temperature in Celsius
     * @param timeOfReading Time of Reading
     */
    public TemperatureEvent(int temperature, Date timeOfReading) {
        this.temperature = temperature;
        this.timeOfReading = timeOfReading;
    }

    /**
     * Get the Temperature.
     * @return Temperature in Celsius
     */
    public int getTemperature() {
        return temperature;
    }
       
    /**
     * Get time Temperature reading was taken.
     * @return Time of Reading
     */
    public Date getTimeOfReading() {
        return timeOfReading;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TemperatureEvent{");
        sb.append("temperature=").append(temperature);
        sb.append(", timeOfReading=").append(timeOfReading);
        sb.append('}');
        return sb.toString();
    }
}
