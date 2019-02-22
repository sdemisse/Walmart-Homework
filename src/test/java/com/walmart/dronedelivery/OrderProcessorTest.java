package com.walmart.dronedelivery;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.walmart.dronedelivery.Order;
import com.walmart.dronedelivery.OrderProcessor;

public class OrderProcessorTest {

	OrderProcessor orderProcessor = new OrderProcessor();

	@Test
	public void TestOneScheduleDelivery() {
		List<String> schedule = orderProcessor.scheduleDelivery(getOrdersWithOneOrder());
		assertEquals(schedule.get(0), "WM001 06:00");
		assertEquals(orderProcessor.calculateNPS(), 100.00, 0.1);
	}

	@Test
	public void TestMultipleScheduleDelivery() {
		List<String> schedule = orderProcessor.scheduleDelivery(getOrdersWithMultipleOrder());
		assertEquals(schedule.get(0), "WM002 06:00");
		assertEquals(schedule.get(1), "WM001 06:07:14");
		assertEquals(schedule.get(2), "WM004 06:31:24");
		assertEquals(schedule.get(3), "WM003 06:55:34");
		assertEquals(orderProcessor.calculateNPS(), 75.00, 0.1);
	}
	
	@Test
	public void TestScheduleDeliveryWithLongerPeriodInBetween() {
		List<String> schedule = orderProcessor.scheduleDelivery(getOrdersWithLongerPeriodInBetween());
		assertEquals(schedule.get(0), "WM001 06:00");
		assertEquals(schedule.get(1), "WM002 08:11:55");
		assertEquals(schedule.get(2), "WM003 08:19:09");
		assertEquals(orderProcessor.calculateNPS(), 100.00, 0.1);
	}
	
	@Test
	public void TestScheduleDeliveryWithStartTimeAfter6() {
		List<String> schedule = orderProcessor.scheduleDelivery(getOrdersWithStartTimeAfter6());
		assertEquals(schedule.get(0), "WM001 06:11:51");
		assertEquals(schedule.get(1), "WM002 06:36:01");
		assertEquals(schedule.get(2), "WM003 06:43:15");
		assertEquals(orderProcessor.calculateNPS(), 100.00, 0.1);
	}


	private List<Order> getOrdersWithOneOrder() {
		List<Order> orders = new LinkedList<>();
		Order o1 = new Order();
		o1.setId("WM001");
		o1.setCoordinate("N11W5");
		o1.setTimestamp("05:11:50");
		orders.add(o1);

		return orders;
	}

	private List<Order> getOrdersWithMultipleOrder() {
		List<Order> orders = new LinkedList<>();
		Order o1 = new Order();
		o1.setId("WM001");
		o1.setCoordinate("N11W5");
		o1.setTimestamp("05:11:50");

		Order o2 = new Order();
		o2.setId("WM002");
		o2.setCoordinate("S3E2");
		o2.setTimestamp("05:11:55");

		Order o3 = new Order();
		o3.setId("WM003");
		o3.setCoordinate("N7E50");
		o3.setTimestamp("05:31:50");

		Order o4 = new Order();
		o4.setId("WM004");
		o4.setCoordinate("N11E5");
		o4.setTimestamp("06:11:50");

		orders.add(o1);
		orders.add(o2);
		orders.add(o3);
		orders.add(o4);

		return orders;
	}
	private List<Order> getOrdersWithLongerPeriodInBetween() {
		List<Order> orders = new LinkedList<>();
		Order o1 = new Order();
		o1.setId("WM001");
		o1.setCoordinate("N11W5");
		o1.setTimestamp("05:11:50");

		Order o2 = new Order();
		o2.setId("WM002");
		o2.setCoordinate("S3E2");
		o2.setTimestamp("08:11:55");

		Order o3 = new Order();
		o3.setId("WM003");
		o3.setCoordinate("N7E50");
		o3.setTimestamp("08:12:55");

		orders.add(o1);
		orders.add(o2);
		orders.add(o3);

		return orders;
	}
	
	private List<Order> getOrdersWithStartTimeAfter6() {
		List<Order> orders = new LinkedList<>();
		Order o1 = new Order();
		o1.setId("WM001");
		o1.setCoordinate("N11W5");
		o1.setTimestamp("06:11:51");

		Order o2 = new Order();
		o2.setId("WM002");
		o2.setCoordinate("S3E2");
		o2.setTimestamp("06:19:55");

		Order o3 = new Order();
		o3.setId("WM003");
		o3.setCoordinate("N7E50");
		o3.setTimestamp("06:35:55");

		orders.add(o1);
		orders.add(o2);
		orders.add(o3);

		return orders;
	}

}
