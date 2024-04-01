package com.tmf.lock

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

//!MQTT
class MQTTClient constructor(context: Context) {
    private val context: Context
    private val toast: ToastUtil
    init {
        this.context = context
        this.toast = ToastUtil(context)
    }

    private lateinit var mqttClient: MqttAndroidClient

    // TAG
    companion object {
        const val TAG = "AndroidMqttClient"
    }

    fun connect(context: Context) {
        toast.show(R.string.mqtt_connecting)
//        val serverURI = "tcp://121.41.80.56:1883"
        val serverURI = context.getString(R.string.mqtt_broker_uri)
        val clientId = android.os.Build.MODEL + "__kotlin_client"

        mqttClient = MqttAndroidClient(context, serverURI, clientId)
        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d(TAG, "Receive message: ${message.toString()} from topic: $topic")
                if (message.toString() == context.getString(R.string.response_success)) {
                    toast.show(R.string.open)
                } else if (message.toString() == context.getString(R.string.response_failed)) {
                    toast.show(R.string.open_failed)
                } else {
                    Log.d(TAG, "Unknown message: ${message.toString()} from topic: $topic")
                }
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d(TAG, "Connection lost ${cause.toString()}")
                toast.show(R.string.connection_lost)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                toast.show(R.string.opening)
            }
        })

        try {
            mqttClient.connect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Connection success")
                    toast.show(R.string.mqtt_connected)
                    subscribe(context.getString(R.string.response_topic))
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Connection failure")
                    toast.show(R.string.mqtt_connect_failed)
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
            toast.show(context.getString(R.string.mqtt_connect_error))
        }

    }
    fun isConnected(): Boolean {
        return mqttClient.isConnected
    }

    fun subscribe(topic: String, qos: Int = 1) {
        try {
            mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Subscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to subscribe $topic")
                    toast.show(R.string.subscribe_failed)
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(topic: String) {
        try {
            mqttClient.unsubscribe(topic, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Unsubscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to unsubscribe $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "$msg published to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to publish $msg to $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            mqttClient.disconnect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Disconnected")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to disconnect")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

}


