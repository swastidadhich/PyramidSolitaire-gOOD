package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;

/**
 * The model for playing a game of Pyramid Solitaire: this maintains the state and enforces the
 * rules of gameplay.
 */

public final class PyramidSolitaire {

  /**
   * The model for playing a game of Pyramid Solitaire: this maintains the state and enforces the
   * rules of gameplay.
   */
  public static void main(String[] args) {
    // FILL IN HERE
    String type = args[0];
    int numRows = Integer.parseInt(args[1]);
    int numDraw = Integer.parseInt(args[2]);
    PyramidSolitaireModel model = PyramidSolitaireCreator.create(GameType.getEnumType(type));
    model.startGame(model.getDeck(), false, numRows, numDraw);
    System.out.println(model);
  }
}
