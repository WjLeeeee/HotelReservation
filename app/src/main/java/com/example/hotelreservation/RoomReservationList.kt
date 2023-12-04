package com.example.hotelreservation

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.*
import java.time.temporal.ChronoUnit

data class Reservation(
    val name: String,
    var roomNumber: String,
    var checkIn: String,
    var checkOut: String,
    val randomUserPayment: Int,
    var randomHotelFee: Int,
    var myMoney:Int
)

class RoomReservationList {
    private val reservations: MutableList<Reservation> = mutableListOf()

    //예약자를 정해주는 함수
    fun addReservation(name: String, roomNumber: String, checkIn: String, checkOut: String) {
        val randomUserPayment = (100000..500000).random()
        val randomHotelFee = (10000..50000).random()
        val myMoney = randomUserPayment - randomHotelFee

        val reservation = Reservation(name, roomNumber, checkIn, checkOut, randomUserPayment, randomHotelFee, myMoney)
        reservations.add(reservation)
        println("예약자 저장.")
    }

    //해당 날짜에 방이 있는지 확인해주는 함수
    fun isRoomOk(roomNumber: String, checkIn: String): Boolean {
        for (reservation in reservations) {
            if (reservation.roomNumber == roomNumber && reservation.checkIn == checkIn) {
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
                println("${index + 1}: 사용자: ${reservation.name}, 방번호: ${reservation.roomNumber}호, 체크인: ${reservation.checkIn}, 체크아웃:  ${reservation.checkOut}")
            }
        }
    }

    //예약자목록을 정렬해서 보여주는 함수
    fun sortReservations() {
        reservations.sortBy { it.checkIn }
        println("호텔 예약자 목록입니다.")
        for ((index, reservation) in reservations.withIndex()) {
            println("${index + 1}: 사용자: ${reservation.name}, 방번호: ${reservation.roomNumber}호, 체크인: ${reservation.checkIn}, 체크아웃:  ${reservation.checkOut}")
        }
    }

    //금액 출입금 내역 목록 출력
    fun myMoney(myName: String) {
        for (reservation in reservations) {
            if (reservation.name == myName) {
                println("조회하실 사용자: $myName")
                println("초기금액: ${reservation.randomUserPayment} 원")
                println("호텔 예약비로 빠져나간 금액: ${reservation.randomHotelFee} 원")
                println("현재 남은금액: ${reservation.myMoney} 원")
            } else {
                println("조회하실 사용자를 찾을 수 없습니다.")
            }
        }

    }

    // 예약 변경/취소
    fun reservationChangeCancle(reName: String) {
        val iterator = reservations.iterator()
        while (iterator.hasNext()) {
            val reservation = iterator.next()

            if (reservation.name == reName) {
                println("$reName 님이 예약한 목록입니다. 변경하실 예약번호를 입력해주세요 (탈출은 exit입력)")
                for ((index, res) in reservations.withIndex()) {
                    if (res.name == reName) {
                        println("${index + 1}: 방번호: ${res.roomNumber}호, 체크인: ${res.checkIn}, 체크아웃:  ${res.checkOut}")
                    }
                }
                var myIndex = putInfo("choose number").toString().toInt()
                if (myIndex >= 1 && myIndex <= reservations.size) {
                    println("해당 예약을 어떻게 하시겠어요? 1. 변경 2. 취소 / 이외 번호. 메뉴로 돌아가기")
                    var changeOrCancle = putInfo("changeOrCancle").toString().toInt()
                    when (changeOrCancle) {
                        1 -> {
                            //예약변경 로직 구현 X
                            println("변경하실 내용을 선택해주세요.")
                            println("[1]. 방번호  [2]. 체크인날짜 [3]. 체크아웃 날짜")
                            var changeAny = putInfo("choose number").toString().toInt()
                            when(changeAny){
                                1 -> {
                                    println("변경하실 방 번호를 입력해주세요")
                                    val newRoomNum = readLine().toString()
                                    reservation.roomNumber = newRoomNum
                                }
                                2 -> {
                                    println("변경하실 체크인 날짜를 입력해주세요")
                                    val newCheckInDate = readLine().toString()
                                    reservation.checkIn = newCheckInDate
                                }
                                3 -> {
                                    println("변경하실 체크아웃 날짜를 입력해주세요")
                                    val newCheckOutDate = readLine().toString()
                                    reservation.checkOut = newCheckOutDate
                                }
                                else -> {
                                    println("올바른 번호가 아닙니다.")
                                    break
                                }
                            }
                            println("예약이 변경되었습니다.")
                        }

                        2 -> { //예약금 환불 로직 구현 X
                            println("체크인 3일 이전 취소 예약금 환불 불가")
                            println("체크인 5일 이전 취소 예약금의 30% 환불")
                            println("체크인 7일 이전 취소 예약금의 50% 환불")
                            println("체크인 14일 이전 취소 예약금의 80% 환불")
                            println("체크인 30일 이전 취소 예약금의 100% 환불")
                            println("----------------------------------")
                            val cancellationPeriod = calculateCancellationPeriod(reservation.checkIn)
                            val refundPercentage = calculateRefundPercentage(cancellationPeriod)
                            if (refundPercentage == 0) {
                                println("체크인 3일이전취소로 예약금 환불 없이 취소완료")
                            } else {
                                val refundAmount =
                                    (reservation.randomHotelFee * refundPercentage / 100).toInt()
                                println("체크인 $cancellationPeriod 일 이전 취소 예약금의 $refundPercentage% 환불")
                                println("환불금액: $refundAmount 원")
                                reservation.myMoney += refundAmount
                                println("사용자에게 남은 금액 : ${reservation.myMoney}")
                            }

                            iterator.remove() // 반복자를 통해 리스트에서 제거
                            break
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

    //현재날짜와 예약된 날짜가 며칠 전인지 확인하는 함수
    fun calculateCancellationPeriod(checkInDate: String): Int {
        val formatter = ofPattern("yyyyMMdd")
        val today = LocalDate.now()
        val checkIn = LocalDate.parse(checkInDate, formatter)
        val cancellationPeriod = ChronoUnit.DAYS.between(today, checkIn).toInt()
        return cancellationPeriod
    }

    //calculateCancellationPeriod함수를 이용해 구한 날짜를 남은 날별로 몇%환불이 가능한지 return하는 함수
    fun calculateRefundPercentage(cancellationPeriod: Int): Int {
        return when {
            cancellationPeriod >= 30 -> 100
            cancellationPeriod >= 14 -> 80
            cancellationPeriod >= 7 -> 50
            cancellationPeriod >= 5 -> 30
            cancellationPeriod >= 3 -> 0
            else -> 0
        }
    }

    fun putInfo(type: String): Any? {
        when (type) {
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

            else -> {
                return "no"
            }
        }
    }
}
