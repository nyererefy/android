package com.nyererefy.data

import com.google.gson.Gson
import com.nyererefy.utilities.models.ApiError
import okhttp3.ResponseBody
import retrofit2.Response

/*
    * Returns all Api error messages as Strings
 */
fun getError(response: Response<ResponseBody>): String? {
    val gson = Gson()
    val message = gson.fromJson(response.errorBody()?.charStream(), ApiError::class.java)
    return message.message
}
