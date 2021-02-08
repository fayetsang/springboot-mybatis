package com.fftest.study.testcase;

import com.fftest.study.testcase.lombok.TestSynchronized;
import com.fftest.study.testcase.lombok.User;
import org.junit.Assert;
import org.junit.Test;

public class LombokTest {

    @Test
    public void userWithNonNullOnConstructorParameters() {
        User user;
        try {
            user = new User(null, null, "2020-11-11", null);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertEquals("id is marked non-null but is null", e.getMessage());
        }

        try {
            user = new User(1, null, "2020-11-11", null);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertEquals("username is marked non-null but is null", e.getMessage());
        }
    }


    @Test
    public void userWithoutNonNullOnConstructorParameters() {
        User user = new User(null, "Sherry");
        System.out.println(user); //user(id=null, username=Sherry, birthday=null, address=null)
    }

    @Test
    public void testGetterAndSetter() {
        User user = new User();
        user.setAddress(null);
        Assert.assertEquals(null, user.getAddress());

        user.setBirthday(null);
        Assert.assertEquals(null, user.getBirthday());

        try {
            user.setUsername(null);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertEquals("username is marked non-null but is null", e.getMessage());
        }

        try {
            user.setId(null);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertEquals("id is marked non-null but is null", e.getMessage());
        }
    }

    @Test
    public void testSneakyThrows() {
        User user = new User();
        try {
            user.testSneakyThrows();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getClass().isInstance(new InterruptedException()));
            Assert.assertEquals("My InterruptedException", e.getMessage());
        }
    }

    @Test
    public void testSynchronized() throws InterruptedException {
        TestSynchronized aSynchronized = new TestSynchronized();
        Thread thread1 = new Thread(aSynchronized);
        Thread thread2 = new Thread(aSynchronized);
        thread1.start();
        thread2.start();
        Thread.sleep(3000);
        Assert.assertEquals(0, TestSynchronized.count);
    }

}
