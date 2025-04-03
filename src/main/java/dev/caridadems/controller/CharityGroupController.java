package dev.caridadems.controller;

import dev.caridadems.dto.CharityGroupDTO;
import dev.caridadems.service.CharityGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class CharityGroupController {

    private final CharityGroupService charityGroupService;

    @PostMapping(value = "/new-group")
    public ResponseEntity<CharityGroupDTO> createProduct(@RequestBody CharityGroupDTO charityGroupDTO) {
        return ResponseEntity.ok(charityGroupService.createCharityGroup(charityGroupDTO));
    }
}
