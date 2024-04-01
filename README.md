## description
用手机app发送开门信号，信号发送端

## 和另外几个项目一起使用
- 信号发送端： [lock-web in JavaScript](https://github.com/faf4r/lock-web) 或 [本项目](https://github.com/faf4r/lock-app)
- 信号接收端： [lock-esp in C++](https://github.com/faf4r/lock-esp) 或 [lock-raspberry in python](https://github.com/faf4r/lock)

## usage
在strings.xml配置MQTT broker等信息，然后编译安装即可(应无上架Google需求，targetSdk为30)
注意自行修改mqtt broker的配置
