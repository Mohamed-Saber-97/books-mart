package org.example.booksmart.repository;

import org.example.booksmart.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository< Admin, Long > {
    public Admin findAdminByAccount_EmailAndIsDeletedFalse(String email);
    
    public Admin findAdminByAccount_PhoneNumberAndIsDeletedFalse(String phone);
}