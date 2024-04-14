package com.rahulghag.realmsample.domain.models

data class Task(
    val id: String,
    val title: String,
    val isCompleted: Boolean,
    val timestamp: String
)