package com.greenmist.vector.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.greenmist.vector.Vector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var selectedFilename: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val svgList = assets.list("").filter { it.endsWith("svg") }

        svg_spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, svgList)
        svg_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedFilename = svg_spinner.adapter.getItem(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        get_svg.setOnClickListener { getSvg() }
    }

    private fun getSvg() {
        val svg = Vector.with(this)
                .from(assets.open(selectedFilename))
                .load()

        svg_image.setImageBitmap(svg.getBitmap())
    }
}