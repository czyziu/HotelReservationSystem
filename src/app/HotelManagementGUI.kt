package app

import enums.RoomStatus
import enums.RoomType
import models.Room
import repositories.ReservationRepository
import repositories.RoomRepository
import services.ReservationService
import users.Guest
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.SwingUtilities

class HotelManagementGUI : JFrame() {

    private val outputArea = JTextArea()

    private val roomRepository = RoomRepository()
    private val reservationRepository = ReservationRepository()
    private val reservationService = ReservationService(roomRepository, reservationRepository)

    private val guest = Guest(1, "Jan", "Kowalski", "jan@test.pl")

    init {
        title = "Hotel Reservation System"
        setSize(700, 500)
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()

        prepareData()

        outputArea.isEditable = false
        add(JScrollPane(outputArea), BorderLayout.CENTER)

        val buttonPanel = JPanel()
        buttonPanel.layout = GridLayout(2, 2)

        val showAllRoomsButton = JButton("Show all rooms")
        val showAvailableRoomsButton = JButton("Show available rooms")
        val createReservationButton = JButton("Create reservation")
        val cancelReservationButton = JButton("Cancel reservation")

        showAllRoomsButton.addActionListener {
            showAllRooms()
        }

        showAvailableRoomsButton.addActionListener {
            showAvailableRooms()
        }

        createReservationButton.addActionListener {
            createSampleReservation()
        }

        cancelReservationButton.addActionListener {
            cancelSampleReservation()
        }

        buttonPanel.add(showAllRoomsButton)
        buttonPanel.add(showAvailableRoomsButton)
        buttonPanel.add(createReservationButton)
        buttonPanel.add(cancelReservationButton)

        add(buttonPanel, BorderLayout.SOUTH)
    }

    private fun prepareData() {
        roomRepository.addRoom(
            Room(101, "A101", RoomType.SINGLE, 1, 200.0, RoomStatus.AVAILABLE)
        )
        roomRepository.addRoom(
            Room(102, "A102", RoomType.DOUBLE, 2, 300.0, RoomStatus.AVAILABLE)
        )
        roomRepository.addRoom(
            Room(103, "A103", RoomType.APARTMENT, 4, 600.0, RoomStatus.OCCUPIED)
        )
    }

    private fun showAllRooms() {
        outputArea.text = "ALL ROOMS:\n\n"
        roomRepository.findAll().forEach {
            outputArea.append(
                "Room number: ${it.number}\n" +
                        "Type: ${it.type}\n" +
                        "Capacity: ${it.capacity}\n" +
                        "Price per night: ${it.pricePerNight}\n" +
                        "Status: ${it.status}\n\n"
            )
        }
    }

    private fun showAvailableRooms() {
        outputArea.text = "AVAILABLE ROOMS:\n\n"
        roomRepository.findAvailableRooms().forEach {
            outputArea.append(
                "Room number: ${it.number}\n" +
                        "Type: ${it.type}\n" +
                        "Capacity: ${it.capacity}\n" +
                        "Price per night: ${it.pricePerNight}\n" +
                        "Status: ${it.status}\n\n"
            )
        }
    }

    private fun createSampleReservation() {
        try {
            val reservation = reservationService.createReservation(
                reservationId = 1,
                guest = guest,
                roomId = 102,
                checkInDate = "2025-06-10",
                checkOutDate = "2025-06-15"
            )

            outputArea.text =
                "RESERVATION CREATED:\n\n" +
                        "Reservation id: ${reservation.id}\n" +
                        "Guest: ${reservation.guest.firstName} ${reservation.guest.lastName}\n" +
                        "Room: ${reservation.room.number}\n" +
                        "Check-in: ${reservation.checkInDate}\n" +
                        "Check-out: ${reservation.checkOutDate}\n" +
                        "Status: ${reservation.status}\n"
        } catch (e: Exception) {
            outputArea.text = "Error: ${e.message}"
        }
    }

    private fun cancelSampleReservation() {
        try {
            reservationService.cancelReservation(1)
            outputArea.text = "Reservation with id 1 has been cancelled."
        } catch (e: Exception) {
            outputArea.text = "Error: ${e.message}"
        }
    }
}

fun main() {
    SwingUtilities.invokeLater {
        val gui = HotelManagementGUI()
        gui.isVisible = true
    }
}