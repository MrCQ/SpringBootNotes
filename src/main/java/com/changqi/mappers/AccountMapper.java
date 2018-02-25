package com.changqi.mappers;

import com.changqi.models.AccountEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {
    @Select("select * from account")
    @Results({
            @Result(property = "userSex", column = "user_sex", javaType = String.class)
    })
    List<AccountEntity> getAllAccounts();

    @Insert("insert into account (username, password, user_sex) values (#{username}, #{password}, #{userSex})")
    void insert(AccountEntity accountEntity);

    @Delete("delete from account where username = #{username}")
    void delete(String username);

    @Select("select * from account where username = #{username}")
    AccountEntity getAccountByName(@Param("username") String username);
}
