# 云舒云笔记

> 基于HBASE的大数据存储分布式云计算笔记
>
> 云笔记使用大数据成熟的分布式存储解决方案，解决了传统笔记数据日益膨胀，数据丢失等问题。云笔记通过数据分析、用户画像等技术实现，能够通过精准推送其他人公开分享的云笔记来达到扩充用户知识行囊的目的，并且通过独立的账户安全体系来达到笔记安全私有化,保证用户独立的空间。且分布式存储可以达到用户笔记空间无限大,笔记平台响应式，满足用户不同平台办公的需求。

[前端项目地址:https://github.com/itning/yunshu-notes](https://github.com/itning/yunshu-notes)

[![GitHub stars](https://img.shields.io/github/stars/itning/yunshu-notes-r.svg?style=social&label=Stars)](https://github.com/itning/yunshu-notes-r/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/itning/yunshu-notes-r.svg?style=social&label=Fork)](https://github.com/itning/yunshu-notes-r/network/members)
[![GitHub watchers](https://img.shields.io/github/watchers/itning/yunshu-notes-r.svg?style=social&label=Watch)](https://github.com/itning/yunshu-notes-r/watchers)
[![GitHub followers](https://img.shields.io/github/followers/itning.svg?style=social&label=Follow)](https://github.com/itning?tab=followers)

[![GitHub issues](https://img.shields.io/github/issues/itning/yunshu-notes-r.svg)](https://github.com/itning/yunshu-notes-r/issues)
[![GitHub license](https://img.shields.io/github/license/itning/yunshu-notes-r.svg)](https://github.com/itning/yunshu-notes-r/blob/master/LICENSE)
[![GitHub last commit](https://img.shields.io/github/last-commit/itning/yunshu-notes-r.svg)](https://github.com/itning/yunshu-notes-r/commits)
[![GitHub release](https://img.shields.io/github/release/itning/yunshu-notes-r.svg)](https://github.com/itning/yunshu-notes-r/releases)
[![GitHub repo size in bytes](https://img.shields.io/github/repo-size/itning/yunshu-notes-r.svg)](https://github.com/itning/yunshu-notes-r)
[![HitCount](http://hits.dwyl.io/itning/yunshu-notes-r.svg)](http://hits.dwyl.io/itning/yunshu-notes-r)
[![language](https://img.shields.io/badge/language-JAVA-green.svg)](https://github.com/itning/yunshu-notes-r)

## 接口
### 笔记本
| 方法 | 接口        | 参数 | 说明 |
| ---- | ----------- | ---- |---- |
| get  | /note_books |      | 获取所有笔记本信息集合 |
| post | /note_book | name:笔记本名称 | 新建笔记本 |
| patch | /note_book/{id}/{name} | id:笔记本ID;name:新笔记本名 | 修改笔记本 |
| delete | /note_book/{id} | id:笔记本ID | 删除笔记本 |
### 笔记
| 方法 | 接口        | 参数 | 说明 |
| ---- | ----------- | ---- |---- |
| get  | /note/{id} | id:笔记ID | 获取笔记 |
| post | /note | noteBookId:笔记本ID;title:笔记标题;content:笔记内容 | 新建笔记 |
| patch | /note/{id}/{title}/{content} | id:笔记ID;title:笔记名;content:笔记内容 | 修改笔记 |
| delete | /note/{id} | id:笔记ID | 删除笔记 |
### 用户
| 方法 | 接口        | 参数 | 说明 |
| ---- | ----------- | ---- |---- |
| post | /login | username:用户名;password:密码 | 登陆 |
| get | /logout |  | 注销 |
| get | /getLoginUser |  | 获取登录用户信息 |
| post | /registered | name:昵称;username:用户名;password:密码;code:验证码 | 删除笔记 |
| get | /get_code | email:邮箱 | 获取验证码 |
| get | /forget_get_code | email:邮箱 | 获取忘记密码邮箱验证码 |
| post | /forget_password | code:验证码;vCode:密钥;password:密码 | 忘记密码 |
| post | /change_user_profile | id:用户ID;name:新用户名:password:新密码 | 更改用户信息 |

## 关于跨域

[CorsConfig.java](https://github.com/itning/yunshu-notes-r/blob/master/src/main/java/top/itning/yunshunotesr/config/CorsConfig.java#L16)

[SecurityFilter.java](https://github.com/itning/yunshu-notes-r/blob/master/src/main/java/top/itning/yunshunotesr/securtiy/SecurityFilter.java#L53)

修改这两个位置，可能下个版本会单独提到配置文件中

## 项目技术栈

[spring boot](https://github.com/spring-projects/spring-boot)
[apache hbase](https://github.com/apache/hbase)



## 开源协议

MIT
