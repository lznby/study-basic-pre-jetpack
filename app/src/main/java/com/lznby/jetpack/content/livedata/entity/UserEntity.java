package com.lznby.jetpack.content.livedata.entity;

/**
 * @author Lznby
 */
public class UserEntity {

    private String name;
    private String age;

    public UserEntity() {
    }

    public UserEntity(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
