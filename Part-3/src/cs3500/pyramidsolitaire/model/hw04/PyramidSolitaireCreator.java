package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;


/**
 * This method to allow you to choose different board shapes from the command line, when running
 * your program.
 */
public class PyramidSolitaireCreator {

  /**
   * This method to allow you to choose different board shapes from the command line, when running
   * your program.
   */
  public static PyramidSolitaireModel<?> create(GameType modelType) {
    PyramidSolitaireModel modelImpl = null;
    switch (modelType) {
      case BASIC:
        modelImpl = new BasicPyramidSolitaire();
        break;
      case RELAXED:
        modelImpl = new RelaxedPyramidSolitaire();
        break;
      case MULTIPYRAMID:
        modelImpl = new MultiPyramidSolitaire();
        break;
      default:
    }
    return modelImpl;
  }

  /**
   * Used enum to represent 3 different types games.
   */

  public enum GameType {
    BASIC("basic"),
    MULTIPYRAMID("multipyramid"),
    RELAXED("relaxed");

    private String type;

    /**
     * specifies Game type.
     */

    GameType(String type) {
      this.type = type;
    }

    /**
     * specifies Game type.
     */

    public String getType() {
      return type;
    }

    /**
     * specifies Game type using enums.
     */

    public static GameType getEnumType(String type) {
      GameType res = null;
      for (GameType gameType : GameType.values()) {
        if (gameType.type.equals(type)) {
          res = gameType;
          break;
        }
      }
      return res;
    }
  }

}
