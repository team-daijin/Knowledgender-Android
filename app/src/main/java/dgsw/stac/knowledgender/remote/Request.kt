package dgsw.stac.knowledgender.remote

enum class Gender {
    MALE,
    FEMALE
}
data class RegisterRequest(
    val accountId: String,
    val password: String,
    val name: String,
    val age: Int,
    val gender: Gender
)
//"accountId": String,
//"password": String,
//"name": String,
//"age": int,
//"gender": String
data class LoginRequest(
    val accountId: String,
    val password: String
)
//"accountId": String,
//"password": String