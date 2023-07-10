package com.flores.aplicacionmoviles.ui.utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentsManager {

    fun replaceFragmet(manager: FragmentManager, container: Int, fragment: Fragment) {
        with(manager.beginTransaction()) {
            replace(container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun addFragmet(manager: FragmentManager, container: Int, fragment: Fragment) {
        with(manager.beginTransaction()) {
            add(container, fragment)
            commit()
        }
    }
}