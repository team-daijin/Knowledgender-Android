package dgsw.stac.knowledgender.remote

data class Register(
    val id: String,
    val pw: String,
    val name: String,
    val age: Int,
    val sex: Boolean
)
