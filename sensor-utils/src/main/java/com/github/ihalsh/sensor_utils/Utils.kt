package com.github.ihalsh.sensor_utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun SensorManager.getDataAsFlowFrom(sensor: Sensor): Flow<FloatArray> = callbackFlow {
    val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let { channel.trySend(it.values) }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            //Not implemented
        }
    }

    registerListener(listener, sensor, SensorManager.SENSOR_DELAY_FASTEST)

    awaitClose {
        unregisterListener(listener)
    }
}