package com.metoa.emtlibrary.repository

import com.metoa.emtlibrary.domain.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CountriesRepository: JpaRepository<Country, Long> {

    @Modifying
    @Query("update Country c set c.name = :name, c.continent = :continent where c.id = :id")
    fun editCountry(id: Long, name: String, continent: String): Int

    @Query("select count(c) from Country c")
    fun getSize(): Long
}