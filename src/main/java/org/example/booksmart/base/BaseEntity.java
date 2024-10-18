package org.example.booksmart.base;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<ID> implements Serializable {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    @NotNull
    private final Instant createdDate = Instant.now();

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_on", nullable = false)
    @NotNull
    private final Instant lastUpdatedOn = Instant.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private ID id;

    @Column(name = "is_deleted", nullable = false)
    @NotNull
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @Column(name = "created_by", length = 100)
    private String createdBy = null;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy = null;

    public void markAsDeleted() {
        this.isDeleted = true;
    }

}

