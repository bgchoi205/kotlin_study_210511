
val articleRepository = ArticleRepository()
val memberRepository = MemberRepository()
val boardRepository = BoardRepository()

var loginedMember : Member? = null

fun main(){

    val articleController = ArticleController()
    val memberController = MemberController()
    val boardController = BoardController()

    memberRepository.makeTestMembers()
    boardRepository.makeTestBoard()

    println("==프로그램 시작==")

    while(true){

        loginedMember = memberRepository.getMemberById(1)

        val prompt = if(loginedMember == null){
            "명령어 입력 : "
        }else{
            "${loginedMember!!.nickName})"
        }
        print(prompt)
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
            "/board/add" -> {
                boardController.add()
            }
            "/board/list" -> {
                boardController.list()
            }
            "/board/delete" -> {
                boardController.delete(rq)
            }
            "/board/delete" -> {
                boardController.modify(rq)
            }
        }
    }
    println("==프로그램 끝==")
}


// Board
data class Board(
    val id : Int,
    var name : String,
    var code : String,
    val regDate : String,
    var updateDate : String
)

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

class BoardController{
    fun add() {
        print("게시판 이름 입력 : ")
        val name = readLineTrim()
        val board = boardRepository.getBoardByName(name)
        if(board != null){
            println("사용중인 이름입니다.")
            return
        }
        print("게시판 코드 입력 : ")
        val code = readLineTrim()
        if(board != null){
            println("사용중인 코드입니다.")
            return
        }
        boardRepository.addBoard(name, code)
        println("${name} 게시판이 추가되었습니다.")
    }

    fun list() {
        for(board in boardRepository.boards){
            println("${board.id} / ${board.name} / ${board.code}")
        }
    }

    fun delete(rq :Rq){
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시판 번호를 입력해주세요.")
            return
        }
        val board = boardRepository.getBoardById(id)
        if(board == null){
            println("존재하지 않는 게시판 번호입니다.")
            return
        }
        boardRepository.boards.remove(board)
        println("${board.name}게시판 삭제 완료")
    }

    fun modify(rq :Rq){
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시판 번호를 입력해주세요.")
            return
        }
        val board = boardRepository.getBoardById(id)
        if(board == null){
            println("존재하지 않는 게시판 번호입니다.")
            return
        }
        print("새 이름 : ")
        val name = readLineTrim()
        print("새 코드 : ")
        val code = readLineTrim()
        val updateDate = Util.getDateNowStr()
        board.name = name
        board.code = code
        board.updateDate = updateDate
        println("$id 번 게시판 수정완료")
    }


}