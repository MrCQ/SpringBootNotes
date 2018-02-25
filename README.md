


Cache注解详解

首先，在Spring Boot主类中增加@EnableCaching注解开启缓存功能

* @CacheConfig：配置在类上，主要用于配置该类中会用到的一些共用的缓存配置。在这里@CacheConfig(cacheNames = "users")：配置了该数据访问对象中返回的内容将存储于名为users的缓存对象中，我们也可以不使用该注解，直接通过@Cacheable自己配置缓存集的名字来定义。

* @Cacheable：配置在比如查询方法 `findByName` 函数上，其函数返回值将被加入缓存。同时在查询时，会先从缓存中获取，若不存在才再发起对数据库的访问。该注解主要有下面几个参数：

    * value、cacheNames：两个等同的参数（cacheNames为Spring 4新增，作为value的别名），用于指定缓存存储的集合名。由于Spring 4中新增了@CacheConfig，因此在Spring 3中原本必须有的value属性，也成为非必需项了
    * key：缓存对象存储在Map集合中的key值，非必需，缺省按照函数的所有参数组合作为key值，若自己配置需使用SpEL表达式，比如：@Cacheable(key = "#p0")：使用函数第一个参数作为缓存的key值，更多关于SpEL表达式的详细内容可参考官方文档
    * condition：缓存对象的条件，非必需，也需使用SpEL表达式，只有满足表达式条件的内容才会被缓存，比如：@Cacheable(key = "#p0", condition = "#p0.length() < 3")，表示只有当第一个参数的长度小于3的时候才会被缓存，若做此配置上面的AAA用户就不会被缓存，读者可自行实验尝试。
    * unless：另外一个缓存条件参数，非必需，需使用SpEL表达式。它不同于condition参数的地方在于它的判断时机，该条件是在函数被调用之后才做判断的，所以它可以通过对result进行判断。
    * keyGenerator：用于指定key生成器，非必需。若需要指定一个自定义的key生成器，我们需要去实现org.springframework.cache.interceptor.KeyGenerator接口，并使用该参数来指定。需要注意的是：该参数与key是互斥的
    * cacheManager：用于指定使用哪个缓存管理器，非必需。只有当有多个时才需要使用
    * cacheResolver：用于指定使用那个缓存解析器，非必需。需通过org.springframework.cache.interceptor.CacheResolver接口来实现自己的缓存解析器，并用该参数指定。

除了这里用到的两个注解之外，还有下面几个核心注解：

* @CachePut：配置于函数上，能够根据参数定义条件来进行缓存，它与@Cacheable不同的是，它每次都会真是调用函数，所以主要用于数据新增和修改操作上。它的参数与@Cacheable类似，具体功能可参考上面对@Cacheable参数的解析
* @CacheEvict：配置于函数上，通常用在删除方法上，用来从缓存中移除相应数据。除了同@Cacheable一样的参数之外，它还有下面两个参数：
    allEntries：非必需，默认为false。当为true时，会移除所有数据
    beforeInvocation：非必需，默认为false，会在调用方法之后移除数据。当为true时，会在调用方法之前移除数据。


缓存配置

完成了上面的缓存实验之后，可能大家会问，那我们在Spring Boot中到底使用了什么缓存呢？

在Spring Boot中通过 @EnableCaching 注解自动化配置合适的缓存管理器（CacheManager），Spring Boot根据下面的顺序去侦测缓存提供者：

* Generic
* JCache (JSR-107)
* EhCache 2.x
* Hazelcast
* Infinispan
* Redis
* Guava
* Simple

除了按顺序侦测外，我们也可以通过配置属性spring.cache.type来强制指定。我们可以通过debug调试查看cacheManager对象的实例来判断当前使用了什么缓存。


问题：

1. 当存在多个配置类，需要按照一定的加载顺序进行控制时候怎么处理？

Spring 4.2 之后可以利用 @Order 控制配置类的加载顺序

2. 在设置动态数据库切换时候，当我试图使用 @Import({MasterMysqlConfig.class, SlaveMysqlConfig.class})
从其他配置类中引入 master 与 slave 数据库的 DataSource 类时候，一直会报一个错误：
Requested bean is currently in creation: Is there an unresolvable circular reference?
也就是编译器认为
```
public DataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                 @Qualifier("slaveDataSource") DataSource slaveDataSource)
```
这段代码的两个参数似乎都没有正确获取到其对应的bean，所以 spring 试图使用当前的 DataSource， 导致出现循环依赖
但是，为什么呢？