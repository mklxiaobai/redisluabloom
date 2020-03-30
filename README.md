# redisluabloom
lua脚本实现redisbloom

#### 实现原理

add: 将传入字符串hash成二进制数组，查找标记出值为1的位置，并覆盖redis位图中对应位置的值为1

exists:将传入的字符串hash成二进制数组，查询对应bloomkey的位图，查找hash二进制数组值为1的位置在位图中是否为1，若全部匹配，则存在，否则则不存在

#### 运行

运行LuaApplication的main函数
