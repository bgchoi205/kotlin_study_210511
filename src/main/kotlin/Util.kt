import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun readLineTrim() = readLine()!!.trim()

fun testArticleFromJson(jsonStr: String): TestArticle {
    val jsonMap = mapFromJson(jsonStr)

    val id = jsonMap["id"].toString().toInt()
    val title = jsonMap["title"].toString()
    val body = jsonMap["body"].toString()

    return TestArticle(id, title, body)
}

fun mapFromJson(jsonStr: String): Map<String, Any> {
    val map = mutableMapOf<String, Any>()

    var jsonStr = jsonStr.drop(1)  // drop 으로 첫 문자 ( { ) 를 떨어뜨린다, 제거하다
    jsonStr = jsonStr.dropLast(1)  // 뒤에서 첫 문자 ( } ) 제거

    val jsonItems = jsonStr.split(",\r\n")

    for(jsonItem in jsonItems){
        val jsonItemBits = jsonItem.trim().split(":", limit = 2)  // 나누는 개수가 정해져 있으면 limit(몇개로 나눌지) 걸어주는게 좋다

        val key = jsonItemBits[0].trim().drop(1).dropLast(1)
        // key 가 "id" 인 경우라면 큰따옴표""를 제거하기 위함.
        var value = jsonItemBits[1].trim()

        when {
            value == "true" -> {
                map[key] = true
            }
            value == "false" -> {
                map[key] = false
            }
            value.startsWith("\"") -> {   // 만약 value가 문장이라면(" 로 시작한다면)
                map[key] = value.drop(1).dropLast(1)  // 처음과 끝의 " 를 제거한다
            }
            value.contains(".") -> {
                map[key] = value.toDouble()
            }
            else -> {
                map[key] = value.toInt()
            }
        }
    }

    return map.toMap()
}


object Util{
    fun getDateNowStr() : String{
        var now = LocalDateTime.now()
        var getNowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분 ss초"))
        return getNowStr
    }
}