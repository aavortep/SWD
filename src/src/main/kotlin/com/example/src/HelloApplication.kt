package com.example.src

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.sql.Time

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

fun test_business() {
    TestBusiness().createAcc()
    TestBusiness().showBases()
    TestBusiness().getBase()
    TestBusiness().bookReh()
    TestBusiness().checkReh()
    TestBusiness().cancelReh()
    TestBusiness().regBase()
    TestBusiness().allRehs()
    TestBusiness().delRoom()
    TestBusiness().delBase()
    TestBusiness().delAcc()
}

fun test_access(): Int {
    //if (PostgresAccess().connect() == 1)
        //return 1

    val acc = Account()
    acc.id = 1
    acc.fio = "Петрова А А"
    acc.phone = "89169885354"
    acc.mail = "nura.alexevna@yandex.ru"
    acc.password = "LinkinPark20"
    TestAccess().createAcc(acc)
    acc.id = 2
    acc.fio = "Хилькевич Д А"
    acc.phone = "89876543210"
    acc.mail = "qwerty@mail.ru"
    acc.password = "1234"
    TestAccess().createAcc(acc)

    val base = RehearsalBase()
    val room = Room()
    base.id = 1
    base.name = "Highgain Studio"
    base.address = "Волгоградский проспект"
    base.phone = "89123456789"
    base.mail = "abcd@gmail.com"
    base.ownerId = 1
    room.id = 1
    room.name = "Yellow"
    room.area = 23
    room.cost = 1500
    room.type = "band"
    room.baseId = 1
    TestAccess().regBase(base, room)
    room.id = 2
    room.name = "Black"
    room.area = 35
    room.cost = 1500
    room.type = "band"
    room.baseId = 1
    TestAccess().regBase(base, room)

    TestAccess().showBases()
    TestAccess().getBase(1)

    val reh = Rehearsal()
    reh.id = 1
    reh.musicianId = 1
    reh.roomId = 1
    reh.time = Time(12, 0, 0)
    TestAccess().bookReh(reh)
    TestAccess().checkReh(1)
    reh.id = 2
    reh.musicianId = 2
    reh.roomId = 2
    reh.time = Time(17, 0, 0)
    TestAccess().bookReh(reh)
    TestAccess().allRehs(1)
    TestAccess().cancelReh(2)

    TestAccess().delAcc(1)

    return 0
}

fun main() {
    //Application.launch(HelloApplication::class.java)

    test_access()
}