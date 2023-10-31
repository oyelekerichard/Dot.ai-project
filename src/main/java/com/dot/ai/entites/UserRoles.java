package com.dot.ai.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "username")
    private String username;

    @Column(name = "role_name")
    private String rolename;

    @Column(name = "active")
    private boolean active;

    public UserRoles() {
    }


    public UserRoles(Long userId, Long roleId, String username, String rolename, boolean active) {
        super();
        this.userId = userId;
        this.roleId = roleId;
        this.username = username;
        this.rolename = rolename;
        this.active = active;
    }


    @Override
    public String toString() {
        return "UserRoles [id=" + id + ", userId=" + userId + ", roleId=" + roleId + ", username=" + username
                + ", rolename=" + rolename + ", active=" + active + "]";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getRolename() {
        return rolename;
    }


    public void setRolename(String rolename) {
        this.rolename = rolename;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }
}
