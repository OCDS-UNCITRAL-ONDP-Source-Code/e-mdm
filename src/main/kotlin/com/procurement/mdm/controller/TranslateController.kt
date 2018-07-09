package com.procurement.mdm.controller

import com.procurement.mdm.model.dto.ResponseDto
import com.procurement.mdm.service.TranslateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/translate")
class TranslateController(private val translateService: TranslateService) {

    @GetMapping
    fun getTranslate(@RequestParam lang: String,
                     @RequestParam code: String,
               @RequestParam(required = false) internal: Boolean = false): ResponseEntity<ResponseDto> {
        return ResponseEntity(
                translateService.getSubMetDet(
                        languageCode = lang.toLowerCase(),
                        internal = internal),
                HttpStatus.OK)
    }
}
