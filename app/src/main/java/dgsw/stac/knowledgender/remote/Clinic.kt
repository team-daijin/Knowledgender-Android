package site.algosipeosseong.model

data class ClinicRequest(
    val latitude: Double,
    val longitude: Double
)
data class Clinic(
    val id : Int,
    val image: String,
    val name: String,
    val address: String,
    val contact: String,
    val description: String
)

