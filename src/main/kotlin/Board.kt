// Board
data class Board(
    val id : Int,
    var name : String,
    var code : String,
    val regDate : String,
    var updateDate : String
){
    fun toJson(): String{

        var jsonStr = ""

        jsonStr += "{"
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "id":$id """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "name":"$name" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "code":"$code" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "regDate":"$regDate" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "updateDate":"$updateDate" """.trim()
        jsonStr += "\r\n"
        jsonStr += "}"

        return jsonStr
    }
}