package com.s0qva.application.model.dictionary;

import com.s0qva.application.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dictionary_role")
public class DictionaryRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @Column(name = "short_name")
    private String shortName;

    private String description;

    @OneToMany(mappedBy = "role")
    @Builder.Default
    @ToString.Exclude
    private List<UserRole> userRoles = new ArrayList<>();

    @Override
    public String getAuthority() {
        return name;
    }
}
