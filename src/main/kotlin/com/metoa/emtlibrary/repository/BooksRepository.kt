package com.metoa.emtlibrary.repository

import com.metoa.emtlibrary.domain.Author
import com.metoa.emtlibrary.domain.Book
import com.metoa.emtlibrary.domain.BookCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BooksRepository: JpaRepository<Book, Long> {

    @Modifying
    @Query("update Book b set b.name = :name, b.category = :category, b.author = :author where b.id = :id")
    fun editBook(id: Long, name: String, category: BookCategory, author: Author): Int

    @Modifying
    @Query("update Book b set b.availableCopies = (b.availableCopies-1) where b.id = :id")
    fun markAsTaken(id: Long): Int

    @Modifying
    @Query("update Book b set b.availableCopies = (b.availableCopies+1) where b.id = :id")
    fun addCopy(id: Long): Int

    @Query("select count(b) from Book b")
    fun getSize(): Long
}