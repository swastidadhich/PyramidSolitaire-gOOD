import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.io.StringReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test all the method from the PyramidSolitaireController interface.
 */

public class PyramidSolitaireTextualControllerTest {

  BasicPyramidSolitaire model;
  PyramidSolitaireTextualView textualView;
  Appendable out;
  PyramidSolitaireController controller;
  Readable rd;

  @Before
  public void setUp() {
    model = new BasicPyramidSolitaire();
    model.startGame(model.getDeck(), false, 7, 3);
    out = new StringBuilder();
    rd = new StringReader("rm1 7 5 rm2 7 3 7 s 7 rmwd 1 7 h 2 dd -1 q");
    textualView = new PyramidSolitaireTextualView(model, out);
    controller = new PyramidSolitaireTextualController(rd, out);
  }


  @Test
  public void testPlayGame() {
    controller.playGame(model, model.getDeck(), false, 7, 3);
    System.out.println(out);
    Assert.assertEquals(null, model.getCardAt(6, 4));
  }

  @Test
  public void testPlayGamerm2() {
    controller.playGame(model, model.getDeck(), false, 7, 3);
    System.out.println(out);
    Assert.assertEquals(null,
        model.getCardAt(6, 2), model.getCardAt(6, 6));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNegCase() {
    controller.playGame(null, model.getDeck(), false, 7, 3);
    Assert.assertEquals(null, model.getCardAt(-6, 4));
  }


}
