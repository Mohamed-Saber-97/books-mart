package org.example.booksmart.service;

import org.example.booksmart.model.Admin;
import org.example.booksmart.repository.AdminRepository;
import org.example.booksmart.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;


    public Admin update(Admin newAdmin) {
        Admin existingAdmin = adminRepository.findById(newAdmin.getId()).orElse(null);
        if (existingAdmin == null) {
            return null;
        }
        existingAdmin.getAccount().setAddress(newAdmin.getAccount().getAddress());
        existingAdmin.setAccount(newAdmin.getAccount());
        existingAdmin.getAccount().setPassword(PasswordUtil.hashPassword(newAdmin.getAccount().getPassword()));
        return adminRepository.save(existingAdmin);
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
        return adminRepository.findAdminByAccount_PhoneAndIsDeletedFalse(phoneNumber);
    }
}