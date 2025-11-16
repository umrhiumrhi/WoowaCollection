package com.woowacourse.woowacollectionapp.racingcar.domain

import kotlin.random.Random

class Game(private val cars: List<Car>) {
    fun play(): List<String> {
        moveCars()
        return cars.map { it.getProgressString() }
    }

    private fun moveCars() {
        cars.forEach { it.move(Random.nextInt(0, 10)) }
    }

    private fun getBestScore(): Int {
        return cars.maxOf { it.getScore() }
    }

    fun getWinners(): List<String> {
        val maxScore = getBestScore()
        return cars.filter { it.getScore() == maxScore }.map { it.getName() }
    }
}

