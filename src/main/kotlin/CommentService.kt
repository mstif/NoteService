package ru.netology

object CommentService:ParentService<Comment>() {
    private var commentId: Int = 1

    override fun clear() {
        super.clear()
        commentId=1
    }
     fun getCommentById(id: Int): Comment? {
        for (itemElement in NoteService.elements) {
            val comment: Comment? = itemElement.commentsList.find { it.id == id }
            if (comment != null) {
                return comment
            }
        }
        return null
    }

    fun createComment(noteId: Int, message: String): Int {
        val comment = Comment(message, noteId, 0, false, commentId++)
        val note = NoteService.getById(noteId)?: throw NoteNotFoundException(noteId)
        if(note.isDeleted)throw ElementIsDeletedExeption(noteId)
        add(comment,note.commentsList)
        return comment.id
    }

    fun deleteComment1(commentId: Int): Boolean {
        val comment: Comment? = getCommentById(commentId)
        comment ?: throw CommentNotFoundException(commentId)
        if (comment.isDeleted) throw CommentIsDeletedException(commentId)
        return delete(comment)
    }
    fun deleteComment(commentId: Int): Boolean {
        val comment: Comment? = getCommentById(commentId)
        comment ?: throw CommentNotFoundException(commentId)
        if (comment.isDeleted) throw CommentIsDeletedException(commentId)
        val note = NoteService.getById(comment.noteId)
        note?:throw NoteNotFoundException(comment.noteId)
        return delete(comment.id,note.commentsList)
    }

    fun restoreComment(commentId: Int): Boolean {
        val comment: Comment? = getCommentById(commentId)
        comment ?: throw CommentNotFoundException(commentId)
        if (!comment.isDeleted) throw RuntimeException("Комментарий с id=$commentId не был ранее удален!")
        return restore(comment)
    }
    fun getComments(noteId: Int, sort: Int = 0, count: Int = 0): Array<Comment> {
        val note = NoteService.getById(noteId) ?: throw NoteNotFoundException(noteId)
        val comments: MutableList<Comment> = note.commentsList
        if (sort == 0)
            comments.sortBy { it.date }
        else
            comments.sortByDescending { it.date }

        var res: Array<Comment> = emptyArray()
        val cnt = if (count == 0) comments.count() - 1 else count - 1
        for ((index, comment) in comments.withIndex()) {
            if (index > cnt) break
            if (comment.isDeleted) continue
            res += comment

        }
        return res

    }

    fun editComment(commentId: Int, message: String): Boolean {
        val comment: Comment? = getCommentById(commentId)
        comment ?: throw CommentNotFoundException(commentId)
        if (comment.isDeleted) throw RuntimeException("Комментарий с id=$commentId  был ранее удален!")
        val commentCopy = comment.copy(text = message)
        var note = NoteService.getById(comment.noteId)
        note ?: throw  NoteNotFoundException(comment.noteId)
        if (note.isDeleted) throw RuntimeException("Заметка с id=${comment.noteId}  была ранее удалена! Редактирование комментария невозможно")
        return update(commentCopy,note.commentsList)


    }



}