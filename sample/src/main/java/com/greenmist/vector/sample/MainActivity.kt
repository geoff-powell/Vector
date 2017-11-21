package com.greenmist.vector.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.greenmist.vector.new.Svg
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        get_svg.setOnClickListener {
            getSvg()
        }
    }


    private fun getSvg() {
        Svg.getFromInputStream(assets.open("testmap.svg"))
    }
}
