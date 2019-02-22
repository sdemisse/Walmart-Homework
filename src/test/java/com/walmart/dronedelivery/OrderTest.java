package com.walmart.dronedelivery;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author sinshaw
 *
 * 2019
 */
public class OrderTest {
	
	@Test
	public void testDroneDeliveryTime() {
		Order order = new Order();
		order.setCoordinate("N5E3");
		assertEquals(order.getDronDeliveryTime(), 350L);
	}
	@Test
	public void testDroneDeliveryTimeWithMultipleDigit() {
		Order order = new Order();
		order.setCoordinate("N15E30");
		assertEquals(order.getDronDeliveryTime(), 2013L);
	}
	
	
}
