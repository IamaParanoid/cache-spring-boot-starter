### 基于 redis 的缓存starter

#### 设置缓存

支持自定义缓存 value、key、缓存时长、缓存时间单位,key支持 SpEL 表达式，通过入参动态生成key

```
    @GetMapping("/test")
    @RedisCache(value = "cache", key = "#id-#name")
    public List<ChatRoomDO> test(@RequestParam Long id, @RequestParam String name) {
        return chatRoomService.getRooms(id);
    }
```

#### 删除缓存

根据定义的 value、key 删除缓存,key支持 SpEL 表达式，通过入参动态生成key

```
    @GetMapping("/test")
    @RedisCacheEvict(value = "cache", key = "#id-#name")
    public List<ChatRoomDO> test(@RequestParam Long id, @RequestParam String name) {
        return chatRoomService.getRooms(id);
    }
```

#### 说明

经过压测 95 % 的请求落在 10 ms 以内(受数据大小影响)；读取缓存时未作同步处理，如果觉得有用麻烦给个 star。