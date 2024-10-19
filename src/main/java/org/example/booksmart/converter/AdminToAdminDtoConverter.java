package org.example.booksmart.converter;

import org.example.booksmart.dto.AdminDto;
import org.example.booksmart.model.Admin;

public class AdminToAdminDtoConverter {
    public static AdminDto convert(Admin admin) {
        return new AdminDto(
                admin.getId(),
                admin.getAccount().getName(),
                admin.getAccount().getEmail(),
                admin.getAccount().getPhoneNumber(),
                admin.getAccount().getJob(),
                admin.getAccount().getBirthday(),
                admin.getAccount().getAddress().getCountry(),
                admin.getAccount().getAddress().getCity(),
                admin.getAccount().getAddress().getStreet(),
                admin.getAccount().getAddress().getZipcode()
        );
    }
}
