package vn.emiu.picabe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.emiu.picabe.entity.enums.RoleType;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;

    @Column(nullable = false)
    private String password;

//    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoStrip> photoStrips;
}
