package com.walmart.dronedelivery;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * @author sinshaw
 *
 * 2019
 */
public class OrderProcessor {

	/** The promoter count. */
	private float promoterCnt = 0;
	
	/** The detractor count. */
	private float detractorCnt = 0;
	
	/** The total order. */
	private float totalOrder = 0;

	/**
	 * Assumption 1 - The sum of the time taken for all orders to deliver is always
	 * less than or equal to 16 hour
	 * 
	 * Assumption 2- The drone know the distance after the order received -.
	 *
	 */
	public List<String> scheduleDelivery(List<Order> orders) {

		totalOrder = orders.size();
		Queue<Order> queue = new LinkedList<>(orders);
		List<Order> readyToDelivere = new LinkedList<>();
		List<String> outputFile = new ArrayList<>();

		LocalTime firstOrderTimestamp = queue.peek().getTimestampInLocalTime();

		LocalTime deliveryTime = firstOrderTimestamp.isBefore(LocalTime.of(6, 0)) ? LocalTime.of(6, 0, 0)
				: firstOrderTimestamp;

		while (!queue.isEmpty() || !readyToDelivere.isEmpty()) {
			Order od = queue.peek();
			
			if (!queue.isEmpty() && (od.getTimestampInLocalTime().isBefore(deliveryTime)
					|| od.getTimestampInLocalTime().equals(deliveryTime))) {
				readyToDelivere.add(queue.poll());
			} else {
				// When the next order is after the drone complete the order
				// before and stayed idle
				if (!queue.isEmpty() && readyToDelivere.isEmpty()) {
					deliveryTime = queue.peek().getTimestampInLocalTime();
					readyToDelivere.add(queue.poll());
				}
				Order o = readyToDelivere.stream().min(Comparator.comparing(Order::getDronDeliveryTime))
						.orElseThrow(NoSuchElementException::new);
				// waiting time is the sum of waiting time from order placed to schedule
				// delivery
				// and the time takes to delivery
				long waitingTime = ChronoUnit.SECONDS.between(o.getTimestampInLocalTime(), deliveryTime)
						+ o.getDronDeliveryTime();

				calculateScore(waitingTime);
				
				// The time when the drone returned to the station
				LocalTime drownReturnTime = deliveryTime.plusSeconds((o.getDronDeliveryTime() * 2));
				
				outputFile.add(o.getId() + " " + deliveryTime);
				if (drownReturnTime.isBefore(LocalTime.of(22, 00))) {
					deliveryTime = drownReturnTime;
				} else {
					deliveryTime = LocalTime.of(6, 0,0);
				}

				readyToDelivere.remove(o);
			}
		}
		return outputFile;
	}

	/**
	 * Calculate score  for promoter and detractor .
	 *
	 * Order waiting time < 2 hour ==> promoter
	 * Order waiting time < 4 hour and >= 2 ==> neutral
	 * Order waiting time >=4 ==> detractor
	 * 
	 */
	private void calculateScore(long waitingTime) {
		if (waitingTime < 7200) {
			promoterCnt++;
		} else if (waitingTime >= 14400) {
			detractorCnt++;
		}
	}

	/**
	 * Calculate NPS.
	 *
	 * @return net promoter score
	 */
	public float calculateNPS() {
		return (100 * ((promoterCnt - detractorCnt) / totalOrder));

	}

}
