package app

import users.Admin
import users.Receptionist
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField

class LoginGUI(
    private val appContext: AppContext
) : JFrame() {

    private val emailField = JTextField()

    init {
        title = "Hotel Reservation System - Login"
        setSize(400, 200)
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()

        val formPanel = JPanel(GridLayout(2, 2))
        formPanel.add(JLabel("Email:"))
        formPanel.add(emailField)

        val loginButton = JButton("Login")
        formPanel.add(JLabel())
        formPanel.add(loginButton)

        add(formPanel, BorderLayout.CENTER)

        loginButton.addActionListener {
            login()
        }
    }

    private fun login() {
        val email = emailField.text.trim()

        try {
            val user = appContext.authService.loginByEmail(email)

            when (user) {
                is Admin -> {
                    val adminGUI = AdminGUI(appContext)
                    adminGUI.isVisible = true
                    dispose()
                }
                is Receptionist -> {
                    val receptionGUI = ReceptionGUI(appContext)
                    receptionGUI.isVisible = true
                    dispose()
                }
                else -> {
                    JOptionPane.showMessageDialog(this, "Unknown user role.")
                }
            }
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, e.message)
        }
    }
}