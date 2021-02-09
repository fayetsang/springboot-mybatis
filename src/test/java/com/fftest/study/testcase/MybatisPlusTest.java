package com.fftest.study.testcase;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fftest.study.mapper.UserMapper;
import com.fftest.study.pojo.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    private static final Long ID = 1L;
    private static final String NAME = "unitTest";
    private static final String INACTIVE = "inactive";
    private static final String ACTIVE = "active";
    private static final String LOCK = "lock";

    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_STATUS = "status";

    @After
    public void clearData() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.between(true, "ID", 0, 1000);
        userMapper.delete(userQueryWrapper);
    }

    @Test
    public void testInsertWithoutId() {
        String username = "testInsertWithoutIdAndCheckIdIncrease";
        userMapper.insert(new User(username, INACTIVE));
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(FIELD_NAME, username);
        Assert.assertTrue(userMapper.selectOne(userQueryWrapper).getId()>0);
    }

    @Test
    public void testUpdateById() {
        prepareData();
        String updateName = "updatename";
        userMapper.updateById(new User(ID, updateName, ACTIVE));

        User userAfterUpdate = userMapper.selectById(ID);
        Assert.assertEquals(ID, userAfterUpdate.getId());
        Assert.assertEquals(updateName, userAfterUpdate.getName());
        Assert.assertEquals(ACTIVE, userAfterUpdate.getStatus());
    }


    @Test
    public void testSelectList() {
        userMapper.insert(new User(2L, "test", ACTIVE));
        QueryWrapper<User> listWrapper = new QueryWrapper<>();
        listWrapper.select(FIELD_ID, FIELD_NAME).eq(FIELD_STATUS, ACTIVE);
        List<User> users = userMapper.selectList(listWrapper);
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void testSelectBatchIds() {
        ArrayList<Long> ids = new ArrayList();
        for(int i=200; i<210; i++) {
            String name = "gen_" + UUID.randomUUID().toString();
            userMapper.insert(new User(Integer.toUnsignedLong(i), name, INACTIVE));
            ids.add(Integer.toUnsignedLong(i));
        }
        List<User> usersFromDB = userMapper.selectBatchIds(ids);
        Assert.assertEquals(10, usersFromDB.size());
    }

    @Test
    public void testSelectCount() {
        prepareData();
        QueryWrapper<User> countWrapper = new QueryWrapper<>();
        countWrapper.select(FIELD_ID);
        Integer count = userMapper.selectCount(countWrapper);
        Assert.assertEquals(1, count.intValue());
    }

    @Test
    public void testSelectMaps() {
        prepareData();
        for(int i=200; i<210; i++) {
            String name = "gen_" + UUID.randomUUID().toString();
            userMapper.insert(new User(Integer.toUnsignedLong(i), name, i%2==0?ACTIVE:LOCK));
        }
        QueryWrapper<User> userQueryWrapper = Wrappers.query();
        userQueryWrapper.select(FIELD_NAME, FIELD_STATUS).ne(FIELD_STATUS, LOCK);

        List<Map<String, Object>> maps = userMapper.selectMaps(userQueryWrapper);
        Assert.assertEquals(6, maps.size());
        maps.forEach(t -> Assert.assertNotEquals(LOCK, t.get(FIELD_STATUS)));
    }

    @Test
    public void testSelectByMap() {
        prepareData();
        for(int i=200; i<210; i++) {
            String name = "gen_" + UUID.randomUUID().toString();
            userMapper.insert(new User(Integer.toUnsignedLong(i), name, i%2==0?ACTIVE:INACTIVE));
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(FIELD_STATUS, INACTIVE);
        queryMap.put(FIELD_ID, ID);
        List<User> users = userMapper.selectByMap(queryMap);
        Assert.assertEquals(1, users.size());
        Assert.assertEquals(ID, users.get(0).getId());
        Assert.assertEquals(INACTIVE, users.get(0).getStatus());
        Assert.assertEquals(NAME, users.get(0).getName());
    }

    @Test
    public void testDeleteById() {
        prepareData();
        userMapper.deleteById(ID);
        Assert.assertNull(userMapper.selectById(ID));
    }

    private void prepareData() {
        userMapper.insert(new User(ID, NAME, INACTIVE));
        User user = userMapper.selectById(ID);
        Assert.assertEquals(ID, user.getId());
        Assert.assertEquals(NAME, user.getName());
        Assert.assertEquals(INACTIVE, user.getStatus());
    }

}
