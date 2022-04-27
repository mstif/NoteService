package ru.netology

object NoteService : ParentService<Notes>() {


    fun add(title: String, text: String, privacy: Int): Int {
        val note = Notes(
            title = title,
            text = text,
            date = 0,
            privacy = privacy,
            comments = 0,
            commentsList = mutableListOf(),
            id = 0
        )

        return add(note).id
    }
/*
    fun createComment(noteId: Int, message: String): Int {
        val comment = Comment(message, noteId, 0, false, commentId++)
        val note: Notes? = getById(noteId)
        note?.commentsList?.add(comment)
        return comment.id
    }

    private fun getCommentById(id: Int): Comment? {
        for (itemElement in elements) {
            val comment: Comment? = itemElement.commentsList.find { it.id == id }
            if (comment != null) {
                return comment
            }
        }
        return null
    }

    fun deleteComment(commentId: Int): Boolean {
        val comment: Comment? = getCommentById(commentId)
        comment ?: throw CommentNotFoundException(commentId)
        if (comment.isDeleted) throw CommentIsDeletedException(commentId)
        return delete(comment)
    }

    fun restoreComment(commentId: Int): Boolean {
        val comment: Comment? = getCommentById(commentId)
        comment ?: throw CommentNotFoundException(commentId)
        if (!comment.isDeleted) throw RuntimeException("Комментарий с id=$commentId не был ранее удален!")
        return restore(comment)
    }*/

    /*fun editComment(commentId: Int, message: String): Boolean {
        val comment: Comment? = getCommentById(commentId)
        comment ?: throw CommentNotFoundException(commentId)
        if (!comment.isDeleted) throw RuntimeException("Комментарий с id=$commentId не был ранее удален!")
        val commentCopy = comment.copy(text = message)
        var note = getById(comment.noteId)
        note ?: throw  NoteNotFoundException(comment.noteId)
        if (note.isDeleted) throw RuntimeException("Заметка с id=${comment.noteId}  была ранее удалена! Редактирование комментария невозможно")
        //update(comment,note.commentsList)
        var commentNote = note.commentsList.find { it.id == commentId }
        commentNote = commentCopy
        return true
    }*/

    fun edit(noteId: Int, title: String, text: String): Boolean {
        val note: Notes = getById(noteId) ?: throw NoteNotFoundException(noteId)
        if (note.isDeleted) throw ElementIsDeletedExeption(noteId )
        val copyNote =
            Notes(title, text, note.date, privacy = note.privacy, note.comments,
                note.commentsList.toMutableList(),id=note.id)
        return super.update(copyNote,elements)

    }

    fun delete(noteId: Int): Boolean {
        val note = getById(noteId) ?: throw NoteNotFoundException(noteId)
        val comments = CommentService.getComments(noteId)
        for (comment in comments) {
            CommentService.deleteComment(comment.id)
        }
        return super.delete(noteId,elements)
    }

    fun delete1(noteId: Int): Boolean {
        val note = getById(noteId) ?: throw NoteNotFoundException(noteId)
        val comments = CommentService.getComments(noteId)
        for (comment in comments) {
            CommentService.deleteComment1(comment.id)
        }
        return super.delete(note)
    }

    private fun restoreNote(noteId: Int): Boolean {
        val note = getById(noteId) ?: throw NoteNotFoundException(noteId)
        return super.restore(note)
    }

    fun get(notesIds: String, count: Int=0, offset: Int = 0, sort: Int = 0): Array<Notes> {
        var res = arrayOf<Notes>()

        if (notesIds.isNotBlank()) {
            val ids = notesIds.split(',')
            for (id in ids) {
                val note = getById(id.toInt()) ?: continue
                if (note.isDeleted) continue
                res += note
            }
        } else {
            if (count == 0) return res
            for (index: Int in offset until count) {
                res += elements[index]
            }
        }


        if (sort == 0)
            res.sortBy { it.date }
        else
            res.sortByDescending { it.date }

        return res
    }

    /*fun getComments(noteId: Int, sort: Int = 0, count: Int = 0): Array<Comment> {
        val note = getById(noteId) ?: throw NoteNotFoundException(noteId)
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

    }*/

}