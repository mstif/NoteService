package ru.netology

abstract class ParentService<T : ParentElement> {
    open var elements: MutableList<T> = mutableListOf()
    private var maxId = 1
    open fun clear() {
        elements = mutableListOf()
        maxId = 1
    }

    fun add(elem: T, destinationList: MutableList<T> = elements): T {

        val copyElem: T = elem.copyElement(maxId++) as T
        destinationList.add(copyElem)
        return destinationList.last()
    }

    fun update(elem: T, destinationList: MutableList<T> = elements): Boolean {

        for ((index, itemElement) in destinationList.withIndex()) {
            if (itemElement.id == elem.id) {
                if (itemElement.isDeleted) throw ElementIsDeletedExeption(elem.id)
                destinationList[index] = elem.copyElement(0) as T
                return true
            }
        }
        return false
    }

    fun getById(id: Int, destinationList: MutableList<T> = elements): T? {
        for (itemElement in destinationList) {
            if (itemElement.id == id) {

                return itemElement
            }
        }
        return null
    }

    open fun deleteFinal(id: Int): Boolean {
        val elem: T? = getById(id)
        return elements.remove(elem)
    }

    open fun delete(id: Int, destinationList: MutableList<T> = elements): Boolean {
        for ((index, itemElement) in destinationList.withIndex()) {
            if (itemElement.id == id) {
                if (itemElement.isDeleted) throw ElementIsDeletedExeption(id)
                val elemCopy = itemElement.copyElement(0)
                elemCopy.isDeleted = true
                destinationList[index] = elemCopy as T
                return true
            }
        }
        return false
    }

    open fun delete(elem: T?): Boolean {
        (elem ?: throw RuntimeException("Попытка удаления несуществующего элемента ")).isDeleted = true
        return elem.isDeleted
    }

    open fun restore(elem: T?): Boolean {
        (elem ?: throw RuntimeException("Попытка восстановления несуществующего элемента ")).isDeleted = false
        return elem.isDeleted
    }


}