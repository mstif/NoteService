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



}