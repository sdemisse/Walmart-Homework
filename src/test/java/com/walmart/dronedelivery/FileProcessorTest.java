package com.walmart.dronedelivery;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class FileProcessorTest {
	FileProcessor fileProcessor = new FileProcessor();
	
	@Test
	public void testGetInputFileTest(){
		List<Order> orders = fileProcessor.getInputFile("resource/testInputFile");
		assertEquals(orders.size(), 4);
		assertEquals(orders.get(1).getCoordinate(), "S3E2");
	}

}
