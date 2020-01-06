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
            get() = Locale.getDefault().getLanguage()

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
         * 获取手机厂商
         *
         * @return  手机厂商
         */
        val deviceBrand: String
            get() = Build.BRAND

        val deviceManufacturer: String
            get() = Build.MANUFACTURER

        /**
         * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
         *
         * @return  手机IMEI
         */
        fun getIMEI(ctx: Context): String? {
            val tm =
                ctx.getSystemService(Activity.TELEPHONY_SERVICE) as TelephonyManager
            return tm?.deviceId
        }
}