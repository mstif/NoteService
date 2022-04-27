#Сервис заметок (Notes)
##Реализованы функции
* ###notes.add
    * Исключены параметры comment_privacy,privacy_view, privacy_comment
* ###notes.createComment
  * replay_id, owner_id, guid
* ###notes.delete
* ###notes.deleteComment
  * owner_id
* ###notes.edit
  * privacy, comment_privacy,privacy_view, privacy_comment
* ###notes.editComment
  * owner_id
* ###notes.get
  * user_id
* ###notes.getById
  * owner_id, need_wiki
* ###notes.getComments
  * owner_id
* ###notes.restoreComment
  * owner_id