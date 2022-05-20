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
    fun delByAcc(accId: Int) {
        var exists: Boolean = false
        for (b in bases)
            if (b.ownerId == accId) {
                exists = true
                break
            }
        if (exists)
            println("deleting bases by acc...")
        else
            println("no bases by this acc")
    }
    fun getBase(baseId: Int): RehearsalBase {
        println("selecting base...")
        return RehearsalBase()
    }
    fun getAllBases(): MutableList<RehearsalBase> {
        println("selecting all bases...")
        return bases
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
    fun delByAcc(accId: Int) {
        RoomActs().delByAcc(accId)
        rep.delByAcc(accId)
    }
    fun getBase(baseId: Int): RehearsalBase {
        return rep.getBase(baseId)
    }
    fun allBases(): MutableList<RehearsalBase> {
        return rep.getAllBases()
    }
}