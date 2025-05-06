package com.example.kotlinwrapperforjavafuture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DataRepository) : ViewModel() {
    private val mutableWriteEvent = MutableStateFlow(listOf("Hello Android!"))
    val writeEvent = mutableWriteEvent

    init {
        viewModelScope.launch {
            computeAndWrite { repository.kotlinFunctionWithEmptyContinuation().toString() }
            computeAndWrite { repository.kotlinFunctionWithIntContinuation().toString() }
            computeAndWrite {
                val byteArray = ByteArray(0).plus(1).plus(2).plus(3)
                repository.kotlinFunctionWithByteArrayContinuation(byteArray).toList().toString()
            }
        }
    }

    private fun computeAndWrite(block: suspend () -> String) {
        viewModelScope.launch {
            block().let { result ->
                mutableWriteEvent.update { list -> list.plus(result) }
                println("KWFJF: $result")
            }
        }
    }
}