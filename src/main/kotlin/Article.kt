
// Article
data class Article(
    val id : Int,
    val memberId : Int,
    val boardId : Int,
    var title : String,
    var body : String,
    val regDate : String,
    var updateDate : String
){
    fun toJson() : String{
        var jsonStr = ""
        jsonStr += "{"
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "id":$id """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "memberId":$memberId """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "boardId":$boardId """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "title":"$title" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "body":"$body" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "regDate":"$regDate" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "updateDate":"$updateDate" """.trim()
        jsonStr += "\r\n"
        jsonStr += "}"

        return jsonStr
    }
}