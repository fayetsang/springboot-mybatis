package com.fftest.study.testcase;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fftest.study.mapper.UserMapper;
import com.fftest.study.pojo.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    private static final Long ID = 10L;
    private static final String NAME = "unitTest";
    private static final String INACTIVE = "inactive";
    private static final String ACTIVE = "active";

    @Before
    public void setUp() {
        // delete data
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.between(true, "ID", 1, 1000);
        userMapper.delete(userQueryWrapper);
    }

    @Test
    public void testCURD() {
        // verify that no data in table
        QueryWrapper<User> countWrapper = new QueryWrapper<>();
        countWrapper.select("ID");
        Integer count = userMapper.selectCount(countWrapper);
        Assert.assertEquals(0, count.intValue());

        // insert with ID
        userMapper.insert(new User(ID, NAME, INACTIVE));
        User user = userMapper.selectById(ID);
        Assert.assertEquals(ID, user.getId());
        Assert.assertEquals(NAME, user.getName());
        Assert.assertEquals(INACTIVE, user.getStatus());

        // insert without ID
        String username = "testInsertWithoutIdAndCheckIdIncrease";
        userMapper.insert(new User(username, INACTIVE));
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name", username);
        Assert.assertTrue(userMapper.selectOne(userQueryWrapper).getId() > ID);

        // update name and status
        String updateName = "updatename";
        userMapper.updateById(new User(ID, "updatename", ACTIVE));

        // query by id
        User userAfterUpdate = userMapper.selectById(ID);
        Assert.assertEquals(ID, user.getId());
        Assert.assertEquals(updateName, userAfterUpdate.getName());
        Assert.assertEquals(ACTIVE, userAfterUpdate.getStatus());

        // query by wrapper
        userMapper.insert(new User(1L, "test", ACTIVE));
        QueryWrapper<User> listWrapper = new QueryWrapper<>();
        listWrapper.select("ID", "name").eq("status", ACTIVE);
        List<User> users = userMapper.selectList(listWrapper);
        Assert.assertEquals(2, users.size());

        // delete
        userMapper.deleteById(ID);
        Assert.assertNull(userMapper.selectById(ID));
    }


}
