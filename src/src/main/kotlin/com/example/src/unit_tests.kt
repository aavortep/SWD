package com.example.src

class TestBusiness
{
    private var acc = Account()
    private var base = RehearsalBase()
    private var room = Room()
    private var reh = Rehearsal()

    fun createAcc() {
        acc.id = 1
        acc.fio = "Петрова А А"
        acc.phone = "89169885354"
        acc.mail = "nura.alexevna@yandex.ru"
        acc.password = "LinkinPark20"
        MusicianActs().save(acc)
    }
    fun delAcc() {
        MusicianActs().delete(1)
    }
    fun showBases() {
        RehBaseActs().allBases()
    }
    fun getBase() {
        RehBaseActs().getBase(3)
    }
    fun bookReh() {
        reh.id = 1
        reh.musicianId = 1
        reh.roomId = 1
        reh.time = "12:00 - 15:00"
        MusicianActs().bookReh(reh)
    }
    fun cancelReh() {
        MusicianActs().cancelReh(1)
    }
    fun checkReh() {
        MusicianActs().checkReh(1)
    }
    fun regBase() {
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

        OwnerActs().saveBase(base, room)
    }
    fun delBase() {
        OwnerActs().delBase(1)
    }
    fun allRehs() {
        OwnerActs().allRehs(1)
    }
    fun delRoom() {
        OwnerActs().delRoom(1)
    }
}