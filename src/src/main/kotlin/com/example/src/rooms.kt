package com.example.src

class Room
{
    var id: Int = -1
    var name: String? = null
    var type: String? = null
    var area: Int = -1
    var cost: Int = -1
    var baseId: Int = -1
}

class RoomRepository
{
    private val rooms = mutableListOf<Room>()

    fun saveRoom(room: Room) {
        var exists: Boolean = false
        var ind = 0
        for (r in rooms) {
            if (r.id == room.id) {
                exists = true
                break
            }
            ++ind
        }
        if (exists) {
            println("updating room...")
            rooms[ind] = room
            PostgresAccess().update(room)
        }
        else {
            println("inserting room...")
            rooms.add(room)
            PostgresAccess().insert(room)
        }
    }
    fun deleteRoom(roomId: Int) {
        println("deleting room...")
        var ind = 0
        for (r in rooms) {
            if (r.id == roomId) {
                rooms.removeAt(ind)
                break
            }
            ++ind
        }
        PostgresAccess().deleteRoom(roomId)
    }
    fun delByBase(baseId: Int) {
        println("deleting rooms by base...")
        for (ind in rooms.size - 1 downTo 0) {
            if (rooms[ind].baseId == baseId) {
                rooms.removeAt(ind)
            }
        }
        PostgresAccess().deleteRoomsByBase(baseId)
    }
    fun delByAcc(accId: Int) {
        println("deleting rooms by acc...")
        val basesId = mutableListOf<Int>()
        val bases = PostgresAccess().selectAllBases()
        for (b in bases){
            if (b.ownerId == accId)
                basesId.add(b.id)
        }
        for (i in 0..basesId.size) {
            for (ind in rooms.size - 1 downTo 0) {
                if (rooms[ind].baseId == basesId[i]) {
                    rooms.removeAt(ind)
                }
            }
        }
        PostgresAccess().deleteBasesByAcc(accId)
    }
}

class RoomActs
{
    private val rep = RoomRepository()

    fun save(room: Room) {
        rep.saveRoom(room)
    }
    fun delete(roomId: Int) {
        rep.deleteRoom(roomId)
    }
    fun delByBase(baseId: Int) {
        rep.delByBase(baseId)
    }
    fun delByAcc(accId: Int) {
        rep.delByAcc(accId)
    }
}