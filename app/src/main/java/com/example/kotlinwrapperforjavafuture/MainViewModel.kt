package com.example.kotlinwrapperforjavafuture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DataRepository) : ViewModel() {
    private val mutableViewState = MutableStateFlow(listOf("Hello Android!"))
    val viewState = mutableViewState

    init {
        viewModelScope.launch {
            runAndUpdateState { repository.kotlinFunctionWithEmptyContinuation().toString() }
            runAndUpdateState { repository.kotlinFunctionWithIntContinuation().toString() }
            runAndUpdateState {
                val byteArray = ByteArray(0).plus(1).plus(2).plus(3)
                repository.kotlinFunctionWithByteArrayContinuation(byteArray).toList().toString()
            }
        }
    }

    private fun runAndUpdateState(block: suspend () -> String) {
        viewModelScope.launch {
            block().let { result ->
                mutableViewState.update { list -> list.plus(result) }
                println("KWFJF: $result")
            }
        }
    }
}