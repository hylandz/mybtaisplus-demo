# ssm-shiro
`Spring`+`SpringMVC`+`Mybatis-Plus`+`Shiro`
## 测试
```java
/**
 * mybatis-plus测试
 *
 * @author xielx on 2019/7/15
 */
public class MPTest {

  ApplicationContext context = null;

  UserMapper userMapper = null;
  @Before
  public void init(){
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
     userMapper = context.getBean("userMapper", UserMapper.class);
  }

  @Test
  public void testApplication(){
    DataSource dataSource = context.getBean("dataSource", DataSource.class);
    System.out.println(dataSource);
  }

  @Test
  public void testMybatisPlusCRUD(){

    //新增
    User u1 = new User();
    u1.setUserId(4L);
    u1.setDeptId(1L);
    u1.setUserAccount("G0009263");
    u1.setUserPassword("123456");
    userMapper.insert(u1);//会进行非空判断
    //删除
    userMapper.deleteById(4L);
    //修改
    User u2 = new User();
    u1.setUserId(2L);
    u1.setDeptId(1L);
    u1.setUserPassword("123456");
    userMapper.updateById(u2);//有非空判断
    //查询
    User u3 = userMapper.selectById(1L);
    System.out.println(u3);

    // 多个id查询
    List<Long> list = new ArrayList<>();
    list.add(1L);
    list.add(2L);
    list.add(3L);
    List<User> userList =userMapper.selectBatchIds(list);
    System.out.println(userList);

    // key=数据库的列名,value=值
    Map<String,Object> map = new HashMap<>();
    map.put("user_account","admin");
    map.put("salt","123");
    List<User> userList2 = userMapper.selectByMap(map);
    System.out.println(userList2);

  }

  /**
   * 构造器 查询
   */
  @Test
  public void testEntityWrapperSelect(){
    // 查询id>1且密码相同的用户
    /*List<User> userList = userMapper.selectPage(new Page<User>(1,2),
            new EntityWrapper<User>()
                    .gt("user_id",1)
                    .eq("user_password","123456"));
    System.out.println(userList);*/

    //使用Condition.create()替换new EntityWrapper<User>()
    List<User> userList = userMapper.selectPage(new Page<>(1,2),
            Condition.create()
                    .gt("user_id",1L)
                    .eq("user_password","123456"));
    System.out.println(userList);

  }

  @Test
  public void testWrapperUpdate(){

    User user = new User();
    user.setUserPassword("123456");
    user.setUserId(2L);
    //user.setLocked(true);
    userMapper.update(user,new EntityWrapper<User>().eq("user_id",2L));
  }
}
```

```java
/**
 * activeRecord:活动记录
 * AR:领域模型模式,一个模型类对应一个关系型数据库中的一个表,一个模型类的实例对应表中的一行记录
 * @author xielx on 2019/7/16
 */
public class MPActiveRecordTest {

  ApplicationContext context = null;

  @Before
  public void init(){
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
  }


  @Test
  public void testARSelect(){
    User user = new User();
//    User user1 = user.selectById(1L);
//    System.out.println(user1);
//    Integer r = user.selectCount("1=1");
//    System.out.println(r);

    Page<User> userPage = user.selectPage(new Page<>(1,5),new EntityWrapper<>());
    List<User> userList = userPage.getRecords();
    System.out.println("userList:" + userList);

  }

  @Test
  public void testARUpdate(){
    User user = new User();
    user.setUserId(2L);
    user.setUserPassword("123");
    user.updateById();

  }
}
```
