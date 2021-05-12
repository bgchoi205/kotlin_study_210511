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

    fun getMemberById(id: Int): Member? {
        for(member in members){
            if(member.id == id){
                return member
            }
        }
        return null
    }

}