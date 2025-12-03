package com.example.taskapi.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Embeddable
data class AuditFields(

    @CreatedBy
    @Column(name = "created_by")
    var createdBy: String? = null,

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: Instant? = null,

    @LastModifiedBy
    @Column(name = "updated_by")
    var updatedBy: String? = null,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant? = null
)
