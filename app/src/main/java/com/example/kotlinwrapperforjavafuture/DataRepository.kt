package com.example.kotlinwrapperforjavafuture

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout

class DataRepository {

    suspend fun kotlinFunctionWithEmptyContinuation(timeOutMs: Long = 200) =
        withTimeout(timeOutMs) {
            suspendCancellableCoroutine { cont ->
                ContinuationsPlayground.functionWithEmptyContinuation {
                    if (cont.isActive && !cont.isCompleted) {
                        cont.resumeWith(Result.success(Unit))
                    }
                }
            }
        }

    suspend fun kotlinFunctionWithIntContinuation(timeOutMs: Long = 200): Int =
        withTimeout(timeOutMs) {
            suspendCancellableCoroutine { cont ->
                ContinuationsPlayground.functionWithIntContinuation({ result ->
                    if (cont.isActive && !cont.isCompleted) {
                        cont.resumeWith(Result.success(result))
                    }
                })
            }
        }

    suspend fun kotlinFunctionWithByteArrayContinuation(byteArray: ByteArray, timeOutMs: Long = 200): ByteArray =
        withTimeout(timeOutMs) {
            suspendCancellableCoroutine { cont ->
                ContinuationsPlayground.functionWithByteArrayContinuation(byteArray, { result ->
                    if (cont.isActive && !cont.isCompleted) {
                        cont.resumeWith(Result.success(result))
                    }
                })
            }
        }

}