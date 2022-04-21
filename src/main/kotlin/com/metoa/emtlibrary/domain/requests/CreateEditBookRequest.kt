package com.metoa.emtlibrary.domain.requests

import com.metoa.emtlibrary.domain.BookCategory

data class CreateEditBookRequest(
    val name: String,
    val category: BookCategory,
    val authorId: Long
)
