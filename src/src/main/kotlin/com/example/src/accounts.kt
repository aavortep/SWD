package com.example.src

import java.sql.Connection

class Account
{
    var id: Int = -1
    var fio: String? = null
    var phone: String? = null
    var mail: String? = null
    var password: String? = null
}

class AccRepository(val connect: Connection?)
{
    private val accs = PostgresAccess(connect).selectAllAccs()

    fun saveAcc(acc: Account) {
        var exists: Boolean = false
        var ind = 0
        for (a in accs) {
            if (a.id == acc.id) {
                exists = true
                break
            }
            ++ind
        }
        if (exists) {
            println("updating account...")
            accs[ind] = acc
            PostgresAccess(connect).update(acc)
        }
        else {
            println("inserting account...")
            accs.add(acc)
            for (a in accs) {
                println(a.id)
                println(a.fio)
                println(a.phone)
                println(a.mail)
                println(a.password)
            }
            PostgresAccess(connect).insert(acc)
        }
    }
    fun deleteAcc(accId: Int) {
        println("deleting account...")
        var ind = 0
        for (a in accs) {
            if (a.id == accId) {
                accs.removeAt(ind)
                break
            }
            ++ind
        }
        PostgresAccess(connect).deleteAcc(accId)
    }
}

open class AccActs(private final val connect: Connection?)
{
    private val rep = AccRepository(connect)

    fun save(acc: Account) {
        rep.saveAcc(acc)
    }
    fun delete(accId: Int) {
        RehActs(connect).delByAcc(accId)
        RehBaseActs(connect).delByAcc(accId)
        rep.deleteAcc(accId)
    }
    fun cancelReh(rehId: Int) {
        RehActs(connect).cancel(rehId)
    }
}

class MusicianActs(val connect: Connection?) : AccActs(connect)
{
    fun bookReh(reh: Rehearsal) {
        RehActs(connect).book(reh)
    }
    fun checkReh(rehId: Int): Rehearsal {
        return RehActs(connect).getReh(rehId)
    }
}

class OwnerActs(val connect: Connection?) : AccActs(connect)
{
    fun saveBase(base: RehearsalBase, room: Room) {
        RehBaseActs(connect).save(base, room)
    }
    fun delBase(baseId: Int) {
        RehBaseActs(connect).delete(baseId)
    }
    fun delRoom(roomId: Int) {
        RoomActs(connect).delete(roomId)
    }
    fun allRehs(baseId: Int): MutableList<Rehearsal> {
        return RehActs(connect).allRehs(baseId)
    }
}

