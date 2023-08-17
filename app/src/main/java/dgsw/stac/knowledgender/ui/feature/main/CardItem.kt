package dgsw.stac.knowledgender.ui.feature.main

import dgsw.stac.knowledgender.remote.Category
import java.util.UUID

data class CardItem(val id: String, val title: String, val category: Category, val image: String)
