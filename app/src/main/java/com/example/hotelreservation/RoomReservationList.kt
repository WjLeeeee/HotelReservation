package com.example.hotelreservation

import android.text.method.BaseKeyListener

data class Reservation(
    val name: String,
    val roomNumber: String,
    val checkIn: String,
    val checkOut: String,
    val randomUserPayment: Int,
    val randomHotelFee: Int
)

class RoomReservationList {
    private val reservations: MutableList<Reservation> = mutableListOf()

    //예약자를 정해주는 함수
    fun addReservation(name: String, roomNumber: String, checkIn:String, checkOut:String){
        val randomUserPayment = (100000..500000).random()
        val randomHotelFee = (10000..50000).random()

        val reservation = Reservation(name, roomNumber, checkIn, checkOut, randomUserPayment, randomHotelFee)
        reservations.add(reservation)
        println("예약자 저장.")
    }

    //해당 날짜에 방이 있는지 확인해주는 함수
    fun isRoomOk(roomNumber:String, checkIn: String): Boolean{
        for(reservation in reservations){
            if(reservation.roomNumber == roomNumber && reservation.checkIn == checkIn){
                return false
            }
        }
        return true
    }

    //예약자 목록을 보여주는 함수
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

    //예약자목록을 정렬해서 보여주는 함수
    fun sortReservations(){
        reservations.sortBy { it.checkIn }
        println("호텔 예약자 목록입니다.")
        for ((index, reservation) in reservations.withIndex()) {
            println("${index+1}: 사용자: ${reservation.name}, 방번호: ${reservation.roomNumber}호, 체크인: ${reservation.checkIn}, 체크아웃:  ${reservation.checkOut}")
        }
    }

    //금액 출입금 내역 목록 출력
    fun myMoney(myName:String){
        for(reservation in reservations){
            if(reservation.name == myName){
                println("조회하실 사용자: $myName")
                println("초기금액: ${reservation.randomUserPayment} 원")
                println("호텔 예약비로 빠져나간 금액: ${reservation.randomHotelFee} 원")
                println("현재 남은금액: ${reservation.randomUserPayment - reservation.randomHotelFee} 원")
            }else{
                println("조회하실 사용자를 찾을 수 없습니다.")
            }
        }

    }

    // 예약 변경/취소
    fun reservationChangeCancle(reName: String) {
        var selectedReservation: Reservation? = null

        for (reservation in reservations) {
            if (reservation.name == reName) {
                println("$reName 님이 예약한 목록입니다. 변경하실 예약번호를 입력해주세요 (탈출은 exit입력)")
                for ((index, res) in reservations.withIndex()) {
                    if (res.name == reName) {
                        println("${index + 1}: 방번호: ${res.roomNumber}호, 체크인: ${res.checkIn}, 체크아웃:  ${res.checkOut}")
                    }
                }
                var myIndex = putInfo("choose number").toString().toInt()
                if (myIndex >= 1) {
                    println("해당 예약을 어떻게 하시겠어요? 1. 변경 2. 취소 / 이외 번호. 메뉴로 돌아가기")
                    var changeOrCancle = putInfo("changeOrCancle").toString().toInt()
                    selectedReservation = reservations[changeOrCancle - 1]
                    when (changeOrCancle) {
                        1 -> {
                            println("예약이 변경되었습니다.")
                        }
                        2 -> {
                            reservations.remove(selectedReservation)
                        }
                        else -> println("메뉴로 돌아갑니다.")
                    }
                } else {
                    println("범위에 없는 예약번호입니다.")
                }
            } else {
                println("예약된 사용자를 찾을 수 없습니다.")
            }
        }
    }


    fun putInfo(type: String): Any?{
        when(type){
            "choose number" -> {
                while (true) {
                    try {
                        var num: String? = readLine()
                        var result = num?.toInt()
                        return result
                    } catch (e: Exception) {
                        println("메뉴를 다시 선택해주세요.")
                    }
                }
            }
            "changeOrCancle" -> {
                while (true) {
                    try {
                        var num: String? = readLine()
                        var result = num?.toInt()
                        return result
                    } catch (e: Exception) {
                        println("다시 선택해주세요.")
                    }
                }
            }
            else ->{
                return "no"
            }
        }
    }

}