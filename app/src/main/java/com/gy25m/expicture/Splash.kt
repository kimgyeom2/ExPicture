package com.gy25m.expicture

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(this@Splash, "Permission Granted", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000L)
                    var intent = Intent(this@Splash, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@Splash,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.CAMERA)
            .check();

    }
}