package com.metoa.emtlibrary.controller

import com.metoa.emtlibrary.domain.Book
import com.metoa.emtlibrary.domain.requests.CreateEditBookRequest
import com.metoa.emtlibrary.errors.NotFoundException
import com.metoa.emtlibrary.service.AuthorsService
import com.metoa.emtlibrary.service.BooksService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/books")
@CrossOrigin("http://localhost:3000")
class BooksController(
    val booksService: BooksService,
    val authorsService: AuthorsService
) {
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Book =
        booksService.findById(id).orElseThrow { NotFoundException("Book with id $id not found") }

    @GetMapping("/size")
    fun getSize(): Long = booksService.getSize()

    @GetMapping("/paged")
    fun getPaged(pageable: Pageable): List<Book> = booksService.getPaged(pageable).content

    @PostMapping("/create")
    fun createBook(@RequestBody request: CreateEditBookRequest): ResponseEntity<Book> = with(request) {
        val author = authorsService.findById(authorId).orElseThrow { NotFoundException("Author with id $authorId not found") }
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(booksService.createBook(name, category, author))
    }

    @PutMapping("/{id}/edit")
    fun editBook(
        @PathVariable id: Long,
        @RequestBody request: CreateEditBookRequest
    ): ResponseEntity<Void> = with(request) {
        val author = authorsService.findById(authorId).orElseThrow { NotFoundException("Author with id $authorId not found") }
        if (booksService.editBook(id, name, category, author)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_MODIFIED)
        }
    }

    @PutMapping("/{id}/mark_as_taken")
    fun markAsTaken(@PathVariable id: Long): ResponseEntity<Void> =
        if (booksService.markAsTaken(id)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_MODIFIED)
        }

    @PutMapping("/{id}/add_copy")
    fun addCopy(@PathVariable id: Long): ResponseEntity<Void> =
        if (booksService.addCopy(id)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_MODIFIED)
        }

    @DeleteMapping("/{id}/delete")
    fun deleteBook(@PathVariable id: Long) = booksService.deleteBook(id)
}