# READ-ME

Homework for F24 OOD <br>
Nathan C, Eric Y <br>
The code was written in Java. <br>
Three trios game.

### Model/player envisioning

We decided to instantiate players by having a player object for each player, which would interact
with the controller. Each player would have a view corresponding to them, representing the
UI that each player sees.
The controller would be controlling the model, and the player object would then call the controller
to control the model based on whose turn it is. The player will not be aware of the model,
nor with the model be aware of the players. Their only connection is through the controller.
Given the fact this player might be an AI player, our design is flexible in the way that the player
object can call the controller without needing human input, rendering it as a player without a UI.

[model]

↑

[controller] ← [Player 1] ← [view]

↑

[Player 2] <- [view]

**Overview:**
The purpose of this code is to overall create a fully functioning Three Trios Game. Currently,
we have a working model and view, which allows us to maintain and enforce the rules of the game,
as well as produce a text render to visualize the game state.
We've abstracted the functionality of the model under the possibility that there may be
variations in the game rules, thus, we created our design to account for any future extensions to
the game rules.
An example of this is apparent in the battleMethod which handles the combo damage step
implementation. In the future, this combo step could perhaps be altered into a new variation, thus,
are methods in the interface allow for any new combo/damage steps to the class.

All relevant functionality can be found within the cs3500.threetrios package, organized
by model, view, and controller folders.

### Quick start:

```java
    //This creates a new model object
ThreeTriosModel model = new ThreeTriosGameModel();

//Start the game:
    model.

startGame("gridWithPathToAllCells.txt","docs/deckLarge.txt");

//Play a turn:
    finishedWithTieModel.

playCardToCell(0,1,1);
    finishedWithTieModel.

battle();
    finishedWithTieModel.

nextTurn();
```

### Key components

The model drives the control-flow, which is using the functionality within the Grid, Cell, Card
and Player classes. These are the classes that are being driven.

#### Invariant 1

The phase can not be null

#### Invariant 2

The current turn can not be null.

### Key subcomponents:

Nouns: Grid, Card, Player, Cell
All of these can be found within the model folder of the cs3500.threetrios package. This is all
part of the src folder.

###### Grid:

The `grid` represents the game board, where all actions involving card placements, moves, and
interactions happen. It holds cells that form the playing area and is responsible for managing
the state and arrangement of these cells.
The `grid` provides the structure and organization of the game. It stores each `Cell` in a 2D array
and provides methods for interacting with individual cells.

###### Card:

The card exists to represent an individual game piece with specific characteristics,
such as color, name, and `AttackValues`. These cards are the tools players use to interact with
each other as well as with the `grid`.

###### Player:

The `Player` in the model represents the hand and the color that is associated with a physical
player.
It serves as a container for the actions specific to each individual engaging in the game.
Having the `Player` class manage the cards in each player’s hand, name, and color, helps
to differentiate between players and their moves. The player’s hand moves by adding and removing
cards enabling them to participate in the game.

NOTE: the player in the model is not equivalent to a physical human player that interacts with
the game.

###### Cell:

The `cell` represents a unit within the `grid`, marking a single location that can hold a `card` or
be a hole. They are useful for positioning cards in the gameplay area of the grid.
Each `Cell` can hold a `card`, remain empty, or be marked as a hole. Cells are where cards are
placed
for battles.

### Notes for our implementation

The configuration file takes a src to the `grid`, and then a src to the deck.
We put the file paths in the docs folder. This was so we could organize the files into the
same folder. The file reader will automatically look for the file name in the docs folder
if only the name was provided. Otherwise, if an absolute file path is provided, it will follow it
and look for the file. For our purposes, having all the files inside the same folder was easier
for testing, that way we could merely just put the file’s exact name, and then it could find
both the `grid` and the deck files.

For our `grid`, we decided to make it 0-indexed based, starting with (0,0) and the top left and
moving right and down as we increased numbers. The rows represented the y-axis and the columns
represented the x-axis.

# Changes for Part 2

### New model changes

1. Removed the configuration file reader from the model and moved it to the controller package.
2. Updated the `StartGame` method and made it take in a 2D array of booleans and a list of cards
   instead of a configuration file.
3. Added many new method such as `getCellAt`, `getPlayerHand`, `getGridWidth`, `getGridLength`,
   `isPlayLegal`, `getPlayerScore` and `getGrid`. For the specifics of each method, please look
   at the javadoc documentation listed above each method. These were added as a way to peek
   into the state of the model without actually returning parts of the model like the grid object.
4. Specifically for the `getGrid`, `getCellAt` and `getPlayerHand`, mutating the return values
   will not affect the actual model.
5. Made interfaces for the `Player`, `Cell`, `Grid` and `Card`. These were missing from
   our original design and were added to keep open to the variations of the game.

####### Other classes we changed/added

1. Read me to document our changes for the new HW
2. `GameCard`, `GameCell`, `GameGrid`, `GamePlayer` classes, which are the newly added concrete
   classes associated with each interface,
3. `ReadOnlyThreeTriosModel` interface just as a read only version of the model so that
   the view is not able to mutate it
4. The entire strategies package, which includes all the new strategies as well as relevant
   classes, abstract classes and interfaces that aided us for the creation of the strategies.
5. The entire view package, which defined our GUI that allows us to physically render and show the
   game state. This includes the interfaces and the classes to display the hand, grid and frame
   classes for the GUI.
6. Also added a ThreeTrios main runner that allowed us to actually run and display the code.
7. A lot of our tests got added and changed throughout the MVC and were also refactored
8. We added a new package for strategies that tested all of the above strategies that we wrote,
   including integration and unit tests. Many of the model tests were changed to account for
   things we created and edited in the model, as well as for the controller.
9. We moved the GridBuilder class because it was not needed for our new design, and in turn also
   got rid of those relevant tests as well.
10. We also created mocks that allowed us to test certain strategies. These mocks ensured that
    we can test specific aspects of these strategies, specifically breaking ties properly and
    checking each location properly when needed.
11. The TestThreeTriosGameModel that matched the new model fixes we made.
12. Also added screenshots of the view at different phases to prove that the GUI is working
    properly.
13. Added a transcript txt file that shows we checked each location required from our
    mocked model.
14. To implement the features design pattern, we made a very bare-bone controller that simply
    implements the Features interface. We did this whilst thinking ahead about the controller we
    are going to implement for the next assignment, but also we felt that it is poor design to
    handle the mouseclick events (and it's after effects) all in the view. In addition, we also felt
    that printing the coordinates of the cell clicked was defined as input/output, so the controller
    should be the one handling that (the view should only be in charge of displaying to the UI).

### Extra Credit

We implemented both of the extra credit strategies, and they were both placed in the strategies
package. Furthermore, we thoroughly tested each individual test, both integration and unit tests,
and ensured that they each matched the expected output. These tests can be found in the
test package underneath the strategies package for the tests.
These two strategies were `MinimizeChanceOfBeingFlippedStrategy` and the `MiniMaxStrategy`.

For the first extra credit strategy `MinimizeChanceOfBeingFlippedStrategy`, we noticed
that it was a generalized version of the second strategy, the one that chooses the corners
that are hardest to flip. So, we refactored the code to make it work for the entire grid
of playable cells. Furthermore, the corner strategy actually extends the 1st extra credit
strategy, to reduce code duplication and redundancy.

For the second extra credit strategy `MiniMaxStrategy`, we first decided to take in the strategy
that the opponent was using to find out their best move. Then, we look through all of our
possible moves, and determined which of those would minimize the amount of cards the
opponent could flip in their next turn, and in other words, their maximum strategy is minimized.

### Other documentations for strategies

When two strategies are chained, it will first determine what is the best move, according to
its standards, and afterwards it will pass in the results it gets into the next strategy
to look for the best move according to the next strategy's standards within the pool of moves
we already have.

A strategy moves on to the next when it finds a set of answers. If it does not produce an answer
or find a best move, the next strategy will not be able to produce any next moves because they
are dependent on each other.

How you determine the hardest card to flip is you look through every possible opponent card
placed in the 4 directions around it, and see how many combinations can flip our card. The card
with the least chance of being flipped is deemed the hardest card to be flipped.

### View notes

In our view gui, a user is able to click on both the red hand and the blue hand without deselecting
one another. This is something that we decided should be the controllers job, not the view,
because the controller decides who move it should be. Therefore, it's possible for both the red and
blue hand to be selecting at the same time, and deselecting would only affect the same hand colors.

Furthermore, the gui title only changes when we physically edit the model, in the main runner.
After we do the battle stage and delegate next turns does the title change. The view
does not physically do this as it merely just renders.

# Changes for Section 3

In this section, we added a fully functioning controller to allow the user to physically
be able to play the game with other Players. The controller package also include
two adapter classes, `ModelFeaturesAdapter` and `ViewFeaturesAdapter` to prevent casting
into an actual controller giving it access to the model features methods. This controller
has two different views which allows multiplayer functionality through its different interactive
windows. This controller ensures that players are able to play the game with other players during
their respective turn and will be notably prompted when it is their appropriate turn. 
We also added a `ThreeTriosGameModelObserver` in the model package to make our model uncastable.

The controller package now includes the following classes: `ConfigurationFileReader`,
`ModelFeatures`, `ModelFeaturesAdapter`,`ThreeTriosGameController`,`ViewFeatures`
and the `ViewFeaturesAdapter`. These classes and interfaces all compose of the controller 
description as provided above. 

Furthermore, we decided to make controller tests to test the functionality of the controller and 
ensure it all works properly. These included mocks, for the model, frame and player action. 
These mocks allowed us to unit test the controller without having to worry about the model, 
as these mocks allowed us to test specific aspects we wanted to ensure worked properly.
Also added a player Test directory to test the player interfaces separately. 

We also fixed some javadoc comments and style all throughout the rest of the code. Particularly in
the model package, where the startGame method Javadoc was changed as lots of the logic ended up 
being moved to the constructor instead. This was documented and reflected through new updated
javadoc comments. 

Part of the thing we did was make sure the local variables were properly declared with the 
interface, rather than the concrete class which we fixed from previous homeworks. This
was changed for all the new classes that we added for this assignment. 

Additionally, we also removed a lot of files in our /doc folder to go under the threshold of the 
125 files for submission.

# HW9
Slightly changed the ordering in the text view to display South card number before East.
As such slightly fixed the textView test class to account for these changes. 

For this assignment we decided to implement all the variations that were offered for us to 
do and complete. 
Added abstract class `ThreeTriosGameVariantModel` and classes `ThreeTriosGameFallenAceModel`, 
`ThreeTriosGamePlusModel`, `ThreeTriosGameReverseModel` and `ThreeTriosGameSameModel`.

Added test hint class, tests for SameModel, ReverseModel, plusModel, fallenAceModel and 
modelCombined classes. 

Changed and fixed javadocs throughout assignment as well to fix style, and updated README.

We removed ALL tests for the controller, strategies, and player classes (and corresponding mock 
classes) to fit under the 125 file limit.
