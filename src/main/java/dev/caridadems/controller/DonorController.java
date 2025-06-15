package dev.caridadems.controller;

import dev.caridadems.dto.DonorRegisterDTO;
import dev.caridadems.dto.DonorRegisterResponseDTO;
import dev.caridadems.service.DonorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donors")
@AllArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @PostMapping(value = "/new-donor")
    public ResponseEntity<DonorRegisterResponseDTO> newDonor(@RequestBody DonorRegisterDTO donorRegisterDto) {
        return ResponseEntity.ok(donorService.newDonor(donorRegisterDto));
    }
}
