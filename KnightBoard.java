public class KnightBoard {
  private int[][] board;
  private int rows;
  private int cols;

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
        if (board[r][c] == 0) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean solve(int startingRow, int startingCol){
    if (isException()) {
      throw new IllegalStateException();
    }
    if (startingRow <= 0 || startingCol <= 0) {
      throw new IllegalArgumentException();
    }
    return solveH(startingRow, startingCol, 1);
  }

  public boolean isOnBoard(int row, int col, int vertical, int horizontal) {
    if (row + vertical < 0 || row + vertical > rows ||
        col + horizontal < 0 || col + horizontal > cols) {
      return false;
    }
    return true;
  }

  public boolean addKnight(int row, int col, int vertical, int horizontal, int level) {
    if (isOnBoard(row, col, vertical, horizontal) &&
        board[row + vertical][col + horizontal] == 0) {
          board[row + vertical][col + horizontal] = level;
          return true;
    }
    return false;
  }

  //check if next move is on board and if there is no KnightBoard
  //if good continue otherwise remove and return false
  public boolean solveH(int row, int col, int level) {
    if (level == rows*cols) {
      return true;
    }
    else {
      if (addKnight(row, col, -2, -1)) {
        if (solveH()) {
          return true
        }
        if (isOnBoard(row, col, -2, -1)) {
          if (solveH(row-2, col-1, level+1)) {
            return true;
          }
        }
        if (isOnBoard(row, col, -2, 1)) {
          if (solveH(row-2, col+1, level+1)) {
            return true;
          }
        }
        if (isOnBoard(row, col, -1, -2)) {
          if (solveH(row-1, col-2, level+1)) {
            return true;
          }
        }
        if (isOnBoard(row, col, -1, 2)) {
          if (solveH(row-1, col+2, level+1)) {
            return true;
          }
        }
        if (isOnBoard(row, col, 1, -2)) {
          if (solveH(row+1, col-2, level+1)) {
            return true;
          }
        }
        if (isOnBoard(row, col, 1, 2)) {
          if (solveH(row+1, col+2, level+1)) {
            return true;
          }
        }
        if (isOnBoard(row, col, 2, -1)) {
          if (solveH(row+2, col-1, level+1)) {
            return true;
          }
        }
        if (isOnBoard(row, col, 2, 1)) {
          if (solveH(row+2, col+1, level+1)) {
            return true;
          }
        }
        else {
          board[row][col] = 0;
        }
      }
      return false;
    }
  }

/*
  public int countSolutions(int startingRow, int startingCol){
    if (isException()) {
      throw new IllegalStateException();
    }
    if (startingRows <= 0 || startingCols <= 0) {
      throw new IllegalArgumentException();
    }

  }
*/
  public static void main(String[] args) {
    KnightBoard A = new KnightBoard(3, 3);
    System.out.println(A.toString());
  }

}
