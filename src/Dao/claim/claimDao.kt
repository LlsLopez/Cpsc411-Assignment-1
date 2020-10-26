package org.csuf.cspc411.Dao.claim

import org.csuf.cspc411.Dao.Dao
import org.csuf.cspc411.Dao.Database

class claimDao: Dao() {

    fun addClaim(cObj : claim){
        val conn = Database.getInstance()?.getDbConnection()

        sqlStmt = "insert into claim (Id, Title, Date, isSolved) values ('${cObj.id}','${cObj.title}','${cObj.date}','${cObj.isSolved}')"

        conn?.exec(sqlStmt)
    }

    fun getAll() : List<claim> {
        val conn = Database.getInstance()?.getDbConnection()

        sqlStmt = "select id, title, date, isSolved from claim"

        var claimList : MutableList<claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        while(st?.step()!!){
            val cId = st.columnString(0)
            val cTitle = st.columnString(1)
            val cDate = st.columnString(2)
            val cIsSolved = st.columnInt(3)
            claimList.add(claim(cId,cTitle,cDate,cIsSolved))
        }
        return claimList
    }


}