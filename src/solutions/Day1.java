package solutions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Day1 {
	  public static void main(String[] args) throws Exception
	    {
	 
	        File file = new File("C:\\AoC\\depths.txt");
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        ArrayList<Integer> depths = new ArrayList<Integer>();
	        String currentDepth;

	        while ((currentDepth = reader.readLine()) != null) {
 	           int depth = Integer.parseInt(currentDepth);  
 	           depths.add(depth);
	        }
	        reader.close();
	        
	        int totalNumberOfIncreases = countIncreases(depths);

	        System.out.println("Number of depth increases: "  + totalNumberOfIncreases);
	        
	        ArrayList<Integer> windows = createWindows(depths);
	        
	        int totalNumberOfIncreasesWindows = countIncreases(windows);
	        
	        System.out.println("Number of depth window increases: " + totalNumberOfIncreasesWindows);
	    }
	  	
	  private static ArrayList<Integer> createWindows(ArrayList<Integer> depths){
		  ArrayList<Integer> windows = new ArrayList<Integer>();
		  int count = 0;
		  int tempSum = 0;
		  for(int i = 0; i <= depths.size()-1; i++) {
			  tempSum += depths.get(i);
			  count++;
			  if (count == 3) {
				  windows.add(tempSum);
				  tempSum = 0;
				  count = 0;
				  i -= 2;
			  }
		  }
		  
		  return windows;
		  
	  }
	  
	  	private static int countIncreases(ArrayList<Integer> depths){
	  		int increases = 0;
	  		for (int i = 0; i < depths.size()-1; i++) {
	        	if (depths.get(i) < depths.get(i+1)) {
	        		increases++;
	        	}
	        }
	  		return increases;
	  	}
}
