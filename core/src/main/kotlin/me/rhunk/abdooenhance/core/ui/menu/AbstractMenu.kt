package me.rhunk.abdooenhance.core.ui.menu

import android.view.View
import android.view.ViewGroup
import me.rhunk.abdooenhance.core.ModContext

abstract class AbstractMenu {
    lateinit var menuViewInjector: MenuViewInjector
    lateinit var context: ModContext

    open fun inject(parent: ViewGroup, view: View, viewConsumer: (View) -> Unit) {}

    open fun init() {}
}