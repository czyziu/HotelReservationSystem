package app

import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val appContext = AppContext()
        val loginGUI = LoginGUI(appContext)
        loginGUI.isVisible = true
    }
}