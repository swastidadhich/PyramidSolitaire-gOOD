package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is the implementation for the multipyramid class.
 */

public class MultiPyramidSolitaire extends BasicPyramidSolitaire {

  public MultiPyramidSolitaire() {
    super();
    isMultiPyramid = true;
  }

  /**
   * modified start game for  the deck of multipyramid.
   */
  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {

    if (deck == null || deck.size() == 0 || numRows <= 0 || deck.size() < 104
        || numDraw < 0 || checktriplets(deck)) {
      throw new IllegalArgumentException("Invalid Arguments");
    }
    List<Card> shuffleDeck = new ArrayList<>(deck);
    if (shuffle) {
      shuffleDeck = shuffle(shuffleDeck);
    }
    if (isGameStarted) {
      pyramidOfCards = new ArrayList<>();
      stock = new ArrayList<>();
      isGameStarted = false;
    }
    int deckCount = 0;
    int maxCard = 3;
    for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
      maxCard = rowIndex > 0 ? 3 + maxCard : maxCard;
      int colCount = numRows % 2 == 0 ? numRows + 1 + rowIndex : numRows + rowIndex;
      ArrayList<Card> column = new ArrayList<>();
      for (int colIndex = 0; colIndex < colCount && deckCount < shuffleDeck.size(); colIndex++) {
        if (numRows < 4 || rowIndex >= Math.ceil(numRows / 2) - 1) {
          column.add((Card) shuffleDeck.get(deckCount++));
        } else {
          int numDots = colCount - maxCard;
          int cardIndex = 0;
          int dotIndex = 0;
          while (cardIndex < maxCard) {
            int i = 0;
            int j = 0;
            while (i < maxCard / 3) {
              column.add((Card) shuffleDeck.get(deckCount++));
              cardIndex++;
              i++;
              colIndex++;
            }
            while (dotIndex < numDots && j < numDots / 2) {
              column.add(null);
              j++;
              dotIndex++;
              colIndex++;
            }
          }
        }
      }
      pyramidOfCards.add(column);
    }
    if (deckCount == 0) {
      throw new IllegalArgumentException("Invalid Arguments");
    }
    drawPile = new Card[numDraw];
    int pileCount = 0;
    while (pileCount < numDraw && deckCount < shuffleDeck.size()) {
      drawPile[pileCount] = (Card) shuffleDeck.get(deckCount);
      deckCount++;
      pileCount++;
    }
    while (deckCount < shuffleDeck.size()) {
      stock.add((Card) shuffleDeck.get(deckCount));
      deckCount++;
    }
    isGameStarted = true;
  }

  /**
   * private helper for checking if there are triples in the deck of multipyramid.
   */

  private boolean checktriplets(List<Card> deck) {
    Map<Card, Integer> cardMap = new HashMap<>();
    for (Card card : deck) {
      int count = 0;
      if (cardMap.containsKey(card)) {
        count = cardMap.get(card);
        if (count >= 2) {
          return true;
        }
      }
      cardMap.put(card, ++count);
    }
    return false;
  }


  /**
   * modified helper for checking rowwidth in the deck of multipyramid deck.
   */
  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    if (row >= pyramidOfCards.size() || row < 0) {
      throw new IllegalArgumentException("Invalid Argument");
    }
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    return pyramidOfCards.get(row).size();
  }
}
