package com.TuT597.movies.authorities;

public enum MARole {
    ADMIN, USER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
