package dgsw.stac.knowledgender.remote

enum class Category{
    HEART,
    BODY,
    CRIME,
    RELATIONSHIP,
    EQUALITY
}

data class Profile(
    val name: String,
    val age: String,
    val gender: String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
//"accessToken": String,
//"refreshToken": String

data class CardNewsDetailResponse(
    val id: String,
    val title: String,
    val writer: String,
    val category: Category,
    val content: String,
    val image: String
)

data class CardCategoryResponse(
    val id: String,
    val title: String,
    val writer: String,
    val category: String,
    val content: String,
    val image: String
)

//data class CategoryCardItem(
//    val id: String,
//    val title: String,
//    val writer: String,
//    val category: Category,
//    val content: String,
//    val image: String
//)

data class BannerResponse(
    val id : String,
    val banner : String,
    val redirect : String
)
data class AppointmentResponse(
    val id:String,
    val name: String,
    val address: String,
    val contact: String,
    val introduce: String,
    val image: String,
    val appointmentAvailable: Boolean
)
