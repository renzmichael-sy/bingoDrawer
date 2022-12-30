package com.sy.renz.bingo.domain.use_case

import javax.inject.Inject

class ConvertStringToDrawListUseCase @Inject constructor(

) {
    //if(viewModel.state.value.index != - 1 && viewModel.state.value.mainBingoData?.bingoData?.drawList?.isNotEmpty() == true)
    // viewModel.state.value.mainBingoData?.bingoData?.drawList?.subList(0, viewModel.state.value.index + 1) else emptyList())
    operator fun invoke(stringList: String): List<Int>{
        return if(stringList.isNotEmpty())
            stringList.split(",").map{ it.toInt() }
        else
            emptyList()
    }

}