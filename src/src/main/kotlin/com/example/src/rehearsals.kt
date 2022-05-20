package com.example.src

import java.sql.Time

class Rehearsal
{
    var id: Int = -1
    var musicianId: Int = -1
    var time: Time? = null
    var roomId: Int = -1
}

class RehearsalRepository
{
    private val rehearsals = mutableListOf<Rehearsal>()

    fun addRehearsal(reh: Rehearsal) {
        println("inserting rehearsal...")
        rehearsals.add(reh)
        PostgresAccess().insert(reh)
    }
    fun deleteRehearsal(rehId: Int) {
        println("deleting rehearsal...")
        var ind = 0
        for (r in rehearsals) {
            if (r.id == rehId) {
                rehearsals.removeAt(ind)
                break
            }
            ++ind
        }
        PostgresAccess().deleteReh(rehId)
    }
    fun delByAcc(accId: Int) {
        println("deleting rehearsals by acc...")
        for (ind in rehearsals.size - 1 downTo 0) {
            if (rehearsals[ind].musicianId == accId) {
                rehearsals.removeAt(ind)
            }
        }
        PostgresAccess().deleteRehsByAcc(accId)
    }
    fun getRehearsal(rehId: Int): Rehearsal {
        println("selecting rehearsal...")
        return PostgresAccess().selectReh(rehId)
    }
    fun getAllRehs(baseId: Int): MutableList<Rehearsal> {
        println("selecting rehearsals by base...")
        return PostgresAccess().selectAllRehs(baseId)
    }
}

class RehActs
{
    private val rep = RehearsalRepository()

    fun book(reh: Rehearsal) {
        rep.addRehearsal(reh)
    }
    fun cancel(rehId: Int) {
        rep.deleteRehearsal(rehId)
    }
    fun delByAcc(accId: Int) {
        rep.delByAcc(accId)
    }
    fun getReh(rehId: Int): Rehearsal {
        return rep.getRehearsal(rehId)
    }
    fun allRehs(baseId: Int): MutableList<Rehearsal> {
        return rep.getAllRehs(baseId)
    }
}