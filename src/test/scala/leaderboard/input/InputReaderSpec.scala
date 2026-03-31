package leaderboard.input

import munit.FunSuite
import leaderboard.input.InputReader
import leaderboard.model._

class InputReaderSpec extends FunSuite {

    test("reads from file when args are provided") {
        val fakeFileReader = (_: String) => List("Lions 3, Snakes 3")
        val result = InputReader.read(
            args = Array("input.txt"),
            readFile = fakeFileReader,
            readStdIn = () => Nil
        )
        assertEquals(result, List("Lions 3, Snakes 3"))
    }

    test("reads from stdin when no args provided") {
        val fakeStdIn = () =>
            List(
                "Lions 3, Snakes 3",
                "Tarantulas 1, FC Awesome 0"
            )

        val result = InputReader.read(
            args = Array(),
            readFile = _ => Nil,
            readStdIn = fakeStdIn
        )

        assertEquals(
            result,
            List(
                "Lions 3, Snakes 3",
                "Tarantulas 1, FC Awesome 0"
            )    
        )
    }

    test("throws exception when file reading fails") {
        val failingFileReader = (_: String) => throw new RuntimeException("boom")

        intercept[RuntimeException] {
            InputReader.read(
                args = Array("bad.txt"),
                readFile = failingFileReader,
                readStdIn = () => Nil
            )
        }
    }

    test("returns empty list when no input is provided") {
        val result = InputReader.read(
            args = Array(),
            readFile = _ => Nil,
            readStdIn = () => Nil
        )
        assertEquals(result, Nil)
    }
}