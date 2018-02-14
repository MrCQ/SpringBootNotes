package com.changqi.models;

import lombok.Builder;

@Builder
public class AccountEntity {
    int id;
    String username;
    String password;
    String userSex;
}
