package ru.netology

class NoteNotFoundException(noteId:Int):RuntimeException("Заметка с id = $noteId не найдена!")
class CommentNotFoundException(commentId:Int):RuntimeException("Комментарий с id = $commentId не найден!")
class CommentIsDeletedException(commentId:Int):RuntimeException("Комментарий с id = $commentId удален!")
class ElementIsDeletedExeption(id:Int):RuntimeException("Комментарий с id = $id удален!")
class ElementNotFoundException(id:Int):RuntimeException("Заметка с id = $id не найдена!")