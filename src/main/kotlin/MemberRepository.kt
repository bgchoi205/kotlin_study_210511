class MemberRepository{

    fun getMembers() : List<Member>{
        val members = mutableListOf<Member>()
        val lastId = getLastMemberId()

        for(i in 1..lastId){
            val member = memberFromFile("data/member/$i.json")
            if(member != null){
                members.add(member)
            }
        }
        return members
    }

    fun memberFromFile(filePath: String): Member? {
        val jsonStr = readStrFromFile(filePath)

        if(jsonStr == ""){
            return null
        }
        val map = mapFromJson(jsonStr)

        val id = map["id"].toString().toInt()
        val loginId = map["loginId"].toString()
        val loginPw = map["loginPw"].toString()
        val name = map["name"].toString()
        val nickName = map["nickName"].toString()

        return Member(id, loginId, loginPw, name, nickName)
    }

    fun getLastMemberId() : Int{
        val lastMemberId = readIntFromFile("data/member/lastMemberId.txt",0)
        return lastMemberId
    }

    fun addMember(loginId: String, loginPw: String, name: String, nickName: String): Int {
        val id = getLastMemberId() + 1
        val member = Member(id, loginId, loginPw, name, nickName)
        val jsonStr = member.toJson()
        writeStrFile("data/member/${member.id}.json", jsonStr)
        writeIntFile("data/member/lastMemberId.txt", id)
        return id
    }

    fun makeTestMembers(){
        for(i in 1..20){
            addMember("user$i", "user$i", "철수$i", "사용자$i")
        }
    }

    fun getMemberByLoginId(loginId: String): Member? {
        val members = getMembers()
        for(member in members){
            if(member.loginId == loginId){
                return member
            }
        }
        return null
    }

    fun getMemberById(id: Int): Member? {
        val member = memberFromFile("data/member/$id.json")
        return member
    }

}