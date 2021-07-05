package com.chipmong.dms.extensions

import android.graphics.Bitmap
import android.graphics.Color
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 14-Feb-20
 */
//fun GoogleMap.addMarker(
//    position: LatLng,
//    title: String? = null, icon: Bitmap? = null,
//    anchor: Float? = null
//): Marker {
//    return addMarker(
//        MarkerOptions().title(title?.toUpperCase(Locale.getDefault())).position(position).apply {
//            anchor?.let {
//                this.anchor(
//                    anchor,
//                    anchor
//                )
//            }
//            icon?.let {
//                this.icon(BitmapDescriptorFactory.fromBitmap(icon))
//            }
//        }
//    )
//}
//
//fun GoogleMap.addMarker(outlet: OutletModel, icon: Bitmap? = null, anchor: Float? = null): Marker? {
//    return if (outlet.latLngOutlet != null) addMarker(
//        outlet.latLngOutlet!!,
//        outlet.name?.toUpperCase(Locale.getDefault()), icon, anchor
//    ) else null
//}
//
//fun GoogleMap.addCircle(radius: Double, center: LatLng): Circle {
//    return addCircle(CircleOptions().radius(radius).center(center))
//}
//
//fun GoogleMap.addPolyLine(latLng: LatLng, with: Float = 10f, color: Int = Color.RED) {
//    this.addPolyline(PolylineOptions().add(latLng).width(with).color(color))
//}
//
//fun GoogleMap.addPolyLine(outlet: OutletModel) {
//    outlet.latLngOutlet?.let {
//        this.addPolyLine(it)
//    }
//
//}
//
//fun GoogleMap.addAllPolyline(listLatLng: List<LatLng>, with: Float = 10f) {
//    this.addPolyline(PolylineOptions().addAll(listLatLng).width(with).color(Color.RED))
//}
//
//fun GoogleMap.addAllPolyLineFromOutlets(listOutletCenus: List<OutletModel>) {
//    val listLatLat = ArrayList<LatLng>()
//    for (outlet in listOutletCenus) {
//        outlet.latLngOutlet?.let {
//            listLatLat.add(it)
//        }
//    }
//    this.addAllPolyline(listLatLat)
//}
//
//fun GoogleMap.addAllPolyLineFromSchedule(listOutlet: List<ScheduleModel>) {
//    val listLatLat = ArrayList<LatLng>()
//    for (outlet in listOutlet) {
//        outlet.outlet?.latLngOutlet?.let {
//            listLatLat.add(it)
//        }
//    }
//    this.addAllPolyline(listLatLat)
//}
////
////fun GoogleMap.addAllPolyLineFromSale(listOutlet: List<MapUserLogModel>) {
////    val listLatLat = ArrayList<LatLng>()
////    for (mapHistory in listOutlet) {
////        mapHistory.latLng?.let {
////            listLatLat.add(it)
////        }
////    }
////    this.addAllPolyline(listLatLat)
////}
//
//fun GoogleMap.addAllPolyLineFromSale(listOutlet: SaleHistoryModel) {
//    val listLatLat = ArrayList<LatLng>()
//    listOutlet.route?.let {
//        for (mapHistory in it) {
//            if (mapHistory.size > 1) {
//                listLatLat.add(LatLng(mapHistory[0], mapHistory[1]))
//            }
//        }
//    }
//
//    this.addAllPolyline(listLatLat)
//}
//
//fun GoogleMap.addAllPolyLineFromSaleHistory(listOutlet: OutletMapApiModel) {
//    val listLatLat = ArrayList<LatLng>()
//    listOutlet.data?.let {
//        for (mapHistory in it) {
//            mapHistory.latLng?.let {
//                listLatLat.add(it)
//            }
//        }
//        this.addAllPolyline(listLatLat)
//    }
//
//}
//
//
//fun GoogleMap.moveCameraBounds(latLngBounds: LatLngBounds, width: Int, height: Int, padding: Int) {
//    this.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, padding))
//}
//
//fun GoogleMap.moveCameraBounds(latLngBounds: LatLngBounds, padding: Int) {
//    this.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, padding))
//}