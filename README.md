# GreanHand
## 技术栈
- Spring Boot spring整体框架
- Spring MVC spring的web框架 处理http请求
- thymeleaf 前端框架
- okhttp 模拟http请求
- FastJSON 处理JSON
- Mybatis-Plus ORM框架 对象关系映射
- lombok 自动注入set get toString等方法

## 实现功能
### 用户登录
- 使用Github的登录接口进行登录,登出
- 登录成功后获取用户信息并入库
- 将登录信息写入session,前端进行展示
- 浏览器发送cookie到服务器 服务器判断cookie返回用户

### 问题发布
- 发布问题并存入数据库
- 发布的问题在首页展示
- 点击发布的问题进入详情

### 20200224
- 增加首页显示发布的问题，点击进入详情
- 优化登录逻辑，session中有user对象则不去数据库中重新获取

### 20200225
- 首页问题分页显示