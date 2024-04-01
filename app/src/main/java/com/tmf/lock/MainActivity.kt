package com.tmf.lock

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val client = MQTTClient()   //MQTT client
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
        client.publish(topic, signal)
        Toast.makeText(this, resources.getString(R.string.open), Toast.LENGTH_SHORT).show()
    }
}

