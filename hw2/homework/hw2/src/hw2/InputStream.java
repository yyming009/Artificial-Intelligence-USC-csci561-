package hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputStream {
	
	private int rows;
	private int cols;
	private int numOfFruit;
	private double timeRemain;
	private int[][] board;
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getNumOfFruit() {
		return numOfFruit;
	}
	
	public double getTimeRamain() {
		return timeRemain;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public InputStream() {
			
		ArrayList<String> inputList = new ArrayList<String>();
		
		String pathName = "input.txt";
		File file = new File(pathName);
		
		//System.out.println(file);
		
		
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (input.hasNextLine()) {
		    String text = input.nextLine();
		    inputList.add(text);
		}
		
		//get all argument from input file
		rows = Integer.parseInt(inputList.get(0));
		cols = rows;
		//System.out.println(cols);

		numOfFruit = Integer.parseInt(inputList.get(1));
		//System.out.println(numOfFruit);

		timeRemain = Double.parseDouble(inputList.get(2));
		//System.out.println(timeRemain);
		
		board = new int[rows][cols];
		
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (inputList.get(3+i).charAt(j) == '*') {
					board[i][j] = -1;
				} else {
					board[i][j] = Character.getNumericValue(inputList.get(3+i).charAt(j));
				}
			}
		}
		
		//System.out.println(board[5][5]);
	}

}
