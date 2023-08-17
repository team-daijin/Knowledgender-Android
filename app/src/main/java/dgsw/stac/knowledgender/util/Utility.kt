package dgsw.stac.knowledgender.util

import dgsw.stac.knowledgender.remote.Category


object Utility {

    fun categoryToString(category: Category): String {
        return when (category) {
            Category.HEART -> "마음"
            Category.BODY -> "신체"
            Category.CRIME -> "폭력"
            Category.RELATIONSHIP -> "관계"
            Category.EQUALITY -> "평등"
        }
    }
}