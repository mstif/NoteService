package ru.netology

data class Notes(
    val title: String = "",
    val text: String = "",
    val date: Int = 0,
    val privacy: Int = 0,
    val comments: Int = 0,
    val commentsList: MutableList<Comment> = mutableListOf(),
    override var isDeleted: Boolean = false,
    override val id: Int = 0
) : ParentElement {
    override fun copyElement( maxId: Int): Notes {
        val resId = if (maxId == 0) id else maxId
        return this.copy(id = resId)

    }

}