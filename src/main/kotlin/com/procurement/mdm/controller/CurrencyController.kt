package com.procurement.mdm.controller

import com.procurement.mdm.model.dto.ResponseDto
import com.procurement.mdm.service.CurrencyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/currency")
class CurrencyController(private val currencyService: CurrencyService) {

    @GetMapping
    fun getCurrencies(@RequestParam lang: String,
                      @RequestParam country: String): ResponseEntity<ResponseDto> {
        return ResponseEntity(
                currencyService.getCurrencies(
                        languageCode = lang.toLowerCase(),
                        countryCode = country.toUpperCase()),
                HttpStatus.OK)
    }
}
