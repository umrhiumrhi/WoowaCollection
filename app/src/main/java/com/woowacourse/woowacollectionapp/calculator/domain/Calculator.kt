package com.woowacourse.woowacollectionapp.calculator.domain

class Calculator {
    private val delimiterCandidateList: MutableList<String> = mutableListOf(",", ":")
    private val splitPattern = Regex("[^0-9]+")

    fun calculate(input: String): Int {
        // TODO: 계산 로직 구현
        return 0
    }

    private fun extractCustomDelimiter(input: String): String {
        if (!input.startsWith("//")) return input

        val endIndex = input.lastIndexOf("\\n")
        if (endIndex == -1) {
            throw IllegalArgumentException("커스텀 구분자를 지정하는 방식이 잘못되었습니다. (\\n누락)")
        }

        val customDelimiter = input.substring(2, endIndex)
        if (customDelimiter.isEmpty()) {
            throw IllegalArgumentException("커스텀 구분자가 비어있습니다.")
        }

        delimiterCandidateList.add(customDelimiter)
        return input.substring(endIndex + 2)
    }

    private fun extractNumbers(input: String): List<Int> {
        val numberList = input.split(splitPattern)
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
        return numberList
    }

    private fun extractDelimiters(input: String): List<String> {
        val delimiterList = splitPattern.findAll(input)
            .map { it.value }
            .toList()
        return delimiterList
    }

    private fun validate(numberList: List<Int>, delimiterList: List<String>) {
        if (numberList.isEmpty() && delimiterList.isEmpty()) return
        if (delimiterList.size + 1 != numberList.size) {
            throw IllegalArgumentException("구분자의 입력이 잘못되었습니다.")
        }
        if (delimiterList.any { it !in delimiterCandidateList }) {
            throw IllegalArgumentException("구분자로 사용될 수 없는 구분자가 사용되었습니다.")
        }
    }

    fun getDelimiters(): List<String> = delimiterCandidateList.toList()
}

