import java.io.File

class BoardController {
    fun add() {
        print("게시판 이름 입력 : ")
        val name = readLineTrim()
        val board = boardRepository.getBoardByName(name)
        if (board != null) {
            println("사용중인 이름입니다.")
            return
        }
        print("게시판 코드 입력 : ")
        val code = readLineTrim()
        val boardCode = boardRepository.getBoardByCode(code)
        if (boardCode != null) {
            println("사용중인 코드입니다.")
            return
        }
        boardRepository.addBoard(name, code)
        println("${name} 게시판이 추가되었습니다.")
    }

    fun list() {
        for (board in boardRepository.getBoards()) {
            println("${board.id} / ${board.name} / ${board.code}")
        }
    }

    fun delete(rq: Rq) {
        val id = rq.getIntParam("id", 0)
        if (id == 0) {
            println("게시판 번호를 입력해주세요.")
            return
        }
        val board = boardRepository.getBoardById(id)
        if (board == null) {
            println("존재하지 않는 게시판 번호입니다.")
            return
        }
        File("data/board/$id.json").delete()
        println("${board.name}게시판 삭제 완료")
    }

    fun modify(rq: Rq) {
        val id = rq.getIntParam("id", 0)
        if (id == 0) {
            println("게시판 번호를 입력해주세요.")
            return
        }
        val board = boardRepository.getBoardById(id)
        if (board == null) {
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
