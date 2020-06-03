package com.softuni.game_store.domain.entities;




import com.softuni.game_store.domain.entities.base.BaseEntity;
import com.softuni.game_store.domain.entities.enums.Role;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
public class User extends BaseEntity {

    private String email;
    private String password;
    private String fullName;
    private Role role;
    private List<Game> games;

    public User() {

    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToMany(targetEntity = Game.class)
    @JoinTable(
            name = "users_games",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
    public List<Game> getGames() {
        return this.games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
