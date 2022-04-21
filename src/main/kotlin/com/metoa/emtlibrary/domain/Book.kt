package com.metoa.emtlibrary.domain

import javax.persistence.*

@Entity
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String,

    @Enumerated(value = EnumType.STRING)
    val category: BookCategory,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: Author,

    @Column(name = "available_copies")
    val availableCopies: Int = 0
)
