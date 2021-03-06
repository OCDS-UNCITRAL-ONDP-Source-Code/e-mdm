package com.procurement.mdm.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "locality")
data class Locality(

        @EmbeddedId
        val localityKey: LocalityKey? = null,

        @Column(name = "name")
        val name: String = "",

        @Column(name = "description")
        val description: String = "",

        @Column(name = "scheme")
        val scheme: String = "",

        @Column(name = "uri")
        val uri: String = ""

)

@Embeddable
class LocalityKey : Serializable {

    @Column(name = "code", length = 255)
    val code: String? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumns(
            JoinColumn(name = "region_code"),
            JoinColumn(name = "region_country_code"),
            JoinColumn(name = "region_country_language_code"),
            foreignKey = ForeignKey(name = "FK_locality_region"))
    private val region: Region? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LocalityKey
        if (code != other.code) return false
        if (region != other.region) return false
        return true
    }

    override fun hashCode(): Int {
        var result = code?.hashCode() ?: 0
        result = 31 * result + (region?.hashCode() ?: 0)
        return result
    }
}

data class LocalityDto(

        @JsonProperty("code")
        val code: String?,

        @JsonProperty("name")
        val name: String?
)

fun List<Locality>.getItems(): List<LocalityDto> =
        this.asSequence().map { LocalityDto(code = it.localityKey?.code, name = it.name) }.toList()
