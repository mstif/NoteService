package ru.netology

import org.junit.Assert
import org.junit.Test

class NoteServiceTest {

    @Test
    fun add_new_Note() {
        NoteService.clear()
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        val noteId2 = NoteService.add("new Note #2", "Content #2", 1)
        var result = true
        val resNoteId = NoteService.getById(noteId1)?.id ?: 0
        if (resNoteId != noteId1) result = false
        val resNoteId2 = NoteService.getById(noteId2)?.id ?: 0
        if (resNoteId2 != noteId2) result = false
        if (resNoteId2 != 2) result = false
        Assert.assertTrue(result)
    }

    @Test
    fun createComment() {
        CommentService.clear()
        NoteService.clear()
        var result = true
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        val noteId2 = NoteService.add("new Note #2", "Content #2", 1)

        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId2, "Это комментарий к заметке $noteId2")
        val noteComm1 = NoteService.getById(noteId1)?.commentsList?.get(0)?.id
        if (noteComm1 != commId1) result = false
        val noteComm2 = NoteService.getById(noteId2)?.commentsList?.get(0)?.id
        if (noteComm2 != commId2) result = false
        Assert.assertTrue(result)

    }

     @Test(expected = NoteNotFoundException::class)
    fun createComment_exception_notfound() {
        CommentService.clear()
        NoteService.clear()
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        CommentService.createComment(noteId1+1, "Это комментарий к заметке ")
    }

    @Test(expected = ElementIsDeletedExeption::class)
    fun createComment_exception_IsDeleted() {
        CommentService.clear()
        NoteService.clear()
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        NoteService.delete(noteId1)
        CommentService.createComment(noteId1, "Это комментарий к заметке ")


    }


    @Test
    fun edit_Note() {
        CommentService.clear()
        NoteService.clear()
        var result = true
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        if( !NoteService.edit(noteId1,"edited Note #1", "edited content#1")) result = false
        val note = NoteService.getById(noteId1)
        if(note?.text!="edited content#1")result = false
        Assert.assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun edit_Note_NotFound_Exeption() {
        CommentService.clear()
        NoteService.clear()
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        NoteService.edit(noteId1+1,"edited Note #1", "edited content#1")
    }

    @Test(expected = ElementIsDeletedExeption::class)
    fun edit_Note_IsDeleted_Exception() {
        CommentService.clear()
        NoteService.clear()
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        NoteService.delete(noteId1)
        NoteService.edit(noteId1,"edited Note #1", "edited content#1")
    }



    @Test
    fun deleteNote() {
        NoteService.clear()
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        var result = true
        NoteService.delete(noteId1)
        val note=NoteService.getById(noteId1)
       if(note?.isDeleted == false) result = false
        val comments = note?.commentsList
        if( comments!=null){
        if(comments.count()<2||!comments[0].isDeleted||!comments[1].isDeleted) result=false}
        else result=false
        Assert.assertTrue(result)


    }


    @Test(expected = NoteNotFoundException::class)
    fun deleteNote_Nfe_Exception() {
        NoteService.clear()
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        NoteService.delete(noteId1+1)

    }
    @Test(expected = ElementIsDeletedExeption::class)
    fun deleteNote_Eid_Exception() {
        NoteService.clear()
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        NoteService.delete(noteId1)
        NoteService.delete(noteId1)

    }



    @Test
    fun get() {
        NoteService.clear()
        var result = true
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        val noteId2 = NoteService.add("new Note #2", "Content #2", 1)
        val noteId3 = NoteService.add("new Note #3", "Content #3", 1)
        NoteService.delete(noteId2)
        val notes = NoteService.get("$noteId1,$noteId2,$noteId3")
        Assert.assertTrue(notes.count()==2)
    }

    @Test
    fun deleteComment() {
        CommentService.clear()
        NoteService.clear()
        var result = true
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)

        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        CommentService.deleteComment(commId2)
        Assert.assertTrue(CommentService.getComments(noteId1).count()==1)

    }
    @Test(expected = CommentIsDeletedException::class)
    fun deleteComment_Eid_Exception() {
        CommentService.clear()
        NoteService.clear()
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)

        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        NoteService.delete(noteId1)
        CommentService.deleteComment(commId2)


    }


    @Test(expected = RuntimeException::class)
    fun restoreComment_Exception() {
        CommentService.clear()
        NoteService.clear()

        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)
        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")

        CommentService.restoreComment(commId2)


    }

    @Test
    fun restoreComment() {
        CommentService.clear()
        NoteService.clear()
        var result = true
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)

        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        CommentService.deleteComment(commId2)
        if(CommentService.getComments(noteId1).count()!=1) result = false
        CommentService.restoreComment(commId2)
        if(CommentService.getComments(noteId1).count()!=2) result = false
        Assert.assertTrue(result)

    }

    @Test
    fun getComments() {
        CommentService.clear()
        NoteService.clear()
        var result = true
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)

        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        CommentService.deleteComment(commId2)
        if(CommentService.getComments(noteId1).count()!=1) result = false
        Assert.assertTrue(result)
    }


    @Test(expected = NoteNotFoundException::class)
    fun getComments_Nfe_Exception() {
        CommentService.clear()
        NoteService.clear()

        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)

        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")

        CommentService.getComments(noteId1+1)

    }

    @Test
    fun editComment() {
        CommentService.clear()
        NoteService.clear()
        var result = true
        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)

        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        CommentService.editComment(commId1,"edited comment")

       Assert.assertTrue( CommentService.getCommentById(commId1)?.text=="edited comment")

    }


    @Test(expected = RuntimeException::class)
    fun editComment_Exception() {
        CommentService.clear()
        NoteService.clear()

        val noteId1 = NoteService.add("new Note #1", "Content #1", 1)

        val commId1 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        val commId2 = CommentService.createComment(noteId1, "Это комментарий к заметке $noteId1")
        CommentService.deleteComment(commId1)
        CommentService.editComment(commId1,"edited comment")



    }

}