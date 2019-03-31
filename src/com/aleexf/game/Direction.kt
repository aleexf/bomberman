package com.aleexf.game

enum class Direction(val dx: Int, val dy: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1)
}