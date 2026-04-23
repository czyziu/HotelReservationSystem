package app

import enums.RoomStatus
import enums.RoomType
import models.Room
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

class AdminGUI(
    private val appContext: AppContext
) : JFrame() {

    private val outputArea = JTextArea()

    private val numberField = JTextField()
    private val typeField = JTextField()
    private val capacityField = JTextField()
    private val priceField = JTextField()

    private val removeRoomIdField = JTextField()

    init {
        title = "Admin Panel"
        setSize(900, 600)
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()

        outputArea.isEditable = false
        add(JScrollPane(outputArea), BorderLayout.CENTER)

        val topPanel = JPanel(GridLayout(3, 1))

        val addRoomPanel = JPanel(GridLayout(5, 2))

        addRoomPanel.add(JLabel("Room Number:"))
        addRoomPanel.add(numberField)

        addRoomPanel.add(JLabel("Room Type (SINGLE/DOUBLE/APARTMENT):"))
        addRoomPanel.add(typeField)

        addRoomPanel.add(JLabel("Capacity:"))
        addRoomPanel.add(capacityField)

        addRoomPanel.add(JLabel("Price Per Night:"))
        addRoomPanel.add(priceField)

        val addRoomButton = JButton("Add Room")
        addRoomPanel.add(JLabel())
        addRoomPanel.add(addRoomButton)

        val removeRoomPanel = JPanel(GridLayout(2, 2))
        removeRoomPanel.add(JLabel("Room ID to remove:"))
        removeRoomPanel.add(removeRoomIdField)

        val removeRoomButton = JButton("Remove Room")
        val showRoomsButton = JButton("Show All Rooms")

        removeRoomPanel.add(removeRoomButton)
        removeRoomPanel.add(showRoomsButton)

        val bottomButtonPanel = JPanel(GridLayout(1, 3))
        val logoutButton = JButton("Logout")
        val showAvailableRoomsButton = JButton("Show Available Rooms")
        bottomButtonPanel.add(showAvailableRoomsButton)
        bottomButtonPanel.add(showRoomsButton)
        bottomButtonPanel.add(logoutButton)

        topPanel.add(addRoomPanel)
        topPanel.add(removeRoomPanel)
        topPanel.add(bottomButtonPanel)

        add(topPanel, BorderLayout.NORTH)

        addRoomButton.addActionListener { addRoom() }
        removeRoomButton.addActionListener { removeRoom() }
        showRoomsButton.addActionListener { showAllRooms() }
        showAvailableRoomsButton.addActionListener { showAvailableRooms() }
        logoutButton.addActionListener { logout() }

        showAllRooms()
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

    private fun addRoom() {
        try {
            val number = numberField.text.trim()
            val type = RoomType.valueOf(typeField.text.trim().uppercase())
            val capacity = capacityField.text.trim().toInt()
            val price = priceField.text.trim().toDouble()

            val room = Room(
                id = appContext.idGenerator.getNextRoomId(),
                number = number,
                type = type,
                capacity = capacity,
                pricePerNight = price,
                status = RoomStatus.AVAILABLE
            )

            appContext.roomService.addRoom(room)

            JOptionPane.showMessageDialog(this, "Room added successfully. Generated ID: ${room.id}")
            clearAddRoomFields()
            showAllRooms()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Error: ${e.message}")
        }
    }

    private fun removeRoom() {
        try {
            val roomId = removeRoomIdField.text.trim().toInt()
            appContext.roomService.removeRoom(roomId)

            JOptionPane.showMessageDialog(this, "Room removed successfully.")
            removeRoomIdField.text = ""
            showAllRooms()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Error: ${e.message}")
        }
    }

    private fun clearAddRoomFields() {
        numberField.text = ""
        typeField.text = ""
        capacityField.text = ""
        priceField.text = ""
    }

    private fun logout() {
        val loginGUI = LoginGUI(appContext)
        loginGUI.isVisible = true
        dispose()
    }
}