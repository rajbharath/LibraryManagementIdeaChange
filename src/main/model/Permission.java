package main.model;

import java.util.HashMap;
import java.util.Map;

public enum Permission {
    ADD_BOOK(1),
    REMOVE_BOOK(2),
    BORROW_BOOK(3),
    RENEW_BOOK(4),
    RETURN_BOOK(5),
    GET_USER_STATUS(6),
    SEARCH_BY_BOOKNAME(7),
    SEARCH_BY_AUTHOR(8);
    private final int value;
    private static final Map<Integer, Permission> valueToPermissionMap = new HashMap<Integer, Permission>();

    private Permission(int value) {
        this.value = value;
    }

    static {
        for (Permission permission : Permission.values()) {
            valueToPermissionMap.put(permission.value, permission);
        }
    }

    public static Permission valueOf(int value) {
        return valueToPermissionMap.get(value);
    }

}
