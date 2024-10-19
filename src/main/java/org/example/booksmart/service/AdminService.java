package org.example.booksmart.service;

import org.example.booksmart.converter.AdminToAdminDtoConverter;
import org.example.booksmart.dto.AdminDto;
import org.example.booksmart.model.Admin;
import org.example.booksmart.repository.AdminRepository;
import org.example.booksmart.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;


    public AdminDto update(Admin newAdmin) {
        Admin existingAdmin = adminRepository.findById(newAdmin.getId()).orElse(null);
        if (existingAdmin == null) {
            return null;
        }
        existingAdmin.getAccount().setAddress(newAdmin.getAccount().getAddress());
        existingAdmin.setAccount(newAdmin.getAccount());
        existingAdmin.getAccount().setPassword(PasswordUtil.hashPassword(newAdmin.getAccount().getPassword()));
        Admin savedAdmin = adminRepository.save(existingAdmin);
        return AdminToAdminDtoConverter.convert(savedAdmin);
    }

    public boolean checkValidLoginCredentials(String email, String password) {
        Admin admin = adminRepository.findAdminByAccount_EmailAndIsDeletedFalse(email);
        if (admin != null) {
            return PasswordUtil.checkPassword(password, admin.getAccount().getPassword());
        }
        return false;
    }

    public Admin findByEmail(String email) {
        return adminRepository.findAdminByAccount_EmailAndIsDeletedFalse(email);
    }

    public Admin findByPhoneNumber(String phoneNumber) {
        return adminRepository.findAdminByAccount_PhoneNumberAndIsDeletedFalse(phoneNumber);
    }

    public List<AdminDto> findAll() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(AdminToAdminDtoConverter::convert).toList();
    }

    public AdminDto findById(Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        return AdminToAdminDtoConverter.convert(admin);
    }

    public void delete(Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin != null) {
            admin.setIsDeleted(true);
            adminRepository.save(admin);
        }
    }

    public Admin save(Admin admin) {
        admin.getAccount().setPassword(PasswordUtil.hashPassword(admin.getAccount().getPassword()));
        return adminRepository.save(admin);
    }
}
