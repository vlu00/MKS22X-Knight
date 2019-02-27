import java.util.ArrayList;

public class KnightBoard {
  private int[][] board; //board with knight placements
  private int[][] moves; //number of possible outgoing moves
  private int rows; //number of rows in board
  private int cols; //number of cols in board
  private int[] x = new int [] {-2, -2, -1, -1, 1, 1, 2, 2}; //possible knight movements in regard to the rows
  private int[] y = new int [] {-1, 1, 2, -2, 2, -2, -1, 1}; //possible knight movements in regard to the cols

  public KnightBoard(int startingRows, int startingCols) {
    if (startingRows <= 0 || startingCols <= 0) {
      throw new IllegalArgumentException();
    }
    rows = startingRows;
    cols = startingCols;
    board = new int[rows][cols];
    for (int r = 0; r < startingRows; r++) {
      for (int c = 0; c < startingCols; c++) {
        board[r][c] = 0; //fill the board with 0
      }
    }
    moves = new int[rows][cols];
    setUpMoves(); //sets up board with outgoing moves
  }

  private void setUpMoves() {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        moves[r][c] = countMoves(r, c);
      }
    }
  }

  private int countMoves(int row, int col) {
    int counter = 0;
    for (int i = 0; i < x.length; i++) {
      if (isOnBoard(row, col, x[i], y[i])) { //if one of the x and y combos are one the board
        counter++;
      }
    }
    return counter;
  }

  public String toString() {
    String display = "";
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (board[r][c] == 0) {
          display += " _";
        }
        else if (rows*cols < 10) {
          display += " " + board[r][c];
        }
        else {
          if (board[r][c] < 10) {
            display += "  " + board[r][c];
          }
          else {
            display += " " + board[r][c];
          }
        }
      }
      display += "\n";
    }
    return display;
  }

  public boolean isException() {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (board[r][c] != 0) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isOnBoard(int row, int col, int vertical, int horizontal) {
    if (row + vertical < 0 || row + vertical > rows-1 || //checks if out of bounds or off the board
        col + horizontal < 0 || col + horizontal > cols-1 ||
        board[row+vertical][col+horizontal] != 0) { //checks if there's already a knight
      return false;
    }
    return true;
  }

  public boolean solve(int startingRow, int startingCol){
    if (isException()) {
      throw new IllegalStateException();
    }
    if (startingRow < 0 || startingRow > rows-1||
        startingCol < 0 || startingCol > cols-1) {
      throw new IllegalArgumentException();
    }
    board[startingRow][startingCol] = 1; //place first knight (given)
    return solveH(startingRow, startingCol, 2); //starting from the second place knights
  }

  public boolean solveH(int row, int col, int level) {
    if (level > rows*cols) { //there's one knight on each square
      return true;
    }
    else {
      ArrayList<Integer> xnew = newX(row, col); //ordered (based on number of moves) possible x movements
      ArrayList<Integer> ynew = newY(row, col); //ordered (based on number of moves) corresponding y movements

      for (int i = 0; i < xnew.size(); i++) {
        board[row+xnew.get(i)][col+ynew.get(i)] = level; //try the combo
        if (solveH(row+xnew.get(i), col+ynew.get(i), level+1)) {
          return true;
        }
        else {
          board[row+xnew.get(i)][col+ynew.get(i)] = 0; //remove if it doesn't work
        }
      }
    }
    return false;
  }

  private ArrayList<Integer> newX (int row, int col) {
    ArrayList<Integer> orderedMoves = new ArrayList<Integer>(); //list of number of outgoing moves
    ArrayList<Integer> newXVal = new ArrayList<Integer>(); //corresonding x movements
    boolean first = true;
    for (int i = 0; i < x.length; i++) {
      if (isOnBoard(row, col, x[i], y[i])) { //knight is on the board
        if (first) { //just add the first one, you have nothing to compare it to
          orderedMoves.add(moves[row+x[i]][col+y[i]]);
          newXVal.add(x[i]);
          first = false;
        }
        else { //all others
          int index = 0;
          while (index < orderedMoves.size() && //compare with number of movecs already on orderedMoves list
                 moves[row+x[i]][col+y[i]] > orderedMoves.get(index)) {
            index++; //find the right index
          }
          orderedMoves.add(index, moves[row+x[i]][col+y[i]]); //place the move in proper place in list
          newXVal.add(index, x[i]); //place the corresponding x movement in the same position
        }
      }
    }
    return newXVal;
  }

  private ArrayList<Integer> newY (int row, int col) { //same as newX except with y movements
    ArrayList<Integer> orderedMoves = new ArrayList<Integer>();
    ArrayList<Integer> newYVal = new ArrayList<Integer>(); //corresponding y movements
    boolean first = true;
    for (int i = 0; i < y.length; i++) {
      if (isOnBoard(row, col, x[i], y[i])) {
        if (first) {
          orderedMoves.add(moves[row+x[i]][col+y[i]]);
          newYVal.add(y[i]);
          first = false;
        }
        else {
          int index = 0;
          while (index < orderedMoves.size() &&
                 moves[row+x[i]][col+y[i]] > orderedMoves.get(index)) {
            index++;
          }
          orderedMoves.add(index, moves[row+x[i]][col+y[i]]);
          newYVal.add(index, y[i]);
        }
      }
    }
    return newYVal;
  }

  public int countSolutions(int startingRow, int startingCol){
    if (isException()) {
      throw new IllegalStateException();
    }
    if (startingRow < 0 || startingCol < 0) {
      throw new IllegalArgumentException();
    }
    int counter = 0;
    board[startingRow][startingCol] = 1; //place first knight
    counter = countHelper(startingRow, startingCol, 2); //start counting
    board[startingRow][startingCol] = 0; //remove first knight from board
    return counter;
  }

  public int countHelper(int row, int col, int level) {
    int solutions = 0;
    if (level > rows*cols) { //all knights placed
      return 1;
    }
    else {
      if (isOnBoard(row, col, -2, -1)) { //checking if possibility is on board
        board[row-2][col-1] = level; //place knight down
        solutions += countHelper(row-2, col-1, level+1); //continue to see if its a solution
        board[row-2][col-1] = 0; //remove knight and continue to next possibility
      }

      if (isOnBoard(row, col, -2, 1)) {
        board[row-2][col+1] = level;
        solutions += countHelper(row-2, col+1, level+1);
        board[row-2][col+1] = 0;
      }

      if (isOnBoard(row, col, -1, -2)) {
        board[row-1][col-2] = level;
        solutions += countHelper(row-1, col-2, level+1);
        board[row-1][col-2] = 0;

      }

      if (isOnBoard(row, col, -1, 2)) {
        board[row-1][col+2] = level;
        solutions += countHelper(row-1, col+2, level+1);
        board[row-1][col+2] = 0;
      }

      if (isOnBoard(row, col, 1, -2)) {
        board[row+1][col-2] = level;
        solutions += countHelper(row+1, col-2, level+1);
        board[row+1][col-2] = 0;
      }

      if (isOnBoard(row, col, 1, 2)) {
        board[row+1][col+2] = level;
        solutions += countHelper(row+1, col+2, level+1);
        board[row+1][col+2] = 0;
      }

      if (isOnBoard(row, col, 2, -1)) {
        board[row+2][col-1] = level;
        solutions += countHelper(row+2, col-1, level+1);
        board[row+2][col-1] = 0;
      }

      if (isOnBoard(row, col, 2, 1)) {
        board[row+2][col+1] = level;
        solutions += countHelper(row+2, col+1, level+1);
        board[row+2][col+1] = 0;
      }
    }
    return solutions;
  }
}
