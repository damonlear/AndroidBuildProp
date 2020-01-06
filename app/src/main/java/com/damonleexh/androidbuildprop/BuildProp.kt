package com.damonleexh.androidbuildprop

import android.app.Activity
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import java.util.*


object BuildProp {
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    val systemLanguage: String
        get() = Locale.getDefault().language

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    val systemLanguageList: Array<Locale>
        get() = Locale.getAvailableLocales()

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    val systemVersion: String
        get() = Build.VERSION.RELEASE

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    val systemModel: String
        get() = Build.MODEL

    /**
     * 获取当前系统版本号数字
     *
     * @return  系统SDK
     */
    val systemSdk: Int
        get() = Build.VERSION.SDK_INT

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    val deviceBrand: String
        get() = Build.BRAND

    /**
     * 主板 设备基板名称
     * @return  主板 设备基板名称
     */
    val deviceBoard: String
        get() = Build.BOARD

    /**
     * 设备参数
     * @return  设备参数
     */
    val deviceDevice: String
        get() = Build.DEVICE

    /**
     * 显示屏参数
     * @return  显示屏参数
     */
    val deviceDisplay: String
        get() = Build.DISPLAY

    /**
     * 硬件名称
     * @return  显示屏参数
     */
    val deviceFingerprint : String
        get() = Build.FINGERPRINT

    /**
     * 硬件制造商
     * @return  硬件制造商
     */
    val deviceManufacturer: String
        get() = Build.MANUFACTURER

    /**
     * 手机制造商
     * @return  手机制造商
     */
    val deviceProduct : String
        get() = Build.PRODUCT

    /**
     * 设备版本号
     * @return  设备版本号
     */
    val deviceID : String
        get() = Build.ID

    /**
     * 设备版本号
     * @return  设备版本号
     */
    val deviceHardware : String
        get() = Build.HARDWARE

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    fun getIMEI(ctx: Context): String? {
        val tm =
            ctx.getSystemService(Activity.TELEPHONY_SERVICE) as TelephonyManager
        return tm.deviceId
    }
}