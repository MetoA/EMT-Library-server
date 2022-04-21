package com.metoa.emtlibrary.service

import com.metoa.emtlibrary.domain.Country
import com.metoa.emtlibrary.repository.CountriesRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
class CountriesService(
    val countriesRepository: CountriesRepository
) {
    fun findById(id: Long) = countriesRepository.findById(id)

    fun getPaged(pageable: Pageable): Page<Country> = countriesRepository.findAll(pageable)

    fun findAll(): List<Country> = countriesRepository.findAll()

    @Transactional
    fun createCountry(name: String, continent: String): Country {
        val country = Country(
            id = 0,
            name = name,
            continent = continent
        )

        return countriesRepository.save(country)
    }

    @Transactional
    fun editCountry(id: Long, name: String, continent: String) = countriesRepository.editCountry(id, name, continent) > 0

    @Transactional
    fun deleteCountry(id: Long) = countriesRepository.deleteById(id)
}