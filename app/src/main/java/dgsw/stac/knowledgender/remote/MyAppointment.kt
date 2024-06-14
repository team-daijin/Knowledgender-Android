package site.algosipeosseong.model

data class MyAppointment(
    val appointmentResponses: List<AppointmentResponse>
)

data class AppointmentResponse(
    val name: String,
    val introduce: String,
    val contact: String,
    val code: String,
    val appointmentDate: String,
    val appointmentTime: String,
    val location: Location
)

data class Location(
    val address: String,
    val latitude: Double,
    val longitude: Double
)
