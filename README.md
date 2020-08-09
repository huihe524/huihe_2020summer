# huihe_2020summer
2020假期spring、vue学习

# 1、注解和反射
## 1.注解

Java 注解（Annotation）又称 Java 标注，是 JDK5.0 引入的一种注释机制。

Java 语言中的**类、方法、变量、参数和包**等都可以被标注。

### 内置的注解

**作用在代码的注解是**

- @Override - 检查该方法是否是重写方法。如果发现其父类，或者是引用的接口中并没有该方法时，会报编译错误。

- @Deprecated - 标记过时方法。如果使用该方法，会报编译警告。

- @SuppressWarnings - 指示编译器去忽略注解中声明的警告。

**作用在其他注解的注解(或者说 元注解)是:**

- @Retention - 标识这个注解怎么保存，是只在代码中，还是编入class文件中，或者是在运行时可以通过反射访问。

- @Documented - 标记这些注解是否包含在用户文档中。

- @Target - 标记这个注解应该是哪种 Java 成员。

- @Inherited - 标记这个注解是继承于哪个注解类(默认 注解并没有继承于任何子类)

**从 Java 7 开始，额外添加了 3 个注解:**

- @SafeVarargs - Java 7 开始支持，忽略任何使用参数为泛型变量的方法或构造函数调用产生的警告。

- @FunctionalInterface - Java 8 开始支持，标识一个匿名函数或函数式接口。

- @Repeatable - Java 8 开始支持，标识某注解可以在同一个声明上使用多次。

### 3 个主干类

Annotation.java

```java
 package java.lang.annotation;
 public interface Annotation {

   boolean  equals(Object obj);

   int hashCode();

  String toString();

  Class<? extends Annotation> annotationType();
}
```

ElementType.java

```java
 package java.lang.annotation;

 public enum ElementType {
  TYPE,        /* 类、接口（包括注释类型）或枚举声明  */

  FIELD,        /* 字段声明（包括枚举常量）  */

  METHOD,       /* 方法声明  */

  PARAMETER,      /* 参数声明  */

  CONSTRUCTOR,     /* 构造方法声明 */

  LOCAL_VARIABLE,   /* 局部变量声明  */

  ANNOTATION_TYPE,   /* 注释类型声明  */

  PACKAGE        /* 包声明 */
}
```

RetentionPolicy.java

```java
package java.lang.annotation;
public enum RetentionPolicy {
  SOURCE,    /* Annotation信息仅存在于编译器处理期间，编译器处理完之后就没有该Annotation信息了  */

  CLASS,       /* 编译器将Annotation存储于类对应的.class文件中。默认行为  */

  RUNTIME       /* 编译器将Annotation存储于class文件中，并且可由JVM读入 */
}
```

**每 1 个 Annotation 都与 1 个 RetentionPolicy关联，并且与 1～n 个 ElementType关联**

**自定义注解**

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface myAnnotation {
    String value() default "";
}
```

### 作用

Annotation 是一个辅助类，它在 Junit、Mybatis、Spring 等工具框架中被广泛使用。

1）编译检查  -- @Override、@Deprecated、@SuppressWarnings

2) 在反射中使用 Annotation

3) 根据 Annotation 生成帮助文档 -@Documented

## 2.反射

**[JAVA反射机制](https://baike.baidu.com/item/JAVA反射机制/6015990)是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意方法和属性；这种动态获取信息以及动态调用对象方法的功能称为java语言的反射机制。**

**反射被视为动态语言的关键**

**静态语言（强类型语言）**

静态语言是在编译时变量的数据类型即可确定的语言，多数静态类型语言要求在使用变量之前必须声明数据类型。 
例如：C++、Java、Delphi、C#等。

**动态语言（弱类型语言）**

动态语言是在运行时确定数据类型的语言。变量使用之前不需要类型声明，通常变量的类型是被赋值的那个值的类型。 
例如PHP/ASP/Ruby/Python/Perl/ABAP/SQL/JavaScript/Unix Shell等等。

**像这样的Python语句的赋值操作和eval函数，静态语言就做不到**

![](img\1.png)

**Java不是动态语言，但Java具有一定的动态性，表现在以下几个方面:**

- **反射机制；**

- 动态字节码操作；（动态(运行中)改变某个类的结构（添加/删除/修改 新的类/属性/方法））

- 动态编译；(通过Runtime调用javac, 通过JavaCompiler动态编译)

- 执行其他脚本代码；(例如 通过js引擎可以执行javascript代码)



### 核心类

`java.lang.reflect`包反射核心类有核心类`Class`、`Constructor`、`Method`、`Field`、`Parameter`

#### Class

​		**加载完某个类之后，在堆内存的方法区中就产生了对应的Class类型的对象（一个类只有一个Class对象），这个Class对象就包含了完整的类的结构信息。可以通过这个对象看到类的结构。**

1. 在Object类中定义了public final Class getClass()，该方法会被所有的子类继承。
2. 一个Class对象对应的是一个加载到JVM中的一个.class文件；
3. 一个加载的类在JVM中，只有一个Class实例；
4. 通过Class可以完整地得到一个类中的所有加载的结构

#####  获取Class实例

```java
/*
若已知具体的类，通过类的class属性获取，该方法最为安全可靠，程序性能最高。  Class clazz = 类名.class;
*/
Class c1 = User.class; 
/*
已知某个类的实例，通过调用该实例的getClass()方法获取Class对象。  Class clazz = 实例.getClass();
*/
Class c2 = new User().getClass();
/*
已知一个类的全类名，且该类在类路径下，可通过Class类的静态方法forName()获取，需要处理ClassNotFoundException异常。 Class clazz = Class.forName("包名.类名");
全限定类名：包名+类名
*/
Class c3 = Class.forName("com.huihe.entity.User");
/*
内置基本数据类型的TYPE属性 如 Integer.TYPE
*/
System.out.println(c1 == c2 && c2 == c3); //true 一个类只有一个Class对象
```

##### 什么类型有Class对象

```java
Class c1 = Object.class; //类 class java.lang.Object
Class c2 = Comparable.class; // 接口 interface java.lang.Comparable
Class c3 = String[].class; //一维数组 class [Ljava.lang.String;
Class c4 = int[][].class; //二维数组 class [[I
Class c5 = ElementType.class; //枚举 class java.lang.annotation.ElementType
Class c6 = Override.class; // 注解 interface java.lang.Override
Class c7 = int.class; // 基本数据类型 int
Class c8 = void.class; // void void
```

##### 常用API

```java
Class<User> c = (Class<User>) Class.forName("com.huihe.entity.User");//获取Class对象
String name = c.getName();//全限定类名
String simpleName = c.getSimpleName();//类名
Field username = c.getDeclaredField("username");//某个字段 getField();
Field[] declaredFields = c.getDeclaredFields();//所有字段 任何权限 不包含继承过来得
Method getUsername = c.getDeclaredMethod("getUsername");//某个方法
Method[] declaredMethods = c.getDeclaredMethods();//所有方法
Constructor<User> declaredConstructor = c.getDeclaredConstructor();//某个构造
Constructor<?>[] declaredConstructors = c.getDeclaredConstructors();//所有构造
User user = c.newInstance(); //通过Class调用无参构造生成对象
User user1 = declaredConstructor.newInstance(); //通过无参构造器对象构造对象
User user2 = (User) declaredConstructors[1].newInstance("1","2"); //通过全参构造器对象构造对象
username.setAccessible(true); //跳过权限检测 暴力反射
System.out.println(username.get(user2)); //调用属性对象的方法，查看在某个实体中的值 user2.username
System.out.println(getUsername.invoke(user2)); //方法对象的方法，激活某个实体的方法
System.out.println(Arrays.toString(c.getAnnotations())); //查看类上的注解
System.out.println(Arrays.toString(username.getAnnotations())); //查看属性上的注解
System.out.println(Arrays.toString(getUsername.getAnnotations())); //查看方法上的注解
for(Annotation annotation :c.getAnnotations()){ //遍历类上注解
    if (annotation.annotationType().equals(MyAnnotation.class)){ //如果有我的自定义注解
        User instance = c.newInstance(); //构造一个user对象
    }
}
```

## 3.例子

### 3.1、基于注解动态实例化对象

#### 3.1.1 注解

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyAnnotation {
    String value() default "";
}
```

#### 3.1.2 类

```java
package com.huihe.bean;

import com.huihe.annotation.MyAnnotation;

public class User {
    //Flied
    @MyAnnotation("hxj")
    private String username;

    @MyAnnotation("123")
    private String password;

    public User(){}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Method
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
```

#### 3.1.3 测试程序

```java
@Test
public void test3() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    Class<?> c = Class.forName("com.huihe.bean.User");
    Field[] fields = c.getDeclaredFields();
    Object instance = c.newInstance();
    for(Field field : fields){
        MyAnnotation annotation = field.getAnnotation(MyAnnotation.class);
        field.setAccessible(true);
        System.out.println(annotation.value());
        field.set(instance, annotation.value());
    }
    System.out.println(instance);
}
```

### 3.2、基于配置文件动态实例化对象

#### 3.2.1 bean.properties

```properties
class=com.huihe.bean.User
username=hxj
password=123
```

#### 3.2.2 类 

同3.1.2

#### 3.2.3 测试程序

```java
@Test
public void test4() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    Properties properties = new Properties();
    InputStream inputStream = this.getClass().getResourceAsStream("/bean.properties");
    properties.load(inputStream);
    String clz = properties.getProperty("class", "");
    Class<?> c = Class.forName(clz);
    Object instance = c.newInstance();
    for(Field field : c.getDeclaredFields()){
        field.setAccessible(true);
        field.set(instance, properties.getProperty(field.getName(),""));
    }
    System.out.println(instance);
}
```

# Spring框架概述

### 优点：

1. 方便解耦，开发（Spring就是一个大工厂，可以将所有对象的创建和依赖关系维护都交给spring管理）**IoC**
2. spring支持**aop**编程（spring提供面向切面编程，可以很方便的实现对程序进行权限拦截和运行监控等功能）
3. 声明式事务的支持（通过配置就完成对事务的支持，不需要手动编程）
4. 方便程序的测试，spring 对junit支持，可以通过注解方便的测试spring 程序
5. 方便集成各种优秀的框架（Hibernate 、Mybatis、Redis、Ehcache、Quartz、RabbitMQ、Shiro 等）
6. 降低javaEE API的使用难度（Spring 对javaEE开发中非常难用的一些API 例如JDBC, javaMail, 远程调用等，都提供了封装，是这些API应用难度大大降低）

**Spring是一个轻量级控制反转(IoC)和面向切面(AOP)的容器框架。**

### 缺点：

1. 大量反射，运行效率低了。
2. spring像一个胶水，将框架黏在一起，后面拆分的话就不容易拆分了。
3. 配置繁琐(spring boot简化了许多配置，”约定优于配置“)

### 组成模块：

Spring框架包含组织为约20个模块的功能。这些模块分为核心容器IoC，数据访问/集成，Web，AOP（面向切面的编程），检测，消息传递和测试，如下图所示。

![](img\spring-overview.png)

如果使用[Maven](https://maven.apache.org/)进行依赖管理，您甚至无需显式提供日志记录依赖。例如，要创建应用程序上下文并使用依赖项注入来配置应用程序，您的Maven依赖项将如下所示：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>4.3.28.RELEASE</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

# 2.Spring IoC容器

​	**控制反转**（Inversion of Control，缩写为**IoC**），是[面向对象编程](https://baike.baidu.com/item/面向对象编程)中的一种设计原则，可以用来减低计算机代码之间的[耦合度](https://baike.baidu.com/item/耦合度)。其中最常见的方式叫做**依赖注入**（Dependency Injection，简称**DI**），还有一种方式叫“依赖查找”（Dependency Lookup）。通过控制反转，对象在被创建的时候，由一个调控系统内所有对象的外界实体将其所依赖的对象的引用传递给它。也可以说，依赖被注入到对象中。IoC/DI

spring实现:通过反射获取**set方法**或者**构造方法**，注入所要依赖的对象。 set注入/构造注入

下图是Spring工作方式的高级视图。您的应用程序类与配置元数据结合在一起，以便在`ApplicationContext`创建和初始化之后，您便拥有了完整配置的可执行系统或应用程序。

![](img\container-magic.png)



### 1.配置文件一般格式：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

<!--    <bean id="dao" class="com.huihe.dao.impl.OracleDao">-->
<!--    </bean>-->
<!--   静态工厂  -->
<!--    <bean id="dao" class="com.huihe.factory.DaoFactory" factory-method="getInstance">-->
<!--    </bean>-->
	<!--   实例工厂  -->
    <bean id="factory" class="com.huihe.factory.DaoFactory">
        <!-- collaborators and configuration for this bean go here -->
    </bean>

    <bean id="dao" factory-bean="factory" factory-method="getInstance">
        <!-- collaborators and configuration for this bean go here -->
    </bean>
</beans>
```

#### 读取配置文件的Bean

```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
dao = (Dao) context.getBean("dao");
dao.insert();
```

### 2.构造器注入

#### 1.准备一个类User（构造方法）

```java
public class User {
    private String username;
    private Date birthday;
    
	//构造方法是关键
    public User(String username, Date birthday) {
        this.username = username;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
```

#### 2.xml配置文件的三种构造器注入方式

```xml
	<!--  构造器注入  -->
    <bean id="myDate" class="java.util.Date"/>

    <bean id="user1" class="com.huihe.model.User">
        <constructor-arg>
            <ref bean="myDate"/>
        </constructor-arg>
        <constructor-arg>
            <value>hxj</value>
        </constructor-arg>
    </bean>

    <bean id="user2" class="com.huihe.model.User">
        <constructor-arg name="username" value="hxj"/>
        <constructor-arg name="birthday" ref="myDate"/>
    </bean>

    <bean id="user3" class="com.huihe.model.User">
        <constructor-arg index="0" value="hxj"/>
        <constructor-arg index="1" ref="myDate"/>
    </bean>
```

#### 3.测试程序

```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
User user1 = (User) context.getBean("user1");
User user2 = context.getBean("user2", User.class);
User user3 = context.getBean("user3", User.class);
System.out.println(user1);
System.out.println(user2);
System.out.println(user3);
```

### 3.set注入

#### 1.准备User类（setter方法）

```java
public class User {
    private String username;
    private Date birthday;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
```

#### 2.xml的配置

```xml
<!--  set注入  -->
    <bean id="myDate" class="java.util.Date"/>

    <bean id="user" class="com.huihe.model.User">
        <property name="username"  value=""/>
        <property name="birthday"> 
        	<null/>
        </property>
    </bean>
```

#### 3.测试程序

```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
User user = (User) context.getBean("user");
System.out.println(user);
```



### 4.p命名空间(p-**) property

#### 1.User类同set注入

#### 2.xml中加入p命名空间约束及配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
	 <!--  p命名空间-->
    <bean id="myDate" class="java.util.Date"/>
    <bean id="user" class="com.huihe.model.User"
          p:username="hxj" p:birthday-ref="myDate"/>
</beans>
```

#### 3.测试程序同set注入



### 5.c命名空间(c-**) constructor

#### 1.User类同构造器注入

#### 2.xml中加入c命名空间约束及两种配置方法

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
       <!--  p命名空间-->
        <bean id="myDate" class="java.util.Date"/>
        <bean id="user1" class="com.huihe.model.User"
         c:_0="hxj" c:_1-ref="myDate"/>

        <bean id="user2" class="com.huihe.model.User"
          c:username="hxj" c:birthday-ref="myDate"/>
</beans>
```

#### 3.测试程序

```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
User user1 = (User) context.getBean("user1");
User user2 = (User) context.getBean("user2");
System.out.println(user1);
System.out.println(user2);
```



### 6.注入集合类型

#### 1.xml配置

```xml
<bean id="user" class="com.huihe.model.User">
    <property name="config">
        <props>
            <prop key="name">hxj</prop>
            <prop key="password">123</prop>
        </props>
    </property>
    <property name="foods">
        <array>
            <value>水果</value>
            <value>面</value>
            <value>米</value>
        </array>
    </property>

    <property name="hobbies">
        <list>
            <value>吃饭</value>
            <value>打豆豆</value>
            <value>睡觉</value>
            <value>睡觉</value>
        </list>
    </property>
    <property name="plays">
        <set>
            <value>足球</value>
            <value>蓝球</value>
            <value>蓝球</value>
        </set>
    </property>
    <property name="plans">
        <map>
            <entry key="周一" value="上班"/>
            <entry key="周二" value="上班"/>
            <entry key="周二" value="睡觉"/>
        </map>
    </property>
</bean>
```

#### 2.User类

```java
public class User {
    String[] foods;
    List<String> hobbies;
    Set<String> plays;
    Map<String, String> plans;
    Properties config;

    public void setFoods(String[] foods) {
        this.foods = foods;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public void setPlays(Set<String> plays) {
        this.plays = plays;
    }

    public void setPlans(Map<String, String> plans) {
        this.plans = plans;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "User{" +
                "hobbies=" + hobbies +
                ", plays=" + plays +
                ", foods=" + Arrays.toString(foods) +
                ", plans=" + plans +
                ", config=" + config +
                '}';
    }
}
```

#### 3.测试程序

```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
User user = (User) context.getBean("user");
System.out.println(user);
```



### 7.懒加载

在bean标签的属性中 设置如下属性即可为懒加载  默认为false

```xml
lazy-init="true"
```

### 8.Bean作用域(Scope)

| Scope                                                        | Description                                                  |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [singleton](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-singleton) | (Default) Scopes a single bean definition to a single object instance for each Spring IoC container. |
| [prototype](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-prototype) | Scopes a single bean definition to any number of object instances. |
| [request](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-request) | Scopes a single bean definition to the lifecycle of a single HTTP request. That is, each HTTP request has its own instance of a bean created off the back of a single bean definition. Only valid in the context of a web-aware Spring `ApplicationContext`. |
| [session](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-session) | Scopes a single bean definition to the lifecycle of an HTTP `Session`. Only valid in the context of a web-aware Spring `ApplicationContext`. |
| [application](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-application) | Scopes a single bean definition to the lifecycle of a `ServletContext`. Only valid in the context of a web-aware Spring `ApplicationContext`. |
| [websocket](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/web.html#websocket-stomp-websocket-scope) | Scopes a single bean definition to the lifecycle of a `WebSocket`. Only valid in the context of a web-aware Spring `ApplicationContext`. |

|                             范围                             | 描述                                                         |
| :----------------------------------------------------------: | :----------------------------------------------------------- |
|                             单例                             | （默认）为每个Spring IoC容器将单个bean定义的作用域限定为单个对象实例。 |
| [原型](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-prototype) | 将单个bean定义的作用域限定为任意数量的对象实例。             |
| [请求](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-request) | 将单个bean定义的范围限定为单个HTTP请求的生命周期。也就是说，每个HTTP请求都有一个在单个bean定义后面创建的bean实例。仅在可感知网络的Spring上下文中有效`ApplicationContext`。 |
| [会议](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-session) | 将单个bean定义的范围限定为HTTP的生命周期`Session`。仅在可感知网络的Spring上下文中有效`ApplicationContext`。 |
| [应用](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-application) | 将单个bean定义的作用域限定为的生命周期`ServletContext`。仅在可感知网络的Spring上下文中有效`ApplicationContext`。 |
| [网络套接字](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/web.html#websocket-stomp-websocket-scope) | 将单个bean定义的作用域限定为的生命周期`WebSocket`。仅在可感知网络的Spring上下文中有效`ApplicationContext`。 |

request、session、application、websocket都是web专用的，我们现在只看singleton和prototype

这都是设置在bean标签的scope属性的值,

singleton为单例，即只有一份实例,每次取到的都是同一个对象

![](img/singleton.png)

prototype为原型，即有多个实例，每次取到的都是新的对象

![](img/prototype.png)

### 9.自动注入

bean标签的属性autowire

| 模式          | 说明                                                         |
| :------------ | :----------------------------------------------------------- |
| `no`          | （默认）无自动装配。Bean引用必须由`ref`元素定义。对于大型部署，不建议更改默认设置，因为显式指定协作者可提供更好的控制和清晰度。在某种程度上，它记录了系统的结构。 |
| `byName`      | 按属性名称自动布线。Spring寻找与需要自动装配的属性同名的bean。例如，如果一个bean定义被设置为按名称自动装配并且包含一个`master`属性（即它具有一个 `setMaster(..)`方法），那么Spring将查找一个名为的bean定义`master`并使用它来设置该属性。 |
| `byType`      | 如果容器中恰好存在一个该属性类型的bean，则使该属性自动连接。如果存在多个错误，则将引发致命异常，这表明您不能`byType`对该bean 使用自动装配。如果没有匹配的bean，则什么都不会发生（未设置该属性）。 |
| `constructor` | 类似于`byType`但适用于构造函数参数。如果容器中不存在构造函数参数类型的一个bean，则将引发致命错误。 |

#### 1.准备User类和Dog类

- Date类的按类型和名称注入有问题，我还没想明白

```java
public class User {
    private String username;
    private Dog dog;
    
    public User(){
        System.out.println("默认构造");
    }

    public User(Dog dog) {
        System.out.println("dog");
        this.dog = dog;
    }

    public User(String username, Dog dog) {
        System.out.println("username and dog");
        this.username = username;
        this.dog = dog;
    }
	
    public void setUsername(String username) {
        this.username = username;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Dog getDog() {
        return dog;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", dog=" + dog +
                '}';
    }
}

public class Dog {
    public void wang(){
        System.out.println("汪。。。");
    }
}
```

#### 2.按名称自动注入的xml

```xml
<bean id="dog" class="com.huihe.model.Dog"/>
<bean id="user" class="com.huihe.model.User" autowire="byName">
    <property name="username"  value="hxj"/>
</bean>
```

#### 3.按类型自动注入的xml

```xml
<bean id="dog" class="com.huihe.model.Dog"/>
<bean id="user" class="com.huihe.model.User" autowire="byType">
    <property name="username"  value="hxj"/>
</bean>
```

#### 4.按构造自动注入的xml

```xml
<bean id="dog" class="com.huihe.model.Dog"/>
<bean id="username" class="java.lang.String"/>
    <bean id="user" class="com.huihe.model.User" autowire="constructor">
	</bean>
```

#### 5.测试程序

```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
User user = (User) context.getBean("user");
System.out.println(user);
user.getDog().wang();
```

### 10.引入注解配置IoC

#### 1.xml约束以及开启注解支持

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>
	<bean id="dog" class="com.huihe.model.Dog"/>
    <bean id="user" class="com.huihe.model.User"/>
</beans>
```

#### 2.User类和Dog类

```java
public class User {

    private String username;

    @Autowired //当属性required=false时，找不到也不会报错  先根据type 后根据id
    @Qualifier("dog") //指定具体名称
    @Resource(name = "dog") //相当于@Autowired和@Qualifier的组合，默认是Autowired     设置了name会按照名称，先name后type
    private Dog dog;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", dog=" + dog +
                '}';
    }
}
public class Dog {
    public void wang(){
        System.out.println("汪。。。");
    }
}
```

#### 3.测试程序

```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
User user = (User) context.getBean("user");
System.out.println(user);
```

### 11.xml中启用注解

#### 1.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>
    <context:component-scan base-package="com.huihe"/>
</beans>
```

#### 2.User类和Dog类

```java
@Component
public class User {

    @Value("hxj")
    private String username;

    @Autowired
    private Dog dog;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", dog=" + dog +
                '}';
    }
}

@Component
public class Dog {
    public void wang(){
        System.out.println("汪。。。");
    }
}
```

#### 3.测试程序

```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
User user = (User) context.getBean("user");
System.out.println(user);
```

### 12.纯注解javaConfig

#### 1.新建一个配置类

```java
@Configuration
@ComponentScan("com.huihe.model")
public class MyConfig {
    @Bean
    public User getUser(){
        return new User();
    }
}
```

#### 2.User类和Dog类

```java
public class User {

    @Value("hxj")
    private String username;

    @Autowired
    private Dog dog;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", dog=" + dog +
                '}';
    }
}

@Component
public class Dog {
    public void wang(){
        System.out.println("汪。。。");
    }
}
```

#### 3.测试程序

```java
ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
User user = (User) context.getBean("getUser");
System.out.println(user);
```

### 13.总结

Xml配置概括：写起来比较灵活、修改方便，但是写和维护麻烦，关系一目了然，适用于任何场景。

注解简单概括：写起来比较简单、方便，看起来也简洁，但是修改麻烦，不是自定义的类用不了。

推荐使用xml配置bean,注解实现属性注入。

# 3.Spring AOP

## 1.前置-代理模式

![img](img\2.png)

在代理模式（Proxy Pattern）中，一个类代表另一个类的功能。这种类型的设计模式属于结构型模式。

在代理模式中，我们创建具有现有对象的对象，以便向外界提供功能接口。

我们将创建一个接口和实现了该接口的实体类。

我们的客户类使用代理来获取要加载的委托类对象，并按照需求进行显示。

### 1.静态代理

接口和代理全部手动编码实现。

#### 1.接口

```java
public interface UserService {
    void insert();

    void select();

    void delete();

    void update();
}
```

#### 2.委托类

```java
public class UserServiceImpl implements UserService {

    @Override
    public void insert() {
        System.out.println("insert");
    }

    @Override
    public void select() {
        System.out.println("select");
    }

    @Override
    public void delete() {
        System.out.println("delete");
    }

    @Override
    public void update() {
        System.out.println("update");
    }
}
```

#### 3.代理类

```java
public class UserServiceProxy implements UserService {

    private UserService userService;

    public UserServiceProxy(UserService userService){
        this.userService = userService;
    }

    @Override
    public void insert() {
        log("insert");
        userService.insert();
    }

    @Override
    public void select() {
        log("select");
        userService.select();
    }

    @Override
    public void delete() {
        log("delete");
        userService.delete();
    }

    @Override
    public void update() {
        log("update");
        userService.update();
    }

    private void log(String msg){
        System.out.println("[Info] "+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +" 执行了"+msg+"方法");
    }
}
```

增加了公共得日志记录功能

#### 4.测试程序

```java
public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        UserServiceProxy proxy = new UserServiceProxy(userService);
        proxy.delete();
    }
}
```

#### 5.总结

优点：可以做到在符合开闭原则的情况下对目标对象进行功能扩展。

缺点：我们得为每一个服务都得创建代理类，工作量太大，不易管理。同时接口一旦发生改变，代理类也得相应修改。  

### 2.jdk动态代理

不需要手动的创建代理类，只需要编写一个动态处理器，真正的代理对象由JDK再运行时通过**反射**为我们动态的来创建。

#### 1.接口

同静态代理

#### 2.委托类

同静态代理

#### 3.动态处理器

```java
public class ProxyHandler implements InvocationHandler {

    private Object object;
    public ProxyHandler(Object object){
        this.object = object;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{UserService.class}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("[info] "+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +" 执行了"+method.getName()+"方法。");
        Object result = method.invoke(object, args);
        return result;
    }
}
```

#### 4.测试程序

```java
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ProxyHandler handler = new ProxyHandler(userService);
        UserService proxy = (UserService)handler.getProxy();
        proxy.delete();
    }
}
```

#### 5.总结

优点：

1.Java本身支持，不用担心依赖问题，随着版本稳定升级；

2.代码实现简单；

缺点：

1.目标类必须实现某个接口，换言之，没有实现接口的类是不能生成代理对象的；

2.代理的方法必须都声明在接口中，否则，无法代理；

3.执行速度性能相对cglib较低；

### 3.cglib动态代理

CGLIB基于继承来实现代理，代理对象实际上是目标对象的子类，它内部通过第三方类库ASM，加载目标对象类的class文件，修改字节码来生成子类，生成类的过程较低效，但生成类以后的执行很高效，可以通过将ASM生成的类进行缓存来解决生成类过程低效的问题；

导入cglib依赖

```xml
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.2.4</version>
</dependency>
```

#### 1.接口

同静态代理

#### 2.委托类

同静态代理

#### 3.动态处理器

```java
public class CglibProxyHandler implements MethodInterceptor {

  @Override
  public Object intercept(Object proxy, Method method, Object[] params, MethodProxy methodProxy)
          throws Throwable {
    System.out.println("执行前----");
    Object result = methodProxy.invokeSuper(proxy, params);
    System.out.println("执行后----");
    return result;
  }

  public Object getProxy(Object target) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(target.getClass());
    enhancer.setCallback(this);
    enhancer.setClassLoader(target.getClass().getClassLoader());
    return enhancer.create();
  }

}
```

#### 4.测试程序

```java
@Test
public void test(){
    UserServiceImpl service = new UserServiceImpl();
    CglibProxyHandler handler = new CglibProxyHandler();
    UserService proxy = (UserService) handler.getProxy(service);
    proxy.delete();
}
```

#### 5.总结

Cglib原理：

1.通过字节码增强技术动态的创建代理对象；

2.代理的是代理对象的引用；

Cglib优缺点：

优点：

1.代理的类无需实现接口；

2.执行速度相对JDK动态代理较高；

缺点： 

1.字节码库需要进行更新以保证在新版java上能运行；

2.动态创建代理对象的代价相对JDK动态代理较高；(创建代理速度慢)

## 2.AOP概念

AOP （Aspect Oriented Programming ）是一种编程思想，是面向对象编程（OOP）的一种补充。面向对象编程将程序抽象成各个层次的对象，而面向切面编程是将程序抽象成各个切面。

1. 切面（Aspect）：是对横切逻辑的抽象，一个切面由通知和切点两部分组成。在实际应用中，切面被定义成一个类。
2. 通知（Advice）：是横切逻辑的具体实现。在实际应用中，通知被定义成切面类中的一个方法，方法体内的代码就是横切代码。通知的分类：以目标方法为参照点，根据切入方位的不同，可分为前置通知（Before）、后置通知（AfterReturning）、异常通知（AfterThrowing）、最终通知（After）与环绕通知（Around）5种。
3. 切点（Pointcut）：用于说明将通知织入到哪个方法上，它是由切点表达式来定义的。
4. 目标对象（Target）：是指那些即将织入切面的对象。这些对象中已经只剩下干干净净的核心业务逻辑的代码了，所有的横切逻辑的代码都等待 AOP 框架的织入。
5. 代理对象（Proxy）：是指将切面应用到目标对象之后由 AOP 框架所创建的对象。可以简单地理解为，代理对象的功能等于目标对象的核心业务逻辑功能加上横切逻辑功能，代理对象对使用者而言是透明的。
6. 织入（Weaving）：是指将切面应用到目标对象从而创建一个新的代理对象的过程。



使用AOP织入，需要导入依赖包

```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.3</version>
</dependency>
```



## 3.通过xml配置实现

### 1.方式一、使用Spring的API接口

#### 1.UserSevice和Impl同上

#### 2.Log类

```java
public class Log implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println(o.getClass().getName()+"的"+method.getName()+"方法执行了。");
    }
}
```

以前置通知为例

#### 3.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns="http://www.springframework.org/schema/beans"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">
<!--注册bean-->
    <bean id="userService" class="com.huihe.service.impl.UserServiceImpl"/>
    <bean id="log" class="com.huihe.aop.Log"/>
<!--配置aop-->
    <aop:config>
        <!--切入点-->
        <aop:pointcut id="pointcut" expression="execution(* com.huihe.service.impl.UserServiceImpl.*(..))"/>
		<!--执行环绕增强-->
        <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
    </aop:config>
</beans>
```

#### 4.测试程序

```java
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService service = context.getBean("userService", UserService.class);
        service.delete();
    }
}
```

### 2.方式二、自定义切面来实现AOP

#### 1.UserSevice和Impl同上

#### 2.Log类

```java
public class Log{
    public void before(){
        System.out.println("====方法执行前");
    }

    public void after(){
        System.out.println("方法执行后====");
    }
}
```

#### 3.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns="http://www.springframework.org/schema/beans"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">
<!--注册bean-->
    <bean id="userService" class="com.huihe.service.impl.UserServiceImpl"/>
    <bean id="log" class="com.huihe.aop.Log"/>
<!--配置aop-->
    <aop:config>
        <aop:aspect ref="log">
            <!--切入点-->
            <aop:pointcut id="pointcut" expression="execution(* com.huihe.service.impl.UserServiceImpl.*(..))"/>
            <!--通知-->
            <aop:before method="before" pointcut-ref="pointcut"/>
            <aop:after method="after" pointcut-ref="pointcut"/>
        </aop:aspect>
    </aop:config>
</beans>
```

#### 4.测试程序同上



## 4.通过注解实现

### 1.UserSevice和Impl同上

### 2.Log类

```java
@Aspect //标识切面
public class Log{
    @Pointcut("execution(* com.huihe.service.impl.UserServiceImpl.*(..))")
    public void point(){}

    @Before("point()")
    public void before(){
        System.out.println("方法执行前");
    }
    @After("point()")
    public void after(){
        System.out.println("方法执行最终");
    }

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("====方法执行前");
        Object o = joinPoint.proceed();
        System.out.println("====方法执行后");
    }

    @AfterReturning("point()")
    public void afterReturning() throws Throwable {
        System.out.println("方法执行后");
    }

    @AfterThrowing("point()")
    public void afterThrowing(){
        System.out.println("异常通知");
    }
}
```

### 3.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns="http://www.springframework.org/schema/beans"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--注册bean-->
    <bean id="userService" class="com.huihe.service.impl.UserServiceImpl"/>
    <bean id="log" class="com.huihe.aop.Log"/>

    <!--开启注解支持-->
    <aop:aspectj-autoproxy/>
</beans>
```

### 4.测试程序同上



# 4.数据库与Javaweb基础

到隔壁仓库看以前写的

https://github.com/huihe524/2019JavaStudyCode/tree/master/huihe-groupOne-jdbc

