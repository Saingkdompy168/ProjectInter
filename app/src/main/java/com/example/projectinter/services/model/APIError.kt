package com.chipmong.dms.services.model

data class APIError(var statusCode: Int = 0, var message: String? = null, var errors: List<Errors>? = null) {
    data class Errors(var field: String? = null, var validation: String? = null, var message: String? = null)
}