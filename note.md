# Spring Boot

- Spring Initializy
- thymeleaf 前端技术
- OkHttp 模拟get或者post方法请求
- fastJson 快速处理json对象
- Mybatis Plus

## Controller层

- 添加@Controller注解标记该类
- 添加请求映射@GetMapping注解
- 获取请求参数注解@RequestParam
- 添加返回信息类Model
- 设置返回信息model.addAttribute()
- 返回页面return "index"

```java
@Controller
public class Hello {
@GetMapping("/")
public String HelloWorld(@RequestParam String name, Model model){
    model.addAttribute("name",name);        return "index";    }
}
```

- pom.xml配置thymeleaf依赖

```xml
  <dependency>    <groupId>org.springframework.boot</groupId>    <artifactId>spring-boot-starter-thymeleaf</artifactId></dependency>
```

## 前端页面

- 添加标签th

```html
<html xmlns:th="http://www.thymeleaf.org">
```

- 设置meta

```html
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
```

- 添加服务器返回参数

```html
  <p th:text="'Hello, ' + ${name} + '!'" />
```

## 创建GitHub授权登录
- 调用GitHub授权地址添加请求参数
```html
<li><a href="https://github.com/login/oauth/authorize?client_id=11b72d27d64c3bb3b65f&state=1">Login</a></li>
```
- GitHub返回回调地址 同时返回code
- 重新访问GitHub地址并且携带code
```java
        RequestBody body = RequestBody.create(head,JSON.toJSONString(accessTokenDTO));
        Request Request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(Request).execute()){
            responseMsg = response.body().string();
```
- GitHub返回access_token
- 通过access_token调用GitHub接口获取user信息
```java
       Request Request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(Request).execute()){
            responseMsg = response.body().string();
```
- 获取github返回的用户信息并生成对象
```java
githubUser = JSON.parseObject(responseMsg,GithubUser.class);
```

## 使用配置文件配置参数
- application.properties添加github的参数
```text
githubClint_id:11b72d27d64c3bb3b65f
githubClient_secret:14e0615c7bf319b1cd5f5726fad98358d61b8883
```
- 使用注解将属性的值注入
```java
    @Value("${githubClint_id}")
    String client_id;
    @Value("${githubClient_secret}")
    String client_secret;
    @Value("${github.client.redirectUri}")
    String redireceUri;
```

## 使用数据库保存用户信息
- 使用mybatis plus框架
- 引入依赖
```xml
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.1.tmp</version>
        </dependency>
```
- 创建user表
- 配置数据源
```text
spring.datasource.username = root
spring.datasource.password = root
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/community?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
```

- 编写model层 映射user表
```java
@Data
@TableName("user")
public class User {
    @TableId(type= IdType.AUTO)
    int id;
    @TableField
    String name;
    @TableField("account_no")
    String accountNo;
    @TableField("token")
    String token;
    @TableField("gmt_create")
    long gmtCreate;
    @TableField("gmt_modify")
    long gmtModify;
}
```

- 编写Mapper层 UserMapper
```java
public interface UserMapper extends BaseMapper<User> {
}
```

## 使用浏览器返回的cookie判断用户是否登录
- 获取前端返回的cookie

- 获取cookie中的token 在数据库中查找是否有记录
- 将查到的用户信息返回前端

## 分页功能
### config 层
- 加载mybatis plus的分页功能
```java
@Configuration
@MapperScan("com.hjh.community.mapper*")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
```

### service层
- 使用当前页和每页数量变量来控制显示数据 默认当前页1 每页数量3
```java
    int pageCount = 3;

    int pageIndex = 1;
```
- 使用mapper的方法查询出Question的分页对象
```java
    public IPage<Question> getQuestionPage(){
        //查询第index页 每页返回coun条
        Page<Question> questionPage = new Page<>(this.pageIndex,this.getPageCount());
        IPage<Question> questionIPage = questionMapper.selectPage(questionPage,null);
        return questionIPage;
    }
```
- 遍历分页Question分页对象 管理user对象组合QuestionDTO对象
```java
    public List<QuestionDTO> getQuestionDTOListPage(){
        List<Question> questions = getQuestionPage();
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question tmpQuestion:questions){
            QuestionDTO questionDTO;
            questionDTO = getQuestionDTOById(tmpQuestion.getId());
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
```
- 获取页总数
```java
    public int getQuestionPageCount(){
        int page = 0;
        int allCount = 0;
        allCount = questionMapper.selectCount(null);
        double tmp = allCount*1.0/this.getPageCount();
        page = (int) Math.ceil(tmp);
        return page;
    }
```
- 返回一个页码集合给前端遍历
```java
    public List<Integer> getQuestionCountList(){
        int count = getQuestionPageCount();
        List<Integer> integers = new ArrayList<>();
        for (int i = 1; i <= count;i++){
            integers.add(i);
        }
        return integers;
    }
```

### controller层
-  获取页码集合和QuesrtionDTO集合返回前端
```java
List<Integer> pageList = questionService.getQuestionCountList();
        questionDTOs = questionService.getQuestionDTOListPage();

        model.addAttribute("questionDTOs",questionDTOs);
        model.addAttribute("pageList",pageList);
```
- 翻页设置当前页码并重新获取值
```java
    @GetMapping("/questionDTOPage")
    public String questionDTOPage(@RequestParam int page){
        questionService.setPageIndex(page);
        return "redirect:/";
    }
```