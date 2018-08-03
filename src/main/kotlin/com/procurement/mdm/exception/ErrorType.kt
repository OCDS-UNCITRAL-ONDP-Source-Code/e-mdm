package com.procurement.mdm.exception

enum class ErrorType constructor(val code: String, val message: String) {
    NOT_IMPLEMENTED("00.00", "Not implemented."),
    LANG_UNKNOWN("00.01", "Language not found."),
    COUNTRY_UNKNOWN("00.02", "Country for current language not found."),
    UNIT_CLASS_UNKNOWN("00.03", "Unit class for current language not found."),
    ENTITY_KIND_UNKNOWN("00.03", "Entity kind not found."),
    CPV_CODE_UNKNOWN("00.04", "Cpv code not found."),
    CPVS_CODE_UNKNOWN("00.05", "CPVs code not found."),
    INVALID_DATA("00.06", "Invalid data."),
    TRANSLATION_UNKNOWN("00.07", "Translation for current language not found."),
    CURRENCY_UNKNOWN("00.08", "Currency not found."),
    COUNTRY_NOT_FOUND("00.09", "Country not found."),
    RS_UNKNOWN("00.10", "Registration scheme not found."),
    REGION_UNKNOWN("00.11", "Region not found."),
    LOCALITY_UNKNOWN("00.12", "Locality not found."),
    PMD_UNKNOWN("00.13", "Procurement method details for current language not found."),
    INVALID_JSON_TYPE("01.01", "Invalid json type."),
    INVALID_ITEMS("01.02", "Invalid items code."),
    INVALID_CPV("01.03", "Invalid cpv code."),
    INVALID_COMMON_CPV("01.04", "Invalid common cpv class code."),
    INVALID_CPVS("01.05", "Invalid cpvs code."),
    INVALID_UNIT("01.06", "Invalid unit code."),
    INVALID_PMD("01.07", "Invalid pmd."),
    INVALID_BUYER("01.08", "Invalid buyer."),
    INVALID_PR_ENTITY("01.09", "Invalid procuring entity."),
    INVALID_COUNTRY("01.10", "Invalid country.");
}
