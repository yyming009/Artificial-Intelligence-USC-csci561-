package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class CA {
	
	private int numOfLizard = new InputStream().getNumOfLizard();
	private int numOfRange = new InputStream().getNumOfRange();
	private Interval[] intervals;
	private int numOfIntervals;
	
	Node node = new Node();
	int[][] nursery = node.getNode();	
	int[][] temp = new int[numOfRange][numOfRange];
	int totalTrial;   
	
	public CA() {
		
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
		
		intervals = new Interval[numOfIntervals];
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
	}
	
	public void printSolution(){
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
		
		if (caSimulated()) {
			writer.println("OK");
			System.out.println("OK");

			for (int i = 0; i < numOfRange; i++){
	            for (int j = 0; j < numOfRange; j++) {
	            	System.out.print(nursery[i][j]);
	    			writer.print(nursery[i][j]);
	            }    
	            System.out.println();
	            writer.println();
	        }
			
		    writer.close();

		} else {
			System.out.println("FAIL");
			writer.println("FAIL");
		    writer.close();
		}
    }
	
	public boolean initial() {
		
		Random rand = new Random();
	    for (int i = 0; i < numOfIntervals; i++) {
	    	
	    	Interval inte = intervals[i];
	    	int start = inte.start;
			int end = inte.end;
			int row = inte.row;
			int num = 0;
			if(end != 0) {
				num = rand.nextInt(end)%(end-start+1) + start;
			}
			 
			//System.out.println(num);
	        nursery[row][num] = 1;
	        
	        if (i == numOfLizard - 1 ) {
	        	break;
	        }
	        
	        if (i == numOfIntervals - 1 && i < numOfLizard) {
	        	return false;
	        }
	    }
	    
	    return true;
	}

	private boolean checkResult(int[][] nursery) {
		if (Check.check(nursery)){
			return true;
		} else {
			return false;
		}
	}
	
	
	
	private int findCollision(int[][] temp) {
	    int count = 0;
	    for (int k = 0; k < numOfRange * numOfRange; k++) {
	        if (temp[k/numOfRange][k%numOfRange] == 1) {
	        	
	        	int[] rowCheck = new int[numOfRange];
	        	int p0 = 0;
	            for (int j = 0; j < numOfRange; j++){     	            	
	                if (temp[k/numOfRange][j] != 0) {
	                	rowCheck[p0++] = temp[k/numOfRange][j];
	                }
	            }
	            for (int n = 0; n < p0 - 1; n++) {
					if (rowCheck[n] == 1 && rowCheck[n+1] == 1) {
	                	count++;
					}
				}
	        	
	        	int[] colCheck = new int[numOfRange];
	        	int p = 0;
	            for (int i = 0; i < numOfRange; i++){     	            	
	                if (temp[i][k%numOfRange] != 0) {
	                	colCheck[p++] = temp[i][k%numOfRange];
	                }
	            }
	            for (int n = 0; n < p - 1; n++) {
					if (colCheck[n] == 1 && colCheck[n+1] == 1) {
	                	count++;
					}
				}
	                	
	            int[] diaCheck1 = new int[numOfRange];
            	int p1 = 0;
	            for (int i = k/numOfRange, j = k%numOfRange; i < numOfRange && j < numOfRange; i++, j++) {
	            	if (temp[i][j] != 0) {
	            		diaCheck1[p1++] = temp[i][j];
	                	
	            	}
	            }
	            for (int n = 0; n < p1 - 1; n++) {
					if (diaCheck1[n] == 1 && diaCheck1[n+1] == 1) {
	                	count++;
					}
				}
	                	            
	            int[] diaCheck2 = new int[numOfRange];
            	int p2 = 0;
	            for (int i = k/numOfRange, j = k%numOfRange; i >= 0 && j >= 0; i--, j--) {  
	            	if (temp[i][j] != 0) {
	            		diaCheck2[p2++] = temp[i][j];
	            	}
	            }
	            for (int n = 0; n < p2 - 1; n++) {
					if (diaCheck2[n] == 1 && diaCheck2[n+1] == 1) {
	                	count++;
					}
				}
	            
	            int[] diaCheck3 = new int[numOfRange];
            	int p3 = 0;
	            for (int i = k/numOfRange, j = k%numOfRange; i < numOfRange && j >= 0; i++, j--)  {
	            	if (temp[i][j] != 0) {
	            		diaCheck3[p3++] = temp[i][j];
	            	}
	            }
	            for (int n = 0; n < p3 - 1; n++) {
					if (diaCheck3[n] == 1 && diaCheck3[n+1] == 1) {
	                	count++;
					}
				}
	            
	            int[] diaCheck4 = new int[numOfRange];
            	int p4 = 0;
	            for (int i = k/numOfRange, j = k%numOfRange; i >= 0 && j < numOfRange; i--, j++) {   
	            	if (temp[i][j] != 0) {
	            		diaCheck4[p4++] = temp[i][j];
	            	}
	            }
	            for (int n = 0; n < p4 - 1; n++) {
					if (diaCheck4[n] == 1 && diaCheck4[n+1] == 1) {
	                	count++;
					}
				}
	        }
	    }
	    return count;
	}
	
	public boolean caSimulated() {
		double temperature = numOfRange;
	    int trial = 0;
	    
	    while (temperature > 0.00001) {
	    	
	    	int[][] h = new int[numOfRange][numOfRange];
	    	
	    	
	        int	curState = 0;
	        for (int i = 0; i < numOfRange; i++) {
	            for (int j = 0; j < numOfRange; j++) {
	                temp[i][j] = nursery[i][j];
	            }
	        }
	        	        
	        for (int i = 0; i < numOfRange; i++) {				
	            for (int j = 0; j < numOfRange; j++) {
	                
	            	if (temp[i][j] == 1) {
	            		h[i][j] = findCollision(temp);
	            		curState += h[i][j];
	            	}
	            }
	        }
	        //System.out.println(curState);
	        
	        boolean better = false;
	        int next1, next0, nextState = 0;
	        
	        Random rand = new Random();
	        int num = rand.nextInt(Integer.MAX_VALUE);	
	        next1 = num % (numOfRange * numOfRange);
	        next0 = num % (numOfRange * numOfRange);
	        
	        while (nursery[next1/numOfRange][next1%numOfRange] == 2 || nursery[next1/numOfRange][next1%numOfRange] == 0) {
	        	num = rand.nextInt(Integer.MAX_VALUE);	
		        next1 = num % (numOfRange * numOfRange);
	        }
	        while (nursery[next0/numOfRange][next0%numOfRange] == 2 || nursery[next0/numOfRange][next0%numOfRange] == 1) {
	        	num = rand.nextInt(Integer.MAX_VALUE);	
		        next0 = num % (numOfRange * numOfRange);
	        }
	        
	        temp[next0/numOfRange][next0%numOfRange] = 1;
	        temp[next1/numOfRange][next1%numOfRange] = 0;
	        	        
	        for (int i = 0; i < numOfRange; i++) {				
	            for (int j = 0; j < numOfRange; j++) {
	                
	            	if (temp[i][j] == 1) {
	            		h[i][j] = findCollision(temp);
	            		nextState += h[i][j];
	            	}
	            }
	        }
	        //System.out.println(nextState);
	        
	        int E = nextState - curState;
	        if (E < 0) {
	            better = true;
	        } else if (Math.exp((-1)*E/temperature) > ((double)(rand.nextInt(Integer.MAX_VALUE) % 1000) / 1000)) {
	            better = true;
	        }

	        if (better) {                
	        	nursery[next0/numOfRange][next0%numOfRange] = 1;
		        nursery[next1/numOfRange][next1%numOfRange] = 0;	            
	            trial++;
	        }
            
	        if (checkResult(nursery)) {
	            totalTrial += trial;
	            return true;
	        }

	        temperature *= 0.9999;      
	    }
	    
	    return false;
	        
	}
}