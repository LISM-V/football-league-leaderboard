# Football League Leaderboard

A **command-line application** that calculates the ranking table for a football (soccer) league based on match results. Written in **Scala**, this project demonstrates clean architecture with **input, parser, service, and main layers**, and is **fully testable and production-ready**.

---

## Table of Contents
- [Project Description](#project-description)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Input Format](#input-format)
- [Output Format](#output-format)
- [Example](#example)
- [Running Tests](#running-tests)
- [Future Improvements](#future-improvements)
- [Contributing](#contributing)
- [License](#license)

---

## Project Description

This application reads match results and produces a **league leaderboard** showing each team’s rank and points.  

Key design goals:

- **Clean architecture**: separate layers for input, parsing, computation, and output.
- **Functional programming style**: immutable data structures, `Either` for error handling.
- **Production-ready**: handles invalid input gracefully, sorts teams correctly, and supports ties.

---

## Features

- Calculates **points** per league rules:
  - Win = 3 points
  - Draw = 1 point
  - Loss = 0 points
- Handles **ties** in points, ranking alphabetically.
- Accepts input from **stdin** or **text files**.
- Fully **unit-tested**.

---

## Installation

### Prerequisites
- Java 17+  
- [SBT](https://www.scala-sbt.org/) (Scala Build Tool)

### Steps
1. Clone the repository:
git clone https://github.com/LISM-V/football-league-leaderboard.git
cd football-league-leaderboard

2. Compile the project:
sbt compile

3. (Optional) Run unit tests:
sbt test

---

## Usage

# 1. Using a text file as input:
sbt "run path/to/matches.txt" | sbt "run input.txt"

# 2. Using stdin:
sbt run

# Then enter match results line by line.
# Press Enter on an empty line to finish input.

---

## Input Format

Each line represents a match in the format:

Team1 Goals1, Team2 Goals2

- `Team` names can contain spaces.
- `Goals` must be a non-negative integer.
- Example:
Lions 3, Snakes 3

Invalid lines are **skipped** with a warning.

---

## Output Format

The leaderboard is printed as:

1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
3. Snakes, 1 pt
5. Grouches, 0 pts

- Points displayed as `pt` for 1 point, `pts` for multiple points.
- Teams tied on points share the same rank and are ordered alphabetically.

---

## Example

### Input
- Lions 3, Snakes 3
- Tarantulas 1, FC Awesome 0
- Lions 1, FC Awesome 1
- Tarantulas 3, Snakes 1
- Lions 4, Grouches 0

### Output
1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
3. Snakes, 1 pt
5. Grouches, 0 pts

---

## Running Tests

This project uses **MUnit** for unit testing:

sbt test

Tests cover:

- Input parsing (`MatchParser`)
- Points calculation (`LeaderBoardService`)
- Parsing Matches (`MatchParser`)

---

## Future Improvements

While this project is fully functional, the following enhancements could improve **robustness and maintainability**:

- **Advanced error handling**: return detailed error reports instead of skipping lines silently.
- **Configurable points system**: allow wins/draws/losses to be adjusted via config.
- **Support for large input files**: stream processing for scalability.
- **Improved CLI options**: flags for input/output files, verbose logging.
- **Better logging**: integrate a logging library instead of `println`.
- **Docker container**: for easy deployment and portability.

---

## License

MIT License © 2026