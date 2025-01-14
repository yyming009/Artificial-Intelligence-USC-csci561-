package homework;

import java.util.LinkedList;
import java.util.List;

public class BFS {
	
	private int numOfLizard = new InputStream().getNumOfLizard();
	private int numOfRange = new InputStream().getNumOfRange();
	private List<int[][]> list = new LinkedList<int[][]>();
	private List<int[][]> result = new LinkedList<int[][]>();
	
	public void copyArray(int[][] origin, int[][] copy) {
		for (int i = 0; i < origin.length; i++) {
			for (int j = 0; j < origin[i].length; j++) {
				copy[i][j] = origin[i][j];
			}
		}
	}

	public List<int[][]> getResult() {
		return result;
	}
	
	public void bfsSearch() {
				
		Node node = new Node();
		int[][] nursery = node.getNode();
				
		//find the number of trees
		int numOfIntervals = 0;
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
		Interval[] intervals = new Interval[numOfIntervals];
		int temp = 0;
		for (int i = 0; i < numOfRange; i++) {	
			int start = 0;
			for (int j = 0; j < numOfRange; j++) {				
				int end = numOfRange - 1;
				if (nursery[i][j] == 2) {
					end = j - 1;
					if (end != -1 && (nursery[i][start] != 2)) {
						intervals[temp++] = new Interval(i, start, end);
					}
					start = j + 1;
				}
				if (j == end && start != numOfRange) {
					intervals[temp++] = new Interval(i, start, end);
				}
			}
			
		}
		
		//System.out.println(intervals[1].start + "+" + intervals[1].end + "+" + intervals[1].row);
		
		
		//BFS carry out
		list.add(nursery);		
		for (Interval interval : intervals) {
			List<int[][]> newList = new LinkedList<int[][]>();
			newList.addAll(list);
			for (int[][] board : list) {
				for (int k = interval.start; k <= interval.end; k++) {
					int[][] checkedBoard = new int[numOfRange][numOfRange];
					copyArray(board, checkedBoard);
					//check condition of no lizard
//					if(k == interval.start && Check.check(checkedBoard)) {
//						newList.add(checkedBoard);
//					}
					checkedBoard[interval.row][k] = 1;
					if (Check.check(checkedBoard)) {
						newList.add(checkedBoard);
					}					
				}
			list = newList;					
			}
		}
		
		for (int[][] board : list) {
			int count = 0;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if(board[i][j] == 1) {
						count++;
					}
				}
			}
			if (count == numOfLizard) {
				result.add(board);
			}
		}
		
		//System.out.println(result.size());		
	}
}
