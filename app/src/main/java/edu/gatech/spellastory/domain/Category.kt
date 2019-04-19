package edu.gatech.spellastory.domain

import java.io.Serializable

class Category(val code: Int, val name: String) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (code != other.code) return false

        return true
    }

    override fun hashCode() = code

    override fun toString() = "Category(name='$name')"
}