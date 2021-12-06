package solutions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class Day6 {
	static String totalNumberOfLanternFish;
	static String totalNumberOfEternalFish;
	static int[] school; 
	
	public static void main(String[] args) {
		parseData();
		
		Long[] sortedFish = groupFishByDaysUntilDuplication(school);		
		totalNumberOfLanternFish = calculateNumberOfFishAfterXDays(sortedFish, 80);
		Long[] sortedEternalFish = groupFishByDaysUntilDuplication(school);
		totalNumberOfEternalFish =  calculateNumberOfFishAfterXDays(sortedEternalFish, 256);
		
		System.out.println("Total number of lantern fish: " + totalNumberOfLanternFish);
		System.out.println("Total number of eternal lantern fish: " + totalNumberOfEternalFish);
	}
	
	private static String calculateNumberOfFishAfterXDays(Long[]fish, int numDays) {
		
		for(int i = 0; i < numDays; i++) {
			Long[] temp = fish.clone();
			fish[0] = temp[1];
			fish[1] = temp[2];
			fish[2] = temp[3];
			fish[3] = temp[4];
			fish[4] = temp[5];
			fish[5] = temp[6];
			fish[6] = temp[7]+temp[0];
			fish[7] = temp[8];
			fish[8] = temp[0];	
		}

		BigInteger numFish = new BigInteger("0");
		for(int i = 0; i < fish.length; i++) {
			String s = String.valueOf(fish[i]);
			BigInteger currentGroup = new BigInteger(s);
			numFish = numFish.add(currentGroup);
		}
		
		return numFish.toString();
	}
	
	private static Long[] groupFishByDaysUntilDuplication(int[] fish) {
		Long[] numFishGroupedByDaysUntilDuplication = {0L,0L,0L,0L,0L,0L,0L,0L,0L};

		for(int i = 0; i < fish.length; i++) {
			numFishGroupedByDaysUntilDuplication[fish[i]]++;
		}
		return numFishGroupedByDaysUntilDuplication;
	}
	
	private static void parseData() {
		File file = new File("C:\\AoC\\LanternFish.txt");
        try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String schoolString = reader.readLine();
			reader.close();
			String[] temp = schoolString.split(",");
			school = new int[temp.length];
			for (int i = 0; i < temp.length; i++) {
				school[i] = Integer.parseInt(temp[i]);
				}
			} 
        catch (IOException e) {
			e.printStackTrace();
		}
	}
}