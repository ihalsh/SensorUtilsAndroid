package com.github.ihalsh.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.ihalsh.sensor_utils.getDataAsFlowFrom
import com.github.ihalsh.sensors.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                binding.textView.text = sensorManager.getSensorList(Sensor.TYPE_ALL)
//                    .map { "\n${it.name}" }
//                    .toString()
                sensorManager.getDataAsFlowFrom(sensor).collect {
                    binding.textView.text = it.first().toString()
                }
            }
        }
    }
}
