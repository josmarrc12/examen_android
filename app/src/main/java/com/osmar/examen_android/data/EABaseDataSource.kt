package com.osmar.examen_android.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class EABaseDataSource {

    suspend fun <T> getResult(call: suspend () -> Response<T>):Flow <EANetworkResult<T>>{
        return flow {
            emit(EANetworkResult.loading())
            try {
                val response = call()
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null) emit(EANetworkResult.success(body))
                }else{
                    emit(error(" ${response.code()} ${response.message()}"))
                }
            }catch (e:Exception){
                emit(error(e.message ?: e.toString()))
            }
        }
    }

    private fun <T> error(message: String): EANetworkResult<T>{
        return EANetworkResult.error(message)
    }
}