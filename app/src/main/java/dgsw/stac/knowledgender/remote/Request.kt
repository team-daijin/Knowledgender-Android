package dgsw.stac.knowledgender.remote




data class RegisterRequest(
    val accountId: String,
    val password: String,
    val name: String,
    val age: Int,
    val gender: String
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

data class AppointmentReservationRequest(
    val date : String,
    val time : String,
    val content: String,
    val clientId: String
)