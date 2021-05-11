import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val articleRepository = ArticleRepository()
val memberRepository = MemberRepository()

var loginedMember : Member? = null

fun main(){

    val articleController = ArticleController()
    val memberController = MemberController()

    articleRepository.makeTestArticles()
    memberRepository.makeTestMembers()

    println("==프로그램 시작==")

    while(true){
        print("명령어 입력 : ")
        val cmd = readLineTrim()
        val rq = Rq(cmd)
        when(rq.actionPath){
            "/exit" -> {
                break
            }
            "/member/join" -> {
                memberController.join()
            }
            "/member/login" -> {
                memberController.login()
            }
            "/member/logout" -> {
                memberController.logout()
            }
            "/article/write" -> {
                articleController.add()
            }
            "/article/list" -> {
                articleController.list(rq)
            }
            "/article/detail" -> {
                articleController.detail(rq)
            }
            "/article/delete" -> {
                articleController.delete(rq)
            }
            "/article/modify" -> {
                articleController.modify(rq)
            }

        }


    }

    println("==프로그램 끝==")
}

// Member
data class Member(
    val id : Int,
    val loginId : String,
    val loginPw : String,
    val name : String,
    val nickName : String
)

class MemberRepository{
    val members = mutableListOf<Member>()
    var lastMemberId = 0

    fun addMember(loginId: String, loginPw: String, name: String, nickName: String): Int {
        val id = ++lastMemberId
        members.add(Member(id, loginId, loginPw, name, nickName))
        return id
    }

    fun makeTestMembers(){
        for(i in 1..20){
            addMember("user$i", "user$i", "철수$i", "사용자$i")
        }
    }

    fun getMemberByLoginId(loginId: String): Member? {
        for(member in members){
            if(member.loginId == loginId){
                return member
            }
        }
        return null
    }

}

class MemberController{
    fun join() {
        print("사용할 아이디 입력 : ")
        val loginId = readLineTrim()
        val member = memberRepository.getMemberByLoginId(loginId)
        if(member != null){
            println("사용중인 아이디입니다.")
            return
        }
        print("사용할 비밀번호 입력 : ")
        val loginPw = readLineTrim()
        print("이름 입력 : ")
        val name = readLineTrim()
        print("닉네임 입력 : ")
        val nickName = readLineTrim()
        val id = memberRepository.addMember(loginId, loginPw, name, nickName)
        println("$id 번 회원으로 가입 완료")
    }

    fun login() {
        print("아이디 입력 : ")
        val loginId = readLineTrim()
        val member = memberRepository.getMemberByLoginId(loginId)
        if(member == null){
            println("아이디가 틀립니다.")
            return
        }
        print("비밀번호 입력 : ")
        val loginPw = readLineTrim()
        if(member.loginPw != loginPw){
            println("비밀번호가 틀립니다.")
            return
        }
        loginedMember = member
        println("${member.nickName}님 환영합니다.")
    }

    fun logout(){
        loginedMember = null
        println("로그아웃")
    }

}



// Article
data class Article(
    val id : Int,
    var title : String,
    var body : String,
    val regDate : String,
    var updateDate : String
)

class ArticleRepository{
    val articles = mutableListOf<Article>()
    var lastArticleId = 0

    fun addArticle(title: String, body: String): Int {
        val id = ++lastArticleId
        val regDate = Util.getDateNowStr()
        val updateDate = Util.getDateNowStr()
        articles.add(Article(id, title, body, regDate, updateDate))
        return id
    }

    fun makeTestArticles(){
        for(i in 1..30){
            addArticle("제목$i", "내용$i")
        }
    }

    fun getArticleById(id: Int): Article? {
        for(article in articles){
            if(article.id == id){
                return article
            }
        }
        return null
    }

    fun articlesFilter(keyword: String, page: Int, pageCount: Int): List<Article> {
        val filtered1Articles = articlesFilterByKey(keyword)
        val filtered3Articles = articlesFilterByPage(filtered1Articles, page, pageCount)
        return filtered3Articles
    }

    private fun articlesFilterByPage(filtered1Articles: List<Article>, page: Int, pageCount: Int): List<Article> {
        val startIndex = filtered1Articles.lastIndex - ((page - 1) * pageCount)
        var endIndex = startIndex - pageCount + 1
        if(endIndex < 0){
            endIndex = 0
        }
        val filtered3Articles = mutableListOf<Article>()
        for(i in startIndex downTo endIndex){
            filtered3Articles.add(filtered1Articles[i])
        }
        return filtered3Articles
    }

    private fun articlesFilterByKey(keyword: String): List<Article> {
        if(keyword.isEmpty()){
            return articles
        }
        val filtered1Articles = mutableListOf<Article>()
        for(article in articles){
            if(article.title.contains(keyword)){
                filtered1Articles.add(article)
            }
        }
        return filtered1Articles
    }

}

class ArticleController{
    fun add() {
        if(loginedMember == null){
            println("로그인 후 이용해주세요")
            return
        }
        print("제목 입력 : ")
        val title = readLineTrim()
        print("내용 입력 : ")
        val body = readLineTrim()
        val id = articleRepository.addArticle(title, body)
        println("$id 번 게시물 등록완료")
    }

    fun list(rq: Rq) {
        val keyword = rq.getStringParam("keyword", "")
        val page = rq.getIntParam("page", 1)
        val filteredArticles = articleRepository.articlesFilter(keyword, page, 5)

        for(article in filteredArticles){
            println("${article.id} / ${article.title} / ${article.regDate}")
        }
    }

    fun detail(rq: Rq) {
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시물 번호를 입력해주세요.")
            return
        }
        val article = articleRepository.getArticleById(id)
        if(article == null){
            println("없는 게시물 번호입니다.")
            return
        }
        println("번호 : ${article.id}")
        println("제목 : ${article.title}")
        println("내용 : ${article.body}")
        println("등록일 : ${article.regDate}")
        println("수정일 : ${article.updateDate}")
    }

    fun delete(rq: Rq) {
        if(loginedMember == null){
            println("로그인 후 이용해주세요")
            return
        }
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시물 번호를 입력해주세요.")
            return
        }
        val article = articleRepository.getArticleById(id)
        if(article == null){
            println("없는 게시물 번호입니다.")
            return
        }
        articleRepository.articles.remove(article)
        println("$id 번 게시물 삭제완료")
    }

    fun modify(rq : Rq){
        if(loginedMember == null){
            println("로그인 후 이용해주세요")
            return
        }
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시물 번호를 입력해주세요.")
            return
        }
        val article = articleRepository.getArticleById(id)
        if(article == null){
            println("없는 게시물 번호입니다.")
            return
        }
        print("새 제목 : ")
        val title = readLineTrim()
        print("새 내용 : ")
        val body = readLineTrim()
        val updateDate = Util.getDateNowStr()
        article.title = title
        article.body = body
        article.updateDate = updateDate
        println("$id 번 게시물 수정 완료")
    }


}




fun readLineTrim() = readLine()!!.trim()

object Util{
    fun getDateNowStr() : String{
        var now = LocalDateTime.now()
        var getNowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분 ss초"))
        return getNowStr
    }
}