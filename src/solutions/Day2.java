package solutions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Day2 {
	public static void main (String[] args) throws Exception{
	    File file = new File("C:\\AoC\\directions.txt");
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    int horizontalPosition = 0;
	    int verticalPosition = 0;
	    int verticalPositionWithAim = 0;
	    int aim = 0;
	    String direction;
	    
	    while ((direction = reader.readLine()) != null) {
	    	String split[] = direction.split(" ");
	    	switch (split[0]) {
	    	case "forward":
	    		horizontalPosition += Integer.parseInt(split[1]);
	    		verticalPositionWithAim += aim *Integer.parseInt(split[1]);
	    		break;
	    	case "up":
	    		verticalPosition -= Integer.parseInt(split[1]);
	    		aim -= Integer.parseInt(split[1]);
	    		break;
	    	case "down":
	    		verticalPosition += Integer.parseInt(split[1]);
	    		aim += Integer.parseInt(split[1]);
	    		break;
	    	}
	    }
	    reader.close();	
	    System.out.println("Part 1");
	    System.out.println("Vertical Position: " + verticalPosition);
	    System.out.println("Horizontal Position: " + horizontalPosition);
	    System.out.println("Product: " + horizontalPosition*verticalPosition + "\n");
	    System.out.println("Part 2");
	    System.out.println("Vertical Position: " + verticalPositionWithAim);
	    System.out.println("Horizontal Position: " + horizontalPosition);
	    System.out.println("Product: " + verticalPositionWithAim*horizontalPosition);
	}
}