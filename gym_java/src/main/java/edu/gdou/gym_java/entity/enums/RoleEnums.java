package edu.gdou.gym_java.entity.enums;

public enum RoleEnums {
    SM("超级管理员", 1), UM("用户管理员",2), PM("场地管理员",3), AM("赛事管理员",4), EM("器材管理员",5),
    Student("学生",6),Teacher("教师",7);
    private final String role;
    private final int rid;

    RoleEnums(String role, int rid) {
        this.role = role;
        this.rid = rid;
    }

    public String getRole() {
        return role;
    }

    public int getRid() {
        return rid;
    }

    @Override
    public String toString() {
        return "RoleEnums{" +
                "role='" + role + '\'' +
                ", rid=" + rid +
                '}';
    }
}
