fun main() {

    fun toDirectionWithAmount(input: List<String>) = input.asSequence()
        .map { it.split(" ") }
        .flatten()
        .windowed(2, step = 2)
        .map { Direction.valueOf(it[0]) to it[1].toInt() }

    fun part1(input: List<String>): Int {
        return toDirectionWithAmount(input)
            .partition { it.first == Direction.forward }
            .let { (horizontal, vertical) ->
                val forward = horizontal.sumOf { (_, amount) -> amount }
                val depth = vertical.sumOf { (direction, amount) ->
                    if (direction == Direction.up) {
                        -amount
                    } else {
                        amount
                    }
                }
                forward * depth
            }

    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var aim = 0
        var depth = 0
        toDirectionWithAmount(input)
            .forEach { (direction, amount) ->
                when (direction) {
                    Direction.down -> aim += amount
                    Direction.up -> aim -= amount
                    Direction.forward -> {
                        horizontal += amount
                        depth += aim * amount
                    }
                }
            }
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))


}

enum class Direction {
    forward, down, up
}