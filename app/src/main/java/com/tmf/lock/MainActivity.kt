package com.tmf.lock

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val client = MQTTClient(this)   //MQTT client
    private val toast = ToastUtil(this)
    private var btn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client.connect(applicationContext)
        setContentView(R.layout.main_layout)
        btn = findViewById(R.id.btn)
        btn?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val topic = resources.getString(R.string.topic)
        val signal = resources.getString(R.string.signal)
        if (client.isConnected()) {
            client.publish(topic, signal)
        } else {
            toast.show(R.string.mqtt_disconnected)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        toast.destroy()
        client.disconnect()
    }
}

