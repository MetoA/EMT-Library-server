package com.metoa.emtlibrary.controller

import com.metoa.emtlibrary.domain.Author
import com.metoa.emtlibrary.domain.requests.CreateEditAuthorRequest
import com.metoa.emtlibrary.errors.NotFoundException
import com.metoa.emtlibrary.service.AuthorsService
import com.metoa.emtlibrary.service.CountriesService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/authors")
class AuthorsController(
    val authorsService: AuthorsService,
    val countriesService: CountriesService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Author =
        authorsService.findById(id).orElseThrow { NotFoundException("Author with id $id not found") }

    @GetMapping
    fun getAll() = authorsService.findAll()

    @GetMapping("/paged")
    fun getPaged(pageable: Pageable): List<Author> = authorsService.getPaged(pageable).content

    @PostMapping("/create")
    fun createAuthor(@RequestBody request: CreateEditAuthorRequest): ResponseEntity<Author> = with(request) {
        val country = countriesService.findById(countryId).orElseThrow { NotFoundException("Country with id $countryId not found") }
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authorsService.createAuthor(name, surname, country))
    }

    @PutMapping("/{id}/edit")
    fun editAuthor(@PathVariable id: Long, @RequestBody request: CreateEditAuthorRequest): ResponseEntity<Void> = with(request) {
        val country = countriesService.findById(countryId).orElseThrow { NotFoundException("Country with id $countryId not found") }
        if (authorsService.editAuthor(id, name, surname, country)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_MODIFIED)
        }
    }

    @DeleteMapping("/{id}/delete")
    fun deleteAuthor(@PathVariable id: Long) = authorsService.deleteAuthor(id)
}