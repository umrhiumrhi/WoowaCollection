package com.woowacourse.woowacollectionapp.racingcar.domain

object GameValidator {
    fun validateCarNames(names: List<String>, input: String) {
        val inputCommaCount = input.count { it == ',' }
        require(names.size == inputCommaCount + 1) { 
            ExceptionMessage.INVALID_NAME_FORMAT 
        }
        require(names.size <= RacingCarConstants.MAX_CAR_COUNT) { 
            ExceptionMessage.MAX_CAR_COUNT_EXCEEDED 
        }
        names.forEach { name ->
            require(name.length <= RacingCarConstants.MAX_NAME_LENGTH) {
                ExceptionMessage.NAME_LENGTH_OVER
            }
        }
    }

    fun validateTryCount(tryCount: Int?) {
        requireNotNull(tryCount) { ExceptionMessage.INVALID_TRY_COUNT }
        require(tryCount > 0) { ExceptionMessage.INVALID_TRY_COUNT }
        require(tryCount <= RacingCarConstants.MAX_TRY_COUNT) { 
            ExceptionMessage.MAX_TRY_COUNT_EXCEEDED 
        }
    }
}

