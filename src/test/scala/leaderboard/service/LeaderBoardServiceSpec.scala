package leaderboard.service

import leaderboard.model._
import leaderboard.service.LeaderBoardService
import leaderboard.parser.MatchParser
import munit.FunSuite

class LeaderBoardServiceSpec extends FunSuite {

    val points = Points()

    test("Single match: win/loss") {
        val match1 = Match(Team("Lions"), Team("Snakes"), Score(3, 0))
        val lb = LeaderBoardService.compute(List(match1), points)
        assert(lb.head.teamScore.team.name == "Lions")
        assert(lb.head.teamScore.points == 3)
        assert(lb(1).teamScore.team.name == "Snakes")
        assert(lb(1).teamScore.points == 0)
    }

    test("Single match: draw") {
        val match1 = Match(Team("Lions"), Team("Snakes"), Score(2, 2))
        val lb = LeaderBoardService.compute(List(match1), points)
        assert(lb.forall(_.teamScore.points == 1))
    }

    test("Multiple matches with aggregation") {
        val matches = List(
        Match(Team("Lions"), Team("Snakes"), Score(3, 0)),
        Match(Team("Lions"), Team("FC Awesome"), Score(1, 1)),
        Match(Team("Tarantulas"), Team("Snakes"), Score(3, 1))
        )
        val lb = LeaderBoardService.compute(matches, points)
        assert(lb.exists(_.teamScore.team.name == "Lions"))
    }

    test("Tie in points assigns same rank") {
        val matches = List(
        Match(Team("FC Awesome"), Team("Snakes"), Score(1, 1)),
        Match(Team("Tarantulas"), Team("Lions"), Score(3, 0))
        )
        val lb = LeaderBoardService.compute(matches, points)
        val ranks = lb.map(_.position)
        assert(ranks.count(_ == 2) == 2 || ranks.count(_ == 3) == 2)
    }

    test("Empty input returns empty leaderboard") {
        val lb = LeaderBoardService.compute(Nil, points)
        assert(lb.isEmpty)
    }

    test("Ranking logic only") {

        val scores = List(
          TeamScore(Team("A"), 6),
          TeamScore(Team("B"), 6),
          TeamScore(Team("C"), 5),
          TeamScore(Team("D"), 5),
          TeamScore(Team("E"), 5),
          TeamScore(Team("F"), 2),
          TeamScore(Team("G"), 2),
          TeamScore(Team("H"), 0)
        )

        val result = LeaderBoardService.assignPositions(scores)

        val ranks = result.map(e => e.teamScore.team.name -> e.position).toMap

        assert(ranks("A") == 1)
        assert(ranks("B") == 1)
        assert(ranks("C") == 3)
        assert(ranks("D") == 3)
        assert(ranks("E") == 3)
        assert(ranks("F") == 6)
        assert(ranks("G") == 6)
        assert(ranks("H") == 8)
    }

}