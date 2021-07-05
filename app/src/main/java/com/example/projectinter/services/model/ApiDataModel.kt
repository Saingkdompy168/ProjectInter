package com.chipmong.dms.services.model

import com.chipmong.dms.utils.MediaManager

/**
 *
 *
 * @author Noel
 * @version
 * @created on 23-Jul-18
 */
open class ApiDataModel<T>(var data: T? = null) {
    var metadata: MetaData? = null

    class MetaData() {
        var total: Int = 0
        var limit: Int = 0
        var offset: Int = 0
    }
}