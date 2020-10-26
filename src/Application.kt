package org.csuf.cspc411

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import org.csuf.cspc411.Dao.Database
import org.csuf.cspc411.Dao.claim.claim
import org.csuf.cspc411.Dao.claim.claimDao
import java.util.UUID.randomUUID


fun main(args: Array<String>): Unit {

    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    routing{
        this.get("/ClaimService/add"){
            println("HTTP message is using GET method with /get ")
            //val cId : String? = call.request.queryParameters["id"]

            val cId : String? = randomUUID().toString() // converts the random UUID to a string
            val cTitle : String? = call.request.queryParameters["title"]
            val cDate : String? = call.request.queryParameters["date"]
            val cIsSolved : Int? = 0 // default value is 0 (equivalent to boolean false)
            val response = String.format("cId %s, cTitle %s, cDate %s, cIsSolved %s", cId, cTitle,cDate,cIsSolved)
            //
            val cObj = claim(cId,cTitle,cDate,cIsSolved)
            call.respondText(response, status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }

        get("/ClaimService/getAll"){
            val cList = claimDao().getAll()
            println("The number of claims : ${cList.size}")
            // JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(cList)
            // display list of json claim objects:
            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType= ContentType.Application.Json)
        }
        post("/ClaimService/add"){
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)


            println("HTTP message is using POST method with /post ")
            call.respondText("The POST request was successfully processed. ",
                status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }

    }


}

