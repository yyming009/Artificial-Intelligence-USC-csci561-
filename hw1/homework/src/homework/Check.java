package homework;

public class Check {
	
	public static boolean check(int[][] stateArray) {
		
		for (int i = 0; i < stateArray.length; i++) {
			int[] rowCheck = new int[stateArray.length];
			int k = 0;
			
			for (int j = 0; j < stateArray[i].length; j++) {
				if (stateArray[i][j] != 0) {
					rowCheck[k] = stateArray[i][j];
					k++;
				}
			}
			if (k > 1) {
				for (int n = 0; n < k - 1; n++) {
					if (rowCheck[n] == 1 && rowCheck[n+1] == 1) {
						return false;
					}
				}
			}
			
		}
		
		for (int j = 0; j < stateArray.length; j++) {
			int[] colCheck = new int[stateArray.length];
			int k = 0;
			
			for (int i = 0; i < stateArray[j].length; i++) {
				if (stateArray[i][j] != 0) {
					colCheck[k] = stateArray[i][j];
					k++;
				}
			}
			if (k > 1) {
				for (int n = 0; n < k - 1; n++) {
					if (colCheck[n] == 1 && colCheck[n+1] == 1) {
						return false;
					}
				}
			}
			
		}
		
		//incline < 0
		int irow = 0, icol = 0;
		while (irow < stateArray.length) {
			int i = irow, j = 0;
			int[] diagCheck = new int[stateArray.length];
			int k = 0;
			while (i < stateArray.length && j < stateArray.length) {			
				if (stateArray[i][j] != 0) {
					diagCheck[k++] = stateArray[i][j];
				}
				
				i++;
				j++;
			}
			if (k > 1) {
				for (int n = 0; n < k - 1; n++) {
					if (diagCheck[n] == 1 && diagCheck[n+1] == 1) {
						return false;
					}
				}
			}
			irow++;
		}
		
		while (icol < stateArray.length) {
			int i = 0, j = icol;
			int[] diagCheck = new int[stateArray.length];
			int k = 0;
			while (j < stateArray.length && i < stateArray.length) {			
				if (stateArray[i][j] != 0) {
					diagCheck[k++] = stateArray[i][j];
				}
				
				i++;
				j++;
			}
			if (k > 1) {
				for (int n = 0; n < k - 1; n++) {
					if (diagCheck[n] == 1 && diagCheck[n+1] == 1) {
						return false;
					}
				}
			}
			icol++;
		}
		
		//incline > 0
		int iirow = 0, iicol = stateArray.length - 1;
		while (iirow < stateArray.length) {
			int i = iirow, j = stateArray.length - 1;
			int[] diagCheck = new int[stateArray.length];
			int k = 0;
			while (j >= iirow && i < stateArray.length) {			
				if (stateArray[i][j] != 0) {
					diagCheck[k++] = stateArray[i][j];
				}
				
				i++;
				j--;
			}
			if (k > 1) {
				for (int n = 0; n < k - 1; n++) {
					if (diagCheck[n] == 1 && diagCheck[n+1] == 1) {
						return false;
					}
				}
			}
			iirow++;
		}
		
		while (iicol >= 0) {
			int i = 0, j = iicol;
			int[] diagCheck = new int[stateArray.length];
			int k = 0;
			while (j >= 0 && i < stateArray.length) {			
				if (stateArray[i][j] != 0) {
					diagCheck[k++] = stateArray[i][j];
				}
				
				i++;
				j--;
			}
			if (k > 1) {
				for (int n = 0; n < k - 1; n++) {
					if (diagCheck[n] == 1 && diagCheck[n+1] == 1) {
						return false;
					}
				}
			}
			iicol--;
		}
		
		return true;		
	}

}
