package com.metoa.emtlibrary.domain.requests

data class CreateEditCountryRequest(
    val name: String,
    val continent: String
)