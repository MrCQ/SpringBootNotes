问题：

1. 在设置动态数据库切换时候，当我试图使用 @Import({MasterMysqlConfig.class, SlaveMysqlConfig.class})
从其他配置类中引入 master 与 slave 数据库的 DataSource 类时候，一直会报一个错误：
Requested bean is currently in creation: Is there an unresolvable circular reference?
也就是编译器认为
```
public DataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                 @Qualifier("slaveDataSource") DataSource slaveDataSource)
```
这段代码的两个参数似乎都没有正确获取到其对应的bean，所以 spring 试图使用当前的 DataSource， 导致出现循环依赖
但是，为什么呢？