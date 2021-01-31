# springboot-mybatis
 for study
---
### 1 mysql准备

```
CREATE DATABASE study;
```

```
CREATE TABLE user(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
status VARCHAR(20) NOT NULL,
PRIMARY KEY ( id )
);
```

### 2 测试
#### 2.1 mysql测试

deleteUserById:

```
curl -v -X DELETE http://localhost:8080/user -d 'id=6'
```

addUser:

```
curl -v -X POST http://localhost:8080/user -d 'id=6&name=test'
```

updateUserStatus:

```
curl -v -X PUT -d "id=6&status=active" http://localhost:8080/user
```

findUserById:

```
curl http://localhost:8080/user/6
```



#### 2.2 redis测试
getCounter:

```
curl http://localhost:8080/counter/2
```

increaseCounter:

```
curl -v -X POST http://localhost:8080/counter -d 'id=2'
```

deleteCounter:

```
curl -v -X DELETE http://localhost:8080/counter -d 'id=2'
```


#### 2.3 kafka测试
createTopic:

```
curl -v -X POST http://localhost:8080/topic -d 'topicName=notification&partitions=1&replicationFactor=1'
```

```
curl -v -X POST http://localhost:8080/topic -d 'topicName=test&partitions=1&replicationFactor=1'
```


getTopics:

```
curl http://localhost:8080/topic
```
```
kafka-topics --list --zookeeper localhost:2181
```

deleteTopic:

```
curl -v -X DELETE http://localhost:8080/topic -d 'topicName=test'
```


increaseCounter: 当count>=3时，会发送消息到kafka（消息生产者）

```
curl -v -X POST http://localhost:8080/counter -d 'id=2'
```


getNotification: 监听kafka消息，当收到消息时会打印log（消息消费者）

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic notification --from-beginning
```
