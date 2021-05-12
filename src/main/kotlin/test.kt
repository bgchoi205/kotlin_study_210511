import java.io.File

fun main(){

    //textWriteFile()
    //textWriteFile2()
    //textWriteFile3()
    //println(textReadFile())
    //textReadFile2()

}


data class TestArticle(
    val id : Int,
    val title : String,
    val body : String
) {
    fun toJson(): String {
        var jsonStr = ""
        jsonStr += "{"
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "id":$id """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "title":"$title" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "body":"$body" """.trim()
        jsonStr += "\r\n"
        jsonStr += "}"

        return jsonStr
    }
}


fun textReadFile2() {
    textWriteFile3()

    val fileContent = File("test/t3.json").readText(Charsets.UTF_8)
    // 여기까지는 아직 문장형태

    val testArticle = testArticleFromJson(fileContent) // mapFromJson 에서 map으로 바꾼 뒤, 다시 객체화

    println(testArticle)
}






fun textReadFile() : String{
    val reader = File("test/t3.json").readText(Charsets.UTF_8)
    return reader
}

fun textWriteFile3() {
    val testArticle = TestArticle(8, "제목8", "내용8")

    File("test/t3.json").writeText(testArticle.toJson())

}

fun textWriteFile2() {
    val id = 1
    val title = "제목"
    val body = "내용"

    var fileContent = ""
    fileContent += "{"
    fileContent += "\r\n"
    fileContent += "\t" + """ "번호":$id """.trim() + ","
    fileContent += "\r\n"
    fileContent += "\t" + """ "제목":"$title" """.trim() + ","
    fileContent += "\r\n"
    fileContent += "\t" + """ "내용":"$body" """.trim()
    fileContent += "\r\n"
    fileContent += "}"

    File("test/1.json").writeText(fileContent)
}

fun textWriteFile() {
    File("test/1.txt").writeText("안녕")
}
