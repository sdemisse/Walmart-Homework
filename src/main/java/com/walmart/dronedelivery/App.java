package com.walmart.dronedelivery;

/**
 * @author sinshaw
 *
 * 2019
 */
public class App
{
    public static void main( String[] args )
    {
    	if(args.length == 0 || args[0].isEmpty()) {
			System.out.println("Please try again by entering the file name"); 
			System.exit(0);
		}
		String inputFileName =args[0];
		FileProcessor fileProcesor = new FileProcessor();
		OrderProcessor orderProcessor = new OrderProcessor();
		fileProcesor.printDeliverySchedule(orderProcessor.scheduleDelivery(fileProcesor.getInputFile(inputFileName)),orderProcessor.calculateNPS());
		System.out.println("NPS "+orderProcessor.calculateNPS());
    }
}
