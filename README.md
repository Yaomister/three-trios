# Three Trios

Java implementation of a tactical, turn-based card placement game with a decoupled MVC design, multiple AI opponents, and optional rule variants. Built to showcase clean architecture and extensibility rather than course scaffolding.

## Highlights
- Swing-based dual-window UI so each player gets a dedicated view while sharing one running game.
- Pluggable AI opponents (`STRATEGY1-4`) covering greedy, positional, risk-aware, and minimax decision making.
- Rule variants (`SAME`, `PLUS`, `REVERSE`, `FALLENACE`) that can be stacked on startup to change scoring and combo rules.
- Configuration-driven setup: grids and decks are read from text files for quick scenario changes (`docs/gridWithPathToAllCells.txt`, `docs/deckLarge.txt` by default).
- Architecture built around model/view/controller packages with observers to keep the UI from mutating state directly.

## Quick Start
Prerequisite: JDK 17+.

Compile from source:
```bash
javac -d out $(find src -name "*.java")
```

Run the GUI (example: human vs. AI with Plus + Same variants):
```bash
java -cp out cs3500.threetrios.ThreeTrios HUMAN STRATEGY1 PLUS SAME
```
- First arg: player one type (`HUMAN` or `STRATEGY1-4`).
- Second arg: player two type (`HUMAN` or `STRATEGY1-4`).
- Remaining args (optional): any combination of `PLUS`, `SAME`, `REVERSE`, `FALLENACE` to toggle rule variants.

A prebuilt artifact is also available if you prefer not to compile:
```bash
java -jar ThreeTrios.jar HUMAN HUMAN
```

## Controls & Flow
- Each player window shows their hand and the shared grid; select a card, then a cell to place it.
- The controller enforces turn order, legal moves, captures, and variant-specific rules.
- Combat resolution and turn advancement occur after a placement; the window titles reflect whose turn it is.

## Project Layout
- `src/cs3500/threetrios/model` – core game state, grid/cell/card/player abstractions, variant models.
- `src/cs3500/threetrios/view` – Swing UI components and frames (read-only access to the model via observers).
- `src/cs3500/threetrios/controller` – event handling, configuration file reader, model/view adapters.
- `src/cs3500/threetrios/players` – human and machine player implementations.
- `src/cs3500/threetrios/strategies` – AI strategies (maximize flips, corner prioritization, minimize risk, minimax).
- `docs` – sample grids and decks used when launching the game.
- `StartOfGame.png`, `MiddleGameState.png`, `SelectBlue.png`, `SelectRed.png` – reference screenshots.

## Notes
- The default grid/deck referenced in `ThreeTrios` can be swapped by updating the file names or providing new assets under `docs/`.
- The model enforces invariants such as non-null phases and turns; defensive copies are returned for read-only accessors.
- Tests are included under `test/` for model and strategy behaviors; controller tests were trimmed for size.
