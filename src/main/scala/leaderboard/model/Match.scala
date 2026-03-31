package leaderboard.model

final case class Match(
    team1: Team,
    team2: Team,
    score: Score
)