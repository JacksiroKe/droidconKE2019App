package com.android254.droidconke19.models

data class TravelInfoModel (
        val shuttleInfo: String,
        val publicTransportationInfo: String,
        val carpoolingParkingInfo: String,
        val rideSharingInfo: String

){
    override fun toString(): String {
        return "TravelInfoModel{" +
                "shuttleInfo='" + shuttleInfo + '\''.toString() +
                ", publicTransportationInfo='" + publicTransportationInfo + '\''.toString() +
                ", carpoolingParkingInfo='" + carpoolingParkingInfo + '\''.toString() +
                '}'.toString()
    }
}


