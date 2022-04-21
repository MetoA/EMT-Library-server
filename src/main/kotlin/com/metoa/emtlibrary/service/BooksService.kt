package com.metoa.emtlibrary.service

import com.metoa.emtlibrary.domain.Author
import com.metoa.emtlibrary.domain.Book
import com.metoa.emtlibrary.domain.BookCategory
import com.metoa.emtlibrary.repository.BooksRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BooksService(
    val booksRepository: BooksRepository
) {

    fun findById(id: Long)= booksRepository.findById(id)

    fun getPaged(pageable: Pageable) = booksRepository.findAll(pageable)

    @Transactional
    fun createBook(name: String, category: BookCategory, author: Author): Book {
        val book = Book(
            id = 0,
            name = name,
            category = category,
            author = author
        )

        return booksRepository.save(book)
    }

    @Transactional
    fun editBook(id: Long, name: String, category: BookCategory, author: Author) =
        booksRepository.editBook(id, name, category, author) > 0

    @Transactional
    fun markAsTaken(id: Long): Boolean = booksRepository.markAsTaken(id) > 0

    @Transactional
    fun addCopy(id: Long): Boolean = booksRepository.addCopy(id) > 0

    @Transactional
    fun deleteBook(id: Long) = booksRepository.deleteById(id)
}