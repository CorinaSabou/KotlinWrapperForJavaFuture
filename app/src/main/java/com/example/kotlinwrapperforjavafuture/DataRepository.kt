package com.example.kotlinwrapperforjavafuture

import kotlinx.coroutines.suspendCancellableCoroutine

class DataRepository {

    suspend fun kotlinFunctionWithEmptyContinuation() =
        suspendCancellableCoroutine { cont ->
            ContinuationsPlayground.functionWithEmptyContinuation {
                if (cont.isActive && !cont.isCompleted) {
                    cont.resumeWith(Result.success(Unit))
                }
            }
        }

    suspend fun kotlinFunctionWithIntContinuation(): Int {
        return suspendCancellableCoroutine { cont ->
            ContinuationsPlayground.functionWithIntContinuation({ result ->
                if (cont.isActive && !cont.isCompleted) {
                    cont.resumeWith(Result.success(result))
                }
            })
        }
    }

    suspend fun kotlinFunctionWithByteArrayContinuation(byteArray: ByteArray): ByteArray {
        return suspendCancellableCoroutine { cont ->
            ContinuationsPlayground.functionWithByteArrayContinuation(byteArray, { result ->
                if (cont.isActive && !cont.isCompleted) {
                    cont.resumeWith(Result.success(result))
                }
            })
        }
    }


}