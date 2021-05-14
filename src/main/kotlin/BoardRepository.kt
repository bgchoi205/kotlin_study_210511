class BoardRepository{

    fun getBoards() : List<Board>{
        val boards = mutableListOf<Board>()
        val lastId = getlastBoardId()
        for(i in 1..lastId){
            val board = boardFromFile("data/board/$i.json")
            if(board != null){
                boards.add(board)
            }
        }
        return boards
    }

    fun boardFromFile(filePath: String) : Board?{
        val jsonStr = readStrFromFile(filePath)
        if(jsonStr == ""){
            return null
        }
        val map = mapFromJson(jsonStr)
        val id = map["id"].toString().toInt()
        val name = map["name"].toString()
        val code = map["code"].toString()
        val regDate = map["regDate"].toString()
        val updateDate = map["updateDate"].toString()

        return Board(id, name, code, regDate, updateDate)
    }

    fun getlastBoardId(): Int{
        val boardId = readIntFromFile("data/board/lastBoardId.txt",0)
        return boardId
    }

    fun addBoard(name: String, code: String) {
        val id = getlastBoardId() + 1
        val regDate = Util.getDateNowStr()
        val updateDate = Util.getDateNowStr()

        val board = Board(id, name, code, regDate, updateDate)
        val jsonStr = board.toJson()
        writeStrFile("data/board/$id.json", jsonStr)
        writeIntFile("data/board/lastBoardId.txt", id)

    }

    fun makeTestBoard(){
        addBoard("공지", "notice")
        addBoard("자유", "free")
    }

    fun getBoardById(id: Any): Board? {
        for(board in getBoards()){
            if(board.id == id){
                return board
            }
        }
        return null
    }

    fun getBoardByName(name: String): Board? {
        for(board in getBoards()){
            if(board.name == name){
                return board
            }
        }
        return null
    }
    fun getBoardByCode(code: String): Board? {
        for(board in getBoards()){
            if(board.code == code){
                return board
            }
        }
        return null
    }

}