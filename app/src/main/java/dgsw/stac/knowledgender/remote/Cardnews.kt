package site.algosipeosseong.model

data class Cardnews(
    val category: String,
    val cards: List<Cards>
)
data class Cards(
    val id: Long,
    val thumbnail: String,
    val category: String,
    val expert: String,
    val date: String,
    val title: String,
    val subTitle: String
)

data class CardnewsDetail(
    val id: Long,
    val thumbnail: String,
    val category: String,
    val title: String,
    val expert: String,
    val content: String,
    val subTitle: String
)

data class CardnewsCategory(
    val id: Long,
    val thumbnail: String,
    val category: String,
    val date: String,
    val expert: String,
    val title: String,
    val subTitle: String
)

//"id": "Long",
//"thumbnail": "String",
//"category": "String",
//"title": "String"
