package com.capstone.grocery.model;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN,
    USER,
    DELIVERY_PARTNER,
    STORE_MANAGER;
}