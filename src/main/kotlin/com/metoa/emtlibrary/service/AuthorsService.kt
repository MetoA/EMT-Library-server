package com.metoa.emtlibrary.service

import com.metoa.emtlibrary.domain.Author
import com.metoa.emtlibrary.domain.Country
import com.metoa.emtlibrary.repository.AuthorsRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AuthorsService(
    val authorsRepository: AuthorsRepository
) {

    fun findById(id: Long) = authorsRepository.findById(id)

    fun getPaged(pageable: Pageable): Page<Author> = authorsRepository.findAll(pageable)

    fun findAll(): List<Author> = authorsRepository.findAll()

    fun getSize() = authorsRepository.getSize()

    @Transactional
    fun createAuthor(name: String, surname: String, country: Country): Author {
        val author = Author(
            id = 0,
            name = name,
            surname = surname,
            country = country
        )

        return authorsRepository.save(author)
    }

    @Transactional
    fun editAuthor(id: Long, name: String, surname: String, country: Country) =
        authorsRepository.editAuthor(id, name, surname, country) > 0

    @Transactional
    fun deleteAuthor(id: Long) = authorsRepository.deleteById(id)
}