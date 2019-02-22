package com.walmart.dronedelivery;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sinshaw
 *
 * 2019
 */
public class FileProcessor {

	public List<Order> getInputFile(String inputFilePath) {

		List<Order> inputeFileList = new LinkedList<>();
		try (Stream<String> stream = Files.lines(Paths.get(inputFilePath))) {

			// - Map the text file to list of order object
			// - Loop through the text and split each line in to 3 element
			// - Change it order to order object
			inputeFileList = stream.map(item -> {
				String[] orderProp = item.split(" ");
				Order order = new Order();
				order.setId(orderProp[0]);
				order.setCoordinate(orderProp[1]);
				order.setTimestamp(orderProp[2]);
				return order;
			}).collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputeFileList;

	}

	public void printDeliverySchedule(List<String> output, float nps) {
		try {
			Random random = new Random();
			Path pathToFile = Paths.get("resource/output"+random.nextInt(100000)+".txt");
			Files.createFile(pathToFile);
			
			PrintWriter outputFile = new PrintWriter(new FileWriter(pathToFile.toAbsolutePath().toString()));
			for (String s : output) {
				outputFile.println(s);
			}
			outputFile.println("NPS " + nps);
			outputFile.close();
			System.out.println();
			System.out.println("******* OUTPUT PATH *********");
			System.out.println(pathToFile.toAbsolutePath().toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
