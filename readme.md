## 说明
- 只在查询类上使用了redis缓存机制,15分钟内的数据不会及时刷新,可以在 `RedisUtil::setex` 中设置

## note
- 在controller中使用 service, 而不使用具体的实现类
