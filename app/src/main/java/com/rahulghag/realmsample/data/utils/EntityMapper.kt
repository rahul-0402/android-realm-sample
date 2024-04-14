package com.rahulghag.realmsample.data.utils

interface DomainMapper<T, DomainModel> {
    fun mapToDomainModel(data: T): DomainModel
}