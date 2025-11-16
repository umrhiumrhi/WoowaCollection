package com.woowacourse.woowacollectionapp.racingcar.domain

class Car(private val name: String, private var score: Int = 0) {
    companion object {
        const val CAN_MOVE_MIN_NUMBER = 4
    }

    init {
        require(name.length <= RacingCarConstants.MAX_NAME_LENGTH) { 
            ExceptionMessage.NAME_LENGTH_OVER 
        }
    }

    fun move(randomNumber: Int) {
        // TODO: 이동 로직 구현
    }

    fun getProgressString(): String = "$name : ${"-".repeat(score)}"

    fun getScore(): Int = score

    fun getName(): String = name
}

