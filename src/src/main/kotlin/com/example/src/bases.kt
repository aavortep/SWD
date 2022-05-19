package com.example.src

class RehearsalBase
{
    var id: Int = -1
    var name: String? = null
    var address: String? = null
    var phone: String? = null
    var mail: String? = null
    var ownerId: Int = -1
}

class RehBaseRepository
{
    private val bases = mutableListOf<RehearsalBase>()

    fun saveBase(base: RehearsalBase) {
        var exists: Boolean = false
        for (b in bases)
            if (b.id == base.id) {
                exists = true
                break
            }
        if (exists)
            println("updating base...")
        else
            println("inserting base...")
    }
    fun deleteBase(baseId: Int) {
        println("deleting base...")
    }
    fun getBase(baseId: Int) {
        println("selecting base...")
    }
    fun getAllBases() {
        println("selecting all bases...")
    }
}

class RehBaseActs
{
    private val rep = RehBaseRepository()

    fun save(base: RehearsalBase, room: Room) {
        RoomActs().save(room)
        rep.saveBase(base)
    }
    fun delete(baseId: Int) {
        RoomActs().delByBase(baseId)
        rep.deleteBase(baseId)
    }
    fun getBase(baseId: Int) {
        rep.getBase(baseId)
    }
    fun allBases() {
        rep.getAllBases()
    }
}