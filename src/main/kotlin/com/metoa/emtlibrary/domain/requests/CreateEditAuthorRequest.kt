package com.metoa.emtlibrary.domain.requests

data class CreateEditAuthorRequest(
    val name: String,
    val surname: String,
    val countryId: Long
)
