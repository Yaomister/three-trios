package cs3500.threetrios.view;

import org.junit.Test;

import java.util.Arrays;

import javax.swing.JButton;

import cs3500.threetrios.controller.ConfigurationFileReader;
import cs3500.threetrios.model.ThreeTriosGameModel;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the hint button
 */
public class TestHint {
  private ThreeTriosGameModel model;

  @Test
  public void testHintToggleButton() {
    model = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithNoHoles.txt"),
            ConfigurationFileReader.createDeckFromFile("deckMedium.txt"), null);
    ThreeTriosGridPanelGUI panel = new ThreeTriosGridPanelGUI(model);

    JButton toggleHints = (JButton) Arrays.stream(panel.getComponents())
            .filter(comp -> comp instanceof JButton)
            .findFirst()
            .orElseThrow(() -> new AssertionError("Toggle hints button not found"));

    assertEquals("Hints : OFF", toggleHints.getText());

    toggleHints.doClick();
    assertEquals("Hints : ON", toggleHints.getText());

    toggleHints.doClick();
    assertEquals("Hints : OFF", toggleHints.getText());
  }


}
