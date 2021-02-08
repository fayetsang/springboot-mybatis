package com.fftest.study.testcase.lombok;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    /**
     *     public void setId(@NonNull final Integer id) {
     *         if (id == null) {
     *             throw new NullPointerException("id is marked non-null but is null");
     *         } else {
     *             this.id = id;
     *         }
     *     }
     *
     *     public void setUsername(@NonNull final String username) {
     *         if (username == null) {
     *             throw new NullPointerException("username is marked non-null but is null");
     *         } else {
     *             this.username = username;
     *         }
     *     }
     */
    @NonNull private Integer id;
    @NonNull private String username;
    private String birthday;
    private String address;


    /**
     *     public TestUser(@NonNull Integer id, @NonNull String username, String birthday, String address) {
     *         if (id == null) {
     *             throw new NullPointerException("id is marked non-null but is null");
     *         } else if (username == null) {
     *             throw new NullPointerException("username is marked non-null but is null");
     *         } else {
     *             this.id = id;
     *             this.username = username;
     *             this.birthday = birthday;
     *             this.address = address;
     *         }
     *     }
     * @param id
     * @param username
     * @param birthday
     * @param address
     */
    public User(@NonNull Integer id, @NonNull String username, String birthday, String address) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.address = address;
    }

    /**
     *     public TestUser(Integer id, String username) {
     *         this.id = id;
     *         this.username = username;
     *     }
     * @param id
     * @param username
     */
    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     *     public void test() {
     *         try {
     *             String name = Thread.currentThread().getName();
     *             System.out.println("threadname=" + name);
     *             Thread.sleep(10000L);
     *         } catch (Throwable var2) {
     *             throw var2;
     *         }
     *     }
     */

    @SneakyThrows
    public void testSneakyThrows() {
        String name = Thread.currentThread().getName();
        System.out.println("threadname=" + name);
        throw new InterruptedException("My InterruptedException");
    }

}
