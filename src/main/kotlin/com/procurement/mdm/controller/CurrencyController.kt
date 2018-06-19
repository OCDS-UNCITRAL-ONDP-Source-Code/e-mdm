package com.procurement.mdm.controller

import com.procurement.budget.model.bpe.ResponseDto
import com.procurement.mdm.service.CurrencyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/currency")
class CurrencyController(private val currencyService: CurrencyService) {

    @GetMapping
    fun getCurrencies(@RequestParam lang: String,
                      @RequestParam country: String): ResponseEntity<ResponseDto> {
        return ResponseEntity(
                currencyService.getCurrencies(lang.toUpperCase(), country.toUpperCase()),
                HttpStatus.OK)
    }
}