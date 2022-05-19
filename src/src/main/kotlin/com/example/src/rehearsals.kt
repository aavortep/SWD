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
        println("deleting rehearsals by account...")
    }
    fun getRehearsal(rehId: Int) {
        println("selecting rehearsal...")
    }
    fun getAllRehs(baseId: Int) {
        println("selecting rehearsals by base...")
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
    fun getReh(rehId: Int) {
        rep.getRehearsal(rehId)
    }
    fun allRehs(baseId: Int) {
        rep.getAllRehs(baseId)
    }
}