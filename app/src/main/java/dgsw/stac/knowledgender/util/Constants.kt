package dgsw.stac.knowledgender.util



enum class Category(val str: String) {
    HEART("HEART"),
    BODY("BODY"),
    RELATION("RELATION"),
    VIOLENCE("VIOLENCE"),
    EQUALITY("EQUALITY")
}

fun categoryConverter(str: String?): Category {
    str?.let {
        return when (it) {
            Category.HEART.str -> Category.HEART
            Category.BODY.str -> Category.BODY
            Category.RELATION.str -> Category.RELATION
            Category.VIOLENCE.str -> Category.VIOLENCE
            else -> Category.EQUALITY
        }
    }.run {
        return Category.HEART
    }
}
