# UNO Card Game Project

A Java implementation of the classic UNO card game using object-oriented programming principles.

## Overview

This project implements the popular UNO card game in Java, featuring both human and AI players. The game follows standard UNO rules with a clean, modular design.

## Features

- **Human vs AI gameplay** - Play against a computer opponent
- **Standard UNO rules** - Follows official UNO game mechanics
- **Object-oriented design** - Clean separation of concerns with dedicated classes
- **Interactive gameplay** - User-friendly console interface

## Project Structure

```
UNO_OOP_PROJECT/
├── Main.java          # Entry point of the application
├── Game.java          # Main game logic and flow control
├── Player.java        # Abstract base class for players
├── HumanPlayer.java   # Human player implementation
├── BotPlayer.java     # AI player implementation
├── Card.java          # Card representation and properties
└── Deck.java          # Deck management and card operations
```

## Classes Description

- **Main.java**: Application entry point that initializes and starts the game
- **Game.java**: Core game logic, turn management, and win condition checking
- **Player.java**: Abstract base class defining player interface and common functionality
- **HumanPlayer.java**: Handles human player input and decision making
- **BotPlayer.java**: Implements AI logic for computer opponent
- **Card.java**: Represents individual UNO cards with color, number, and special properties
- **Deck.java**: Manages card deck operations including shuffling, dealing, and drawing

## How to Play

1. Compile and run the Java files
2. Follow the on-screen prompts to make your moves
3. Play cards by matching color or number with the top card
4. Use special cards (Skip, Reverse, Draw Two, Wild, Wild Draw Four) strategically
5. First player to empty their hand wins!

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A Java IDE or command line compiler

### Running the Game

1. Navigate to the `UNO_OOP_PROJECT` directory
2. Compile all Java files:
   ```bash
   javac *.java
   ```
3. Run the main class:
   ```bash
   java Main
   ```

## Game Rules

- Players take turns playing cards that match the color or number of the top card
- Special cards have unique effects (Skip, Reverse, Draw Two, Wild, Wild Draw Four)
- Players must call "UNO" when they have one card left
- First player to play all their cards wins the round

## Contributing

Feel free to contribute to this project by:
- Adding new features
- Improving AI difficulty
- Enhancing the user interface
- Fixing bugs or adding error handling

## License

This project is open source and available under the MIT License.
