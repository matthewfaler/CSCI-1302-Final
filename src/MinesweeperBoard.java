import java.util.Random;

class MinesweeperBoard {
    protected class Space { //Space class for each spot on the grid
        private boolean isBomb = false;
        private int surrounding = 0;

        public boolean ifBomb() { //returns boolean indicating spaces bomb status
            return isBomb;
        }
        
        public int getSurrounding() { //returns the number of bombs in the surrounding spaces
            return surrounding;
        }
    }

    static final int UNPRESSED = -1;
    static final int MARKED = -2;
    int rows;
    int columns;
    private Space[][] board;
    int numBombs;
    int[][] clear;
    int numFlags;

    MinesweeperBoard(int rows, int columns, int bombs) {
        if(rows == 0 || columns == 0) { //Check if dimensions are possible
            System.out.println("Impossible dimensions.");
            return;
        }
        board = new Space[rows][columns];
        clear = new int[rows][columns];
        this.rows = rows;
        this.columns = columns;
        if(rows*columns <= bombs) { //check if too many bombs have been requested (makes the game unplayable/makes the constructor crash)
            System.out.println("Too many bombs.");
            return;
        }
        
        for(int i = 0; i < rows; i++) { //populate the board with space objects
            for(int j = 0; j < columns; j++) {
                board[i][j] = new Space();
            }
        }
        numBombs = bombs;
        numFlags = bombs;
        Random rand = new Random();
        int r;
        int c;
        for(int i = 0; i < bombs; i++) { //sets bombs in place randomly
            r = rand.nextInt(rows);
            c = rand.nextInt(columns);
            if(board[r][c].isBomb == true) {
                while(board[r][c].isBomb == true) {
                    r = rand.nextInt(rows);
                    c = rand.nextInt(columns);
                }
            }
            board[r][c].isBomb = true;
        }
        if(rows > 1 || columns > 1) { //making sure board is not just one space
            this.board = setNumbers(this.board);
        }
        for(int i = 0; i < rows; i++) { //initializes clear board to all unpressed spaces
            for(int j = 0; j < columns; j++) {
                clear[i][j] = UNPRESSED;
            }
        }
    }

    private Space[][] setNumbers(Space[][] b) {
        if(this.rows == 1) { //if the board is one-dimensional on an x-axis
            for(int i = 0; i < columns; i++) {
                if(i == 0) {
                    if(b[0][i+1].isBomb == true) { //only check to the right if the space is on the left
                        b[0][i].surrounding++;
                    }
                } else if(i == this.columns - 1) { //only check to the left if the space is on the right
                    if(b[0][i-1].isBomb == true) {
                        b[0][i].surrounding++;
                    }
                } else { //check both sides if in the middle
                    if(b[0][i+1].isBomb == true) {
                        b[0][i+1].surrounding++;
                    }
                    if(b[0][i-1].isBomb == true) {
                        b[0][i-1].surrounding++;
                    }
                }
            }
            return b;
        }
        if(this.columns == 1) { //if the board is one-dimensional on the y-axis
            for(int i = 0; i < rows; i++) {
                if(i == 0) { //only check down if space is at the top
                    if(b[i+1][0].isBomb == true) {
                        b[i][0].surrounding++;
                    }
                } else if (i == this.rows - 1) { //only check up if space is at the bottom
                    if(b[i-1][0].isBomb == true) {
                        b[i][0].surrounding++;
                    }
                } else { //check both sides if in the middle
                    if(b[i-1][0].isBomb == true) {
                        b[i][0].surrounding++;
                    }
                    if(b[i+1][0].isBomb == true) {
                        b[i][0].surrounding++;
                    }
                }
            }
            return b;
        } else { //check general case
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < columns; j++) {
                    if(i - 1 >= 0 && j - 1 >= 0) { //check upper-left corner
                        if(b[i-1][j-1].isBomb == true) {
                            b[i][j].surrounding++;
                        }
                    }
                    if(j - 1 >= 0) { //check left
                        if(b[i][j-1].isBomb == true) {
                            b[i][j].surrounding++;
                        }
                    }
                    if(i - 1 >= 0) { //check up
                        if(b[i-1][j].isBomb == true) {
                            b[i][j].surrounding++;
                        }
                    }
                    if(j - 1 >= 0 && i < rows - 1) { //check bottom-left corner
                        if(b[i+1][j-1].isBomb == true) {
                            b[i][j].surrounding++;
                        }
                    }
                    if(i - 1 >= 0 && j < columns - 1) { //check upper-right corner
                        if(b[i-1][j+1].isBomb == true) {
                            b[i][j].surrounding++;
                        }
                    }
                    if(i < rows - 1) { //check down
                        if(b[i+1][j].isBomb == true) {
                            b[i][j].surrounding++;
                        }
                    }
                    if(j < columns - 1) { //check right
                        if(b[i][j+1].isBomb == true) {
                            b[i][j].surrounding++;
                        }
                    }
                    if(i < rows - 1 && j < columns - 1) { //check bottom-right corner
                        if(b[i+1][j+1].isBomb == true) {
                            b[i][j].surrounding++;
                        }
                    }
                }
            }
            return b;
        }
    }

    public void mark(int row, int column) { //toggle whether space is marked or unpressed
        if(row < 0 || row >= rows || column < 0 || column >= columns) { //check if space is a valid selection
            System.out.println("Not a valid space.");
        } else if(this.numFlags == 0) { //does not mark if user does not have enough flags
            System.out.println("Not enough flags.");
        } else if(this.clear[row][column] == UNPRESSED) { //marks space
            this.clear[row][column] = MARKED;
            this.numFlags--;
        } else if(this.clear[row][column] == MARKED) { //unmarks space
            this.clear[row][column] = UNPRESSED;
            this.numFlags++;
        } else { //tells user the space cannot be marked
            System.out.println("Cannot mark this space.");
        }
    }

    public boolean isMarked(int row, int column) { //return value of whether the space is marked or not
        return this.clear[row][column] == MARKED;
    }

    public int check(int row, int column) {
        if(row < 0 || row >= rows || column < 0 || column >= columns) { //check if space is a valid selection
            System.out.println("Not a valid space.");
            return 0;
        }
        if(this.clear[row][column] == MARKED) { //if space is already marked return 0
            System.out.println("Cannot check a marked space.");
            return 0;
        } else if(this.board[row][column].isBomb == true) { //if game is lost return -1
            System.out.println("Game lost. Better luck next time!!!");
            return -1;
        } else { //if space is not marked and has no bomb, check adjecent spaces until there is a border
                    //of numbers indicating the quantity of adjacent bombs and return 1
            this.clear = checkAdj(this.clear, row, column);
            return 1;
        }
    }

    private int[][] checkAdj(int[][] b, int row, int column) {
        b[row][column] = this.board[row][column].surrounding; //add space surrounding number to clear board
        if(this.board[row][column].surrounding > 0) { //if surrounding bomb integer is greater than 0, return
            return b;
        } else {
            if(row - 1 >= 0 && column - 1 >= 0 && this.clear[row-1][column-1] == UNPRESSED) { //check upper-left corner
                checkAdj(b, row - 1, column - 1);
            }
            if(row - 1 >= 0 && this.clear[row-1][column] == UNPRESSED) { //check above
                checkAdj(b, row - 1, column);
            }
            if(column - 1 >= 0 && this.clear[row][column-1] == UNPRESSED) { //check left
                checkAdj(b, row, column - 1);
            }
            if(row - 1 >= 0 && column < columns - 1 && this.clear[row-1][column+1] == UNPRESSED) { //check upper-right corner
                checkAdj(b, row - 1, column + 1);
            }
            if(row < rows - 1 && column - 1 >= 0 && this.clear[row+1][column-1] == UNPRESSED) { //check lower-left corner
                checkAdj(b, row + 1, column - 1);
            }
            if(row < rows - 1 && this.clear[row+1][column] == UNPRESSED) { //check down
                checkAdj(b, row + 1, column);
            }
            if(column < columns - 1 && this.clear[row][column+1] == UNPRESSED) { //check right
                checkAdj(b, row, column + 1);
            }
            if(row < rows - 1 && column < columns - 1 && this.clear[row+1][column+1] == UNPRESSED) { //check lower-right corner
                checkAdj(b, row + 1, column + 1);
            }
            return b;
        }
    }

    public Space[][] getBoard() { //returns underlying board
        return this.board;
    }

    public int checkWin() { //checks if game is won
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(this.clear[i][j] == UNPRESSED) {
                    return -1;
                }
            }
        }
        return 1;
    }
}