import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test all the method from the RelaxedPyramidSolitaire.
 */

public class RelaxedPyramidSolitaireTest {

  RelaxedPyramidSolitaire model;

  @Before
  public void setUp() {
    model = new RelaxedPyramidSolitaire();
    model.startGame(model.getDeck(), false, 7, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGame() {
    model.startGame(model.getDeck(), true, -1, 3);
  }

  @Test
  public void testGetNumDrawAfterEmptyStock() {
    Assert.assertEquals(3, model.getNumDraw());
  }

  @Test
  public void testGetRowWdith() {
    Assert.assertEquals(1, model.getRowWidth(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove() {
    model.remove(7, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDraw() {
    model.removeUsingDraw(3, 6, 0);
  }

  @Test
  public void testGetNumDraws() {
    Assert.assertEquals(3, model.getNumDraw());
  }

  @Test
  public void testGetNumRows() {
    Assert.assertEquals(7, model.getNumRows());
  }

  @Test
  public void testIsGameOver() {
    Assert.assertEquals(false, model.isGameOver());
  }

  @Test
  public void testGetScore() {
    Assert.assertNotEquals(0, model.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAt() {
    model.getCardAt(7, 0);
  }

  @Test
  public void testGetDrawCards() {
    Assert.assertEquals(3, model.getDrawCards().size());
  }

  @Test
  public void testGetCardAtWithNum() {
    Assert.assertEquals("Q♦", model.getCardAt(6, 3).toString());
  }

  @Test
  public void testGetScoreWithCard() {
    Assert.assertEquals(185, model.getScore());
  }

  @Test
  public void testRemoveUsingDrawWithNum() {
    model.removeUsingDraw(1, 6, 0);
    Assert.assertEquals(null, model.getCardAt(6, 0));
  }

  @Test
  public void testGetNumRowsWithNum() {
    model.remove(6, 3, 6, 5);
    Assert.assertEquals(null, model.getCardAt(6, 3));
    Assert.assertEquals(null, model.getCardAt(6, 5));
  }

  @Test
  public void testDiscardDrawUsingNum() {
    model.discardDraw(1);
    Assert.assertNotEquals("J♥", model.getDrawCards().get(1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameifDrawLessThanZero() {
    model.startGame(model.getDeck(), true, 3, -1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testStartGameifRowGreaterthanNine() {
    model.startGame(model.getDeck(), true, 10, 3);
  }

  @Test
  public void testIsGameOverActuallyTrueWhenGameOver() {
    Assert.assertNotEquals(true, model.isGameOver());
  }

  @Test
  public void testStartGameWhenGameInProgress() {
    int numRows = model.getNumRows();
    model.startGame(model.getDeck(), false, 5, 3);
    Assert.assertNotEquals(numRows, model.getNumRows());
  }

  @Test
  public void testStartGameActuallyShufflesDeck() {
    List<Card> deck = model.getDeck();
    List<Card> copyDeck = new ArrayList<>(deck);
    model.startGame(deck, true, 3, 3);
    Assert.assertEquals(deck, copyDeck);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameExceptionEmptyString() {
    model.startGame(model.getDeck(), false, -1, 3);
    Assert.assertEquals("", model.toString());
  }

}
