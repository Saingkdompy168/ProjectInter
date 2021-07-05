package com.chipmong.dms.utils

import com.chipmong.dms.services.repository.ApiClient
import com.chipmong.dms.services.model.APIError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response


/**
 *
 *
 * @author Noel
 * @version
 * @created on 04-Feb-20
 */
object ErrorUtils {
    fun parseError(response: Response<*>): APIError {
        val converter: Converter<ResponseBody, APIError> = ApiClient.getDefaultClient().responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))
        val error: APIError
        try {
            error = converter.convert(response.errorBody()!!)!!
        } catch (e: Exception) {
            val apiError = APIError()
            apiError.message = response.message()
            return apiError
        }
        return error
    }
}