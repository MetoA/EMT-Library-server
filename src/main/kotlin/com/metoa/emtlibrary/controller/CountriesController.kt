package com.metoa.emtlibrary.controller

import com.metoa.emtlibrary.domain.Country
import com.metoa.emtlibrary.domain.requests.CreateEditCountryRequest
import com.metoa.emtlibrary.errors.NotFoundException
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
@RequestMapping("/api/countries")
class CountriesController(
    val countriesService: CountriesService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Country =
        countriesService.findById(id).orElseThrow { NotFoundException("Country with id $id not found") }

    @GetMapping
    fun getAll() = countriesService.findAll()

    @GetMapping("/paged")
    fun getPaged(pageable: Pageable): List<Country> = countriesService.getPaged(pageable).content

    @PostMapping("/create")
    fun createCountry(@RequestBody request: CreateEditCountryRequest): ResponseEntity<Country> = with(request) {
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(countriesService.createCountry(name, continent))
    }

    @PutMapping("/{id}/edit")
    fun editCountry(@PathVariable id: Long, @RequestBody request: CreateEditCountryRequest): ResponseEntity<Void> = with(request) {
        if (countriesService.editCountry(id, name, continent)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_MODIFIED)
        }
    }

    @DeleteMapping("/{id}/delete")
    fun deleteCountry(@PathVariable id: Long) = countriesService.deleteCountry(id)
}