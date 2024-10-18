package org.example.booksmart.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.booksmart.base.BaseEntity;

@Entity
@Table(name = "admin")
@Data
@EqualsAndHashCode(callSuper = false)
public class Admin extends BaseEntity< Long > {
    @Embedded
    @Valid
    private Account account;
}