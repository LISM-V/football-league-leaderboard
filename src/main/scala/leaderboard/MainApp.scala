package leaderboard

import leaderboard.model._
import leaderboard.parser.MatchParser
import leaderboard.service.LeaderBoardService

import scala.io.{Source, StdIn}
import scala.util.Using
import leaderboard.input.InputReader

object MainApp extends App {


	// Read input
	val inputLines = InputReader.read(args)

  	// Parse matches
	val parsedMatches: List[Match] = inputLines.flatMap { line =>
    	MatchParser.parse(line) match {
      		case Right(m) => Some(m)
      		case Left(err) =>
        		println(s"Skipping invalid input: $err")
        		None
    	}	
  	}

	// Comupute leaderboard
	val leaderboard: List[LeaderBoardEntry] = LeaderBoardService.compute(parsedMatches)


	// Print leaderboard
	leaderboard.foreach { entry =>
    	val pts = entry.teamScore.points
    	val ptString = if (pts == 1) "pt" else "pts"
    	println(s"${entry.position}. ${entry.teamScore.team.name}, $pts $ptString")
  	}

  
}


