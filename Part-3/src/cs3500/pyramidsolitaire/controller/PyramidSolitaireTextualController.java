package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Controller implementation for interacting with a game of Pyramid Solitaire.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private Scanner input;
  private Appendable output;
  private boolean isQuitting;

  /**
   * Controller implementation for interacting with a game of Pyramid Solitaire constructor.
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap) {
    this.isQuitting = false;
    if (rd != null) {
      this.input = new Scanner(rd);
    }
    this.output = ap;

  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    PyramidSolitaireTextualView view;
    view = new PyramidSolitaireTextualView(model, output);
    if (model == null) {
      throw new IllegalArgumentException("Invalid Arguments");
    }
    if (input == null || output == null) {
      throw new IllegalStateException("Invalid state");
    }
    if (numRows > 0 && numDraw > 0) {
      model.startGame(deck, shuffle, numRows, numDraw);
      try {
        play(model);
        if (this.isQuitting) {
          output.append("Game quit!" + "\n" + "State of game when quit" + "\n");
          view.render();
          output.append("Score: " + model.getScore() + "\n");
        } else {
          view.render();
        }
      } catch (IOException e) {
        throw new IllegalStateException("Invalid State");
      }
    } else {
      throw new IllegalArgumentException("Invalid Arguments");
    }
  }

  private <K> void play(PyramidSolitaireModel<K> model) throws IOException {
    while (input.hasNext()) {
      if (model.isGameOver()) {
        return;
      }
      String moveType = this.input.next();
      if (isQuit(moveType)) {
        this.isQuitting = true;
        return;
      } else {
        try {
          int[] dataInput;

          if (moveType.equals("rm1")) {
            dataInput = getInput(2);
            if (this.isQuitting) {
              return;
            }
            model.remove(dataInput[0], dataInput[1]);
          } else if (moveType.equals("rm2")) {
            dataInput = getInput(4);
            if (this.isQuitting) {
              return;
            }
            model.remove(dataInput[0], dataInput[1], dataInput[2], dataInput[3]);
          } else if (moveType.equals("rmwd")) {
            dataInput = getInput(3);
            if (this.isQuitting) {
              return;
            }
            model.removeUsingDraw(dataInput[0], dataInput[1], dataInput[2]);
          } else if (moveType.equals("dd")) {
            dataInput = getInput(1);
            if (this.isQuitting) {
              return;
            }
            model.discardDraw(dataInput[0]);

          } else {
            continue;
          }
        } catch (IllegalArgumentException | IllegalStateException exp) {
          output.append("Invalid move. Play again. *" + exp.getMessage() + "*" + "\n");
        }
      }
    }
  }

  private boolean isQuit(String input) {
    return (input.equals("q") || input.equals("Q"));
  }

  private boolean isStringInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }

  private int[] getInput(int arg) {
    int[] dataInput = new int[arg];
    int count = 0;
    while (count < arg) {
      String tmp = this.input.next();
      if (isQuit(tmp)) {
        this.isQuitting = true;
        return dataInput;
      }
      if (isStringInt(tmp)) {
        dataInput[count] = Integer.parseInt(tmp) - 1;
        count++;
      }
    }
    return dataInput;
  }

}
