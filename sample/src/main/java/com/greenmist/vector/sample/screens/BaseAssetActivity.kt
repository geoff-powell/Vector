package com.greenmist.vector.sample.screens

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.greenmist.vector.Vector
import com.greenmist.vector.sample.R
import kotlinx.android.synthetic.main.activity_asset.*
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseAssetActivity : BaseActivity() {

    override val layoutId: Int = R.layout.activity_asset
    abstract val assetPath: String

    private var selectedFilename: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val svgList = assets.list(assetPath).filter { it.endsWith(".svg") }

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
        if (selectedFilename.isBlank()) return

        val svg = Vector.with(this)
                .from(assets.open(("$assetPath/$selectedFilename")))
                .load()

        svg_image.setImageBitmap(svg.getBitmap())
    }
}