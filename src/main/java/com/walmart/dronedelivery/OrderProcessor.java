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
	 * Assumption 2- The drone know the distance after the order received.
	 * 
	 * Assumption 3 - All the input will be on the correct format
	 *
	 */
	public List<String> scheduleDelivery(List<Order> orders) {

		totalOrder = orders.size();
		Queue<Order> queue = new LinkedList<>(orders);
		List<Order> readyToDelivery = new LinkedList<>();
		List<String> outputFile = new ArrayList<>();

		LocalTime firstOrderTimestamp = queue.peek().getTimestampInLocalTime();

		LocalTime nextDeliveryTime = firstOrderTimestamp.isBefore(LocalTime.of(6, 0)) ? LocalTime.of(6, 0, 0)
				: firstOrderTimestamp;

		while (!queue.isEmpty() || !readyToDelivery.isEmpty()) {
			Order od = queue.peek();

			if (!queue.isEmpty() && (od.getTimestampInLocalTime().isBefore(nextDeliveryTime)
					|| od.getTimestampInLocalTime().equals(nextDeliveryTime))) {
				readyToDelivery.add(queue.poll());
			} else {

				// When the next order is after the drone complete the order
				// before and stayed idle
				if (!queue.isEmpty() && readyToDelivery.isEmpty()) {
					nextDeliveryTime = queue.peek().getTimestampInLocalTime();
					readyToDelivery.add(queue.poll());
				}
				Order o = readyToDelivery.stream().min(Comparator.comparing(Order::getDronDeliveryTime))
						.orElseThrow(NoSuchElementException::new);

				// Waiting time is will be the time between order placed and delivery time plus
				// the time takes to delivery
				long waitingTime = ChronoUnit.SECONDS.between(o.getTimestampInLocalTime(), nextDeliveryTime)
						+ o.getDronDeliveryTime();

				calculateScore(waitingTime);

				// The time when the drone returned to the station
				LocalTime drownReturnTime = nextDeliveryTime.plusSeconds((o.getDronDeliveryTime() * 2));

				outputFile.add(o.getId() + " " + nextDeliveryTime);

				nextDeliveryTime = drownReturnTime;

				readyToDelivery.remove(o);
			}
		}
		return outputFile;
	}

	/**
	 * Calculate score for promoter and detractor .
	 *
	 * Order waiting time < 2 hour ==> Promoter
	 * 
	 * Order waiting time < 4 hour and >= 2==> Neutral
	 * 
	 * Order waiting time >=4 ==> Detractor
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
