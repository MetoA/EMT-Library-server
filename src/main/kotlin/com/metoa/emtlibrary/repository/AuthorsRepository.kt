package com.metoa.emtlibrary.repository

import com.metoa.emtlibrary.domain.Author
import com.metoa.emtlibrary.domain.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AuthorsRepository: JpaRepository<Author, Long> {

    @Modifying
    @Query("update Author a set a.name = :name, a.surname = :surname, a.country = :country where a.id = :id")
    fun editAuthor(id: Long, name: String, surname: String, country: Country): Int
}