package com.changqi.mappers;

import com.changqi.models.AccountEntity;

import java.util.List;

public interface AccountXmlMapper {
    List<AccountEntity> getAll();

    AccountEntity getOne(Long id);

    void insert(AccountEntity user);

    void update(AccountEntity user);

    void delete(Long id);
}
