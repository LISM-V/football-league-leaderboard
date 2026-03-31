package leaderboard.input

import scala.io.{Source, StdIn}
import scala.util.Using

object InputReader {

    def read(args: Array[String],
            readFile: String => List[String] = defaultReadFile,
            readStdIn: () => List[String] = defaultReadStdIn
    ): List[String] = {
        if (args.nonEmpty) readFile(args(0))
        else readStdIn()
    }

    private def defaultReadFile(filename: String): List[String] = {
        Using(Source.fromFile(filename)) { source =>
            source.getLines().toList
        }.getOrElse {
            throw new RuntimeException(s"Failed to read file: $filename")
        }
    }

    private def defaultReadStdIn(): List[String] = {
        println("\nEnter match results, one per line (empty line to finish):")

        Iterator
            .continually(StdIn.readLine())
            .takeWhile(_ != null)
            .takeWhile(_.nonEmpty)
            .toList
    }

}