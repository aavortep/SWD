package com.example.src
import java.sql.*


interface DBAccess {
    fun connect(): Connection?

    fun insert(acc: Account)
    fun insert(base: RehearsalBase)
    fun insert(room: Room)
    fun insert(reh: Rehearsal)

    fun update(acc: Account)
    fun update(base: RehearsalBase)
    fun update(room: Room)

    fun deleteAcc(accId: Int)
    fun deleteBase(baseId: Int)
    fun deleteBasesByAcc(accId: Int)
    fun deleteRoom(roomId: Int)
    fun deleteRoomsByBase(baseId: Int)
    fun deleteRoomsByAcc(accId: Int)
    fun deleteReh(rehId: Int)
    fun deleteRehsByAcc(accId: Int)

    fun selectBase(baseId: Int): RehearsalBase
    fun selectAllBases(): MutableList<RehearsalBase>
    fun selectReh(rehId: Int): Rehearsal
    fun selectAllRehs(baseId: Int): MutableList<Rehearsal>
}

class PostgresAccess(var connection: Connection? = null) : DBAccess {
    private val user = "postgres"
    private val pass = "LinkinPark20"
    private var url = "jdbc:postgresql://localhost:5432/HearBase"

    private var prepStat: PreparedStatement? = null

    override fun connect(): Connection? {
        try {
            Class.forName("org.postgresql.Driver")
            connection = DriverManager.getConnection(url, user, pass)
            println("Successful connection")
            return connection
        } catch (e: Exception) {
            connection = null
            print(e.message)
            e.printStackTrace()
        }
        return null
    }

    override fun insert(acc: Account) {
        prepStat = connection?.prepareStatement("INSERT INTO account " +
                "VALUES (?, ?, ?, ?, ?)")
        prepStat?.setInt(1, acc.id)
        prepStat?.setString(2, acc.fio)
        prepStat?.setString(3, acc.phone)
        prepStat?.setString(4, acc.mail)
        prepStat?.setString(5, acc.password)
        prepStat?.executeUpdate()

        println("Account inserted successfully")
    }
    override fun insert(base: RehearsalBase) {
        prepStat = connection?.prepareStatement("INSERT INTO reh_base " +
                "VALUES (?, ?, ?, ?, ?, ?)")
        prepStat?.setInt(1, base.id)
        prepStat?.setInt(2, base.ownerId)
        prepStat?.setString(3, base.name)
        prepStat?.setString(4, base.address)
        prepStat?.setString(5, base.phone)
        prepStat?.setString(6, base.mail)
        prepStat?.executeUpdate()

        println("Base inserted successfully")
    }
    override fun insert(room: Room) {
        prepStat = connection?.prepareStatement("INSERT INTO room " +
                "VALUES (?, ?, ?, ?, ?, ?)")
        prepStat?.setInt(1, room.id)
        prepStat?.setInt(2, room.baseId)
        prepStat?.setString(3, room.name)
        prepStat?.setString(4, room.type)
        prepStat?.setInt(5, room.area)
        prepStat?.setInt(6, room.cost)
        prepStat?.executeUpdate()

        println("Room inserted successfully")
    }
    override fun insert(reh: Rehearsal) {
        prepStat = connection?.prepareStatement("INSERT INTO rehearsal " +
                "VALUES (?, ?, ?, ?)")
        prepStat?.setInt(1, reh.id)
        prepStat?.setInt(2, reh.musicianId)
        prepStat?.setInt(3, reh.roomId)
        prepStat?.setTime(4, reh.time)
        prepStat?.executeUpdate()

        println("Rehearsal inserted successfully")
    }

    override fun update(acc: Account) {
        prepStat = connection?.prepareStatement("UPDATE account " +
                                                    "SET (fio, phone, mail, password) = (?, ?, ?, ?) " +
                                                    "WHERE id = ?")
        prepStat?.setInt(5, acc.id)
        prepStat?.setString(1, acc.fio)
        prepStat?.setString(2, acc.phone)
        prepStat?.setString(3, acc.mail)
        prepStat?.setString(4, acc.password)
        prepStat?.executeUpdate()

        println("Account updated successfully")
    }
    override fun update(base: RehearsalBase) {
        prepStat = connection?.prepareStatement("UPDATE reh_base " +
                                                    "SET name = ?, address = ?, phone = ?, mail = ? " +
                                                    "WHERE id = ?")
        prepStat?.setInt(5, base.id)
        prepStat?.setString(1, base.name)
        prepStat?.setString(2, base.address)
        prepStat?.setString(3, base.phone)
        prepStat?.setString(4, base.mail)
        prepStat?.executeUpdate()

        println("Base updated successfully")
    }
    override fun update(room: Room) {
        prepStat = connection?.prepareStatement("UPDATE room " +
                                                    "SET name = ?, type = ?, area = ?, cost = ? " +
                                                    "WHERE id = ?")
        prepStat?.setInt(5, room.id)
        prepStat?.setString(1, room.name)
        prepStat?.setString(2, room.type)
        prepStat?.setInt(3, room.area)
        prepStat?.setInt(4, room.cost)
        prepStat?.executeUpdate()

        println("Room updated successfully")
    }

    override fun deleteAcc(accId: Int) {
        prepStat = connection?.prepareStatement("DELETE FROM account WHERE id = ?")
        prepStat?.setInt(1, accId)
        prepStat?.executeUpdate()

        println("Account deleted successfully")
    }
    override fun deleteBase(baseId: Int) {
        prepStat = connection?.prepareStatement("DELETE FROM reh_base WHERE id = ?")
        prepStat?.setInt(1, baseId)
        prepStat?.executeUpdate()

        println("Base deleted successfully")
    }
    override fun deleteBasesByAcc(accId: Int) {
        prepStat = connection?.prepareStatement("DELETE FROM reh_base WHERE ownerid = ?")
        prepStat?.setInt(1, accId)
        prepStat?.executeUpdate()

        println("Bases of acc $accId deleted successfully")
    }
    override fun deleteReh(rehId: Int) {
        prepStat = connection?.prepareStatement("DELETE FROM rehearsal WHERE id = ?")
        prepStat?.setInt(1, rehId)
        prepStat?.executeUpdate()

        println("Rehearsal deleted successfully")
    }
    override fun deleteRehsByAcc(accId: Int) {
        prepStat = connection?.prepareStatement("DELETE FROM rehearsal WHERE musicianid = ?")
        prepStat?.setInt(1, accId)
        prepStat?.executeUpdate()

        println("Rehearsals of acc $accId deleted successfully")
    }
    override fun deleteRoom(roomId: Int) {
        prepStat = connection?.prepareStatement("DELETE FROM room WHERE id = ?")
        prepStat?.setInt(1, roomId)
        prepStat?.executeUpdate()

        println("Room deleted successfully")
    }
    override fun deleteRoomsByAcc(accId: Int) {
        prepStat = connection?.prepareStatement("DELETE FROM room WHERE baseid IN " +
                                                    "(SELECT reh_base.id FROM reh_base WHERE reh_base.ownerid = ?)")
        prepStat?.setInt(1, accId)
        prepStat?.executeUpdate()

        println("Rooms of account $accId deleted successfully")
    }
    override fun deleteRoomsByBase(baseId: Int) {
        prepStat = connection?.prepareStatement("DELETE FROM room WHERE baseid = ?")
        prepStat?.setInt(1, baseId)
        prepStat?.executeUpdate()

        println("Rooms of base $baseId deleted successfully")
    }

    override fun selectBase(baseId: Int): RehearsalBase {
        val base = RehearsalBase()
        prepStat = connection?.prepareStatement("SELECT * FROM reh_base WHERE id = ?")
        prepStat?.setInt(1, baseId)
        val res = prepStat?.executeQuery() ?: return base
        while (res.next()) {
            base.id = res.getInt("id")
            base.ownerId = res.getInt("ownerid")
            base.name = res.getString("name")
            base.address = res.getString("address")
            base.phone = res.getString("phone")
            base.mail = res.getString("mail")

            println("Selected base:")
            println("ID: " + base.id + "\n" +
                    "ownerID: " + base.ownerId + "\n" +
                    "name: " + base.name + "\n" +
                    "address: " + base.address + "\n" +
                    "phone: " + base.phone + "\n" +
                    "mail: " + base.mail)
        }

        return base
    }
    override fun selectAllBases(): MutableList<RehearsalBase> {
        val bases = mutableListOf<RehearsalBase>()
        prepStat = connection?.prepareStatement("SELECT * FROM reh_base")
        println("All bases:")
        val res = prepStat?.executeQuery() ?: return bases
        while (res.next()) {
            val base = RehearsalBase()
            base.id = res.getInt("id")
            base.ownerId = res.getInt("ownerid")
            base.name = res.getString("name")
            base.address = res.getString("address")
            base.phone = res.getString("phone")
            base.mail = res.getString("mail")
            bases.add(base)

            println("ID: " + base.id + "\n" +
                    "ownerID: " + base.ownerId + "\n" +
                    "name: " + base.name + "\n" +
                    "address: " + base.address + "\n" +
                    "phone: " + base.phone + "\n" +
                    "mail: " + base.mail)
        }
        return bases
    }
    fun selectBases(): MutableList<RehearsalBase> {
        val bases = mutableListOf<RehearsalBase>()
        prepStat = connection?.prepareStatement("SELECT * FROM reh_base")
        val res = prepStat?.executeQuery() ?: return bases
        while (res.next()) {
            val base = RehearsalBase()
            base.id = res.getInt("id")
            base.ownerId = res.getInt("ownerid")
            base.name = res.getString("name")
            base.address = res.getString("address")
            base.phone = res.getString("phone")
            base.mail = res.getString("mail")
            bases.add(base)
        }
        return bases
    }
    override fun selectReh(rehId: Int): Rehearsal {
        val reh = Rehearsal()
        prepStat = connection?.prepareStatement("SELECT * FROM rehearsal WHERE id = ?")
        prepStat?.setInt(1, rehId)
        val res = prepStat?.executeQuery() ?: return reh
        while (res.next()) {
            reh.id = res.getInt("id")
            reh.musicianId = res.getInt("musicianid")
            reh.roomId = res.getInt("roomid")
            reh.time = res.getTime("rehtime")

            println("Selected rehearsal:")
            println("ID: " + reh.id + "\n" +
                    "musicianID: " + reh.musicianId + "\n" +
                    "roomID: " + reh.roomId + "\n" +
                    "time: " + reh.time)
        }

        return reh
    }
    override fun selectAllRehs(baseId: Int): MutableList<Rehearsal> {
        val rehs = mutableListOf<Rehearsal>()
        prepStat = connection?.prepareStatement("SELECT * FROM rehearsal WHERE roomid IN " +
                                                    "(SELECT id FROM room WHERE baseid = ?)")
        prepStat?.setInt(1, baseId)
        println("Selected rehearsals:")
        val res = prepStat?.executeQuery() ?: return rehs
        while (res.next()) {
            val reh = Rehearsal()
            reh.id = res.getInt("id")
            reh.musicianId = res.getInt("musicianid")
            reh.roomId = res.getInt("roomid")
            reh.time = res.getTime("rehtime")
            rehs.add(reh)

            println("ID: " + reh.id + "\n" +
                    "musicianID: " + reh.musicianId + "\n" +
                    "roomID: " + reh.roomId + "\n" +
                    "time: " + reh.time)
        }
        return rehs
    }
    fun selectAllRehs(): MutableList<Rehearsal> {
        val rehs = mutableListOf<Rehearsal>()
        prepStat = connection?.prepareStatement("SELECT * FROM rehearsal")
        val res = prepStat?.executeQuery() ?: return rehs
        while (res.next()) {
            val reh = Rehearsal()
            reh.id = res.getInt("id")
            reh.musicianId = res.getInt("musicianid")
            reh.roomId = res.getInt("roomid")
            reh.time = res.getTime("rehtime")
            rehs.add(reh)
        }
        return rehs
    }
    fun selectAllAccs(): MutableList<Account> {
        val accs = mutableListOf<Account>()
        prepStat = connection?.prepareStatement("SELECT * FROM account")
        val res = prepStat?.executeQuery() ?: return accs
        while (res.next()) {
            val acc = Account()
            acc.id = res.getInt("id")
            acc.fio = res.getString("fio")
            acc.phone = res.getString("phone")
            acc.mail = res.getString("mail")
            acc.password = res.getString("password")
            accs += acc
        }
        return accs
    }
    fun selectAllRooms(): MutableList<Room> {
        val rooms = mutableListOf<Room>()
        prepStat = connection?.prepareStatement("SELECT * FROM room")
        val res = prepStat?.executeQuery() ?: return rooms
        while (res.next()) {
            val room = Room()
            room.id = res.getInt("id")
            room.baseId = res.getInt("baseid")
            room.name = res.getString("name")
            room.type = res.getString("type")
            room.area = res.getInt("area")
            room.cost = res.getInt("cost")
            rooms.add(room)
        }
        return rooms
    }
}