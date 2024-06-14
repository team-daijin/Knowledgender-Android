package dgsw.stac.knowledgender.remote

enum class Category{
    HEART,
    BODY,
    CRIME,
    RELATIONSHIP,
    EQUALITY,
    NONE
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

data class CardResponse(
    val id: String,
    val title: String,
    val writer: String,
    val category: String,
    val content: String,
    val thumbnail: String,
    val image: String
)

data class CardResponseList(
    val cardResponseList: List<CardResponse>
)

//data class CategoryCardItem(
//    val id: String,
//    val title: String,
//    val writer: String,
//    val category: Category,
//    val content: String,
//    val image: String
//)

data class BannerResponseList(
    val bannerResponses: List<BannerResponse>
)

data class BannerResponse(
    val fileUrl : String,
    val redirect : String
)
data class AppointmentResponse(
    val id:String,
    val image: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val contact: String,
    val address: String,
    val description: String,
)

//{
//    "id": 1,
//    "image": "https://knowledgender.s3.ap-northeast-2.amazonaws.com/clinic/8812518e-378a-4640-8cfa-409cbb19aac4-%E1%84%8B%E1%85%A1%E1%86%AF%E1%84%80%E1%85%A9%E1%84%89%E1%85%B5%E1%87%81%E1%84%8B%E1%85%A5%E1%86%BB%E1%84%89%E1%85%A5%E1%86%BC-%E1%84%90%E1%85%A6%E1%84%89%E1%85%B3%E1%84%90%E1%85%B3-%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.jpeg",
//    "name": "대구광역정신건강복지센터",
//    "address": "대구광역시 서구 평리로 157 대구의료원 생명존중센터 3층",
//    "contact": "053-256-0199",
//    "description": "String"
//},
