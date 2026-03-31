package leaderboard.parser

import leaderboard.model.{ Match, Score, Team }
import scala.util.Try

object MatchParser {

    def parse(input: String): Either[String, Match] = {
    
        input.split(",") match {
            case Array(left, right) => 
                
                for {
                    teamScore1 <- parseTeamScore(left.trim)
                    teamScore2 <- parseTeamScore(right.trim)

                } yield {
                    val (team1, goals1) = teamScore1
                    val (team2, goals2) = teamScore2

                    Match(team1, team2, Score(goals1, goals2))
                }
    
            case _ => Left(s"Invalid input format: '$input'")
        }
    }

    private def parseTeamScore(input: String): Either[String, (Team, Int)] = {

        val pattern = "^(.+?)\\s+(\\d+)$".r

        input match {
            case pattern(teamName, goalsStr) =>
                Try(goalsStr.toInt).toEither
                    .left.map(_ => s"Invalid score: '$goalsStr'")
                    .flatMap{ goals => 
                        if (goals < 0) 
                            Left(s"Goals cannot be negative: '$goalsStr'")
                        else 
                            Right((Team(teamName.trim), goals))
                    }
            case _ =>
                Left(s"Invalid team and score format: '$input'. Expected format: 'TeamName +Goals'")
        }
    }
}
