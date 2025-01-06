package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ConfigurationFileReader;
import cs3500.threetrios.controller.ThreeTriosGameController;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ThreeTriosGameFallenAceModel;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosGameModelObserver;
import cs3500.threetrios.model.ThreeTriosGamePlusModel;
import cs3500.threetrios.model.ThreeTriosGameReverseModel;
import cs3500.threetrios.model.ThreeTriosGameSameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.players.HumanPlayer;
import cs3500.threetrios.players.MachinePlayer;
import cs3500.threetrios.players.Player;
import cs3500.threetrios.strategies.CornerStrategy;
import cs3500.threetrios.strategies.MaximizeFlipsStrategy;
import cs3500.threetrios.strategies.MiniMaxStrategy;
import cs3500.threetrios.strategies.MinimizeChanceOfBeingFlippedStrategy;
import cs3500.threetrios.strategies.ThreeTriosStrategy;
import cs3500.threetrios.view.ThreeTriosFrameGUI;

/**
 * Represents the main class for the Three Trios game.
 */
public final class ThreeTrios {

  private static boolean[][] grid;

  private static List<Card> deck;

  /**
   * Main method for the Three Trios game.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    try {
      final String player1Config = args[0].toUpperCase().trim();
      final String player2Config = args[1].toUpperCase().trim();

      List<String> variants = new ArrayList<>();
      for (int index = 2; index < args.length; index++) {
        variants.add(args[index].toUpperCase());
      }


      grid = ConfigurationFileReader
              .createGridFromFile("gridWithPathToAllCells.txt");
      deck = ConfigurationFileReader.createDeckFromFile("deckLarge.txt");


      ThreeTriosModel model = null;


      if (!variants.isEmpty()) {
        if (variants.get(0).equals("SAME")) {
          variants.remove(0);
          model = new ThreeTriosGameSameModel(grid, deck, getModel(variants));
        } else if (variants.get(0).equals("PLUS")) {
          variants.remove(0);
          model = new ThreeTriosGamePlusModel(grid, deck, getModel(variants));
        } else {
          model = new ThreeTriosGameModel(grid,
                  deck, getModel(variants));
        }
      } else {
        model = new ThreeTriosGameModel(grid,
                deck, getModel(variants));
      }

      ThreeTriosFrameGUI view1 = new ThreeTriosFrameGUI(new ThreeTriosGameModelObserver(model));
      ThreeTriosFrameGUI view2 = new ThreeTriosFrameGUI(new ThreeTriosGameModelObserver(model));
      Player player1 = getPlayer(player1Config, model, Color.RED);
      Player player2 = getPlayer(player2Config, model, Color.BLUE);

      new ThreeTriosGameController(model, player1, view1, Color.RED);
      new ThreeTriosGameController(model, player2, view2, Color.BLUE);

      view1.setVisible(true);
      view2.setVisible(true);

      model.startGame();

    } catch (IllegalArgumentException | IndexOutOfBoundsException exception) {
      System.out.println("Invalid setup. Could not start program.");
    }
  }

  private static ThreeTriosModel getModel(List<String> variants) {
    if (variants.isEmpty()) {
      return null;
    }
    String variant = variants.remove(0);
    switch (variant) {

      case "REVERSE":
        return new ThreeTriosGameReverseModel(getModel(variants));
      case "FALLENACE":
        return new ThreeTriosGameFallenAceModel(getModel(variants));
    }
    return null;
  }

  private static Player getPlayer(String type, ThreeTriosModel model, Color color) {
    if (type.equals("HUMAN")) {
      return new HumanPlayer();
    } else {
      ThreeTriosStrategy strategy = null;
      switch (type) {
        case "STRATEGY1":
          strategy = new MaximizeFlipsStrategy(null);
          break;
        case "STRATEGY2":
          strategy = new CornerStrategy(null);
          break;
        case "STRATEGY3":
          strategy = new MinimizeChanceOfBeingFlippedStrategy(null);
          break;
        case "STRATEGY4":
          strategy = new MiniMaxStrategy(new MaximizeFlipsStrategy(null),
                  new MaximizeFlipsStrategy(null));
          break;
        default:
          throw new IllegalArgumentException("Not a valid strategy");
      }
      return new MachinePlayer(model, strategy, color);
    }
  }
}