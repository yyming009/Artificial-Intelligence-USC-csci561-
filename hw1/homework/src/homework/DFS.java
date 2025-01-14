package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DFS {
	
	private int numOfLizard = new InputStream().getNumOfLizard();
	private int numOfRange = new InputStream().getNumOfRange();
	private Interval[] intervals;
	private int numOfIntervals;
	int[][] nursery;
    /* A recursive utility function to solve N
       Queen problem */
	
	public void dfsSearch() {
		
		Node node = new Node();
		nursery = node.getNode();
				
		//find the number of trees
		numOfIntervals = 0;
		for (int i = 0; i < numOfRange; i++) {	
			int numOfBlank = 0;
			for (int j = 0; j < numOfRange; j++) {				
				if (nursery[i][j] == 2) {
					if ((j != 0) && (nursery[i][j-1] == 0)) {
						numOfBlank++;
					}
				}
				if ((j == numOfRange - 1) && (nursery[i][j] == 0)) {
					numOfBlank++;
				}
			}
			numOfIntervals += numOfBlank;		
		}
		
		//System.out.println(numOfIntervals);
		
		//add interval into intervals array
		intervals = new Interval[numOfIntervals];
		int temp = 0;
		for (int i = 0; i < numOfRange; i++) {	
			int start = 0;
			for (int j = 0; j < numOfRange; j++) {				
				int end = numOfRange - 1;
				if (nursery[i][j] == 2) {
					end = j - 1;
					if (end != -1 && (nursery[i][start] != 2)) {
						intervals[temp] = new Interval(i, start, end);
						temp++;
					}
					start = j + 1;
				}
				if (j == end && start != numOfRange) {
					intervals[temp] = new Interval(i, start, end);
					temp++;
				}
			}
		}
		
		File f = new File("output.txt");
		try {
			if (f.createNewFile()){
			    System.out.println("File is created!");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("output.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (backTrack(nursery, 0) == false) { 
			System.out.println("FAIL");
		    writer.println("FAIL");
		    writer.close();
        } else {
			System.out.println("OK");
			writer.println("OK");
        	printSolution(nursery);
        	for (int i = 0; i < numOfRange; i++){
                for (int j = 0; j < numOfRange; j++) {
                	writer.print(nursery[i][j]);
                }    
                writer.println();
            }
		    writer.close();
        }
	}
	
	private boolean backTrack(int[][] board, int k) {
		if (numOfLizard(board) == numOfLizard && k <= numOfIntervals) {
			 return true;
		}
		
		if (k >= numOfIntervals) {
			return false;
		}
			
		Interval inte = intervals[k];
		int start = inte.start;
		int end = inte.end;
		int row = inte.row;
		//System.out.println(start + "" + end + "" + row);
		
		for (int i = start; i <= end + 1; i++) {
			if (i != end + 1) { 
				board[row][i] = 1;
			} 
			
			if (Check.check(board)) {
			    
			    if (backTrack(board, k + 1)) {
			        return true;
			    } else {
			    	if (i != end + 1) {
			    		board[row][i] = 0; // BACKTRACK
			    	}
			    }
			     
			} else {
				if (i != end + 1) {
		    		board[row][i] = 0; // BACKTRACK
		    	}
			}
			//System.out.println(board[0][1]);
			//System.out.println(board[1][0]);	
			//System.out.println(board[1][2]);
		}
			
		return false;
	}
	
	
	private int numOfLizard(int board[][]) {
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1) {
					count++;
				}
			}
		}
		return count;
	}
	
	private void printSolution(int board[][]){
        for (int i = 0; i < numOfRange; i++){
            for (int j = 0; j < numOfRange; j++) {
            	System.out.print(board[i][j]);
            }    
            System.out.println();
        }
    }
	
}
