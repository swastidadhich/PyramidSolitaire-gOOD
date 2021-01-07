package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The model for playing a game of Pyramid Solitaire: this maintains the state and enforces the
 * rules of gameplay.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  private List<List<Card>> pyramidOfCards;
  private List<Card> stock;
  private Card[] drawPile;
  private boolean isGameStarted;

  /**
   * This is the constructor for the BasicPyramidSolitaire class.
   */

  public BasicPyramidSolitaire() {
    pyramidOfCards = new ArrayList<>();
    stock = new ArrayList<>();
    isGameStarted = false;

  }


  @Override
  public List getDeck() {
    List<Card> deckList = new ArrayList<>();
    for (int count = 0; count < Card.DECK_COUNT; count++) {
      deckList.add(new Card(Card.FACEVALUE[count % 13], Card.SYMBOL[count / 13]));
    }
    return deckList;
  }


  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {

    int pyramidCount = numRows * (numRows + 1) / 2 + numDraw;
    if (deck == null || deck.size() == 0 || numRows <= 0 || pyramidCount >= deck.size()
        || numDraw < 0 || deck.size() < Card.DECK_COUNT || isDuplicated(deck)) {
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
    for (int rowCount = 0; rowCount < numRows; rowCount++) {
      ArrayList<Card> column = new ArrayList<>();
      for (int i = 0; i < rowCount + 1 && deckCount < shuffleDeck.size(); i++) {
        column.add((Card) shuffleDeck.get(deckCount));
        deckCount++;
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


  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    if (isVisible(row1, card1) && isVisible(row2, card2)) {
      int cardval1 = getIntValue(pyramidOfCards.get(row1).get(card1).getFaceValue());
      int cardval2 = getIntValue(pyramidOfCards.get(row2).get(card2).getFaceValue());
      if (cardval1 + cardval2 == 13) {
        pyramidOfCards.get(row1).set(card1, null);
        pyramidOfCards.get(row2).set(card2, null);
      } else {
        throw new IllegalArgumentException("Card doesnt sum to 13");
      }
    } else {
      throw new IllegalArgumentException("Remove is Invalid");
    }
  }

  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    int lastRow = pyramidOfCards.size() - 1;
    if (row > lastRow || row < 0) {
      throw new IllegalArgumentException("Invalid Argument Row");
    }
    Card current = pyramidOfCards.get(row).get(card);
    if (lastRow == row) {
      if (current.getFaceValue().equals("K")) {
        pyramidOfCards.get(row).set(card, null);
      } else {
        throw new IllegalArgumentException("Card doesnt sum to 13");
      }
    } else {
      if (isVisible(row + 1, card) && isVisible(row + 1, card + 1)) {
        if (current.getFaceValue().equals("K")) {
          pyramidOfCards.get(row).set(card, null);
        }
      } else {
        throw new IllegalArgumentException("Remove is Invalid");
      }
    }
  }


  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    if (drawIndex >= drawPile.length) {
      throw new IllegalArgumentException("Invalid Arguments");
    }
    if (isVisible(row, card) && drawPile[drawIndex] != null) {
      int cardval1 = getIntValue(pyramidOfCards.get(row).get(card).getFaceValue());
      int cardval2 = getIntValue(drawPile[drawIndex].getFaceValue());
      if (cardval1 + cardval2 == 13) {
        pyramidOfCards.get(row).set(card, null);
        drawPile[drawIndex] = stock.size() > 0 ? stock.remove(0) : null;
      } else {
        throw new IllegalArgumentException("Card doesnt sum to 13");
      }
    } else {
      throw new IllegalArgumentException("Remove is Invalid");
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    if (drawIndex >= drawPile.length || drawPile[drawIndex] == null) {
      throw new IllegalArgumentException("index is invalid or no card is present there");
    }
    drawPile[drawIndex] = stock.size() > 0 ? stock.remove(0) : null;
  }

  // can remove any card that the user specifies from the pile

  @Override
  public int getNumRows() {
    if (!isGameStarted) {
      return -1;
    }
    return pyramidOfCards.size();
  }

  @Override
  public int getNumDraw() {
    if (!isGameStarted) {
      return -1;
    }
    int drawcount = 0;
    int drawIndex = 0;
    while (drawIndex < drawPile.length) {
      if (drawPile[drawIndex] != null) {
        drawcount++;
      }
      drawIndex++;
    }
    return drawcount;
  }

  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    if (row >= pyramidOfCards.size() || row < 0) {
      throw new IllegalArgumentException("Invalid Argument");
    }
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    int cardcount = 0;
    for (Card card : pyramidOfCards.get(row)) {
      if (card != null) {
        cardcount++;
      }
    }
    return cardcount;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    List<Card> visibleCards = new ArrayList<>();
    for (int rowCount = 0; rowCount < pyramidOfCards.size(); rowCount++) {
      for (int cardCount = 0; cardCount < pyramidOfCards.get(rowCount).size(); cardCount++) {
        Card card = pyramidOfCards.get(rowCount).get(cardCount);
        if (card != null && isVisible(rowCount, cardCount)) {
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


  @Override
  public int getScore() throws IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    int score = 0;
    for (int rowCount = 0; rowCount < pyramidOfCards.size(); rowCount++) {
      for (Card card : pyramidOfCards.get(rowCount)) {
        if (card != null) {
          score += getIntValue(card.getFaceValue());
        }
      }
    }
    return score;
  }

  @Override
  public Card getCardAt(int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    int lastrow = pyramidOfCards.size() - 1;
    if (row > lastrow || row < 0 || card >= pyramidOfCards.get(row).size()) {
      throw new IllegalArgumentException("Invalid Arguments");
    }
    return pyramidOfCards.get(row).get(card);
  }


  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started!");
    }
    List<Card> copyDraw = new ArrayList<Card>(Arrays.asList(drawPile));
    return copyDraw;
  }


  private boolean isVisible(int row, int card) {
    int lastRow = pyramidOfCards.size() - 1;
    if (row == lastRow) {
      return true;
    }
    if (row > lastRow || card >= pyramidOfCards.get(row).size()) {
      throw new IllegalArgumentException("Invalid Arguments");
    }
    if ((row + 1) < pyramidOfCards.size() && (card + 1) < pyramidOfCards.get(row + 1).size()) {
      Card parent1 = pyramidOfCards.get(row + 1).get(card);
      Card parent2 = pyramidOfCards.get(row + 1).get(card + 1);
      if (parent1 == null && parent2 == null) {
        return true;
      }
    }
    return false;
  }

  private int getIntValue(String k) {
    switch (k) {
      case "A":
        return 1;
      case "J":
        return 11;
      case "Q":
        return 12;
      case "K":
        return 13;
      default:
    }
    return Integer.parseInt(k);
  }

  private StringBuilder spaceAdder(int i) {
    StringBuilder spaces = new StringBuilder("");
    int numDoubleSpaces = (this.pyramidOfCards.size() - (i + 1));
    for (int x = 0; x < numDoubleSpaces; x++) {
      spaces.append("  ");
    }
    return spaces;
  }

  @Override
  public String toString() {
    StringBuilder pyramidString = new StringBuilder("");
    int size = getNumRows();
    //  n in this case
    for (int i = 0; i < size; i++) {
      for (int j = 0; j <= i; j++) {
        Card card = pyramidOfCards.get(i).get(j);
        String frontPadding;
        if (j == 0) {
          pyramidString.append(spaceAdder(i));
          frontPadding = "";
        } else if (pyramidOfCards.get(i).get(j - 1) != null
            && pyramidOfCards.get(i).get(j - 1).getFaceValue().equals("10")) {
          frontPadding = " ";
        } else {
          frontPadding = "  ";
        }
        if (card != null) {
          pyramidString.append(frontPadding + card.getFaceValue() + card.getSymbol());
        } else {
          if (j == i && i == size - 1) {
            pyramidString.append(frontPadding + ".");
          } else {
            pyramidString.append(frontPadding + ".  ");
          }
        }
      }
      pyramidString.append("\n");
    }
    List<String> drawString = Arrays.stream(drawPile).filter(y -> y != null)
        .map(x -> x.getFaceValue() + x.getSymbol()).collect(Collectors.toList());
    String draw = String.join(", ", drawString);
    pyramidString.append("Draw: " + draw);
    return pyramidString.toString();
  }

  /**
   * Private helper method which will shuffle the cards.
   */
  private List<Card> shuffle(List<Card> deck) {
    Random randomGenerator = new Random();
    for (int first = 0; first < deck.size(); first++) {
      int second = randomGenerator.nextInt(deck.size());
      Card tempCard = deck.get(first);
      deck.set(first, deck.get(second));
      deck.set(second, tempCard);
    }
    return deck;
  }

  /**
   * Private helper method which checks if the cards are duplicated.
   */
  private boolean isDuplicated(List<Card> deck) {
    HashSet<Card> hs = new HashSet<>();
    for (Card card : deck) {
      if (!hs.contains(card)) {
        hs.add(card);
      } else {
        return true;
      }
    }
    return false;
  }
}

