package com.backend.moneymanager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_profiles")
@Data
//@Getter
//@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProfileEntity {

    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    @Column(unique = true)
    private String email;
    private String password;
    private String profileImageUrl;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private String activationToken;

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }


    @PrePersist
    public void prePersist() {
        if(this.isActive == null) {
            isActive = false;
        }
    }
}
