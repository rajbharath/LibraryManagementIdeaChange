package main.model;

import java.util.List;

public class User {
    private String username;
    private List<Permission> permissions;

    public User(String username, List<Permission> permissions) {
        this.username = username;
        this.permissions = permissions;
    }

    public boolean isAuthorized(Permission permission) {
        return permissions.contains(permission);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username != null && username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }


}
