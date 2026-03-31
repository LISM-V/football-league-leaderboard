package leaderboard.service

import leaderboard.model._

object LeaderBoardService {

    def compute(matches: List[Match], points: Points = Points()): List[LeaderBoardEntry] = {

        val allScores: List[TeamScore] = matches.flatMap(pointsPerMatch(_, points))

        val aggregatedScores: Map[Team, Int] = allScores
            .groupBy(_.team)
            .map { 
                case (team, scores) => 
                    team -> scores.map(_.points).sum
            }

        val sortedScores: List[TeamScore] = aggregatedScores.toList
            .map{ case (team, pts) => TeamScore(team, pts) }
            .sortBy(ts => (-ts.points, ts.team.name))

        assignPositions(sortedScores)
    
    }

    private def pointsPerMatch(m: Match, points: Points): List[TeamScore] = {
        
        val (t1, t2) = (m.team1, m.team2)
        val (g1, g2) = (m.score.team1Goals, m.score.team2Goals)

        if (g1 > g2) List(TeamScore(t1, points.win), TeamScore(t2, points.loss))
        else if (g1 < g2) List(TeamScore(t1, points.loss), TeamScore(t2, points.win))
        else List(TeamScore(t1, points.draw), TeamScore(t2, points.draw))

    }

    def assignPositions(scores: List[TeamScore]): List[LeaderBoardEntry] = {

        val initialState = (List.empty[LeaderBoardEntry], Option.empty[Int], 1, 0)

        val (entries, _, _, _) = scores.foldLeft(initialState) {
          case ((acc, prevPoints, rank, count), current) =>

            val (newRank, newCount) = prevPoints match {
              case Some(prev) if prev == current.points =>
                (rank, count + 1)

              case Some(_) =>
                (rank + count + 1, 0)

              case None =>
                (rank, 0)
            }

            val entry = LeaderBoardEntry(newRank, current)

            (acc :+ entry, Some(current.points), newRank, newCount)
        }

        entries
    }
}
