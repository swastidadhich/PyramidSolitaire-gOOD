package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * This class represents a card and its properties.
 */
public class Card {

  public static final int DECK_COUNT = 52;
  public static final char[] SYMBOL = {'♣', '♦', '♥', '♠'};
  public static final String[] FACEVALUE = {"A", "2", "3", "4", "5", "6", "7", "8", "9",
      "10", "J", "Q", "K"};

  private String face;
  private char symbol;


  /**
   * This is the constructor for the card class which contains face value and a symbol.
   */
  public Card(String face, char symbol) {
    this.face = face;
    this.symbol = symbol;

  }

  public String getFaceValue() {
    return face;
  }

  public char getSymbol() {
    return symbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return symbol == card.symbol && Objects.equals(face, card.face);
  }

  @Override
  public int hashCode() {
    return Objects.hash(face, symbol);
  }

  @Override
  public String toString() {
    return face + symbol;
  }
}




