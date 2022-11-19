
import java.util.Random;
import java.util.Scanner;

public final class Game {
  private Game() {
  }

  private String position[];
  private String currentPlayer;
  private String player1Name;
  private String player2Name;
  private int size = 3;
  private int player1Scrore;
  private int player2Scrore;
  private int lineChecker = 0;

  public static void main(String args[]) {
    String playAgainString = "yes";
    Game TripleTGame = new Game();
    while (playAgainString.equals("yes")) {
      TripleTGame.getBoardSize();
      TripleTGame.newBoard();
      TripleTGame.getPlayersName();
      TripleTGame.throwDice();
      TripleTGame.play();
      System.out.println("Would you like to play again (Enter 'yes')? ");
      Scanner scanner = new Scanner(System.in);
      playAgainString = scanner.nextLine();// yes
    }
    System.out.println("Exited");
  }

  public void newBoard() {
    position = new String[(size * size) + 1];
    String positions[] = new String[(size * size) + 1];// arrayaki dikaman haya ka sizakan daxil dkain nmuna 3*3 aw +1
                                                       // bakar det chunka itemaka la 1 ra dast pedaka

    for (int i = 0; i < size * size + 1; i++) {
      positions[i] = String.valueOf(i);
    }

    int i;
    currentPlayer = "X";
    for (i = 0; i < positions.length; i++)
      position[i] = positions[i];
  }

  public void currentBoard() {
    lineChecker = size;
    for (int i = 1; i < position.length; i++) {
      String temp = position[i];
      if (String.valueOf(temp).length() == 1) {
        temp = " " + temp;
      }
      System.out.print(temp + "      |");
      if (i == lineChecker) {
        lineChecker += size;
        System.out.println("");
      }
    }
  }

  public void getPlayersName() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("enter player 1 name:");
    player1Name = scanner.nextLine();
    if (player1Name.isEmpty()) {
      player1Name = "player1";
    }
    System.out.println("enter player 2 name: ");
    player2Name = scanner.nextLine();
    if (player2Name.isEmpty()) {
      player2Name = "player2";
    }
  }

  private void throwDice() {
    int diceNum1 = throwOneDice() + 1;
    int diceNum2 = throwOneDice() + 1;
    while (diceNum1 == diceNum2) {
      diceNum1 = throwOneDice() + 1;
      diceNum2 = throwOneDice() + 1;
    }
    System.out.println("\n" + player1Name + " got a dice number of " + diceNum1);
    System.out.println(player2Name + " got a dice number of " + diceNum2);
    if (diceNum1 < diceNum2) {
      String temp = player1Name;
      player1Name = player2Name;
      player2Name = temp;
    }
  }

  private void getBoardSize() {
    System.out.println("Please enter board size (enter 3 for 3X3 board) (min: 3 and max: 9)");
    Scanner scanner = new Scanner(System.in);
    size = scanner.nextInt();
  }

  private int throwOneDice() {
    return new Random().nextInt(6);
  }

  public void play() {//
    int spot;
    System.out.println("\nPlayer " + player1Name + " will go first, and his/her letter will be X");
    System.out.println("Player " + player2Name + " will go next, and his/her letter will be O");
    do {
      currentBoard();
      System.out.println("\nPlayer " + getPlayer() + " choose a spot: ");
      boolean posTaken = true;
      while (posTaken) {
        Scanner scanner = new Scanner(System.in);
        spot = scanner.nextInt();
        posTaken = checkPosn(spot, true);
        System.out.println("spot number " + spot + " selected.");
        if (posTaken == false)
          position[spot] = getPlayer().equals(player1Name) ? "X" : "O";
        checkForPoint(currentPlayer, spot);
      }
      if (checkWinner().equals("X") || checkWinner().equals("O"))
        currentBoard();

      nextPlayer();
      //
    } while (checkWinner().equals(""));
  }

  private void checkForPoint(String player, int posTaken) {
    int counter = 0;
    int start = 0;
    boolean shouldReturn = false;

    if (posTaken < size) {
      start = 0;
    } else if (posTaken < size * 2 + 1) {
      start = 1;
    } else if (posTaken < size * 3 + 1) {
      start = 2;
    } else if (posTaken < size * 4 + 1) {
      start = 3;
    } else if (posTaken < size * 5 + 1) {
      start = 4;
    } else if (posTaken < size * 6 + 1) {
      start = 5;
    } else if (posTaken < size * 7 + 1) {
      start = 6;
    } else if (posTaken < size * 8 + 1) {
      start = 7;
    } else if (posTaken < size * 9 + 1) {
      start = 8;
    }

    for (int i = start; i < size; i++) {
      for (int j = size * i + 1; j < size * (i + 1) + 1; j++) {

        if (position[j].equals(player)) {
          counter++;
        }
        if (j == posTaken) {
          shouldReturn = true;
        }
      }
    }

    if (counter == 6) {
      if (player.equals("X")) {
        player1Scrore += 2;
        System.out.println(player1Name + " got 2 mark and has these mark: " + player1Scrore);
      } else {
        player2Scrore += 2;
        System.out.println(player2Name + " got 2 mark and has these mark: " + player2Scrore);

      }
      return;
    }
    if (counter == 3) {
      if (player.equals("X")) {
        player1Scrore++;
        System.out.println(player1Name + " got 1 mark and has these mark: " + player1Scrore);
      } else {
        player2Scrore++;
        System.out.println(player2Name + " got 1 mark and has these mark: " + player2Scrore);

      }
    }
    if (shouldReturn) {
      return;
    }

  }

  public String checkWinner() {
    String Winner = "";
    for (int i = 1; i < size * size + 1; i++) {
      if (position[i].equals("X") || position[i].equals("O")) {
        if (i == size * size) {
          if (player1Scrore > player2Scrore) {
            System.out.println(player1Name + " has won the game");
            player1Scrore = 0;
            player2Scrore = 0;
            return "D";
          }
          if (player1Scrore < player2Scrore) {
            System.out.println(player2Name + " has won the game");
            player1Scrore = 0;
            player2Scrore = 0;
            return "D";
          } else {
            String Draw = "D";
            System.out.println(" Game is draw ");
            return Draw;
          }
        }
        continue;
      } else
        break;

    }

    return Winner;
  }

  public boolean checkPosn(int spot, boolean showPrint) {
    if (position[spot].equals("X") || position[spot].equals("O")) {
      if (showPrint)
        System.out.println("That spot is taken, please take another spot.");
      return true;
    } else {
      return false;
    }
  }

  public void nextPlayer() {
    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
  }

  public String getPlayer() {
    return currentPlayer.equals("X") ? player1Name : player2Name;
  }
}
