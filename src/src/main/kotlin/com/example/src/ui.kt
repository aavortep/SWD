package com.example.src

import java.sql.Connection
import java.sql.Time

class TUI(val connect: Connection?)
{
    private var option: Int? = -1
    private val welcome_menu = "0. Exit\n 1. Sign in\n 2. Sign up"
    private val musician_menu = "0. Exit\n 3. Book rehearsal\n" +
            "4. Show booked rehearsals\n 5. Cancel rehearsal\n 6. To owner mode\n" +
            "7. Account settings\n 8. Delete account\n 9. Log out"
    private val owner_menu = "0. Exit\n 10. All rehearsals\n 11. Register base\n" +
            "12. Base settings\n 13. Delete base\n 14. To musician mode"

    private fun check_input(tmp: String?): Boolean {
        return (tmp == null || tmp == "\n" || tmp == "")
    }
    fun welcome() {
        println("Welcome to HearBase!\n")
        while (option != 0) {
            println(welcome_menu)
            print("> ")
            try {
                option = readLine()?.toInt()
            }
            catch (e: NumberFormatException) {
                println("Invalid option")
                option = -1
                continue
            }
            if (option == null) {
                option = -1
                println("Invalid input")
                continue
            }

            when (option) {
                0 -> break
                1 -> {
                    println("LOGGING IN\n")
                    print("Mail: ")
                    var tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    val mail: String = tmp.toString()
                    print("Password: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    val password: String = tmp.toString()

                    val acc = AccActs(connect).findAcc(mail, password)
                    if (acc.id == -1) {
                        println("Invalid email or password. Try again")
                        continue
                    }
                    println("LOGGED IN SUCCESSFULLY")
                    option = musician(acc)
                }
                2 -> {
                    println("CREATING NEW ACCOUNT\n")
                    val acc = Account()
                    print("FIO: ")
                    var tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    acc.fio = tmp
                    print("Phone: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    acc.phone = tmp
                    print("Mail: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    acc.mail = tmp
                    print("Password: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    acc.password = tmp

                    acc.id = AccActs(connect).save(acc)
                    println("ACC CREATED SUCCESSFULLY")
                    option = musician(acc)
                }
                else -> println("Invalid option")
            }
        }
    }
    private fun musician(acc: Account): Int? {
        while (option != 0) {
            println(musician_menu)
            print("> ")
            try {
                option = readLine()?.toInt()
            }
            catch (e: NumberFormatException) {
                println("Invalid option")
                option = -1
                continue
            }
            if (option == null) {
                option = -1
                println("Invalid input")
                continue
            }

            when (option) {
                0 -> break
                3 -> {
                    val reh = Rehearsal()
                    val rooms = PostgresAccess(connect).selectAllRooms()
                    println("ALL ROOMS:")
                    for (i in 0 until rooms.size) {
                        println("${rooms[i].id}. ${rooms[i].name} (${rooms[i].type})")
                    }
                    print("Input ID of the room, you want to book: ")
                    var tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    try {
                        reh.roomId = tmp!!.toInt()
                    }
                    catch (e: NumberFormatException) {
                        println("Invalid input")
                        continue
                    }
                    print("Rehearsal hour: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    try {
                        reh.time = Time(tmp!!.toInt(), 0, 0)
                    }
                    catch (e: NumberFormatException) {
                        println("Invalid input")
                        continue
                    }
                    reh.musicianId = acc.id

                    MusicianActs(connect).bookReh(reh)
                    println("REHEARSAL BOOKED SUCCESSFULLY")
                }
                4 -> {
                    val rehs = RehActs(connect).rehsByAcc(acc.id)
                    val rooms = PostgresAccess(connect).selectAllRooms()
                    println("YOUR BOOKED REHEARSALS:")
                    for (i in 0 until rehs.size) {
                        println("${rooms[rehs[i].roomId-1].name} - ${rehs[i].time}")
                    }
                }
                5 -> {
                    val rehs = RehActs(connect).rehsByAcc(acc.id)
                    val rooms = PostgresAccess(connect).selectAllRooms()
                    var rehId: Int
                    println("YOUR BOOKED REHEARSALS:")
                    for (i in 0 until rehs.size) {
                        println("${rehs[i].id}. ${rooms[rehs[i].roomId-1].name} - " +
                                "${rehs[i].time}")
                    }
                    print("Choose rehearsal, you want to cancel: ")
                    val tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    try {
                        rehId = tmp!!.toInt()
                    }
                    catch (e: NumberFormatException) {
                        println("Invalid input")
                        continue
                    }

                    MusicianActs(connect).cancelReh(rehId)
                    println("REHEARSAL CANCELED SUCCESSFULLY")
                }
                6 -> option = owner(acc)
                7 -> {
                    val accs = PostgresAccess(connect).selectAllAccs()
                    println("GOING TO ACC SETTINGS")
                    val cur_acc = accs[acc.id - 1]
                    println("Current state: ${cur_acc.fio} ${cur_acc.mail} " +
                            "${cur_acc.phone} ${cur_acc.password}")
                    print("Want to change? (Y/n) ")
                    if (readLine() == "n")
                        continue
                    print("New phone: ")
                    var tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    cur_acc.phone = tmp
                    print("New mail: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    cur_acc.mail = tmp
                    print("New password: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    cur_acc.password = tmp

                    AccActs(connect).save(cur_acc)
                    println("ACC CHANGED SUCCESSFULLY")
                }
                8 -> {
                    AccActs(connect).delete(acc.id)
                    println("ACC DELETED SUCCESSFULLY")
                    welcome()
                }
                9 -> welcome()
            }
        }

        return option
    }
    private fun owner(acc: Account): Int? {
        while (option != 0) {
            println(owner_menu)
            print("> ")
            try {
                option = readLine()?.toInt()
            }
            catch (e: NumberFormatException) {
                println("Invalid option")
                option = -1
                continue
            }
            if (option == null) {
                option = -1
                println("Invalid input")
                continue
            }

            when (option) {
                0 -> break
                10 -> {
                    val bases = RehBaseActs(connect).basesByAcc(acc.id)
                    var baseId: Int
                    println("YOUR BASES:")
                    for (i in 0 until bases.size) {
                        println("${bases[i].id}. ${bases[i].name}")
                    }
                    print("Choose base: ")
                    val tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    try {
                        baseId = tmp!!.toInt()
                    }
                    catch (e: NumberFormatException) {
                        println("Invalid input")
                        continue
                    }

                    val rehs = OwnerActs(connect).allRehs(baseId)
                    val rooms = PostgresAccess(connect).selectAllRooms()
                    val accs = PostgresAccess(connect).selectAllAccs()
                    println("BOOKED REHEARSALS ON THIS BASE:")
                    for (i in 0 until rehs.size) {
                        println("${rooms[rehs[i].roomId-1].name} ${rehs[i].time} " +
                                "${accs[rehs[i].musicianId-1].fio}")
                    }
                }
                11 -> {
                    println("ADDING NEW BASE")
                    val base = RehearsalBase()
                    print("Name: ")
                    var tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    base.name = tmp
                    print("Address: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    base.address = tmp
                    print("Phone: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    base.phone = tmp
                    print("Mail: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    base.mail = tmp
                    base.ownerId = acc.id

                    var cnt: Int
                    print("How many rooms? ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    try {
                        cnt = tmp!!.toInt()
                    }
                    catch (e: NumberFormatException) {
                        println("Invalid input")
                        continue
                    }
                    for (i in 1..cnt) {
                        println("ADDING ROOM $i")
                        val room = Room()
                        print("Name: ")
                        tmp = readLine()
                        if (check_input(tmp)) {
                            println("Invalid input")
                            continue
                        }
                        room.name = tmp
                        print("Type: ")
                        tmp = readLine()
                        if (check_input(tmp)) {
                            println("Invalid input")
                            continue
                        }
                        room.type = tmp
                        print("Area: ")
                        tmp = readLine()
                        if (check_input(tmp)) {
                            println("Invalid input")
                            continue
                        }
                        room.area = tmp!!.toInt()
                        print("Cost: ")
                        tmp = readLine()
                        if (check_input(tmp)) {
                            println("Invalid input")
                            continue
                        }
                        room.cost = tmp!!.toInt()

                        OwnerActs(connect).saveBase(base, room)
                    }

                    println("BASE REGISTERED SUCCESSFULLY")
                }
                12 -> {
                    println("BASES SETTINGS")

                    val bases = RehBaseActs(connect).basesByAcc(acc.id)
                    var baseId: Int
                    println("YOUR BASES:")
                    for (i in 0 until bases.size) {
                        println("${bases[i].id}. ${bases[i].name}")
                    }
                    print("Choose base: ")
                    var tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    try {
                        baseId = tmp!!.toInt()
                    }
                    catch (e: NumberFormatException) {
                        println("Invalid input")
                        continue
                    }

                    println("GOING TO BASE $baseId SETTINGS")
                    val base = RehBaseActs(connect).getBase(baseId)
                    val room = Room()
                    println("Current state: ${base.name} ${base.address} " +
                            "${base.phone} ${base.mail}")
                    print("Want to change? (Y/n) ")
                    if (readLine() == "n")
                        continue
                    print("New name: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    base.name = tmp
                    print("New address: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    base.address = tmp
                    print("New phone: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    base.phone = tmp
                    print("New mail: ")
                    tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    base.mail = tmp

                    OwnerActs(connect).saveBase(base, room)
                    println("BASE CHANGED SUCCESSFULLY")
                }
                13 -> {
                    val bases = RehBaseActs(connect).basesByAcc(acc.id)
                    var baseId: Int
                    println("YOUR BASES:")
                    for (i in 0 until bases.size) {
                        println("${bases[i].id}. ${bases[i].name}")
                    }
                    print("Choose base, you want to delete: ")
                    val tmp = readLine()
                    if (check_input(tmp)) {
                        println("Invalid input")
                        continue
                    }
                    try {
                        baseId = tmp!!.toInt()
                    }
                    catch (e: NumberFormatException) {
                        println("Invalid input")
                        continue
                    }

                    OwnerActs(connect).delBase(baseId)
                    println("BASE $baseId DELETED SUCCESSFULLY")
                }
                14 -> option = musician(acc)
            }
        }

        return option
    }
}