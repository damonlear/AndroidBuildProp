package com.damonleexh.androidbuildprop

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
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
            .onDenied { requestPermission() }
            .start()
    }

    private fun requestBuildProp() {
        list.clear()
        list.add("手机厂商：" + BuildProp.deviceBrand)
        list.add("手机型号：" + BuildProp.systemModel)
        list.add("系统版本：" + BuildProp.systemVersion)
        list.add("生产厂商：" + BuildProp.deviceManufacturer)
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
