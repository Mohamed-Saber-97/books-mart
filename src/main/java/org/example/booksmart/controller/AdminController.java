package org.example.booksmart.controller;

import org.example.booksmart.converter.AdminToAdminDtoConverter;
import org.example.booksmart.dto.AdminDto;
import org.example.booksmart.model.Admin;
import org.example.booksmart.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<AdminDto> admins = adminService.findAll();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id) {
        AdminDto adminDto = adminService.findById(id);
        return ResponseEntity.ok(adminDto);
    }

    @PatchMapping
    public ResponseEntity<AdminDto> updateAdmin(@RequestBody Admin admin) {
        AdminDto updatedAdmin = adminService.update(admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminService.delete(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

    @PostMapping
    public ResponseEntity<AdminDto> addAdmin(@RequestBody Admin admin) {
        Admin newAdmin = adminService.save(admin);
        AdminDto adminDto = AdminToAdminDtoConverter.convert(newAdmin);
        return ResponseEntity.ok(adminDto);
    }
}
