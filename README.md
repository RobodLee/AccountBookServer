# 账本APP服务端开发

上一篇文章我们聊了账本APP客户端的开发，这篇文章就来聊一聊服务器端的开发。服务端核心功能就两个，一个是将客户端传过来的数据保存到数据库中，数据库我选择的是MySQL；还有一个功能就是从数据库中查询出指定用户的所有记录传回客户端。这篇文章主要就是来说一下这两个功能，用户注册和登录的功能我就不说了，比较简单，大家可以看一下我的源码。

## 一、环境搭建
整个项目是基于SpringBoot的，所以配置什么的比较简单，我就不多说了。先来说一下我的数据库表的设计，一共有两张表，一张是user表用于存储用户的信息；还有一张是record表，用于存放所有的记录。

![数据表](https://raw.githubusercontent.com/RobodLee/image_store/master/Java/%E8%B4%A6%E6%9C%ACAPP%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%BC%80%E5%8F%91/%E6%95%B0%E6%8D%AE%E8%A1%A8.png)

持久层的框架我选择的是MyBatis，所以需要在pom.xml中添加MyBatis所需的依赖。和数据库交互有关的有三个，一个是MyBatis的起步依赖，还有一个是MySQL的连接驱动，最后一个是MyBatis数据库字段类型映射，没有第三个的话存储Date会报错。

```xml
    <dependencies>

        …………

        <!--mybatis起步依赖-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.1.1</version>
        </dependency>

        <!-- MySQL连接驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>6.0.5</version>
        </dependency>

        <!-- mybatis数据库字段类型映射 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-typehandlers-jsr310</artifactId>
            <version>1.0.1</version>
        </dependency>

        …………

    </dependencies>
```

准备工作做完了就来看一下功能的实现吧。

## 二、功能实现

### 1.上传功能的实现

#### 1.1 Controller层

```java
    @RequestMapping("/uploadRecords")
    public ResultInfo<String> uploadRecords(String phoneNumber , String recordsJson) {
        ResultInfo<String> resultInfo = null;
        List<Record> records = JSONArray.parseArray(recordsJson , Record.class);
        try {
            resultInfo = recordService.uploadRecords(phoneNumber,records);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo<>();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("内部错误");
        }
        return resultInfo;
    }
```

控制层接收两个参数，一个是phoneNumber，作为判断是哪个用户传来的数据，还有一个是List的json字符串。在客户端上传的时候我将List转换成了json，所以首先需要把Json再变成List，然后调用recordService.uploadRecords方法将List传进去。

#### 1.2 Service层

```java
    @Override
    public ResultInfo<String> uploadRecords(String phoneNumber, List<Record> records) throws Exception {
        for (Record record : records) {
            int status = record.getStatus();
            if (status == 1) {  //添加数据
                System.out.println(record.toString());
                recordMapper.addRecord(phoneNumber, record);
            } else if (status == 2) {   //删除数据
                recordMapper.deleteRecord(record.getUuid());
            } else if (status == 3) {   //修改数据
                recordMapper.upgradeRecord(record);
            }
        }
        //如果在上面代码中出现了异常下面的代码就不会执行，直接将异常抛给Controller层处理
        ResultInfo<String> resultInfo = new ResultInfo<>();
        resultInfo.setFlag(true);
        resultInfo.setData("同步成功");
        return resultInfo;
    }
```

在Service层，遍历传进来的List，根据status的值去选择合适的操作，是添加、删除还是修改数据，然后去调用dao层相应的方法。再来看看Dao层具体都干了什么吧。

#### 1.3 Dao层

```java
    /**
     * 添加一条记录
     * @param phoneNumber
     * @param record
     * @throws  Exception
     */
    @Insert("insert into record(id,phoneNumber,category,content,money,status,date) " +
            "values (#{record.uuid},#{phoneNumber},#{record.category},#{record.content},#{record.money},0,#{record.date})")
    void addRecord(@Param("phoneNumber") String phoneNumber,@Param("record") Record record) throws Exception;

    /**
     * 删除指定的记录
     * @param recordId
     * @throws Exception
     */
    @Delete("DELETE FROM record WHERE id = #{recordId}")
    void deleteRecord(@Param("recordId")String recordId) throws Exception;

    /**
     * 修改服务器中的记录
     * @param record
     * @throws Exception
     */
    @Update("UPDATE record SET category=#{category},content=#{content},money=#{money},status=#{status},date=#{dateString} WHERE id = #{uuid}")
    void upgradeRecord(Record record) throws Exception;
```

很简单，我们只要编写对应的SQL语句就可以实现数据的增删改了，其它的操作都由MyBatis帮我们完成了。

说完了上传数据的实现，接下来就来看看下载数据是怎么实现的吧。


### 2.下载功能的实现

#### 2.1 Controller层

```java
    @RequestMapping("/downloadRecords")
    public ResultInfo<List<Record>> downloadRecords(String phoneNumber) {
        ResultInfo<List<Record>> resultInfo = null;
        try {
            resultInfo = recordService.downloadRecords(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo<>();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("内部错误");
        }
        return resultInfo;
    }
```

Controller层的代码很简单，就是接收一个phoneNumber参数用于判断是哪个用户发来的请求，再调用 recordService.downloadRecords(phoneNumber)方法将phoneNumber传进去，然后九来到了Service层。

#### 2.2 Service层

```java
    @Override
    public ResultInfo<List<Record>> downloadRecords(String phoneNumber) throws Exception {
        List<Record> records = recordMapper.findAllByPhoneNumber(phoneNumber);
        //如果在上面代码中出现了异常下面的代码就不会执行，直接将异常抛给Controller层处理
        ResultInfo<List<Record>> resultInfo = new ResultInfo<>();
        resultInfo.setFlag(true);
        resultInfo.setData(records);
        return resultInfo;
    }
```

在Service层中也很简单，就是调用recordMapper.findAllByPhoneNumber(phoneNumber)方法拿到查询出来的List集合。

#### 2.3 Dao层

```java
    /**
     * 根据手机号码查询该用户下所有的信息
     * @param phoneNumber
     * @throws Exception
     * @return
     */
    @Select("SELECT * FROM record WHERE phoneNumber = #{phoneNumber}")
    @Results({
            @Result(property = "uuid",column = "id",id = true),
            @Result(property = "category",column = "category"),
            @Result(property = "content",column = "content"),
            @Result(property = "money",column = "money"),
            @Result(property = "status",column = "status"),
            @Result(property = "date",column = "date")
    })
    List<Record> findAllByPhoneNumber(String phoneNumber) throws Exception;
```

数据库中的record的id字段对应了Record类中的uuid属性，为什么直接id对应id呢？原因我上篇文章也说过了，就是客户端的SQLite中id只能是int型的，所以用加了个uuid属性。

## 总结

服务器端并没有什么复杂的功能，就是一些简单的CRUD,我相信大家跟着我文章的思路就可以捋清楚我的代码了。

