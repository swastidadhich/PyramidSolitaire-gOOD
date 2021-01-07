package cs3500.pyramidsolitaire.view;


import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;

/**
 * This class visually represents the Pyramid Solitaire.
 */

public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private final PyramidSolitaireModel<?> model;
  private Appendable appendable;
  // ... any other fields you need

  /**
   * This class visually represents the Pyramid Solitaire.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model,
      Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  public PyramidSolitaireTextualView(
      PyramidSolitaireModel<?> model) {
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

  @Override
  public void render() throws IOException {
    this.appendable.append(toString());
    this.appendable.append("\n");
  }
}