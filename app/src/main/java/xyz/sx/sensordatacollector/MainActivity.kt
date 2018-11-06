package xyz.sx.sensordatacollector

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Message
import xyz.sx.collectorcore.CollectorContext
import xyz.sx.collectorcore.GlobalMsgHandler
import xyz.sx.collectorcore.OnCollectDataListener
import xyz.sx.collectorcore.protobuf.Sensorcollection
import xyz.sx.sensordatacollector.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity(), OnCollectDataListener, GlobalMsgHandler.GlobalMsgListener {
    override fun OnReceivedMsg(msg: Message?) {
        runOnUiThread { mBinding.msgTxt.text = msg?.data.toString() }
    }

    override fun OnCollectData(collection: Sensorcollection.SensorCollection?) {
        runOnUiThread { mBinding.sensorsTxt.text = "No.${++mCount}  ${System.currentTimeMillis()}\n" }
    }

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mCollectContext: CollectorContext
    private var mCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val path = Environment.getExternalStorageDirectory().absolutePath + "/samples/"
        val file = File(path)
        if (!file.exists())
            file.mkdirs()

        CollectorContext.getInstance().init(applicationContext, true)
        mCollectContext = CollectorContext.getInstance()
        mCollectContext.setOnCollectDataListener(this)
        mBinding.startBtn.setOnClickListener {
            mCount = 0
            mCollectContext.start()
        }
        mBinding.stopBtn.setOnClickListener { mCollectContext.stop() }

        GlobalMsgHandler.registerMsgListener(this)
    }

    override fun onDestroy() {
        mCollectContext.destroy()
        GlobalMsgHandler.unregisterMsgListener(this)
        super.onDestroy()
    }
}
