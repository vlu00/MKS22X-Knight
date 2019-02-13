public class KnightBoard {
  private int[][] board;
  private int rows;
  private int cols;

  public KnightBoard(int startingRows, int startingCols) {
    if (startingRows <= 0 || startingCols <= 0) {
      throw new IllegalArgumentException();
    }
    else {
      for (int r = 0; r < startingRows; r++) {
        for (int c = 0; c < startingCols; c++) {
          board[r][c] = 0;
        }
      }
    }
    rows = startingRows;
    cols = startingCols;
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

  public static void main(String[] args) {
    KnightBoard A = new KnightBoard(3, 3);
  }

}
