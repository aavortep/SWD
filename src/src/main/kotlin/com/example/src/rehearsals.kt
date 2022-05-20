package com.example.src

class Rehearsal
{
    var id: Int = -1
    var musicianId: Int = -1
    var time: String? = null
    var roomId: Int = -1
}

class RehearsalRepository
{
    private val rehearsals = mutableListOf<Rehearsal>()

    fun addRehearsal(reh: Rehearsal) {
        println("inserting rehearsal...")
    }
    fun deleteRehearsal(rehId: Int) {
        println("deleting rehearsal...")
    }
    fun delByAcc(accId: Int) {
        var exists: Boolean = false
        for (r in rehearsals)
            if (r.musicianId == accId) {
                exists = true
                break
            }
        if (exists)
            println("deleting rehearsals by acc...")
        else
            println("no rehearsals by this acc")
    }
    fun getRehearsal(rehId: Int): Rehearsal {
        println("selecting rehearsal...")
        return Rehearsal()
    }
    fun getAllRehs(baseId: Int): MutableList<Rehearsal> {
        println("selecting rehearsals by base...")
        return rehearsals
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