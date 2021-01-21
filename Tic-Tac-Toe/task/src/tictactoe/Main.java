package tictactoe;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String result;
        int stop=0;
        int switchUser=0;
        char[][] field = new char[3][3];
        for (int i = 0; i < 9; i++) {
            field[i / 3][i % 3] = 32;
        }

        while (stop!=1) {
            int check = 0;
            print(field);

            while (check != 1) {
                System.out.print("Enter the coordinates: ");
                Scanner sc = new Scanner(System.in);
                String[] strArray = sc.nextLine().split(" ");
                int[] coordinatesArray = new int[strArray.length];

                check = checkCoordinates(strArray, field);

                if (check == 1) {
                    for (int i = 0; i < coordinatesArray.length; i++) {
                        coordinatesArray[i] = Integer.parseInt(strArray[i]);
                    }

                    userStep(coordinatesArray, field, switchUser);
                    switchUser+=1;
                    if(switchUser==2) switchUser=0;

                    print(field);

                    result=checkWinner(field);
                    System.out.println(result);
                    switch (result) {
                        case ("X wins"):
                            stop=1;
                            break;
                        case ("O wins"):
                            stop=1;
                            break;
                        case ("Draw"):
                            stop=1;
                            break;
                        default:
                            stop=2;
                    }
                } else if (check == 2) {
                    System.out.println("You should enter numbers!");
                } else if (check == 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (check == 4) {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            }

        }
    }


    public static int checkCoordinates(String [] strArray, char [][] field){
        // all correct = 1
        // You should enter numbers! = 2
        // Coordinates should be from 1 to 3! = 3
        // This cell is occupied! Choose another one! = 4

        int check=0;
        int[] coordinatesArray = new int[strArray.length];
        for (int i = 0; i < coordinatesArray.length; i++) {
            try {
                coordinatesArray[i] = Integer.parseInt(strArray[i]);
                if((coordinatesArray[i]>=1&&coordinatesArray[i]<=3)==false){
                    return check=3;
                }
            }catch (NumberFormatException strArr) {
                return check=2;
            }
        }
        int x = coordinatesArray[0]-1;
        int y = coordinatesArray[1]-1;
        if (field[x][y] == 88||field[x][y]==79) {
          return check=4;
        } else {
            return check=1;
        }
    }

    public static char[][] userStep (int coordinatesArray[], char[][] field, int switchUser) {
        int x = coordinatesArray[0]-1;
        int y = coordinatesArray[1]-1;
        if(switchUser==0) field[x][y]=88;
        else field[x][y]=79;

        return field;
    }

    // print field
    public static void print(char[][] field) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.println(String.format("| %c %c %c |", field[i][0], field[i][1], field[i][2]));
        }
        System.out.println("---------");
    }

    public static String checkWinner(char [][] field /*char[] xo*/ ) {
        Boolean xxx = false;
        Boolean ooo = false;
        int xs = 0;
        int os = 0;

        // check if row or column or any of diagonals is win for someone
        for (int i = 0; i < 3; i++) {
            int row = 0;
            int clm = 0;
            int mDiag = 0;
            int aDiag = 0;

            for (int j = 0; j < 3; j++) {
                row += field[i][j];
                clm += field[j][i];
                mDiag += field[j][j];
                aDiag += field[j][2 - j];
                if (field[i][j] ==88){
                    xs += 1;
                } else if (field[i][j] == 79) {
                    os += 1;
                }
            }
            // ASCII value for X is 88 (X+X+X is 264)
            // ASCII value for O is 79 (O+O+O is 237)
            xxx = xxx || row == 264 || clm == 264 || mDiag == 264 || aDiag == 264;
            ooo = ooo || row == 237 || clm == 237 || mDiag == 237 || aDiag == 237;
        }

        String result = Math.abs(xs - os) > 1 || xxx && ooo ? "Impossible"
                : xxx ? "X wins"
                : ooo ? "O wins"
                : xs + os == 9 ? "Draw"
                : "Game not finished";

        return result;
    }
}
