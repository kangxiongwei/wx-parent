#微信公众平台开发

##创建公众号

```markdown
1.申请个人或企业订阅号
2.进入后台管理页面，点击开发者工具
3.进入公众平台测试帐号，从中可以获取appId和appsecret
4.配置接口信息，token可以自定义，url必须可以访问公网
```
```markdown
1.网络穿透工具：花生壳（mac不支持，所以没使用）
2.ngrok，使用方法：./ngrok http 8888，
  然后工具会生成一个url映射到localhost:8888，此url公网可以访问，可以配置
```

##开发接入

###获取accessToken

```markdown
1. 地址：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
2. 获取到token后，可以用以调用其他接口时使用
```