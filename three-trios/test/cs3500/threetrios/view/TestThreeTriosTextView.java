package cs3500.threetrios.view;

import org.junit.Assert;
import org.junit.Test;

import cs3500.threetrios.controller.ConfigurationFileReader;
import cs3500.threetrios.model.ThreeTriosGameModel;

/**
 * Test cases for the ThreeTriosTextView.
 * These tests ensure the output for a given input of grid and deck path
 * is correct and formatted as expected.
 */
public class TestThreeTriosTextView {

  private ThreeTriosGameModel model;
  private ThreeTriosTextView view;


  @Test
  public void testNoHoleLargeCard() {
    model = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithNoHoles.txt"),
            ConfigurationFileReader.createDeckFromFile("deckMedium.txt"), null);
    view = new ThreeTriosTextView(model);
    model.startGame();

    String rendered = view.render();
    Assert.assertTrue(rendered.contains("Player: RED"));

    String expectedOutput =
            "Player: RED\n"
                    + "_ _ _\n"
                    + "_ _ _\n"
                    + "_ _ _\n"
                    + "Hand:\n"
                    + "MortisTheClarinetSorcerer 2 1 8 7\n"
                    + "EmzTheDJMaster 5 1 6 6\n"
                    + "MegTheBattleBeetle 3 4 7 2\n"
                    + "SproutTheGardener A 2 5 8\n"
                    + "StuTheRockstar 9 1 8 4\n";

    Assert.assertEquals(expectedOutput, rendered);
  }

  @Test
  public void testInitialGameState() {
    model = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithNoHoles.txt"),
            ConfigurationFileReader.createDeckFromFile("deckMedium.txt"), null);
    view = new ThreeTriosTextView(model);
    model.startGame();

    String rendered = view.render();
    Assert.assertTrue(rendered.contains("Player: RED"));

    String expectedOutput =
            "Player: RED\n"
                    + "_ _ _\n"
                    + "_ _ _\n"
                    + "_ _ _\n"
                    + "Hand:\n"
                    + "MortisTheClarinetSorcerer 2 1 8 7\n"
                    + "EmzTheDJMaster 5 1 6 6\n"
                    + "MegTheBattleBeetle 3 4 7 2\n"
                    + "SproutTheGardener A 2 5 8\n"
                    + "StuTheRockstar 9 1 8 4\n";

    Assert.assertEquals(expectedOutput, rendered);
  }

  @Test
  public void testLargeCardDeck() {
    model = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithPathToAllCells.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);
    view = new ThreeTriosTextView(model);
    model.startGame();
    String rendered = view.render();

    String expectedOutput =
            "Player: RED\n"
                    + "_   _   _\n"
                    + "_ _ _ _ _\n"
                    + "_   _   _\n"
                    + "_ _ _ _ _\n"
                    + "_   _   _\n"
                    + "Hand:\n"
                    + "8-Bit 5 2 3 9\n"
                    + "Amber 6 9 2 3\n"
                    +  "Angelo 9 1 7 6\n"
                    + "Ash 4 6 3 9\n"
                    + "Barley 4 9 6 2\n"
                    + "Bea A 8 5 2\n"
                    + "Belle 7 9 3 1\n"
                    + "Berry 6 3 4 2\n"
                    + "Bibi 9 1 A 8\n"
                    + "Bo 3 A 1 6\n";

    Assert.assertEquals(expectedOutput, rendered);
  }

  @Test
  public void testLargeCardDeckFilePath() {
    model = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithPathToAllCells.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);
    view = new ThreeTriosTextView(model);
    model.startGame();

    String rendered = view.render();

    String expectedOutput =
            "Player: RED\n"
                    + "_   _   _\n"
                    + "_ _ _ _ _\n"
                    + "_   _   _\n"
                    + "_ _ _ _ _\n"
                    + "_   _   _\n"
                    + "Hand:\n"
                    + "8-Bit 5 2 3 9\n"
                    + "Amber 6 9 2 3\n"
                    + "Angelo 9 1 7 6\n"
                    + "Ash 4 6 3 9\n"
                    + "Barley 4 9 6 2\n"
                    + "Bea A 8 5 2\n"
                    + "Belle 7 9 3 1\n"
                    + "Berry 6 3 4 2\n"
                    + "Bibi 9 1 A 8\n"
                    + "Bo 3 A 1 6\n";

    Assert.assertEquals(expectedOutput, rendered);
  }



}


