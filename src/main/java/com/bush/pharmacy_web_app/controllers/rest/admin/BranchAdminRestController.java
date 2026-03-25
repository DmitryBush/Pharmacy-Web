package com.bush.pharmacy_web_app.controllers.rest.admin;

import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchCreateDto;
import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchInfoDto;
import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.model.dto.user.AdminUserReadDto;
import com.bush.pharmacy_web_app.service.branch.BranchUserAssignmentService;
import com.bush.pharmacy_web_app.service.branch.PharmacyBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/branches")
@RequiredArgsConstructor
public class BranchAdminRestController {
    private final PharmacyBranchService pharmacyBranchService;
    private final BranchUserAssignmentService userAssignmentService;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PharmacyBranchReadDto>>> getBranches(Pageable pageable,
                                                                                      PagedResourcesAssembler<PharmacyBranchReadDto> assembler) {
        return ResponseEntity.ok(assembler.toModel(pharmacyBranchService.findPharmacyBranches(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PharmacyBranchInfoDto> findPharmacyBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(pharmacyBranchService.findBranchInfoById(id));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<AdminUserReadDto>> findAssignedUsersToBranch(@PathVariable Long id) {
        return ResponseEntity.ok(userAssignmentService.findAssignedUsersByBranchId(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PharmacyBranchReadDto> createPharmacyBranch(@RequestBody @Validated
                                                                          PharmacyBranchCreateDto branchCreateDto) {
        return ResponseEntity.ok(pharmacyBranchService.createBranch(branchCreateDto));
    }
}
