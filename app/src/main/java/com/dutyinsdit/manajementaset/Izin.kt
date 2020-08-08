package com.dutyinsdit.manajementaset

/*
 * Created by farras on 8/8/2020
*/

class Izin {
    companion object {
        val IZIN_KAMERA = 1
        val HOST = "http://192.168.1.202"
        val LINK = "${HOST}/aset/"
        val LINK_ISTHEREARE = "${LINK}isthere.php"

        var LINK_DETAIL = "${LINK}detail.php?"

        val GAGAL = "gagal"
        val SUKSES = "berhasil"
    }
}