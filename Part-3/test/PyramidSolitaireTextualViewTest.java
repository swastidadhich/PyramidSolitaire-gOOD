import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the class tests the PyramidSolitaireTextual.
 */
public class PyramidSolitaireTextualViewTest {

  BasicPyramidSolitaire model;
  PyramidSolitaireTextualView textualView;
  Appendable out;

  @Before
  public void setUp() {
    model = new BasicPyramidSolitaire();
    model.startGame(model.getDeck(), false, 7, 3);
    out = new StringBuilder();
    textualView = new PyramidSolitaireTextualView(model, out);
  }

  @Test
  public void testToString() {
    String expected = "            A♣\n"
        + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
        + "Draw: 3♥, 4♥, 5♥\n";
    Assert.assertEquals(expected, textualView.toString());
  }
}





