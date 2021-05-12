class ArticleRepository{
    val articles = mutableListOf<Article>()
    var lastArticleId = 0

    fun addArticle(memberId : Int, title: String, body: String): Int {
        val id = ++lastArticleId
        val regDate = Util.getDateNowStr()
        val updateDate = Util.getDateNowStr()
        articles.add(Article(id, memberId, title, body, regDate, updateDate))
        return id
    }

    fun makeTestArticles(){
        for(i in 1..30){
            addArticle(i % 9 + 1, "제목$i", "내용$i")
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