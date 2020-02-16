# Spring Boot

- Spring Initializy
- thymeleaf 前端技术

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

