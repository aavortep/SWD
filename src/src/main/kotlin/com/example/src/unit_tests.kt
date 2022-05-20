package com.example.src

import java.sql.Time

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
        reh.time = Time(12, 0, 0)
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

class TestAccess
{
    fun createAcc(acc: Account) {
        MusicianActs().save(acc)
    }
    fun delAcc(accId: Int) {
        MusicianActs().delete(accId)
    }
    fun showBases() {
        RehBaseActs().allBases()
    }
    fun getBase(baseId: Int) {
        RehBaseActs().getBase(baseId)
    }
    fun bookReh(reh: Rehearsal) {
        MusicianActs().bookReh(reh)
    }
    fun cancelReh(rehId: Int) {
        MusicianActs().cancelReh(rehId)
    }
    fun checkReh(rehId: Int) {
        MusicianActs().checkReh(rehId)
    }
    fun regBase(base: RehearsalBase, room: Room) {
        OwnerActs().saveBase(base, room)
    }
    fun delBase(baseId: Int) {
        OwnerActs().delBase(baseId)
    }
    fun allRehs(baseId: Int) {
        OwnerActs().allRehs(baseId)
    }
    fun delRoom(roomId: Int) {
        OwnerActs().delRoom(roomId)
    }
}