package com.changqi.test.mapper;

import com.changqi.Application;
import com.changqi.common.DataSourceEnum;
import com.changqi.common.DynamicDataSource;
import com.changqi.mappers.AccountMapper;
import com.changqi.models.AccountEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AccountMapperTest {
    @Autowired
    AccountMapper accountMapper;


    @Test
    public void testInsert() {
        DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
        AccountEntity accountEntity = AccountEntity.builder()
                .username("user").password("password").userSex("male")
                .build();
        accountMapper.insert(accountEntity);
    }

    @Test
    public void testGetAll() {
        DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
        accountMapper.getAllAccounts().stream().forEach(account -> System.out.println(account));
    }
}
