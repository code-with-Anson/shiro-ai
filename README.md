# 🌊 汐落 (Shiro) - 让生活清晰有序的个人记账系统 🌊

## 📝 项目介绍

汐落（Shiro）的设计理念源自日语"shiro"（白色）🤍，象征清晰与纯净的生活态度，旨在帮助用户通过记账使生活变得有序且宁静。如同潮汐的涨落一样自然
🌊，记录每一笔收支，让财务状况一目了然。

## 🏗️ 项目架构

汐落采用前后端分离的微服务架构设计，后端分为两个独立仓库：

### 🌐 前端

- Vue3 + TypeScript
- 技术栈：axios、pinia、vue-router、vant、element-plus、apache-echart等
- 支持移动端和桌面端网页

### ⚙️ 后端 (分为两个独立仓库)

#### 1️⃣ shiro-backend 仓库 (Spring Boot 2.7)

- 多模块结构

  📦

    - **shiro-main**: 核心记账功能模块
    - **shiro-gateway**: API网关模块

- 技术栈

  🛠️

    - Spring Boot 2.7
    - MyBatis-Plus
    - Spring Cloud Alibaba (Nacos)
    - Redis
    - MySQL
    - JWT

#### 2️⃣ shiro-ai 仓库 (Spring Boot 3.4)

- 基于 Spring AI 和 OpenAI 接口 🧠
- 提供 AI 助手功能
- 使用 Spring Boot 3.4 版本

## ✨ 核心功能

### 📊 账单管理 (shiro-main)

- **账单记录**：轻松记录日常收入和支出 💰
- **分类管理**：支持自定义收支分类 🏷️
- **数据可视化**：直观展示消费分布和财务趋势 📈
- **月度/年度报表**：自动生成消费统计和分析 📅

### 🔄 循环付费商品记录 (shiro-main)

- 独立于普通账单的系统 ⭕
- 记录订阅服务、会员等循环付费项目
- 自动计算平均月付/年付价格
- 自动续费状态管理

### 📤 数据导出 (shiro-main)

- 导出账单数据到Excel 📑
- 支持邮件发送导出结果 📧
- 支持本地文件系统导出 💾

### 🤖 AI助手 (shiro-ai)

- 基于OpenAI接口的智能助手 🧠
- 提供财务管理建议和分析
- 支持持续对话和历史记录
- 角色扮演式交互体验

## 🌟 项目特色

1. **⚡ AOP切面统计**：使用AOP切面编程实现账单统计，代码解耦且高效
2. **🚀 多级缓存设计**：Redis + 二级缓存提升查询性能
3. **🔒 安全特性**：密码加密存储、邮箱验证码、JWT认证
4. **📱 响应式设计**：同时支持移动端和桌面端访问
5. **🧩 微服务架构**：可扩展的微服务设计，支持独立部署

## 🚀 快速开始

### 🔧 环境要求

- shiro-backend: JDK 17+ ☕
- shiro-ai: JDK 17+ ☕
- Maven 3.6+ 🏗️
- MySQL 8.0+ 🗃️
- Redis 6.0+ 📦
- Nacos 2.0+ 🔍

### 💽 数据库初始化

1. 创建数据库

```sql
CREATE DATABASE shiro;
CREATE DATABASE shiro_ai;
```

1. 执行初始化脚本

```bash
# 主数据库初始化
mysql -u username -p shiro < shiro-backend/initSQL/shiro-init.sql

# AI模块数据库初始化
mysql -u username -p shiro_ai < shiro-ai/initSQL/shiro-ai-init.sql
```

### ⚙️ 配置文件

#### shiro-backend 配置

创建 `shiro-main/src/main/resources/application-local.yaml`:

```yaml
db:
  host: localhost
  port: 3306
  database: shiro
  username: your-username
  password: your-password

redis:
  host: localhost
  port: 6379
  password:
  database: 0

nacos:
  server-addr: localhost:8848

shiro:
  jwt:
    secret-key: your-secret-key
    secret-expire: 2592000000
    user-token-name: authorization

# 其他必要配置...
```

创建 `shiro-gateway/src/main/resources/application-local.yaml` 使用类似配置。

#### shiro-ai 配置

创建 `src/main/resources/application-local.yaml`:

```yaml
datasource:
  ip: localhost
  port: 3306
  username: your-username
  password: your-password

ai:
  openai:
    api-key: your-openai-api-key
    base-url: https://api.openai.com
    default-deepseek-model: gpt-3.5-turbo

nacos:
  server-addr: localhost:8848

# 其他必要配置...
```

### 🚀 启动服务

1. 启动Nacos服务 🔍
2. 构建并启动 shiro-backend

```bash
cd shiro-backend
mvn clean package -DskipTests

# 启动网关
java -jar shiro-gateway/target/shiro-gateway.jar

# 启动主服务
java -jar shiro-main/target/shiro-main.jar
```

1. 构建并启动 shiro-ai

```bash
cd shiro-ai
mvn clean package -DskipTests
java -jar target/shiro-ai-0.0.1-SNAPSHOT.jar
```

## 👨‍💻 开发说明

### 📁 仓库结构

#### shiro-backend (Spring Boot 2.7)

- **shiro-gateway**: API网关，负责路由、认证和鉴权 🔐
- **shiro-main**: 核心业务逻辑模块，包含记账、统计等功能 💹

#### shiro-ai (Spring Boot 3.4)

- 独立仓库，提供AI助手功能 🤖
- 通过网关与主服务集成

### 🧱 代码风格

两个仓库均采用标准的Spring Boot应用结构：

- controller：接口控制器 🎮
- service：业务逻辑 ⚙️
- mapper：数据访问 🗃️
- domain：数据模型 📊
    - po：持久化对象
    - dto：数据传输对象
    - vo：视图对象

### 📘 API文档

- shiro-backend API：http://localhost:3951/doc.html (Knife4j) 📚
- shiro-ai API：http://localhost:3950/doc.html (Springdoc) 📚
- ✨ 因为shiro-ai项目是springboot3.4，使用knife4j存在一些问题，所以这里非常建议在idea中安装apifox的插件，可以直接扫描整个项目的接口进行测试，会非常好用
  🛠️

## 🤝 贡献指南

欢迎提出任何建议！请遵循以下步骤：

1. Fork 本仓库 🍴
2. 创建你的特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交你的更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 开启 Pull Request 🔍

## 📜 许可证

本项目采用 CC BY-NC 4.0 许可协议 (Creative Commons Attribution-NonCommercial 4.0 International License)

根据此许可证：

- 你可以自由地分享、复制、重新分发本代码，以及修改和构建本代码
- 你必须给出适当的署名，提供指向本许可证的链接，并指明是否进行了修改
- 你不得将本作品用于商业目的
- 对于任何商业用途，请联系原作者获取特别授权

## 📞 联系方式

- 项目作者：Anson
- Email：anson@example.com

© 2025 汐落(Shiro) - 让财务管理如潮汐般自然流畅 🌊
