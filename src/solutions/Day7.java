package solutions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day7 {
	static int highestPosition;
	static int mostFuelEfficientPosition;
	static int leastTotalFuelSpent;
	static int[] crabPositions = null;

	
	public static void main(String[] args) {
		
		initializeClass();
		
		System.out.print("Crab Positions: ");
		printIntArray(crabPositions);


		System.out.println("Part 1");
		leastTotalFuelSpent = getFuelSpent1to1();		
		System.out.println("Fuel spent: " + leastTotalFuelSpent);
		System.out.println("Most fuel efficient position: " + mostFuelEfficientPosition);

		initializeClass();
		System.out.println("Part 2");
		leastTotalFuelSpent = getFuelIncreasing();
		System.out.println("Fuel spent: " + leastTotalFuelSpent);
		System.out.println("Most fuel efficient position: " + mostFuelEfficientPosition);
	}
	
	private static void initializeClass() {
		if(crabPositions == null) {
			parseData();
		}
		mostFuelEfficientPosition = 2147483647;
		leastTotalFuelSpent = 2147483647;
	}
	
	private static int getFuelSpent1to1() {
		int bestTotalFuelSpent = 2147483647;
		for(int i = 0; i < highestPosition; i++) {
			int totalFuelSpent = 0;
			for(int j = 0; j < crabPositions.length; j++) {
				int fuelSpent = Math.abs(i - crabPositions[j]);
				totalFuelSpent += fuelSpent;
			}
			if(totalFuelSpent < bestTotalFuelSpent) {
				bestTotalFuelSpent = totalFuelSpent;
				mostFuelEfficientPosition = i;
			}
		}
		return bestTotalFuelSpent;
	}
	
	private static int getFuelIncreasing() {
		int bestTotalFuelSpent = 2147483647;
		for(int i = 0; i < highestPosition; i++) {
			int totalFuelSpent = 0;
			for(int j = 0; j < crabPositions.length; j++) {
				int fuelSpent = Math.abs(i - crabPositions[j]);
				totalFuelSpent += (fuelSpent*(fuelSpent+1))/2;
			}
			if(totalFuelSpent < bestTotalFuelSpent) {
				bestTotalFuelSpent = totalFuelSpent;
				mostFuelEfficientPosition = i;
			}
		}
		return bestTotalFuelSpent;
	}
	
	private static void parseData() {
		File file = new File("C:\\AoC\\crabs.txt");
        try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String crabPositionString = reader.readLine();
			reader.close();
			String[] temp = crabPositionString.split(",");
			crabPositions = new int[temp.length];
			for (int i = 0; i < temp.length; i++) {
				int position = Integer.parseInt(temp[i]);
				if (position > highestPosition) {
					highestPosition = position;
				}
				crabPositions[i] = position;
				}
			} 
        catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void printIntArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
}
