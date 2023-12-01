package com.example.hotelreservation

data class Reservation(
    val name: String,
    val roomNumber: String,
    val checkIn: String,
    val checkOut: String
)

class RoomReservationList {
    private val reservations: MutableList<Reservation> = mutableListOf()

    fun addReservation(name: String, roomNumber: String, checkIn:String, checkOut:String){
        val reservation = Reservation(name, roomNumber, checkIn, checkOut)
        reservations.add(reservation)
        println("예약자 저장.")
    }

    fun isRoomOk(roomNumber:String, checkIn: String): Boolean{
        for(reservation in reservations){
            if(reservation.roomNumber == roomNumber && reservation.checkIn == checkIn){
                println("해당 날짜에 이미 예약이 되어 있습니다.")
                return false
            }
        }
        return true
    }

    fun printReservations() {
        if (reservations.isEmpty()) {
            println("예약 목록이 비어 있습니다.")
        } else {
            println("호텔 예약자 목록입니다.")
            for ((index, reservation) in reservations.withIndex()) {
                println("${index+1}: 사용자: ${reservation.name}, 방번호: ${reservation.roomNumber}호, 체크인: ${reservation.checkIn}, 체크아웃:  ${reservation.checkOut}")
            }
        }
    }
    fun sortReservations(){
        reservations.sortBy { it.checkIn }
        println("호텔 예약자 목록입니다.")
        for ((index, reservation) in reservations.withIndex()) {
            println("${index+1}: 사용자: ${reservation.name}, 방번호: ${reservation.roomNumber}호, 체크인: ${reservation.checkIn}, 체크아웃:  ${reservation.checkOut}")
        }
    }

}