package models

import enums.PaymentStatus
import interfaces.Identifiable

data class Payment(
    override val id: Int,
    val reservationId: Int,
    val amount: Double,
    var status: PaymentStatus
) : Identifiable {

    fun processPayment() {
        status = PaymentStatus.PAID
    }

    fun refundPayment() {
        status = PaymentStatus.REFUNDED
    }

    fun getPaymentDetails() {
        println("Payment id: $id")
        println("Reservation id: $reservationId")
        println("Amount: $amount")
        println("Status: $status")
    }
}