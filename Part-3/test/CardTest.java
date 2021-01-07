import cs3500.pyramidsolitaire.model.hw02.Card;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test all the method from the Card class.
 */
public class CardTest {

  Card card;

  @Before
  public void setUp() {
    card = new Card("10", '♣');
  }

  @Test
  public void testGetFaceValue() {
    Assert.assertEquals("10", card.getFaceValue());

  }

  @Test
  public void testGetSymbol() {
    Assert.assertEquals('♣', card.getSymbol());

  }

}
