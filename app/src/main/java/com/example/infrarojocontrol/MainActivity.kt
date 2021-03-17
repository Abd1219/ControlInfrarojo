package com.example.infrarojocontrol

import android.hardware.ConsumerIrManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import java.util.*

class MainActivity : AppCompatActivity() {

    private val CMD_TV_POWER =
        "0000 006C 0000 0022 015B 00AD 0016 0041 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0041 0016 0041 0016 0041 0016 0016 0016 0041 0016 0041 0016 0016 0016 0622"
    private val CMD_TV_CH_NEXT =
        "0000 006C 0000 0022 015B 00AD 0016 0041 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0041 0016 0041 0016 0041 0016 0041 0016 0016 0016 0016 0016 0041 0016 0016 0016 0622"
    private val CMD_TV_CH_PREV =
        "0000 006C 0000 0022 015B 00AD 0016 0041 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0016 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0041 0016 0016 0016 0016 0016 0041 0016 0016 0016 0622"
    private val CMD_TV_LEFT =
        "0000 006C 0000 0022 015B 00AD 0016 0041 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0016 0016 0041 0016 0016 0016 0622"
    private val CMD_TV_RIGHT =
        "0000 006C 0000 0022 015B 00AD 0016 0041 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0041 0016 0041 0016 0016 0016 0016 0016 0041 0016 0016 0016 0622"
    private val CMD_TV_ENTER =
        "0000 006C 0000 0022 015B 00AD 0016 0041 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0041 0016 0041 0016 0041 0016 0016 0016 0622"
    private val CMD_SB_VOLUP =
        "0000 006C 0000 0022 015B 00AD 0016 0041 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0041 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0622"
    private val CMD_SB_VOLDOWN =
        "0000 006C 0000 0022 015B 00AD 0016 0041 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0016 0016 0041 0016 0016 0016 0041 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0016 0016 0622"

    private var irManager: ConsumerIrManager? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        irManager = getSystemService(CONSUMER_IR_SERVICE) as ConsumerIrManager
        findViewById<View>(R.id.btnPower).setOnClickListener(ClickListener(hex2ir(CMD_TV_POWER),))
        findViewById<View>(R.id.btnArriba).setOnClickListener(ClickListener(hex2ir(CMD_TV_CH_NEXT)))
        findViewById<View>(R.id.btnAbajo).setOnClickListener(ClickListener(hex2ir(CMD_TV_CH_PREV)))
        findViewById<View>(R.id.btnIzquierda).setOnClickListener(ClickListener(hex2ir(CMD_TV_LEFT)))
        findViewById<View>(R.id.btnDerecha).setOnClickListener(ClickListener(hex2ir(CMD_TV_RIGHT)))
        findViewById<View>(R.id.btnEnter).setOnClickListener(ClickListener(hex2ir(CMD_TV_ENTER)))
        //findViewById<View>(R.id.sbvoldown).setOnClickListener(ClickListener(hex2ir(CMD_SB_VOLDOWN)))
        //findViewById<View>(R.id.sbvolup).setOnClickListener(ClickListener(hex2ir(CMD_SB_VOLUP)))
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun hex2ir(irData: String): IRCommand {
        val list: MutableList<String> =
            ArrayList(listOf(*irData.split(" ".toRegex()).toTypedArray()))
        list.removeAt(0) // dummy
        var frequency: Int = list.removeAt(0).toInt(16) // frequency
        list.removeAt(0) // seq1
        list.removeAt(0) // seq2
        frequency = (1000000 / (frequency * 0.241246)).toInt()
        val pulses = 1000000 / frequency
        var count: Int
        val pattern = IntArray(list.size)
        for (i in list.indices) {
            count = list[i].toInt(16)
            pattern[i] = count * pulses
        }
        return IRCommand(frequency,pattern)
    }

    private class ClickListener(private val cmd: IRCommand) : View.OnClickListener {
        private var irManager: ConsumerIrManager? = null
        //irManager = getSystemService(CONSUMER_IR_SERVICE) as ConsumerIrManager
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onClick(view: View) {
            Log.d("Remote", "frequency: " + cmd.freq)
            Log.d("Remote", "pattern: " + cmd.pattern.contentToString())
            //irManager.transmit(cmd.freq, cmd.pattern);
            irManager?.transmit(38000, cmd.pattern)
        }
    }

    private class IRCommand(val freq: Int, val pattern: IntArray)

}

