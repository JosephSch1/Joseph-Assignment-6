package com.coderscampus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarService {

	public List<Car> readFile(String file) {
		List<Car> carsList = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yy");
		String line = "";
		try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
			fileReader.readLine();
			while ((line = fileReader.readLine()) != null) {
				String[] info = line.split(",");
				YearMonth date = YearMonth.parse(info[0], formatter);
				Integer sales = Integer.parseInt(info[1]);
				carsList.add(new Car(date, sales));
			}
			return carsList;
		} catch (IOException e) {
			System.out.println("Encountered some sort of IO Exception when reading the file");
			e.printStackTrace();
			return carsList;
		}
	}

	public void salesReport (String model) {
		List<Car> data;
		if (model.equals("Model S")) {
			data = readFile("modelS.csv");
		} else if (model.equals("Model 3")) {
			data = readFile("model3.csv");
		} else {
			data = readFile("modelX.csv");
		}
		
		IntSummaryStatistics report = data.stream()
				.collect(Collectors.summarizingInt(Car::getSale)); // allows the variable report to have very useful .properties
		
		Integer lowest = report.getMin();
		Integer highest = report.getMax();
		Car worst = null;
		Car best = null;
		
		
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getSale().equals(lowest)) {
				 worst = data.get(i);
			} if (data.get(i).getSale().equals(highest)) {
				 best = data.get(i);
			} 
		}
		
		Map<Integer, List<Car>> byYear = data.stream()
				.collect(Collectors.groupingBy(year -> year.getDate().getYear()));
		
		String totalYearlySales = byYear.entrySet().stream()
				.map(map -> map.getKey() + " -> " + map.getValue().stream() // places " -> " between the maps key and value
				.collect(Collectors.summingInt(Car::getSale))) // summingInt will find the sum of a list of integers from the hashmap
				.collect(Collectors.joining("\n")); // creates a line break between the yearly sales report
		
		System.out.println(model + " Yearly Sales Report");
		System.out.println("-----------------");
		System.out.println(totalYearlySales + "\n");
		System.out.println("The best month for " + model + " was " + best.getDate());
		System.out.println("The worst month for " + model + " was " + worst.getDate() + "\n");
	}
}

