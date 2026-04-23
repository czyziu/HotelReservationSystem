package app

import users.Guest
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField

class ReceptionGUI(
    private val appContext: AppContext
) : JFrame() {

    private val outputArea = JTextArea()

    private val firstNameField = JTextField()
    private val lastNameField = JTextField()
    private val emailField = JTextField()
    private val roomIdField = JTextField()
    private val checkInField = JTextField()
    private val checkOutField = JTextField()

    private val actionReservationIdField = JTextField()

    init {
        title = "Reception Panel"
        setSize(1100, 700)
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()

        outputArea.isEditable = false
        add(JScrollPane(outputArea), BorderLayout.CENTER)

        val mainPanel = JPanel(GridLayout(3, 1))

        val createReservationPanel = JPanel(GridLayout(6, 2))
        createReservationPanel.add(JLabel("Guest First Name:"))
        createReservationPanel.add(firstNameField)

        createReservationPanel.add(JLabel("Guest Last Name:"))
        createReservationPanel.add(lastNameField)

        createReservationPanel.add(JLabel("Guest Email:"))
        createReservationPanel.add(emailField)

        createReservationPanel.add(JLabel("Room ID:"))
        createReservationPanel.add(roomIdField)

        createReservationPanel.add(JLabel("Check-in Date:"))
        createReservationPanel.add(checkInField)

        createReservationPanel.add(JLabel("Check-out Date:"))
        createReservationPanel.add(checkOutField)

        val createReservationButton = JButton("Create Reservation")

        val createReservationWrapper = JPanel(BorderLayout())
        createReservationWrapper.add(createReservationPanel, BorderLayout.CENTER)
        createReservationWrapper.add(createReservationButton, BorderLayout.SOUTH)

        val reservationActionsPanel = JPanel(GridLayout(3, 2))
        reservationActionsPanel.add(JLabel("Reservation ID for action:"))
        reservationActionsPanel.add(actionReservationIdField)

        val confirmButton = JButton("Confirm Reservation")
        val cancelButton = JButton("Cancel Reservation")
        val checkInButton = JButton("Check-In Guest")
        val checkOutButton = JButton("Check-Out Guest")

        reservationActionsPanel.add(confirmButton)
        reservationActionsPanel.add(cancelButton)
        reservationActionsPanel.add(checkInButton)
        reservationActionsPanel.add(checkOutButton)

        val infoButtonsPanel = JPanel(GridLayout(1, 4))
        val showAvailableRoomsButton = JButton("Show Available Rooms")
        val showAllReservationsButton = JButton("Show All Reservations")
        val showAllRoomsButton = JButton("Show All Rooms")
        val logoutButton = JButton("Logout")

        infoButtonsPanel.add(showAvailableRoomsButton)
        infoButtonsPanel.add(showAllRoomsButton)
        infoButtonsPanel.add(showAllReservationsButton)
        infoButtonsPanel.add(logoutButton)

        mainPanel.add(createReservationWrapper)
        mainPanel.add(reservationActionsPanel)
        mainPanel.add(infoButtonsPanel)

        add(mainPanel, BorderLayout.NORTH)

        createReservationButton.addActionListener { createReservation() }
        confirmButton.addActionListener { confirmReservation() }
        cancelButton.addActionListener { cancelReservation() }
        checkInButton.addActionListener { checkInReservation() }
        checkOutButton.addActionListener { checkOutReservation() }
        showAvailableRoomsButton.addActionListener { showAvailableRooms() }
        showAllReservationsButton.addActionListener { showAllReservations() }
        showAllRoomsButton.addActionListener { showAllRooms() }
        logoutButton.addActionListener { logout() }

        showAvailableRooms()
    }

    private fun createReservation() {
        try {
            val guest = Guest(
                id = appContext.idGenerator.getNextGuestId(),
                firstName = firstNameField.text.trim(),
                lastName = lastNameField.text.trim(),
                email = emailField.text.trim()
            )

            val reservation = appContext.reservationService.createReservation(
                reservationId = appContext.idGenerator.getNextReservationId(),
                guest = guest,
                roomId = roomIdField.text.trim().toInt(),
                checkInDate = checkInField.text.trim(),
                checkOutDate = checkOutField.text.trim()
            )

            JOptionPane.showMessageDialog(
                this,
                "Reservation created. Generated reservation ID: ${reservation.id}"
            )

            clearCreateReservationFields()
            showAllReservations()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Error: ${e.message}")
        }
    }

    private fun confirmReservation() {
        try {
            val reservationId = actionReservationIdField.text.trim().toInt()
            appContext.reservationService.confirmReservation(reservationId)
            JOptionPane.showMessageDialog(this, "Reservation confirmed.")
            showAllReservations()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Error: ${e.message}")
        }
    }

    private fun cancelReservation() {
        try {
            val reservationId = actionReservationIdField.text.trim().toInt()
            appContext.reservationService.cancelReservation(reservationId)
            JOptionPane.showMessageDialog(this, "Reservation cancelled.")
            showAllReservations()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Error: ${e.message}")
        }
    }

    private fun checkInReservation() {
        try {
            val reservationId = actionReservationIdField.text.trim().toInt()
            appContext.reservationService.checkInReservation(reservationId)
            JOptionPane.showMessageDialog(this, "Guest checked in.")
            showAllReservations()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Error: ${e.message}")
        }
    }

    private fun checkOutReservation() {
        try {
            val reservationId = actionReservationIdField.text.trim().toInt()
            appContext.reservationService.checkOutReservation(reservationId)
            JOptionPane.showMessageDialog(this, "Guest checked out.")
            showAllReservations()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Error: ${e.message}")
        }
    }

    private fun showAvailableRooms() {
        outputArea.text = "AVAILABLE ROOMS:\n\n"

        appContext.roomService.getAvailableRooms().forEach { room ->
            outputArea.append(
                "ID: ${room.id}\n" +
                        "Room number: ${room.number}\n" +
                        "Type: ${room.type}\n" +
                        "Capacity: ${room.capacity}\n" +
                        "Price per night: ${room.pricePerNight}\n" +
                        "Status: ${room.status}\n\n"
            )
        }
    }

    private fun showAllRooms() {
        outputArea.text = "ALL ROOMS:\n\n"

        appContext.roomService.getAllRooms().forEach { room ->
            val activeReservation = appContext.reservationService.getActiveReservationForRoom(room.id)

            outputArea.append(
                "ID: ${room.id}\n" +
                        "Room number: ${room.number}\n" +
                        "Type: ${room.type}\n" +
                        "Capacity: ${room.capacity}\n" +
                        "Price per night: ${room.pricePerNight}\n" +
                        "Status: ${room.status}\n"
            )

            if (activeReservation != null) {
                outputArea.append("Reserved/occupied until: ${activeReservation.checkOutDate}\n")
            }

            outputArea.append("\n")
        }
    }

    private fun showAllReservations() {
        outputArea.text = "ALL RESERVATIONS:\n\n"

        appContext.reservationService.getAllReservations().forEach {
            outputArea.append(
                "Reservation ID: ${it.id}\n" +
                        "Guest: ${it.guest.firstName} ${it.guest.lastName}\n" +
                        "Email: ${it.guest.email}\n" +
                        "Room: ${it.room.number}\n" +
                        "Check-in: ${it.checkInDate}\n" +
                        "Check-out: ${it.checkOutDate}\n" +
                        "Status: ${it.status}\n\n"
            )
        }
    }

    private fun clearCreateReservationFields() {
        firstNameField.text = ""
        lastNameField.text = ""
        emailField.text = ""
        roomIdField.text = ""
        checkInField.text = ""
        checkOutField.text = ""
    }

    private fun logout() {
        val loginGUI = LoginGUI(appContext)
        loginGUI.isVisible = true
        dispose()
    }
}