package xyz.sx.sensordatacollector

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import xyz.sx.collectorcore.BaseSensorData
import xyz.sx.collectorcore.CollectorConfig
import xyz.sx.collectorcore.CollectorContext
import xyz.sx.collectorcore.beans.MacScanLine
import xyz.sx.collectorcore.providers.WiFiScanProvider
import xyz.sx.sensordatacollector.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mCollectContext: CollectorContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        CollectorContext.getInstance().init(applicationContext)
        mCollectContext = CollectorContext.getInstance()
        mCollectContext.setOnCollectDataListener { runOnUiThread { mBinding.sensorsTxt.append(it.dump()) } }
        mCollectContext.start()
    }

    override fun onDestroy() {
        mCollectContext.destroy()
        super.onDestroy()
    }
}
