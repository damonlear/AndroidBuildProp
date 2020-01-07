package com.damonleexh.androidbuildprop

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val PERMISSION = Permission.READ_PHONE_STATE
    private val REQUEST_PERMISSION_CODE = 999

    private fun hasPermission(context: Context): Boolean {
        val perm: Int = context.checkCallingOrSelfPermission(PERMISSION)
        return perm == PackageManager.PERMISSION_GRANTED
    }

    var list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.add("暂无")
        listview.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, list)
        if (!hasPermission(this)) {
            Toast.makeText(this, "无权限", Toast.LENGTH_SHORT).show()
            requestPermission()
        } else {
            requestBuildProp()
        }
    }


    private fun requestPermission() {
        AndPermission.with(this)
            .runtime()
            .permission(PERMISSION)
            .onGranted { requestBuildProp() }
            .onDenied {
                if (AndPermission.hasAlwaysDeniedPermission(this, PERMISSION)) {
                    go2System()
                } else {
                    requestPermission()
                }
            }
            .start()
    }

    protected fun go2System() {
        AlertDialog.Builder(this)
            .setMessage("需要手动开启权限才能使用")
            .setPositiveButton("设置") { _, _ ->
                // 跳转到app设置
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivityForResult(intent, REQUEST_PERMISSION_CODE)
            }
            .setNegativeButton("取消") { _, _ ->
                Toast.makeText(this, "缺失必要权限导致部分功能无法使用", Toast.LENGTH_SHORT).show()
                finish()
            }
            .create()
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (REQUEST_PERMISSION_CODE == requestCode) {
                requestBuildProp()
                return
            }
        }
        go2System()
    }

    private fun requestBuildProp() {
        list.clear()
        list.add("手机品牌：" + BuildProp.deviceBrand)
        list.add("手机型号：" + BuildProp.systemModel)
        list.add("系统版本：" + BuildProp.systemVersion)
        list.add("产品名称 ：" + BuildProp.deviceProduct)
        list.add("设备制造：" + BuildProp.deviceManufacturer)
        list.add("主板名称：" + BuildProp.deviceBoard)
        list.add("硬件名称：" + BuildProp.deviceHardware)
        list.add("设备驱动：" + BuildProp.deviceDevice)

        (listview.adapter as BaseAdapter).notifyDataSetChanged()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
