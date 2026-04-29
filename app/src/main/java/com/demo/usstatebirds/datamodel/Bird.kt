package com.demo.usstatebirds.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class Bird (
    val state: String,
    val name: String,
    val nomenclature: String,
    val year: Int,
    val picture: String,
    val thumb: String,
    val description: String,
    val male: String,
    val female: String,
    val history1: String,
    val history2: String
)