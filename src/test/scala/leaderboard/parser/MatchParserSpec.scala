package leaderboard.parser

import leaderboard.model._
import leaderboard.parser.MatchParser

class MatchParserSpec extends munit.FunSuite {

    test("Valid input parses correctly") {
        val line = "Lions 3, Snakes 1"
        val result = MatchParser.parse(line)
        assert(result.isRight)
        val m = result.right.get
        assert(m.team1.name == "Lions")
        assert(m.team2.name == "Snakes")
        assert(m.score.team1Goals == 3)
        assert(m.score.team2Goals == 1)
    }

    test("Invalid format returns Left") {
    	val line = "Lions 3 Snakes 1"
    	val result = MatchParser.parse(line)
    	assert(result.isLeft)
    }

    test("Negative score is rejected") {
      	val line = "Lions -1, Snakes 1"
      	val result = MatchParser.parse(line)
      	assert(result.isLeft)
    }

}