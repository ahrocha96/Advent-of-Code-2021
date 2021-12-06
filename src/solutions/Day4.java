package solutions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Day4 {
	
	static int quickestWinningBoard;
	static int slowestWinningBoard;
	static int numberOfTurns;
	static int slowNumberOfTurns;
	static int finalNumber;
	static int bestFinalNumber;
	static int worstFinalNumber;
	static HashMap<Integer, int[]> winningBoardData = new HashMap<Integer, int[]>();
	static HashMap<Integer, int[]> losingBoardData = new HashMap<Integer, int[]>();
	static HashMap<Integer, int[]> recentBoardData = new HashMap<Integer, int[]>();
	
	static int[] draws;
	static ArrayList<int[][]> boards = new ArrayList<int[][]>();
	
	public static void main(String[] args) throws IOException {
        parseData();
        printAllBoards(boards);
        System.out.print("Numbers to be drawn: ");
        printIntArray(draws);
        quickestWinningBoard = findQuickestWinningBoard(boards);
        slowestWinningBoard = findSlowestWinningBoard(boards);
        
        int solution = calculateSolution();
        int solution2 = calculateSolution2();
        
        System.out.println("Part 1");
        System.out.println("Quickest board to win: " + quickestWinningBoard);
        System.out.println("Lowest number of turns: " + numberOfTurns);
        System.out.println("Final number drawn on best board: " + bestFinalNumber);
        System.out.println("Solution (final number on best board * unmarked numbers on best board): " + solution + "\n");
        
        System.out.println("Part 2");
        System.out.println("Slowest board to win: " + slowestWinningBoard);
        System.out.println("Highest number of turns: " + slowNumberOfTurns);
        System.out.println("Final number drawn on worst board: " + worstFinalNumber);
        System.out.println("Solution (final number on worst board * unmarked numbers on worst board): " + solution2);
	}
	
	private static int calculateSolution() {
		
		int unmarkedSum = 0;
		int totalSum = 0;
		int markedSum = 0;
				
		Iterator<Entry<Integer, int[]>> boardDataIterator = winningBoardData.entrySet().iterator();
  
        while (boardDataIterator.hasNext()) {
            Entry<Integer, int[]> mapElement = boardDataIterator.next();
            int[] info = (int[]) mapElement.getValue();
            if(info[2] == 1) {
            	totalSum += (int)mapElement.getKey();
            	markedSum += (int)mapElement.getKey();
            }
            else {
            	totalSum += (int)mapElement.getKey();
            	unmarkedSum += (int)mapElement.getKey();
            }
        }
        
        System.out.println("Sum of all numbers on winning board: " + totalSum);
        System.out.println("Sum of marked numbers on winning board: " + markedSum);
        System.out.println("Sum of unmarked numbers on winning board: " + unmarkedSum);
        
        return unmarkedSum*bestFinalNumber;
	}
	
	private static int calculateSolution2() {
		
		int unmarkedSum = 0;
		int totalSum = 0;
		int markedSum = 0;
				
		Iterator<Entry<Integer, int[]>> boardDataIterator = losingBoardData.entrySet().iterator();
  
        while (boardDataIterator.hasNext()) {
            Entry<Integer, int[]> mapElement = boardDataIterator.next();
            int[] info = (int[]) mapElement.getValue();
            if(info[2] == 1) {
            	totalSum += (int)mapElement.getKey();
            	markedSum += (int)mapElement.getKey();
            }
            else {
            	totalSum += (int)mapElement.getKey();
            	unmarkedSum += (int)mapElement.getKey();
            }
        }
        
        System.out.println("Sum of all numbers on losing board: " + totalSum);
        System.out.println("Sum of marked numbers on losing board: " + markedSum);
        System.out.println("Sum of unmarked numbers on losing board: " + unmarkedSum);
        
        return unmarkedSum*worstFinalNumber;
	}
	
	
	private static int findQuickestWinningBoard(ArrayList<int[][]> boards) {
		int quickestWinningBoard = 0;
		int lowestNumberOfTurns = draws.length;
		
		for(int i = 0; i < boards.size(); i++) {
			int numberOfTurnsToWin = playBingo(boards.get(i));
			if(numberOfTurnsToWin < lowestNumberOfTurns) {
				lowestNumberOfTurns = numberOfTurnsToWin;
				numberOfTurns = lowestNumberOfTurns;
				bestFinalNumber = finalNumber;
				winningBoardData = recentBoardData;
				quickestWinningBoard = i;
			}
		}
		
		return quickestWinningBoard;
	}
	
	private static int findSlowestWinningBoard(ArrayList<int[][]> boards) {
		int slowestWinningBoard = 0;
		int highestNumberOfTurns = 0;
		
		for(int i = 0; i < boards.size(); i++) {
			int numberOfTurnsToWin = playBingo(boards.get(i));
			if(numberOfTurnsToWin > highestNumberOfTurns) {
				highestNumberOfTurns = numberOfTurnsToWin;
				slowNumberOfTurns = highestNumberOfTurns;
				worstFinalNumber = finalNumber;
				losingBoardData = recentBoardData;
				slowestWinningBoard = i;
			}
		}
		
		return slowestWinningBoard;
	}
	
	//returns number of turns to win
	private static int playBingo(int[][] board) {
		int numberOfTurns = 0;
		
		HashMap<Integer, int[]> boardData = new HashMap<Integer, int[]>();
		int[] numbersInEachRowAndColumn = {0,0,0,0,0,0,0,0,0,0};
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				int[] info = {i, j, 0};
				boardData.put(board[j][i], info);
			}
		}
		
		
		for(int i = 0; i < draws.length; i++) {
			if (boardData.containsKey(draws[i])) {
				int[] data = boardData.get(draws[i]);
				data[2] = 1;
				numbersInEachRowAndColumn[data[0]]++;
				numbersInEachRowAndColumn[data[1]+5]++;
				if(gameOver(numbersInEachRowAndColumn)){
					numberOfTurns = i+1;
					finalNumber = draws[i];
					recentBoardData = boardData;
					break;
				}
			}
		}
		
		return numberOfTurns;
	}
	
	private static boolean gameOver(int[] victoryBoard) {
		boolean gameOver = false;
		
		for(int i = 0; i < victoryBoard.length; i++) {
			if(victoryBoard[i] == 5) {
				gameOver = true;
			}
		}
		
		return gameOver;
	}

	private static void parseData() throws IOException {
		File file = new File("C:\\AoC\\Bingo.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        getDraws(reader);
        reader.readLine();
        fillBoardsWithData(reader);
	}
	
	private static void getDraws(BufferedReader reader) throws IOException {
		String drawsString = reader.readLine();
		String[] temp = drawsString.split(",");
		draws = new int[temp.length];
		for (int i = 0; i < temp.length; i++) {
			draws[i] = Integer.parseInt(temp[i]);
		}
	}
	
	private static void fillBoardsWithData(BufferedReader reader) throws IOException {
        String data;
        String board = ""; 
        int boardNumber = 0;
        while ((data = reader.readLine()) != null) {
        	if (data.equals("")) {
        		boardNumber++;
        		int[][] boardArray = convertBoardStringToIntArray(board, boardNumber);
        		boards.add(boardArray);
            	board = "";
        	}
        	else {
        		board += data + " ";
        	}
        }
        
        int[][]boardArray = convertBoardStringToIntArray(board, boardNumber);
        boards.add(boardArray);
	}
	
	private static void printAllBoards(ArrayList<int[][]> boards) {
		for(int i = 0; i < boards.size(); i++)
        {        
        	printBoard(boards.get(i));
        	System.out.println();
        }
	}
	
	private static void printBoard(int[][] array) {
		for(int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(array[j][i] + " ");
			}
			System.out.println();
		}
	}
	
	private static int[][] convertBoardStringToIntArray(String board, int boardNumber){
		int[][] array = new int[5][5];
		board = board.replaceAll("\\s+"," ");
		board = board.trim();
		String[] nums = board.split(" ");
		for(int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++){
				String s = nums[j+i*5].trim();
				array[j][i] = Integer.parseInt(s);
			}
		}
		return array;
	}
	
	private static void printIntArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
}
