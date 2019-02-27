import java.util.ArrayList;

public class KnightBoard {
  private int[][] board;
  private int[][] moves;
  private int rows;
  private int cols;
  private int[] x = new int [] {-2, -2, -1, -1, 1, 1, 2, 2};
  private int[] y = new int [] {-1, 1, 2, -2, 2, -2, -1, 1};

  public KnightBoard(int startingRows, int startingCols) {
    if (startingRows <= 0 || startingCols <= 0) {
      throw new IllegalArgumentException();
    }
    rows = startingRows;
    cols = startingCols;
    board = new int[rows][cols];
    for (int r = 0; r < startingRows; r++) {
      for (int c = 0; c < startingCols; c++) {
        board[r][c] = 0;
      }
    }
    moves = new int[rows][cols];
    setUpMoves();
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
      if (isOnBoard(row, col, x[i], y[i])) {
        counter++;
      }
    }
    return counter;
  }

/*
  public String toString() {
    String display = "";
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        display += moves[r][c];
      }
      display += "\n";
    }
    return display;
  }
*/
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
    if (row + vertical < 0 || row + vertical > rows-1 ||
        col + horizontal < 0 || col + horizontal > cols-1 ||
        board[row+vertical][col+horizontal] != 0) {
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
    board[startingRow][startingCol] = 1;
    return solveH(startingRow, startingCol, 2);
  }

  public boolean solveH(int row, int col, int level) {
    if (level > rows*cols) {
      return true;
    }
    else {
      ArrayList<Integer> xnew = newX(row, col);
      ArrayList<Integer> ynew = newY(row, col);

      for (int i = 0; i < xnew.size(); i++) {
        board[row+xnew.get(i)][col+ynew.get(i)] = level;
        if (solveH(row+xnew.get(i), col+ynew.get(i), level+1)) {
          return true;
        }
        else {
          board[row+xnew.get(i)][col+ynew.get(i)] = 0;
        }
      }
    }
    return false;
  }

  private ArrayList<Integer> newX (int row, int col) {
    ArrayList<Integer> orderedMoves = new ArrayList<Integer>();
    ArrayList<Integer> newXVal = new ArrayList<Integer>();
    boolean first = true;
    for (int i = 0; i < x.length; i++) {
      if (isOnBoard(row, col, x[i], y[i]) && moves[row+x[i]][col+y[i]] != 0) {
        if (first) {
          orderedMoves.add(moves[row+x[i]][col+y[i]]);
          newXVal.add(x[i]);
          first = false;
        }
        else {
          int index = 0;
          while (index < orderedMoves.size() &&
                 moves[row+x[i]][col+y[i]] > orderedMoves.get(index)) {
            index++;
          }
          orderedMoves.add(index, moves[row+x[i]][col+y[i]]);
          newXVal.add(index, x[i]);
        }
      }
    }
    return newXVal;
  }

  private ArrayList<Integer> newY (int row, int col) {
    ArrayList<Integer> orderedMoves = new ArrayList<Integer>();
    ArrayList<Integer> newYVal = new ArrayList<Integer>();
    boolean first = true;
    for (int i = 0; i < y.length; i++) {
      if (isOnBoard(row, col, x[i], y[i]) && moves[row+x[i]][col+y[i]] != 0) {
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
    int m;
    int n;
    if (rows <= cols) {
      m = rows;
      n = cols;
    }
    else {
      m = cols;
      n = rows;
    }
    int counter = 0;
    board[startingRow][startingCol] = 1;
    counter = countHelper(startingRow, startingCol, 2);
    board[startingRow][startingCol] = 0;
    return counter;
  }

  public int countHelper(int row, int col, int level) {
    int solutions = 0;
    if (level > rows*cols) {
      return 1;
    }
    else {
      if (isOnBoard(row, col, -2, -1)) {
        board[row-2][col-1] = level;
        solutions += countHelper(row-2, col-1, level+1);
        board[row-2][col-1] = 0;
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

  public static void main(String[] args) {
    KnightBoard B = new KnightBoard(5, 6);
    System.out.println(B.countSolutions(0,0));
    System.out.println(B.toString());
  }

}
