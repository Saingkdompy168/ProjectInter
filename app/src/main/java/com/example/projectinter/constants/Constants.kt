package com.chipmong.dms.constants

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 04-Feb-20
 */
object Constants {

    //Database
    const val DEFAULT_DATABASE_NAME: String = "dms_realm_data_base"
    const val KEY_ID = "id"
    const val ORDER_ID = "orderId"
    const val FAKE_ID = "fakeId"
    const val CHECK_LIST_ID = "checkListId"
    const val PRODUCT_NAME = "productName"

    const val EXTRA_OUTLET: String = "extra_outlet"
    const val EXTRA_ORDER: String = "extra_order"
    const val EXTRA_STOCK_REQUEST: String = "extra_request"
    const val EXTRA_SCHEDULE: String = "extra_schedule"
    const val EXTRA_WALK_IN: String = "extra_walk_in"
    var MESSAGE: String = "message"
    const val TYPE_REQUEST = "REQUEST"

    object SocketEvent {
        var MESSAGE: String = "message"
    }

    object Extra {
        const val EXTRA_OUTLET = "extra_outlet"
        const val EXTRA_OUTLET_LIST = "extra_outlet_list"
        const val SUB_ACTION = "SUB_ACTION"
        const val NOTIFICATION_REF_ID = "NOTIFICATION_REF_ID"
    }

    const val SUCCESSFUL = "Successful"
    const val PENDING = "Pending"
    const val CANCEL = "Cancel"
    const val ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android"

    //Retrofit Key
    const val KEY_OFFSET = "offset"
    const val KEY_LIMIT = "limit"
    const val KEY_STATUS = "status"
    const val KEY_GEO_REGION_ID = "geoRegionId"
    const val KEY_GEO_CITY_ID = "geoCityId"
    const val KEY_GEO_DISTRICT_ID = "geoDistrictId"
    const val KEY_DEALER_ID = "dealerId"
    const val KEY_GEO_COMMUNE_ID = "geoCommuneId"


    //Send Message Code
    var SEND_MESSAGE_GET_OUTLET_LIST = 1
    var SEND_MESSAGE_GET_REGION_LIST = 2
    var SEND_MESSAGE_GET_DEALER_LIST = 3
    var SEND_MESSAGE_GET_BUSINESS_NATURE_LIST = 4
    var SEND_MESSAGE_GET_CHANNEL_TYPE_LIST = 5
    var SEND_MESSAGE_GET_OUTLET_TYPE_LIST = 6
    var SEND_MESSAGE_GET_CITY_LIST = 7
    var SEND_MESSAGE_GET_DISTRICT_LIST = 8
    var SEND_MESSAGE_GET_COMMUNE_LIST = 9
    var SEND_MESSAGE_GET_VILLAGE_LIST = 10
    var SEND_MESSAGE_GET_REGION_DEALER_LIST = 11
    var SEND_MESSAGE_STATEMENT = 12
    var SEND_STOCK_CONTAINER = 13
    var SEND_CATEGORY_LIST = 14
    var SEND_INVENTORY_LIST = 15
    var SEND_LANGUAGE_LIST = 16
    var SEND_BRAND_LIST = 17
    var SEND_PRODUCT_LIST = 18
    var SEND_PROMOTION_LIST = 19
    var SEND_FIRM_GROUP_LIST = 20
    var SEND_CONVERSATION_RADIUS = 21
    var SEND_SURVEY_LIST = 22
    var SEND_MESSAGE_GET_OUTLET_GENERAL = 23
}