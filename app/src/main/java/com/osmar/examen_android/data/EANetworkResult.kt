package com.osmar.examen_android.data

class EANetworkResult<T>(
    val status: Status,
    val data: T?,
    val message: String?
){

    enum class Status{
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T):EANetworkResult<T>{
            return EANetworkResult(Status.SUCCESS, data, null)
        }
        fun <T> error(message: String?, data: T? = null):EANetworkResult<T>{
            return EANetworkResult(Status.ERROR, data, message)
        }
        fun <T> loading(data: T? = null):EANetworkResult<T>{
            return EANetworkResult(Status.LOADING, data, null)
        }
    }
}