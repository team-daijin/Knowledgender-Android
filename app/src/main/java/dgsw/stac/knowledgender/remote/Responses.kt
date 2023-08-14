package dgsw.stac.knowledgender.remote

import java.util.UUID

enum class Category{
    HEART,
    BODY,
    CRIME,
    RELATIONSHIP,
    EQUALITY
}

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
//"accessToken": String,
//"refreshToken": String

data class CardNewsDetailResponse(
    val id: UUID,
    val title: String,
    val writer: String,
    val category: Category,
    val content: String,
    val image: String
)

data class CardCategoryResponse(
    val id: UUID,
    val title: String,
    val writer: String,
    val category: Category,
    val content: String,
    val image: String
)
data class BannerResponse(
    val id : UUID,
    val banner : String,
    val redirect : String
)