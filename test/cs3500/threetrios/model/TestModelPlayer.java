package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;


/**
 * Tests for the Player class.
 * These tests ensure that the Player class behaves as expected with the appropriate
 * methods.
 */
public class TestModelPlayer {

  private GameModelPlayer player;
  private GameCard redCard;

  @Before
  public void setUp() {
    player = new GameModelPlayer("SurgeTheSodaMachine", Color.RED);

    redCard = new GameCard("Red Card", AttackValue.ONE,
            AttackValue.A, AttackValue.TWO, AttackValue.FOUR);
    redCard.setColor(Color.RED);
  }

  @Test
  public void testGetName() {
    assertEquals("SurgeTheSodaMachine", player.getName());
  }

  @Test
  public void testGetColor() {
    assertEquals(Color.RED, player.getColor());
  }

  @Test
  public void testGetHand() {
    List<Card> hand = player.getHand();
    assertNotNull(hand);
    assertEquals(0, hand.size());
  }

  @Test
  public void testAddCardToHand() {
    player.addCardToHand(redCard);
    assertEquals(1, player.getHand().size());
    assertEquals(redCard, player.getHand().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardToHandWithNullThrowsIAE() {
    player.addCardToHand(null);
  }

  @Test
  public void testRemoveCardFromHand() {
    player.addCardToHand(redCard);
    Card removedCard = player.removeCardFromHand(0);
    assertEquals(redCard, removedCard);
    assertTrue(player.getHand().isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardFromHandNegativeIndex() {
    player.removeCardFromHand(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardFromHandIndexTooLarge() {
    player.addCardToHand(redCard);
    player.removeCardFromHand(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardFromEmptyHand() {
    player.removeCardFromHand(0);
  }

  @Test
  public void mutatingListDoesNotChangeOriginalList() {
    List<Card> hand = player.getHand();
    hand.add(redCard);
    assertEquals(0, player.getHand().size());
  }
}
