package hw2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class homework {

	public static void main(String[] args) {
		
		InputStream input = new InputStream();
		int rows = input.getRows();
		int cols = input.getCols();
		int numOfFruit = input.getNumOfFruit();
		double timeRemain = input.getTimeRamain();
		int[][] board = input.getBoard();
		
		gameAgent agent = new gameAgent(rows, cols, numOfFruit, timeRemain, board);
		int[][] result = agent.getResultBoard();
		int[] position = agent.getPosition();
		
		int r = position[0];
		int c = position[1];

		char col = (char) (c + 65);
		System.out.println(col);
		int row = r + 1;
		
		File f = new File("output.txt");
		try {
			if (f.createNewFile()){
			    System.out.println("File is created!");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try{
		    PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		    writer.print(col);
		    writer.println(row);
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					System.out.print(result[i][j]);
					if (result[i][j] == -1) {
						writer.print('*');
					} else {
						writer.print(result[i][j]);
					}
				}
				System.out.println("");
				writer.println();
			}
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}

}
