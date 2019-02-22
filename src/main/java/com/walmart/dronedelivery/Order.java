package com.walmart.dronedelivery;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sinshaw
 *
 * 2019
 */
public class Order {

	private String Id;

	private String coordinate;

	private String timestamp;

	public LocalTime getTimestampInLocalTime() {
		return  LocalTime.parse(timestamp);
	}

	public long getDronDeliveryTime() {
		Matcher m = Pattern.compile("(\\d+)\\D+(\\d+)").matcher(coordinate);
		Duration d = null;
		while (m.find()) {
			int dir1Unit = Integer.valueOf(m.group(1));
			int dir2Unit = Integer.valueOf(m.group(2));
			double dronDst = Math.hypot(dir1Unit, dir2Unit);
			d = Duration.of((long) Math.ceil((dronDst * 60)), ChronoUnit.SECONDS);
		}
		return d.getSeconds();

	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Order [Id=" + Id + ", coordinate=" + coordinate + ", timestamp=" + timestamp + "]";
	}

}
