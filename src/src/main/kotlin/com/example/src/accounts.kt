package com.example.src

import java.util.LinkedList

class Account
{
    var id: Int = -1
    var fio: String? = null
    var phone: String? = null
    var mail: String? = null
    var password: String? = null
}

class AccRepository
{
    private val accs = mutableListOf<Account>()

    fun saveAcc(acc: Account) {
        var exists: Boolean = false
        for (a in accs)
            if (a.id == acc.id) {
                exists = true
                break
            }
        if (exists)
            println("updating account...")
        else {
            println("inserting account...")
        }
    }
    fun deleteAcc(accId: Int) {
        println("deleting account...")
    }
}

open class AccActs
{
    private val rep = AccRepository()

    fun save(acc: Account) {
        rep.saveAcc(acc)
    }
    fun delete(accId: Int) {
        RehActs().delByAcc(accId)
        RehBaseActs().delByAcc(accId)
        rep.deleteAcc(accId)
    }
    fun cancelReh(rehId: Int) {
        RehActs().cancel(rehId)
    }
}

class MusicianActs : AccActs()
{
    fun bookReh(reh: Rehearsal) {
        RehActs().book(reh)
    }
    fun checkReh(rehId: Int): Rehearsal {
        return RehActs().getReh(rehId)
    }
}

class OwnerActs : AccActs()
{
    fun saveBase(base: RehearsalBase, room: Room) {
        RehBaseActs().save(base, room)
    }
    fun delBase(baseId: Int) {
        RehBaseActs().delete(baseId)
    }
    fun delRoom(roomId: Int) {
        RoomActs().delete(roomId)
    }
    fun allRehs(baseId: Int): MutableList<Rehearsal> {
        return RehActs().allRehs(baseId)
    }
}

