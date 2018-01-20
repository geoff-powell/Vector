package com.greenmist.vector.sample.screens

import com.greenmist.vector.sample.R

class BasicActivity : BaseAssetActivity() {
    override val assetPath: String = "basic"
    override val drawerItemId: Int = R.id.basic
    override val toolbarTitleId: Int = R.string.basic_title
}