package com.procurement.mdm.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
@Table(name = "cpvs")
data class CPVs(

        @Id
        @Column(name = "code")
        val code: String = "",

        @Column(name = "name")
        val name: String = "",

        @JsonIgnore
        @Column(name = "level")
        val level: Int = 1,

        @JsonIgnore
        @Column(name = "parent")
        val parent: String = "",

        @JsonIgnore
        @Column(name = "description")
        val description: String = "",

        @JsonIgnore
        @ManyToOne(optional = false, fetch = FetchType.LAZY)
        @JoinColumn(foreignKey = ForeignKey(name = "FK_cpvs_language"))
        private val language: Language? = null
)
