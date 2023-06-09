package dev.sourav.currencyconverter.others

data class ApiResponse<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ApiResponse<T> {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ApiResponse<T> {
            return ApiResponse(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ApiResponse<T> {
            return ApiResponse(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}