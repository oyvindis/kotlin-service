package no.oyvindis.kotlin_service.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class ApplicationStatusController {

    @GetMapping("/climate-api/reading/ping")
    fun ping(): ResponseEntity<Void> =
        ResponseEntity.ok().build()

    @GetMapping("/climate-api/reading/ready")
    fun ready(): ResponseEntity<String> =
        ResponseEntity<String>("I am ready!", HttpStatus.OK)

}
