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
        for (r in rooms)
            if (r.id == room.id) {
                exists = true
                break
            }
        if (exists)
            println("updating room...")
        else
            println("inserting room...")
    }
    fun deleteRoom(roomId: Int) {
        println("deleting room...")
    }
    fun delByBase(baseId: Int) {
        println("deleting rooms by base...")
    }
    fun delByAcc(accId: Int) {
        println("deleting rooms by acc...")
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