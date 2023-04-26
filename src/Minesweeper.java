import java.util.Scanner;

class Minesweeper {

    void printBoard(MinesweeperBoard b) { //print user board
        System.out.println("Number of flags: " + b.numFlags);
        for(int i = 0; i <= b.columns; i++) {
            if(i < 10) {
                System.out.print(i + "   ");
            } else if(i < 100) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println();
        for(int i = 1; i <= b.rows; i++) {
            if(i < 10) {
                System.out.print(i + "   ");
            } else if(i < 100) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
            for(int j = 0; j < b.columns; j++) {
                if(b.clear[i-1][j] == -1) {
                    System.out.print("\u001B[37m" + "u   " + "\u001B[0m");
                } else if(b.clear[i-1][j] == -2) {
                    System.out.print("\u001B[31m" + "m   " + "\u001B[0m");
                } else {
                    if(b.clear[i-1][j] == 0) {
                        System.out.print("\u001B[32m" + b.clear[i-1][j] + "   " + "\u001B[0m");
                    } else if(b.clear[i-1][j] == 1) {
                        System.out.print("\u001B[34m" + b.clear[i-1][j] + "   " + "\u001B[0m");
                    } else if(b.clear[i-1][j] == 2) {
                        System.out.print("\u001B[36m" + b.clear[i-1][j] + "   " + "\u001B[0m");
                    } else if(b.clear[i-1][j] == 3) {
                        System.out.print("\u001B[33m" + b.clear[i-1][j] + "   " + "\u001B[0m");
                    } else if(b.clear[i-1][j] == 4) {
                        System.out.print("\u001B[35m" + b.clear[i-1][j] + "   " + "\u001B[0m");
                    } else {
                        System.out.print(b.clear[i-1][j] + "   ");
                    }
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println();
    }

    void printOther(MinesweeperBoard b) { //print underlying board
        for(int i = 0; i <= b.columns; i++) {
            if(i < 10) {
                System.out.print(i + "   ");
            } else if(i < 100) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println();
        for(int i = 1; i <= b.rows; i++) {
            if(i < 10) {
                System.out.print(i + "   ");
            } else if(i < 100) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
            for(int j = 0; j < b.columns; j++) {
                if(b.getBoard()[i-1][j].ifBomb()) {
                    System.out.print("\u001B[31m" + "b   " + "\u001B[0m");
                } else {
                    if(b.getBoard()[i-1][j].getSurrounding() == 0) {
                        System.out.print("\u001B[32m" + b.getBoard()[i-1][j].getSurrounding() + "   " + "\u001B[0m");
                    } else if(b.getBoard()[i-1][j].getSurrounding() == 1) {
                        System.out.print("\u001B[34m" + b.getBoard()[i-1][j].getSurrounding() + "   " + "\u001B[36m");
                    } else if(b.getBoard()[i-1][j].getSurrounding() == 2) {
                        System.out.print("\u001B[36m" + b.getBoard()[i-1][j].getSurrounding() + "   " + "\u001B[36m");
                    } else if(b.getBoard()[i-1][j].getSurrounding() == 3) {
                        System.out.print("\u001B[33m" + b.getBoard()[i-1][j].getSurrounding() + "   " + "\u001B[0m");
                    } else if(b.getBoard()[i-1][j].getSurrounding() == 4) {
                        System.out.print("\u001B[35m" + b.getBoard()[i-1][j].getSurrounding() + "   " + "\u001B[0m");
                    } else {
                        System.out.print(b.getBoard()[i-1][j].getSurrounding() + "   ");
                    }
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println();
    }
    public static void main(String[] args) {
        System.out.println("Welcome to Minesweeper! Enter the game mode you would like (Easy, Medium, Hard, Custom).");
        Scanner s = new Scanner(System.in);
        Minesweeper m = new Minesweeper();
        MinesweeperBoard b;
        String word = s.next();
        while(!word.equalsIgnoreCase("easy") && !word.equalsIgnoreCase("medium") && !word.equalsIgnoreCase("hard") && !word.equalsIgnoreCase("custom")) {
            System.out.println("Please enter a valid game mode.");
            word = s.next();
        }
        if(word.equalsIgnoreCase("easy")) { //easy game mode
            System.out.println("You have made an easy game.");
            b = new MinesweeperBoard(9, 9, 10);
        } else if(word.equalsIgnoreCase("medium")) { //medium game mode
            System.out.println("You have made a medium game.");
            b = new MinesweeperBoard(16, 16, 40);
        } else if(word.equalsIgnoreCase("hard")) { //hard game mode
            System.out.println("You have made a hard game.");
            b = new MinesweeperBoard(16, 30, 99);
        } else { //custom game mode
            System.out.println("You have made a custom game.");
            int h;
            int w;
            int bo;
            System.out.println("What height would you like your board to be?");
            h = s.nextInt();
            System.out.println("What width would you like your board to be?");
            w = s.nextInt();
            System.out.println("How many bombs would you like there to be?");
            bo = s.nextInt();
            b = new MinesweeperBoard(h, w, bo);
        }
        System.out.println();
        System.out.println("u = unchecked \nm = marked\nUse MARK command to toggle marked status.\nUse CHECK command to guess space.");
        m.printBoard(b);
        System.out.println();
        String action;
        int r;
        int c;
        while(b.checkWin() != 1) {
            System.out.println("Choose action and space (action row column):");
            action = s.next();
            if(action.equalsIgnoreCase("mark")) {
                r = s.nextInt();
                c = s.nextInt();
                b.mark(r - 1, c - 1);
            } else if(action.equalsIgnoreCase("check")) {
                r = s.nextInt();
                c = s.nextInt();
                if(b.check(r - 1, c - 1) == -1) {
                    m.printOther(b);
                    s.close();
                    return;
                }
                b.check(r - 1, c - 1);
            } else {
                System.out.println("Please enter a valid action.");
            }
            System.out.println();
            m.printBoard(b);
        }
        System.out.println("You won!!! Congratulations.");
        s.close();
    }
}