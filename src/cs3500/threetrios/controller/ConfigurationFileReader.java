package cs3500.threetrios.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import cs3500.threetrios.model.AttackValue;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.GameCard;

/**
 * Represents a configuration file reader reading in a
 * configuration file and returns a grid or deck.
 * The configuration file for a grid is a file that
 * contains the number of rows and columns of the grid,
 * followed by the grid itself. The configuration file
 * of a deck contains rows of information representing the
 * name, and directional attack values of each card.
 */
public class ConfigurationFileReader {


  /**
   * Reads in the configuration information of a grid and instantiate and builds a 2D array of
   * booleans representing the holes. The source of the file could be the name of a file,
   * which this method will try to look for in the docs folder, or the path ot a file, which this
   * method will try to load that file. The file could have any extension (.txt, .config, .json...).
   *
   * @param source The name of the configuration file, or the path to it.
   * @return the constructed grid
   * @throws IllegalArgumentException if the file source is null, or if the file could not be found
   * @throws IllegalArgumentException if the number of columns and rows stated at the top of the
   *                                  file is not a number, or they are less than 1.
   * @throws IllegalArgumentException if the number of columns and rows stated at the top of the
   *                                  file doesn't match the amount of rows and columns of values
   *                                  in the file.
   * @throws IllegalArgumentException if the file contains letters other than 'C' (playable cells)
   *                                  and 'X' (for holes)
   */
  public static boolean[][] createGridFromFile(String source) {
    if (source == null) {
      throw new IllegalArgumentException("File source cannot be null.");
    }
    Scanner scanner = createScanner(source);
    try {
      int rows = Integer.parseInt(scanner.next());
      int cols = Integer.parseInt(scanner.next());

      if (rows < 1 || cols < 1) {
        throw new IllegalArgumentException("Rows or columns number less than 1.");
      }
      scanner.nextLine();

      boolean[][] grid = new boolean[rows][cols];
      int row = 0;
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.length() > cols) {
          throw new IllegalArgumentException("Mismatch between number of columns "
                  + "stated at the top of the document and actual amount.");
        }
        String[] cells = line.split("");
        for (int col = 0; col < cells.length; col++) {
          if (cells[col].equals("X")) {
            // set the hole at position (col, row);
            grid[row][col] = true;
          } else if (!cells[col].equals("C")) {
            throw new IllegalArgumentException("File cannot have letters other than C and X");
          }
        }
        row++;
      }
      if (row != rows) {
        throw new IllegalArgumentException("Mismatch between number of rows "
                + "stated at the top of the document and actual amount.");
      }
      return grid;
    } catch (NumberFormatException exception) {
      throw new IllegalArgumentException("Number in file not formatted in correct order.");
    }
  }

  /**
   * Reads in the configuration information of a deck and instantiates a list of cards based
   * on those specifications. The source of the file could be the name of a file, which this method
   * will try to look for in the docs folder, or the path ot a file, which this method will try to
   * load that file. The file could have any extension (.txt, .config, .json...).
   *
   * @param source The name of the configuration file, or the path to it.
   * @return the list of cards.
   * @throws IllegalArgumentException if the file source is null, or if the file could not be found
   * @throws IllegalArgumentException if there are less than or more than 4 attack values following
   *                                  the name of the card.
   * @throws IllegalArgumentException if the attack values following the name of the card are
   *                                  not between 1 and 9, or the letter 'A' (representing 10).
   */
  public static List<Card> createDeckFromFile(String source) {
    if (source == null) {
      throw new IllegalArgumentException("File name cannot be null");
    }
    Scanner scanner = createScanner(source);

    try {
      List<Card> deck = new ArrayList<>();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] values = line.split(" ");
        String name = values[0];

        // Convert the attack values from hexadecimal to decimal and find the corresponding enum
        // value.
        AttackValue northValue = Objects.requireNonNull(
                AttackValue.enumOfValue(Integer.parseInt(values[1], 16)));
        AttackValue southValue = Objects.requireNonNull(
                AttackValue.enumOfValue(Integer.parseInt(values[2], 16)));
        AttackValue eastValue = Objects.requireNonNull(
                AttackValue.enumOfValue(Integer.parseInt(values[3], 16)));
        AttackValue westValue = Objects.requireNonNull(
                AttackValue.enumOfValue(Integer.parseInt(values[4], 16)));
        GameCard card = new GameCard(name, northValue, southValue, eastValue, westValue);

        deck.add(card);
      }
      return deck;
    } catch (IndexOutOfBoundsException err) {
      throw new IllegalArgumentException("Invalid file format.");
    } catch (NullPointerException err) {
      throw new IllegalArgumentException("Invalid attack values.");
    }
  }

  // Creates a scanner of the configuration file. Throws an IllegalArgumentsException if the file
  // could not be found.
  private static Scanner createScanner(String source) {
    File file = new File(source);

    if (!file.exists()) {
      String path = "docs" + File.separator + source;
      file = new File(path);
    }
    try {
      FileReader reader = new FileReader(file);
      return new Scanner(reader);
    } catch (FileNotFoundException err) {
      throw new IllegalArgumentException("File could not be found.");
    }
  }
}
