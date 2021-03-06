package com.procurement.mdm.infrastructure.web.controller.address

import com.procurement.mdm.application.service.address.AddressLocalityService
import com.procurement.mdm.application.service.address.AddressLocalityServiceImpl
import com.procurement.mdm.domain.model.identifier.LocalityIdentifier
import com.procurement.mdm.domain.repository.AdvancedLanguageRepository
import com.procurement.mdm.domain.repository.address.AddressLocalityRepository
import com.procurement.mdm.domain.repository.scheme.LocalitySchemeRepository
import com.procurement.mdm.domain.repository.scheme.RegionSchemeRepository
import com.procurement.mdm.infrastructure.repository.AbstractRepositoryTest
import com.procurement.mdm.infrastructure.repository.loadSql
import com.procurement.mdm.infrastructure.web.controller.RestExceptionHandler
import com.procurement.mdm.infrastructure.web.controller.documentation.ModelDescription
import com.procurement.mdm.infrastructure.web.dto.ErrorCode
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_COUNTRY_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_LANGUAGE_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_LOCALITY_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_REGION_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LANGUAGE_REQUEST_PARAMETER_MISSING
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LANGUAGE_REQUEST_PARAMETER_UNKNOWN
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LOCALITY_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LOCALITY_SCHEME_NOT_FOUND
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@ExtendWith(RestDocumentationExtension::class)
class AddressLocalityControllerIT : AbstractRepositoryTest() {
    companion object {
        private const val COUNTRY = "md"
        private const val EMPTY_COUNTRY = "   "
        private const val INVALID_COUNTRY = "INVALID_COUNTRY"

        private const val REGION = "region-1"
        private const val UNKNOWN_REGION = "unknown-region"

        private const val EMPTY_REGION = "   "

        private const val LOCALITY = "locality-1"
        private const val UNKNOWN_LOCALITY = "unknown-locality"
        private const val EMPTY_LOCALITY = "   "

        private const val LANGUAGE = "ro"
        private const val EMPTY_LANGUAGE = "   "
        private const val INVALID_LANGUAGE = "INVALID_LANGUAGE"
        private const val UNKNOWN_LANGUAGE = "ul"

        private const val SCHEME = "CUATM"
        private const val UNKNOWN_SCHEME = "unknown-scheme"
    }

    private lateinit var mockMvc: MockMvc
    private lateinit var addressLocalityService: AddressLocalityService

    @Autowired
    private lateinit var addressLocalityRepository: AddressLocalityRepository

    @Autowired
    private lateinit var advancedLanguageRepository: AdvancedLanguageRepository

    @Autowired
    private lateinit var regionSchemeRepository: RegionSchemeRepository

    @Autowired
    private lateinit var localitySchemeRepository: LocalitySchemeRepository

    @BeforeEach
    fun init(restDocumentation: RestDocumentationContextProvider) {
        addressLocalityService = AddressLocalityServiceImpl(
            addressLocalityRepository = addressLocalityRepository,
            advancedLanguageRepository = advancedLanguageRepository,
            regionSchemeRepository = regionSchemeRepository,
            localitySchemeRepository = localitySchemeRepository
        )

        val controller = AddressLocalityController(addressLocalityService)
        val restExceptionHandler = RestExceptionHandler()
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(restExceptionHandler)
            .apply<StandaloneMockMvcBuilder>(
                documentationConfiguration(restDocumentation)
                    .uris()
                    .withScheme("https")
                    .withHost("eprocurement.systems")
                    .and()
                    .snippets()
                    .and()
                    .operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
            )
            .build()
    }

    @Test
    fun `Getting all localities is successful`() {
        initData()

        val expected = LocalityIdentifier(
            scheme = "CUATM",
            id = LOCALITY.toUpperCase(),
            description = "mun.Chişinău RO",
            uri = "http://statistica.md"
        )

        val url = getUrl(country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.data.length()", equalTo(2)))
            .andExpect(jsonPath("$.data[0].scheme", equalTo(expected.scheme)))
            .andExpect(jsonPath("$.data[0].id", equalTo(expected.id)))
            .andExpect(jsonPath("$.data[0].description", equalTo(expected.description)))
            .andExpect(jsonPath("$.data[0].uri", equalTo(expected.uri)))
            .andDo(
                document(
                    "address/locality/get_all/success",
                    responseFields(ModelDescription.Address.Locality.collection())
                )
            )
    }

    @Test
    fun `Getting all localities is successful (list of localities is empty)`() {
        initLanguages()
        initSchemes()
        initCountries()
        initRegions()

        val url = getUrl(country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.data.length()", equalTo(0)))
            .andDo(
                document(
                    "address/locality/get_all/success_empty",
                    responseFields(ModelDescription.Address.Locality.emptyCollection())
                )
            )
    }

    @Test
    fun `Getting all localities is successful (language request parameter is missing)`() {
        initLanguages()
        initSchemes()
        initCountries()
        initRegions()

        val url = getUrl(country = COUNTRY, region = REGION)
        mockMvc.perform(get(url))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(LANGUAGE_REQUEST_PARAMETER_MISSING.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("The request is missing a required query parameter - 'lang'.")
                )
            )
            .andDo(
                document(
                    "address/locality/get_all/errors/no_lang_query_param",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code is successful`() {
        initData()

        val expected = LocalityIdentifier(
            scheme = "CUATM",
            id = LOCALITY.toUpperCase(),
            description = "mun.Chişinău RO",
            uri = "http://statistica.md"
        )

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.data.scheme", equalTo(expected.scheme)))
            .andExpect(jsonPath("$.data.id", equalTo(expected.id)))
            .andExpect(jsonPath("$.data.description", equalTo(expected.description)))
            .andExpect(jsonPath("$.data.uri", equalTo(expected.uri)))
            .andDo(
                document(
                    "address/locality/get_by_code/success",
                    responseFields(ModelDescription.Address.Locality.one())
                )
            )
    }

    @Test
    fun `Getting the locality by code is error (language request parameter is missing)`() {
        initData()

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(LANGUAGE_REQUEST_PARAMETER_MISSING.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("The request is missing a required query parameter - 'lang'.")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code/errors/no_lang_query_param",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code is error (language request parameter is empty)`() {
        initData()

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", EMPTY_LANGUAGE)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(INVALID_LANGUAGE_CODE.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("Invalid language code (value is blank).")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code/errors/empty_lang",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code is error (language request parameter is invalid)`() {
        initData()

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", INVALID_LANGUAGE)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(INVALID_LANGUAGE_CODE.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("Invalid language code: '$INVALID_LANGUAGE' (wrong length: '${INVALID_LANGUAGE.length}' required: '2').")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code/errors/invalid_lang",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code is error (language request parameter is unknown)`() {
        initData()

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", UNKNOWN_LANGUAGE)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(LANGUAGE_REQUEST_PARAMETER_UNKNOWN.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("The unknown code of a language '$UNKNOWN_LANGUAGE'.")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code/errors/unknown_lang",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code is error (country code is empty)`() {
        initData()

        val url = getUrl(locality = LOCALITY, country = EMPTY_COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(INVALID_COUNTRY_CODE.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("Invalid country code (value is blank).")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code/errors/empty_country",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code is error (country code is invalid)`() {
        initData()

        val url = getUrl(locality = LOCALITY, country = INVALID_COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(INVALID_COUNTRY_CODE.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("Invalid country code: '$INVALID_COUNTRY' (wrong length: '${INVALID_COUNTRY.length}' required: '2').")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code/errors/invalid_country",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code is error (region code is empty)`() {
        initData()

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = EMPTY_REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(INVALID_REGION_CODE.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("Invalid region code (value is blank).")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code/errors/empty_region",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code is error (locality code is empty)`() {
        initData()

        val url = getUrl(locality = EMPTY_LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(INVALID_LOCALITY_CODE.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("Invalid locality code (value is blank).")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code/errors/empty_locality",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code is error (locality is not found)`() {
        initLanguages()

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
        )
            .andExpect(status().isNotFound)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(LOCALITY_NOT_FOUND.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("The locality by code '$LOCALITY', country '$COUNTRY', region '$REGION', language '$LANGUAGE' not found.")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code/errors/locality_not_found",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting all schemes of localities is successful`() {
        initData()

        val url = getUrlSchemesAttribute(country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.data.schemes.length()", equalTo(1)))
            .andExpect(jsonPath("$.data.schemes[0]", equalTo(SCHEME)))
            .andDo(
                document(
                    "address/locality/get_schemes/success",
                    responseFields(ModelDescription.Address.Locality.schemesCollection())
                )
            )
    }

    @Test
    fun `Getting all schemes of localities is successful (list of using schemes are empty)`() {
        initLanguages()
        initSchemes()
        initCountries()
        initRegions()

        val url = getUrlSchemesAttribute(country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.data.schemes.length()", equalTo(0)))
            .andDo(
                document(
                    "address/locality/get_schemes/success",
                    responseFields(ModelDescription.Address.Locality.schemesCollection())
                )
            )
    }

    @Test
    fun `Getting the locality by code with scheme is successful`() {
        initData()

        val expected = LocalityIdentifier(
            scheme = SCHEME,
            id = LOCALITY.toUpperCase(),
            description = "mun.Chişinău",
            uri = "http://statistica.md"
        )

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
                .param("scheme", SCHEME)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.data.scheme", equalTo(expected.scheme)))
            .andExpect(jsonPath("$.data.id", equalTo(expected.id)))
            .andExpect(jsonPath("$.data.description", equalTo(expected.description)))
            .andExpect(jsonPath("$.data.uri", equalTo(expected.uri)))
            .andDo(
                document(
                    "address/locality/get_by_code_and_scheme/success",
                    responseFields(ModelDescription.Address.Locality.one())
                )
            )
    }

    @Test
    fun `Getting the locality by code with scheme is error (unknown scheme)`() {
        initData()

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
                .param("scheme", UNKNOWN_SCHEME)
        )
            .andExpect(status().isNotFound)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(LOCALITY_SCHEME_NOT_FOUND.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("Locality scheme '$UNKNOWN_SCHEME' not found.")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code_and_scheme/errors/unknown_scheme",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code with scheme is error (unknown location by scheme)`() {
        initData()

        val url = getUrl(locality = UNKNOWN_LOCALITY, country = COUNTRY, region = REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
                .param("scheme", SCHEME)
        )
            .andExpect(status().isNotFound)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(LOCALITY_NOT_FOUND.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("The locality by code '$UNKNOWN_LOCALITY' and scheme '$SCHEME' not found.")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code_and_scheme/errors/unknown_location",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    @Test
    fun `Getting the locality by code with scheme is error (region not linked to locality)`() {
        initData()

        val url = getUrl(locality = LOCALITY, country = COUNTRY, region = UNKNOWN_REGION)
        mockMvc.perform(
            get(url)
                .param("lang", LANGUAGE)
                .param("scheme", SCHEME)
        )
            .andExpect(status().isNotFound)
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.errors.length()", equalTo(1)))
            .andExpect(jsonPath("$.errors[0].code", equalTo(ErrorCode.LOCALITY_NOT_LINKED_TO_REGION.code)))
            .andExpect(
                jsonPath(
                    "$.errors[0].description",
                    equalTo("The locality by code '$LOCALITY' and scheme '$SCHEME' is not linked to region '$UNKNOWN_REGION'.")
                )
            )
            .andDo(
                document(
                    "address/locality/get_by_code_and_scheme/errors/region_not_linked_to_locality",
                    responseFields(ModelDescription.responseError())
                )
            )
    }

    private fun initData() {
        initLanguages()
        initSchemes()
        initCountries()
        initRegions()
        initLocalities()
        initLocalitySchemes()
        initRegionSchemes()
    }

    private fun initLanguages() {
        val sqlLanguages = loadSql("sql/languages_init_data.sql")
        executeSQLScript(sqlLanguages)
    }

    private fun initSchemes() {
        val sqlSchemes = loadSql("sql/list_schemes_init_data.sql")
        executeSQLScript(sqlSchemes)
    }

    private fun initCountries() {
        val sqlCountries = loadSql("sql/address/countries_init_data.sql")
        executeSQLScript(sqlCountries)
    }

    private fun initRegions() {
        val sqlRegions = loadSql("sql/address/regions_init_data.sql")
        executeSQLScript(sqlRegions)
    }

    private fun initLocalities() {
        val sqlLocalities = loadSql("sql/address/localities_init_data.sql")
        executeSQLScript(sqlLocalities)
    }

    private fun initLocalitySchemes() {
        val sqlLocalities = loadSql("sql/scheme/locality_schemes_init_data.sql")
        executeSQLScript(sqlLocalities)
    }

    private fun initRegionSchemes() {
        val sqlRegions = loadSql("sql/scheme/region_schemes_init_data.sql")
        executeSQLScript(sqlRegions)
    }

    private fun getUrl(locality: String? = null, country: String, region: String): String =
        if (locality == null)
            String.format("/addresses/countries/%s/regions/%s/localities", country, region)
        else
            String.format("/addresses/countries/%s/regions/%s/localities/%s", country, region, locality)

    private fun getUrlSchemesAttribute(country: String, region: String): String =
        String.format("/addresses/countries/%s/regions/%s/localities/attributes/schemes", country, region)
}
