package cs3500.pyramidsolitaire.view;


import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * This class visually represents the Pyramid Solitaire.
 */

public class PyramidSolitaireTextualView {

  private final PyramidSolitaireModel<?> model;
  // ... any other fields you need

  /**
   * This class visually represents the Pyramid Solitaire.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
  }

  @Override
  public String toString() {
    if (model.getNumRows() == -1) {
      return "";
    } else if (model.getScore() == 0) {
      return "You win!";
    } else if (model.isGameOver()) {
      return "Game over. Score: " + model.getScore();
    }
    return model.toString();
  }
}
