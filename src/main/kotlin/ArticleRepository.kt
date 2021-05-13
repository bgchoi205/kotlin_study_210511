class ArticleRepository{

    fun getArticles() : List<Article>{
        val articles = mutableListOf<Article>()

        val lastId = getLastArticleId()

        for(i in 1..lastId){
            val article = articleFromFile("data/article/$i.json")

            if(article != null){
                articles.add(article)
            }
        }
        return articles
    }

    fun articleFromFile(filePath: String): Article? {
        val jsonStr = readStrFromFile(filePath)

        if(jsonStr == ""){
            return null
        }
        val map = mapFromJson(jsonStr)

        val id = map["id"].toString().toInt()
        val memberId = map["memberId"].toString().toInt()
        val boardId = map["boardId"].toString().toInt()
        val title = map["title"].toString()
        val body = map["body"].toString()
        val regDate = map["regDate"].toString()
        val updateDate = map["updateDate"].toString()

        return Article(id, memberId, boardId, title, body, regDate, updateDate)
    }

    fun getLastArticleId() : Int{
        val lastArticleId = readIntFromFile("data/article/lastArticleId.txt", 0)

        return lastArticleId
    }

    fun addArticle(memberId : Int, boardId : Int, title: String, body: String): Int {
        val id = getLastArticleId() + 1
        val regDate = Util.getDateNowStr()
        val updateDate = Util.getDateNowStr()
        val article = Article(id, memberId, boardId, title, body, regDate, updateDate)
        val jsonStr = article.toJson()
        writeStrFile("data/article/$id.json", jsonStr)
        writeIntFile("data/article/lastArticleId.txt", id)
        return id
    }

    fun makeTestArticles(){
        for(i in 1..30){
            addArticle(i % 9 + 1, i % 2 + 1 ,"제목$i", "내용$i")
        }
    }

    fun getArticleById(id: Int): Article? {
        val article = articleFromFile("data/article/$id.json")
        return article
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
        val articles = getArticles()
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