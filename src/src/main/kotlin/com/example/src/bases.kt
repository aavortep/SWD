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
        var ind = 0
        for (b in bases) {
            if (b.id == base.id) {
                exists = true
                break
            }
            ++ind
        }
        if (exists) {
            println("updating base...")
            bases[ind] = base
            PostgresAccess().update(base)
        }
        else {
            println("inserting base...")
            bases.add(base)
            PostgresAccess().insert(base)
        }
    }
    fun deleteBase(baseId: Int) {
        println("deleting base...")
        for (ind in bases.size - 1 downTo 0) {
            if (bases[ind].id == baseId) {
                bases.removeAt(ind)
                break
            }
        }
        PostgresAccess().deleteBase(baseId)
    }
    fun delByAcc(accId: Int) {
        println("deleting bases by acc...")
        for (ind in bases.size - 1 downTo 0) {
            if (bases[ind].ownerId == accId) {
                bases.removeAt(ind)
            }
        }
        PostgresAccess().deleteBasesByAcc(accId)
    }
    fun getBase(baseId: Int): RehearsalBase {
        println("selecting base...")
        return PostgresAccess().selectBase(baseId)
    }
    fun getAllBases(): MutableList<RehearsalBase> {
        println("selecting all bases...")
        return PostgresAccess().selectAllBases()
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