package br.com.spa.demoparkapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = false, length = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENT;

    @CreatedDate
    private LocalDateTime date_create;

    @LastModifiedDate
    private LocalDateTime date_update;

    @CreatedBy
    private String created_by;

    @LastModifiedBy
    private String modified_by;

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
