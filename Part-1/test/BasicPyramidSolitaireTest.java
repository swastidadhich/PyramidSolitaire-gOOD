import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test all the method from the PyramidSolitaireModel interface.
 */
public class BasicPyramidSolitaireTest {

  BasicPyramidSolitaire model;

  @Before
  public void setUp() {
    model = new BasicPyramidSolitaire();
    model.startGame(model.getDeck(), false, 7, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGame() {
    model.startGame(model.getDeck(), true, 3, 3);
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
  public void testRemove2() {
    model.remove(6, 0, 7, 0);
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
    Assert.assertEquals(".", model.getCardAt(6, 0).toString());
  }

  @Test
  public void testGetNumRowsWithNum() {
    model.remove(6, 3, 6, 5);
    Assert.assertEquals(".", model.getCardAt(6, 3).toString());
    Assert.assertEquals(".", model.getCardAt(6, 5).toString());
  }

  @Test
  public void testDiscardDrawUsingNum() {
    model.discardDraw(1);
    Assert.assertNotEquals("J♥", model.getDrawCards().get(1).toString());
  }



}
