package solutions;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day5 {
	
	static int[][] diagram;
	static int[][] noDiagDiagram;
	static int largestX = 0;
	static int largestY = 0;
	static int overlappingPoints = 0;
	static int overlappingPointsNoDiag = 0;
	static ArrayList<Point[]> lines = new ArrayList<Point[]>();
	static ArrayList<Integer> straightLineIndexes = new ArrayList<Integer>();
	static ArrayList<Integer> straightNoDiagLineIndexes = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		parseData();
		
		diagram = new int[largestX+1][largestY+1];
		noDiagDiagram = new int[largestX+1][largestY+1];

		markDiagram();
		drawDiagram();
		markDiagramNoDiagonal();
		drawDiagramNoDiag();
		System.out.println("Number of overlapping points: " + overlappingPoints);
		System.out.println("Number of overlapping points without diagonals: " + overlappingPointsNoDiag);
	}
	
	private static void drawDiagram() {
		for(int i = 0; i < largestX+1; i++) {
			for (int j = 0; j < largestY+1; j++) {
				if(diagram[j][i] > 1) {
					overlappingPoints++;
				}
				System.out.print(diagram[j][i]);
			}
			System.out.println();
		}
	}
	
	private static void drawDiagramNoDiag() {
		for(int i = 0; i < largestX+1; i++) {
			for (int j = 0; j < largestY+1; j++) {
				if(noDiagDiagram[j][i] > 1) {
					overlappingPointsNoDiag++;
				}
				System.out.print(noDiagDiagram[j][i]);
			}
			System.out.println();
		}
	}
	
	private static void markDiagramNoDiagonal() {
		for(int i = 0; i < straightNoDiagLineIndexes.size(); i++) {
			Point[] line = lines.get(straightNoDiagLineIndexes.get(i));
			String type = "";
			int largeX = 0;
			int smallX = 0;
			int largeY = 0;
			int smallY = 0;
			
			if (line[0].x == line[1].x) {
				type = "Horizontal";
				largeX = smallX = line[0].x;
			}
			else if(line[0].y == line[1].y){
				type = "Vertical";
				largeY = smallY = line[0].y;
			}
			else {
				type = "Diagonal";
			}
			
			switch (type){
				case "Horizontal":
					if(line[0].y > line[1].y) {
						smallY = line[1].y;
						largeY = line[0].y;
					}
					else {
						smallY = line[0].y;
						largeY = line[1].y;
					}
					for(int j = smallY; j <= largeY; j++) {
						noDiagDiagram[largeX][j]++;
					}
					break;
				case "Vertical":
					if(line[0].x > line[1].x) {
						smallX = line[1].x;
						largeX = line[0].x;
					}
					else {
						smallX = line[0].x;
						largeX = line[1].x;
					}
					for(int j = smallX; j <= largeX; j++) {
						noDiagDiagram[j][largeY]++;
					}
					break;
			}
		}
	}
	
	private static void markDiagram() {
		for(int i = 0; i < straightLineIndexes.size(); i++) {
			Point[] line = lines.get(straightLineIndexes.get(i));
			String type = "";
			int largeX = 0;
			int smallX = 0;
			int largeY = 0;
			int smallY = 0;
			
			if (line[0].x == line[1].x) {
				type = "Horizontal";
				largeX = smallX = line[0].x;
			}
			else if(line[0].y == line[1].y){
				type = "Vertical";
				largeY = smallY = line[0].y;
			}
			else {
				type = "Diagonal";
			}
			
			switch (type){
				case "Horizontal":
					if(line[0].y > line[1].y) {
						smallY = line[1].y;
						largeY = line[0].y;
					}
					else {
						smallY = line[0].y;
						largeY = line[1].y;
					}
					for(int j = smallY; j <= largeY; j++) {
						diagram[largeX][j]++;
					}
					break;
				case "Vertical":
					if(line[0].x > line[1].x) {
						smallX = line[1].x;
						largeX = line[0].x;
					}
					else {
						smallX = line[0].x;
						largeX = line[1].x;
					}
					for(int j = smallX; j <= largeX; j++) {
						diagram[j][largeY]++;
					}
					break;
				case "Diagonal":
					int x1;
					int x2;
					int y1;
					int y2;
					if(line[0].x < line[1].x) {
						x1 = line[0].x;
						y1 = line[0].y;
						x2 = line[1].x;
						y2 = line[1].y;
					}
					else {
						x1 = line[1].x;
						y1 = line[1].y;
						x2 = line[0].x;
						y2 = line[0].y;
					}
					if(y1 < y2) {
						//add
						int runs = x2-x1;
						for(int j = 0; j <= runs; j++) {
							diagram[x1][y1]++;
							x1++;
							y1++;
						}
					}
					else {
						//subtract
						int runs = x2-x1;
						for(int j = 0; j <= runs; j++) {
							diagram[x1][y1]++;
							x1++;
							y1--;
						}
					}
					break;
			}
		}
	}
	
	private static void parseData() {
		File file = new File("C:\\AoC\\vents.txt");
        try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String ventLine;
			int lineIndex = 0;
		    while ((ventLine = reader.readLine()) != null) {
		    	Point[] line = lineStringTo2DArray(ventLine);
		    	lines.add(line);
		    	boolean straightNoDiag = isLineHorizontalOrVertical(line);
		    	boolean straight = isLineHorizontalOrVerticalOrDiagonal(line);
		    	if(straight) {
		    		straightLineIndexes.add(lineIndex);
		    	}
		    	if(straightNoDiag) {
		    		straightNoDiagLineIndexes.add(lineIndex);
		    	}
		    	lineIndex++;
		    }
	    	reader.close();
        }
        catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    
    private static boolean isLineHorizontalOrVerticalOrDiagonal(Point[] line) {
    	boolean straight = false;
    	double rise = (double)(line[0].x-line[1].x);
    	double run = (double)(line[0].y-line[1].y);
    	
    	if((line[0].x == line[1].x) || (line[0].y == line[1].y)) {
    		straight = true;
    	}
    	else if((rise/run == (double) 1) || (rise/run == (double) -1)) {
    		straight = true;
    	}
    	
    	return straight;
    }
    
    private static boolean isLineHorizontalOrVertical(Point[] line) {
    	boolean straight = false;

    	if((line[0].x == line[1].x) || (line[0].y == line[1].y)) {
    		straight = true;
    	}
  	
    	return straight;
    }
    
    private static Point[] lineStringTo2DArray(String ventLine) {
    	String[] temp  = ventLine.split("->");
    	Point[] line = new Point[temp.length];
    	for(int i = 0; i < temp.length; i++) {
    		String[] coordinates = temp[i].split(",");
    		coordinates[0] = coordinates[0].trim();
    		coordinates[1] = coordinates[1].trim();
    		int x = Integer.parseInt(coordinates[0]);
    		int y = Integer.parseInt(coordinates[1]);
    		Point point = new Point(x,y);
    		
    		if (x > largestX) {
    			largestX = x;
    		}
    		if(y > largestY) {
    			largestY = y;
    		}
    		line[i] = point;
    	}
    	return line;
    }
}
