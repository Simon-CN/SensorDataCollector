package xyz.sx.sensordatacollector

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pub.devrel.easypermissions.EasyPermissions

class LaunchActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        System.exit(0)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        jump()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun jump() {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val permissions = arrayOf(
            Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (EasyPermissions.hasPermissions(this, *permissions)) {
            jump()
        } else {
            EasyPermissions.requestPermissions(this, "+1s", 101, *permissions)
        }
    }
}