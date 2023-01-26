package com.sy.renz.bingo.domain.use_case

import javax.inject.Inject

class GenerateCallListUseCase @Inject constructor(
) {

    operator fun invoke(callList: String = "",callFrom: String = "1,1,1,1,1", retainCalled: Boolean = false, index: Int = 0): String {

        var newCallList: MutableList<Int> = ArrayList()
        val b = (1..15).toList()
        val i = (16..30).toList()
        val n = (31..45).toList()
        val g = (46..60).toList()
        val o = (61..75).toList()

        val callFromList = callFrom.split(",")

        if (callFromList[0] == "1") newCallList.addAll(b)
        if (callFromList[1] == "1") newCallList.addAll(i)
        if (callFromList[2] == "1") newCallList.addAll(n)
        if (callFromList[3] == "1") newCallList.addAll(g)
        if (callFromList[4] == "1") newCallList.addAll(o)
        newCallList = newCallList.shuffled().toMutableList()

        if (retainCalled && index != -1) {
            newCallList.addAll(0, callList.split(",").subList(0, index + 1).map { it.toInt() })
            newCallList = newCallList.distinct() as MutableList<Int>
        }

        return newCallList.joinToString(",")
    }
}