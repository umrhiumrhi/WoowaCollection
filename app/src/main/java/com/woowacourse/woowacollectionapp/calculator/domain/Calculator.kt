package com.woowacourse.woowacollectionapp.calculator.domain

class Calculator {
    private val delimiterCandidateList: MutableList<String> = mutableListOf(",", ":")

    fun calculate(input: String): Int {
        // TODO: 계산 로직 구현
        return 0
    }

    /**
     * 커스텀 구분자를 추출하고 처리된 입력 문자열을 반환합니다.
     * 커스텀 구분자 형식: //구분자\n숫자들
     * 
     * @param input 원본 입력 문자열
     * @return 커스텀 구분자가 있으면 구분자 부분을 제거한 문자열, 없으면 원본 문자열
     */
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

    /**
     * 현재 사용 가능한 구분자 목록을 반환합니다.
     */
    fun getDelimiters(): List<String> = delimiterCandidateList.toList()
}

