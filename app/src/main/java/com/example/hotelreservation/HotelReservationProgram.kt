package com.example.hotelreservation

fun main() {
    println("호텔예약 프로그램 입니다.")
    val reservationList = RoomReservationList()
    while (true) {
        println("[메뉴]")
        println("1. 방예약, 2. 예약자목록 출력, 3. 예약목록 (정렬) 출력, 4. 시스템 종료, 5. 금액 입금-출금 내역 목록 출력 6. 예약 변경/취소")
        var menuChoice = putMyInfo("menuChoice").toString().toInt()
        when (menuChoice) {
            1 -> {
                var reName = putMyInfo("Name").toString()
                var reRoomNumber = putMyInfo("RoomNumber").toString()
                var reCheckIn = putMyInfo("CheckIn", reRoomNumber).toString()
                var reCheckOut = putMyInfo("CheckOut", reCheckIn).toString()

                reservationList.addReservation(reName, reRoomNumber, reCheckIn, reCheckOut)
                println("호텔 예약이 완료되었습니다.")
            }

            2 -> {
                reservationList.printReservations()
            }

            3 -> {
                reservationList.sortReservations()
            }

            4 -> {
                println("프로그램을 종료합니다.")
                break
            }

            5 -> {}
            6 -> {}
            else -> println("올바른 번호를 골라주세요.")
        }
    }
}

fun putMyInfo(type: String, checkInDate: String? = null, roomNumData: String? = null): Any? {

    return when (type) {
        "menuChoice" -> {
            while (true) {
                try {
                    var menuChoice: String? = readLine()
                    return menuChoice?.toInt() ?: -1
                } catch (e: Exception) {
                    println("메뉴를 다시 선택해주세요.")
                }
            }
        }

        "Name" -> {
            println("예약자분의 성함을 입력해주세요")
            while (true) {
                try {
                    var originName = readLine()
                    if (originName?.toIntOrNull() == null && originName?.first() != '_' && originName?.first() != '!') {
                        return originName
                    } else {
                        println("이름을 다시 입력해주세요")
                    }
                } catch (e: Exception) {
                    println("이름을 다시 입력해주세요")
                }
            }
        }

        "RoomNumber" -> {
            println("예약할 방번호를 입력홰주세요")
            while (true) {
                try {
                    var roomNumber: String? = readLine()
                    val isRoomNumber = roomNumber?.toInt()
                    if (isRoomNumber in 100..999) {
                        return isRoomNumber
                    } else {
                        println("올바르지 않은 방번호 입니다. 방번호는 100~999 영역 이내입니다.")
                    }
                } catch (e: Exception) {
                    println("올바르지 않은 방번호 입니다. 방번호는 100~999 영역 이내입니다.")
                }
            }
        }

        "CheckIn" -> {
            println("체크인 날짜를 입력해주세요 표기형식. 20231130")
            while (true) {
                try {
                    var checkIn: String? = readLine()
                    val isCheckIn = checkIn?.toInt()
                    if (isCheckIn != null && isCheckIn <= 20231130) {
                        println("체크인은 지난날은 선택할 수 없습니다.")
                    } else {
                        return checkIn?.toInt() ?: -1
                    }
                } catch (e: Exception) {
                    println("체크인은 지난날은 선택할 수 없습니다.")
                }
            }
        }

        "CheckOut" -> {
            println("체크아웃 날짜를 입력해주세요 표기형식. 20231130")
            while (true) {
                try {
                    var checkOut: String? = readLine()
                    val ischeckOut = checkOut?.toInt()
                    if (ischeckOut != null && ischeckOut > checkInDate?.toInt() ?: 20231130) {
                        return ischeckOut
                    } else {
                        println("체크인 날짜보다 이전이거나 같을 수는 없습니다.")
                    }
                } catch (e: Exception) {
                    println("체크인 날짜보다 이전이거나 같을 수는 없습니다.")
                }
            }
        }

        else -> {
            return "no"
        }
    }

}