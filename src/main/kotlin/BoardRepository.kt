class BoardRepository{
    val boards = mutableListOf<Board>()
    var lastBoardId = 0

    fun addBoard(name: String, code: String) {
        val id = ++lastBoardId
        val regDate = Util.getDateNowStr()
        val updateDate = Util.getDateNowStr()
        boards.add(Board(id, name, code, regDate, updateDate))
    }

    fun makeTestBoard(){
        addBoard("공지", "notice")
        addBoard("자유", "free")
    }

    fun getBoardById(id: Any): Board? {
        for(board in boards){
            if(board.id == id){
                return board
            }
        }
        return null
    }

    fun getBoardByName(name: String): Board? {
        for(board in boards){
            if(board.name == name){
                return board
            }
        }
        return null
    }
    fun getBoardByCode(code: String): Board? {
        for(board in boards){
            if(board.code == code){
                return board
            }
        }
        return null
    }

}