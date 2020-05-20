package com.procurement.mdm.infrastructure.web.dto

import com.fasterxml.jackson.annotation.JsonValue
import com.procurement.mdm.application.GlobalProperties
import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, group: GroupError, id: String) {

    INTERNAL_SERVER_ERROR(status = HttpStatus.INTERNAL_SERVER_ERROR, group = Groups.SERVER, id = "00"),
    INVALID_URL(status = HttpStatus.NOT_FOUND, group = Groups.SERVER, id = "01"),

    /**
     * Request parameter is missing.
     */
    LANGUAGE_REQUEST_PARAMETER_MISSING(status = HttpStatus.BAD_REQUEST, group = Groups.REQUEST_PARAMETER_MISSING, id = "01"),
    COUNTRY_REQUEST_PARAMETER_MISSING(status = HttpStatus.BAD_REQUEST, group = Groups.REQUEST_PARAMETER_MISSING, id = "02"),

    /**
     * Request parameter is unknown.
     */
    LANGUAGE_REQUEST_PARAMETER_UNKNOWN(status = HttpStatus.BAD_REQUEST, group = Groups.REQUEST_PARAMETER_UNKNOWN, id = "01"),
    COUNTRY_REQUEST_PARAMETER_UNKNOWN(status = HttpStatus.BAD_REQUEST, group = Groups.REQUEST_PARAMETER_UNKNOWN, id = "02"),

    /**
     * Request payload is missing.
     */
    REQUEST_PAYLOAD_MISSING(status = HttpStatus.BAD_REQUEST, group = Groups.REQUEST_PAYLOAD_UNKNOWN, id = "01"),

    /**
     * Language.
     */
    INVALID_LANGUAGE_CODE(status = HttpStatus.BAD_REQUEST, group = Groups.LANGUAGE, id = "01"),

    /**
     * Country.
     */
    INVALID_COUNTRY_CODE(status = HttpStatus.BAD_REQUEST, group = Groups.COUNTRY, id = "01"),
    COUNTRY_NOT_FOUND(status = HttpStatus.NOT_FOUND, group = Groups.COUNTRY, id = "02"),


    /**
     * Region.
     */
    INVALID_REGION_CODE(status = HttpStatus.BAD_REQUEST, group = Groups.REGION, id = "01"),
    REGION_NOT_FOUND(status = HttpStatus.NOT_FOUND, group = Groups.REGION, id = "02"),

    /**
     * Locality.
     */
    INVALID_LOCALITY_CODE(status = HttpStatus.BAD_REQUEST, group = Groups.LOCALITY, id = "01"),
    LOCALITY_NOT_FOUND(status = HttpStatus.NOT_FOUND, group = Groups.LOCALITY, id = "02"),

    /**
     * Organization scheme
     */
    ORGANIZATION_SCHEME_NOT_FOUND(status = HttpStatus.NOT_FOUND, group = Groups.ORGANIZATION_SCHEME, id = "01"),

    /**
     * Organization scale
     */
    ORGANIZATION_SCALE_NOT_FOUND(status = HttpStatus.NOT_FOUND, group = Groups.ORGANIZATION_SCALE, id = "01");

    @JsonValue
    val code: String = "${status.value()}.${GlobalProperties.serviceId}.$group.$id"

    enum class Groups(override val code: String) : GroupError {
        SERVER(code = "00"),
        REQUEST_PARAMETER_MISSING(code = "01"),
        REQUEST_PARAMETER_UNKNOWN(code = "02"),
        REQUEST_PAYLOAD_UNKNOWN(code = "03"),
        LANGUAGE(code = "10"),
        COUNTRY(code = "11"),
        REGION(code = "12"),
        LOCALITY(code = "13"),
        ORGANIZATION_SCHEME(code = "14"),
        ORGANIZATION_SCALE(code = "15");

        override fun toString(): String = code
    }
}

interface GroupError {
    val code: String
}
