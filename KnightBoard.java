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
        if (board[r][c] != 0) {
          return true;
        }
      }
    }
    return false;
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

  public boolean isOnBoard(int row, int col, int vertical, int horizontal) {
    if (row + vertical < 0 || row + vertical > rows-1 ||
        col + horizontal < 0 || col + horizontal > cols-1 ||
        board[row+vertical][col+horizontal] != 0) {
      return false;
    }
    return true;
  }

  public boolean solveH(int row, int col, int level) {
    if (level > rows*cols) {
      return true;
    }
    else {
      if (isOnBoard(row, col, -2, -1)) {
        board[row-2][col-1] = level;
        if (solveH(row-2, col-1, level+1)) {
          return true;
        }
        else {
          board[row-2][col-1] = 0;
        }
      }

      if (isOnBoard(row, col, -2, 1)) {
        board[row-2][col+1] = level;
        if (solveH(row-2, col+1, level+1)) {
          return true;
        }
        else {
          board[row-2][col+1] = 0;
        }
      }

      if (isOnBoard(row, col, -1, -2)) {
        board[row-1][col-2] = level;
        if (solveH(row-1, col-2, level+1)) {
          return true;
        }
        else {
          board[row-1][col-2] = 0;
        }
      }

      if (isOnBoard(row, col, -1, 2)) {
        board[row-1][col+2] = level;
        if (solveH(row-1, col+2, level+1)) {
          return true;
        }
        else {
          board[row-1][col+2] = 0;
        }
      }

      if (isOnBoard(row, col, 1, -2)) {
        board[row+1][col-2] = level;
        if (solveH(row+1, col-2, level+1)) {
          return true;
        }
        else {
          board[row+1][col-2] = 0;
        }
      }

      if (isOnBoard(row, col, 1, 2)) {
        board[row+1][col+2] = level;
        if (solveH(row+1, col+2, level+1)) {
          return true;
        }
        else {
          board[row+1][col+2] = 0;
        }
      }

      if (isOnBoard(row, col, 2, -1)) {
        board[row+2][col-1] = level;
        if (solveH(row+2, col-1, level+1)) {
          return true;
        }
        else {
          board[row+2][col-1] = 0;
        }
      }

      if (isOnBoard(row, col, 2, 1)) {
        board[row+2][col+1] = level;
        if (solveH(row+2, col+1, level+1)) {
          return true;
        }
        else {
          board[row+2][col+1] = 0;
        }
      }
    }
    return false;
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
    if ((m % 2 == 1 && n % 2 == 1) ||
        (m == 3 && n == 4 || n == 6 || n ==8) ||
        (m == 1 || m == 2 || m == 4)) {
      board[startingRow][startingCol] = 1;
      countHelper(startingRow, startingCol, 2);
    }
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
    KnightBoard A = new KnightBoard(5, 5);
    System.out.println(A.toString());
    System.out.println(A.solve(0,0));
    System.out.println(A.toString());

    KnightBoard B = new KnightBoard(5, 5);
    System.out.println(B.countSolutions(0,0));
  }

}
