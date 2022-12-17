package com.s0qva.application.model;

import com.s0qva.application.model.dictionary.DictionaryRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id")
    private DictionaryRole role;

    public UserRole(Long id, User user, DictionaryRole role) {
        this.id = id;

        setUser(user);
        setRole(role);
    }

    public void setUser(User user) {
        this.user = user;

        user.getUserRoles().add(this);
    }

    public void setRole(DictionaryRole role) {
        this.role = role;

        role.getUserRoles().add(this);
    }
}
