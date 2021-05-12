package co.zsmb.cleanchat.data

data class Message(
    val user: User,
    val text: String,
    val imageUrl: String? = null,
)
