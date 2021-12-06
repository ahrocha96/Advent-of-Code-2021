package solutions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day3 {
	static int gRate;
    static int eRate;
    static int o2Rating;
    static int co2Rating;
    static String gammaRate = "";
    static String epsilonRate = "";
    static String oxygenGeneratorRating = "";
    static String co2ScrubberRating = "";
    static int powerConsumption = 0;
    static int lifeSupportRating = 0;
	
	public static void main(String[] args) throws IOException {
		 
        File file = new File("C:\\AoC\\DiagnosticReport.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String diagnostic = reader.readLine();
        int numberOfDiagnostics = 0;

        int[] bitCounts = new int[diagnostic.length()];
        ArrayList<String> diagnostics = new ArrayList<String>();
        
        addDiagnosticToBitCount(bitCounts, diagnostic);
        numberOfDiagnostics++;
        diagnostics.add(diagnostic);
        
        while ((diagnostic = reader.readLine()) != null) {
        	addDiagnosticToBitCount(bitCounts, diagnostic);
        	numberOfDiagnostics++;
        	diagnostics.add(diagnostic);
        }
        
        reader.close();
        
        gammaRate = calculateGammaRate(bitCounts, numberOfDiagnostics);
        epsilonRate = calculateEpsilonRate(gammaRate);
        powerConsumption = calculatePowerConsumption(gammaRate, epsilonRate);
        oxygenGeneratorRating = calculateOxygenGeneratorRating(diagnostics);
        co2ScrubberRating = calculateCO2ScrubberRating(diagnostics);
        lifeSupportRating = getLifeSupportRating(oxygenGeneratorRating, co2ScrubberRating);
        
        printResults();
	}
	
	private static String calculateOxygenGeneratorRating(ArrayList<String> diagnostics) {
		ArrayList<String> temp = new ArrayList<String>(diagnostics);
		
		int diagnosticLength = temp.get(0).length();
		
		for(int i = 0; i < diagnosticLength; i++) {
			char mostCommonBit = getMostCommonBitAtSpecifiedPosition(temp, i);
			removeNumbersWithoutSpecifiedBitAtPosition(temp, i, mostCommonBit);
			
			if(temp.size() == 1) {
				break;
			}
		}
		
		return temp.get(0);
	}
	
	private static void removeNumbersWithoutSpecifiedBitAtPosition(ArrayList<String> diagnostics, int position, char bit) {
		for(int i = diagnostics.size()-1; i >= 0; i--) {
			if(diagnostics.get(i).charAt(position) != bit) {
				diagnostics.remove(i);
			}
		}
	}
	
	private static String calculateCO2ScrubberRating(ArrayList<String> diagnostics) {
		ArrayList<String> temp = new ArrayList<String>(diagnostics);
		
		int diagnosticLength = temp.get(0).length();
		
		for(int i = 0; i < diagnosticLength; i++) {
			char leastCommonBit = getLeastCommonBitAtSpecifiedPosition(temp, i);
			removeNumbersWithoutSpecifiedBitAtPosition(temp, i, leastCommonBit);
			
			if(temp.size() == 1) {
				break;
			}
		}
		
		return temp.get(0);
	}
	
	private static char getMostCommonBitAtSpecifiedPosition(ArrayList<String> diagnostics, int position) {
		int count = 0;
		char bit;
		for(int i = 0; i < diagnostics.size(); i++) {
			if(diagnostics.get(i).charAt(position) == '1') {
				count++;
			}
		}
		
		boolean countValueIsMajority = countedValueIsMajority(count, diagnostics.size());
		
		if(countValueIsMajority) {
			bit = '1';
		}
		else 
			bit = '0';
		
		return bit;
	}
	
	private static boolean countedValueIsMajority(int count, int arraySize) {
		if(count >= (arraySize-count)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static boolean countedValueIsMinority(int count, int arraySize) {
		if(count <= (arraySize-count)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static char getLeastCommonBitAtSpecifiedPosition(ArrayList<String> diagnostics, int position) {
		int count = 0;
		char bit;
		for(int i = 0; i < diagnostics.size(); i++) {
			if(diagnostics.get(i).charAt(position) == '0') {
				count++;
			}
		}
		boolean countIsMinority = countedValueIsMinority(count, diagnostics.size());
		if(countIsMinority) {
			bit = '0';
		}
		else 
			bit = '1';
		
		return bit;
	}
	
	private static String calculateGammaRate(int[] bitCounts, int numberOfDiagnostics) {
		String gammaRate = "";
		
		for(int i = 0; i < bitCounts.length; i++) {
			if (bitCounts[i] < numberOfDiagnostics/2) {
				gammaRate += "0";
			}
			else {
				gammaRate += "1";
			}
		}
		
		return gammaRate;
	}
	
	private static int getLifeSupportRating(String oxygenGeneratorRating, String co2ScrubberRating) {
		
		o2Rating = binaryStringToDecimal(oxygenGeneratorRating);
		co2Rating = binaryStringToDecimal(co2ScrubberRating);
		
		return o2Rating*co2Rating;
	}
	
	private static String calculateEpsilonRate(String gammaRate) {
		String epsilonRate = "";
		
		for(int i = 0; i < gammaRate.length(); i++) {
			if(gammaRate.charAt(i) == '1') {
				epsilonRate += "0";
			}
			else {
				epsilonRate += "1";
			}
		}
		
		return epsilonRate;
	}
	
	private static int calculatePowerConsumption(String gammaRate, String epsilonRate) {
		
		gRate = binaryStringToDecimal(gammaRate);
		eRate = binaryStringToDecimal(epsilonRate);
		
		return eRate*gRate;
	}
	
	private static int binaryStringToDecimal(String binary) {
		int decimal = 0;
		int powerOfTwo = 1;
		for(int i = binary.length()-1; i >= 0; i--) {
			if(binary.charAt(i) == '1') {
				decimal += powerOfTwo;
			}
			powerOfTwo *= 2;
		}
		
		return decimal;
	}
	
	private static void addDiagnosticToBitCount(int[] bitCounts, String diagnostic) {
		String[] bits = diagnostic.split("");
		for(int i = 0; i < bitCounts.length; i++) {
			if(bits[i].equals("1")) {
				bitCounts[i] += Integer.parseInt(bits[i]);
			}
		}
	}
	
	private static void printResults() {
		System.out.println("Part 1");
        System.out.println("Gamma Rate: " + gammaRate + "\nEpsilon Rate: " + epsilonRate);
		System.out.println("Gamma rate in decimal: " + gRate);
		System.out.println("Epsilon rate in decimal: " + eRate);
        System.out.println("Submarine power consumption: " + powerConsumption + "\n");
        
        System.out.println("Part 2");
        System.out.println("Oxygen Generator Rating: " + oxygenGeneratorRating + "\nCO2 Scrubber Rating: " + co2ScrubberRating);
		System.out.println("Oxygen generator rating in decimal: " + o2Rating);
		System.out.println("CO2 Scrubber rating in decimal: " + co2Rating);
        System.out.println("Life Support Rating: " + lifeSupportRating);
	}

}
