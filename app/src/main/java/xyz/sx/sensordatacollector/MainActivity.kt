package xyz.sx.sensordatacollector

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import xyz.sx.collectorcore.BaseSensorData
import xyz.sx.collectorcore.beans.MacBean
import xyz.sx.collectorcore.beans.MacScanLine
import xyz.sx.collectorcore.providers.WiFiScanProvider
import xyz.sx.sensordatacollector.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var timer: Timer
    private lateinit var wifiCollector: WiFiScanProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        wifiCollector = WiFiScanProvider(this)
        wifiCollector.start()

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                processData(wifiCollector.data)
            }
        }, 0, 2000)

    }

    private fun processData(data: BaseSensorData?) {
        val res = data?.data as List<*>
        res.forEach {
            val line = it as MacScanLine
            runOnUiThread { mBinding.sensorsTxt.append("---${line.timestamp}---------------\n") }
            line.data.forEach {
                val scan = it as MacBean
                runOnUiThread {
                    mBinding.sensorsTxt.append(scan.mac + ", " + scan.rssi + "\n")
                }
            }
        }
    }

    override fun onDestroy() {
        wifiCollector.stop()
        super.onDestroy()
    }
}
