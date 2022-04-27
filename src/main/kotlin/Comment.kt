package ru.netology

data class Comment(
    val text: String,
    val noteId: Int,
    val date:Int,
    override var isDeleted: Boolean = false,
    override val id: Int = 0
) : ParentElement {
    override fun copyElement( maxId: Int): Comment {
        return this.copy(id = id)
    }

}
