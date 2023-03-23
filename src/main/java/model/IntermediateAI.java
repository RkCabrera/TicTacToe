package model;

import java.util.Random;

/* IntermediateAI.java
 *  
 * Author: Ryan Cabrera
 */

public class IntermediateAI  implements TicTacToeStrategy {

  private static Random generator;
  
  public IntermediateAI() {
	  generator = new Random();
  }
	
  // start somewhere randomly, find possible win, or block opponent
  @Override
  public OurPoint desiredMove(TicTacToeGame theGame) {
    boolean set = false;
    
    // if AI makes first move, generate random move
    if (theGame.maxMovesRemaining() == 9) {
    	while (!set) {
    		int row = generator.nextInt(3);
    		int col = generator.nextInt(3);
    		if (theGame.available(row, col)) {
    			set = true;
    			return new OurPoint(row, col);
    		}
    	}
    }
    
    // variable holds possible block moves. Only blocks after it is sure
    // there are no possible win moves
    int[] block = new int[2];
    block[0] = -1;
    block[1] = -1;
    
    // checks rows for possible win or block
    for (int i = 0; i < 3; i++) {
    	int oCount = 0;
    	int xCount = 0;
    	int emptyColumn = -1;
    	
    	for (int j = 0; j < 3; j++) {
    		if (theGame.getTicTacToeBoard()[i][j] == 'O') {
    			oCount++;
    		}
    		else if (theGame.getTicTacToeBoard()[i][j] == 'X') {
    			xCount++;
    		}
    		else if (theGame.getTicTacToeBoard()[i][j] == '_') {
    			emptyColumn = j;
    		}
    		// return new point at this row,col if win is possible
    		if (oCount == 2 && xCount == 0 && emptyColumn != -1) {
    			set = true;
    			return new OurPoint(i, emptyColumn);
    		}
    		else if (xCount == 2 && oCount == 0 && emptyColumn != -1) {
    			block[0] = i;
    			block[1] = emptyColumn;
    		}
    	}
    }
    
    // checks columns for possible win or block
    for (int j = 0; j < 3; j++) {
    	int oCount = 0;
    	int xCount = 0;
    	int emptyRow = -1;
    	
    	for (int i = 0; i < 3; i++) {
    		if (theGame.getTicTacToeBoard()[i][j] == 'O') {
    			oCount++;
    		}
    		else if (theGame.getTicTacToeBoard()[i][j] == 'X') {
    			xCount++;
    		}
    		else {
    			emptyRow = i;
    		}
    		// return new point if win is possible
    		if (oCount == 2 && xCount == 0 && emptyRow != -1) {
    			set = true;
    			return new OurPoint(emptyRow, j);
    		}
    		else if (xCount == 2 && oCount == 0) {
    			block[0] = emptyRow;
    			block[1] = j;
    		}
    	}
    }
    
    // checks diagonally for win opportunity or block
    int oCount = 0;
    int xCount = 0;
    int emptyDiagonal = -1;
    
    for (int i = 0; i < 3; i++) {
    	if (theGame.getTicTacToeBoard()[i][i] == 'O') {
    		oCount++;
    	}
    	else if (theGame.getTicTacToeBoard()[i][i] == 'X') {
    		xCount++;
    	}
    	else {
    		emptyDiagonal = i;
    	}
    	if (oCount == 2 && xCount == 0 && emptyDiagonal != -1) {
    		set = true;
    		return new OurPoint(emptyDiagonal, emptyDiagonal);
    	}
    	else if (xCount == 2 && oCount == 0) {
    		block[0] = emptyDiagonal;
    		block[1] = emptyDiagonal;
    	}
    }
    
    // check last possible diagonal
    oCount = 0;
    xCount = 0;
    int emptyRow = -1;
    int emptyColumn = -1;
    
    for (int i = 0; i < 3; i++) {
    	if (theGame.getTicTacToeBoard()[i][2-i] == 'O') {
    		oCount++;
    	}
    	else if (theGame.getTicTacToeBoard()[i][2-i] == 'X') {
    		xCount++;
    	}
    	else {
    		emptyRow = i;
    		emptyColumn = 2-i;
    	}
    	if (oCount == 2 && xCount == 0 && emptyRow != -1 && emptyDiagonal != -1) {
    		set = true;
    		return new OurPoint(emptyRow, emptyColumn);
    	}
    	else if (xCount == 2 && oCount == 0) {
    		block[0] = emptyRow;
    		block[1] = emptyColumn;
    	}
    }
	
    // block opponent from winning
    if (block[0] != -1 && block[1] != -1) {
    	set = true;
    	return new OurPoint(block[0], block[1]);
    }
    
    // else, randomly generate new input
    
    while (!set) {
    	int row = generator.nextInt(3);
    	int col = generator.nextInt(3);
    	if (theGame.available(row, col)) {
    		set = true;
    		return new OurPoint(row, col);
    	}
    }
    
    return null;
  }
}
