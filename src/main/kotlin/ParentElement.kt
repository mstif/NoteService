package ru.netology

 interface ParentElement {
     val id: Int
     var isDeleted:Boolean
     fun copyElement( maxId: Int):ParentElement
}