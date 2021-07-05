package com.example.projectinter.constants


/**
 *
 *
 * @author
 * @version
 * @created on 24-Feb-20
 */
object API {
    const val BASE_URL_GOOGLE_DIRECTION_API = "https://maps.googleapis.com/"

    const val SUB_URL_GOOGLE_DIRECTION = "maps/api/directions/json?"

    const val AUTHORIZE = "/v1/auth/authorize"

    const val SUB_URL_LOGIN = "/v1/auth/login"
    const val SUB_URL_RESET_PASSWORD = "/v1/auth/reset/password"
    const val GET_PROFILE = "/v1/auth/profile"


    const val SUB_URL_PRODUCT_LIST = "v1/products"
    const val SUB_URL_PRODUCT_TREE_LIST = "v1/products/tree"
    const val SUB_URL_OUTLET_LIST_NEAR_BY = "outlets/near-by"
    const val SUB_URL_OUTLET_LIST = "outlets"

    const val SUB_URL_SCHEDULES = "v1/firms/outlets/schedules"
    const val SUB_URL_REDEEMS_REQUEST_LIST = "v1/redeems/requests"
    const val SUB_URL_REDEEMS_REQUEST_SUBMISSION = "v1/redeems/requests/submissions"
    const val SUB_URL_REDEEMS_REQUEST_REDEMPTIONS = "v1/redeems/requests/redemptions"


    const val HEADER_AUTHORIZATION = "Authorization"
    const val NOTIFICATION_LIST = "v1/notifications"
    const val READ_NOTIFICATION = "v1/notifications/{id}"
    const val SUBSCRIBE_NOTIFICATION = "v1/notifications/subscribe"
    const val UNSUBSCRIBE_NOTIFICATION = "v1/notifications/un-subscribe"
    const val NOTIFICATION_COUNT = "v1/notifications/total?isRead=0"


    const val DASHBOARD_CALL_PLAN = "/v1/dashboards/call-plans"
    const val DASHBOARD_SALE_TARGET = "/v1/dashboards/order-items"
    const val DASHBOARD_SALE_TARGET_DETAIL = "/v1/dashboards/order-items/products"
    const val DASHBOARD_ORDERS = "/v1/dashboards/orders"
    const val DASHBOARD_NEW_OUTLET = "/v1/dashboards/outlet-approval-requests"
    const val DASHBOARD_EXCELLENT_EXECUTION = "/v1/dashboards/branding"
    const val DASHBOARD_PRODUCT_AVAILABILITY = "/v1/dashboards/product-availabilities"
    const val DASHBOARD_PRODUCT_AVAILABILITY_DETAIL =
        "/v1/dashboards/product-availabilities/outlets"
    const val DASHBOARD_PRODUCT_AVAILABILITY_CLOSE_TO_EXPIRE =
        "/v1/dashboards/product-availabilities/close-to-expires"
    const val DASHBOARD_CALL_PLAN_VISIT = "/v1/dashboards/call-plans/visits"
    const val DASHBOARD_ORDER_LIST = "/v1/dashboards/orders/outlets"
    const val DASHBOARD_TOUCH_POINT = "/v1/dashboards/requests/products"

    const val QUERY_LIMIT = "limit"
    const val QUERY_OFFSET = "offset"
    const val QUERY_STATUS = "status"
    const val QUERY_CONDUCTION = "conduction"
    const val QUERY_IS_EXTERNAL = "isExternal"
    const val QUERY_TYPE = "type"
    const val PRODUCT_TYPE = "productType"

    const val FIELD_EMAIL = "email"
    const val FIELD_PHONE = "phone"
    const val FIELD_PASSWORD = "password"

    const val PAR_ORIGIN = "origin"
    const val PAR_DESTINATION = "destination"
    const val PAR_KEY = "key"

    const val AUTHORIZATION = "authorize"

//    fun getAuthorization(): String {
//        return "Bearer ${getClientHex()}"
//    }

//    fun getClientHex(): String? {
//        return StringEncryption.SHA1("${BuildConfig.CLIENT_ID}:${BuildConfig.CLIENT_SECRET}")
//    }

    //Outlet
//    val SUB_OUTLET_DETAILS: String =
//        if (Constants.IS_SFA) "v1/admin/firms/outlets/{id}" else "v1/admin/firms/outlet-census/{id}"
}