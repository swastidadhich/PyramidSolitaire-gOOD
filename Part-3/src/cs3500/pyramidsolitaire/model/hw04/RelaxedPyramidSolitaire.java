package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * The model for playing a game of Relaxed Pyramid Solitaire: this maintains the state and enforces
 * the rules of gameplay.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  /**
   * Class for playing a  relaxed game of Pyramid Solitaire.
   */

  public RelaxedPyramidSolitaire() {
    super();
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    try {
      super.remove(row1, card1, row2, card2);
    } catch (IllegalArgumentException exp) {
      if (exp.getMessage().equals("Remove is Invalid")) {
        if (isVisibleRelaxed(row1, card1, row2, card2) && isVisibleRelaxed(row2, card2, row1,
            card1)) {
          int cardval1 = getIntValue(pyramidOfCards.get(row1).get(card1).getFaceValue());
          int cardval2 = getIntValue(pyramidOfCards.get(row2).get(card2).getFaceValue());
          if (cardval1 + cardval2 == 13) {
            pyramidOfCards.get(row1).set(card1, null);
            pyramidOfCards.get(row2).set(card2, null);
          } else {
            throw new IllegalArgumentException("Card doesnt sum to 13");
          }
        }
      }
    }
  }

  /**
   * method that handles when a relaxed game of Pyramid Solitaire is over.
   */
  
  public boolean isGameOver() throws IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    List<Card> visibleCards = new ArrayList<>();
    for (int rowCount = 0; rowCount < pyramidOfCards.size(); rowCount++) {
      for (int cardCount = 0; cardCount < pyramidOfCards.get(rowCount).size(); cardCount++) {
        Card card = pyramidOfCards.get(rowCount).get(cardCount);
        if (card != null && (isVisibleRelaxed(rowCount, cardCount, rowCount + 1, cardCount)
            || isVisibleRelaxed(rowCount, cardCount, rowCount + 1, cardCount + 1))) {
          visibleCards.add(card);
        }
      }
    }
    if (visibleCards.size() == 0) {
      return true;
    } else {
      // checking for pairsum =13 in visible cards
      HashSet<Integer> hm = new HashSet<>();
      for (Card card : visibleCards) {
        int val = getIntValue(card.getFaceValue());
        if (hm.contains(13 - val) || val == 13) {
          return false;
        } else {
          hm.add(val);
        }
      }
      if (getNumDraw() != 0) {
        return false;
      }

    }
    return true;
  }


  protected boolean isVisibleRelaxed(int row, int card, int row2, int card2) {
    int lastRow = pyramidOfCards.size() - 1;
    if (row == lastRow) {
      return true;
    }
    Card childCard = pyramidOfCards.get(row).get(card);
    if (childCard == null) {
      return false;
    }

    if (row > lastRow || card >= pyramidOfCards.get(row).size()) {
      throw new IllegalArgumentException("Invalid Arguments");
    }
    if ((row + 1) < pyramidOfCards.size() && (card + 1) < pyramidOfCards.get(row + 1).size()) {
      Card parent1 = pyramidOfCards.get(row + 1).get(card);
      Card parent2 = pyramidOfCards.get(row + 1).get(card + 1);
      if (parent1 == null && parent2 == null) {
        return true;
      } else if (parent1 == null ^ parent2 == null) {
        Card parent = pyramidOfCards.get(row2).get(card2);

        if ((parent1 != null && parent1.equals(parent))
            || (parent2 != null && parent2.equals(parent))) {
          boolean res = getIntValue(parent.getFaceValue()) + getIntValue(childCard.getFaceValue())
              == 13 ? true : false;
          return res;
        }
      }
    }
    return false;
  }
}
