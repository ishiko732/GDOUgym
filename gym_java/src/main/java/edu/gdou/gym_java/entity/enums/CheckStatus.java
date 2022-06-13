package edu.gdou.gym_java.entity.enums;

public enum CheckStatus {
    WAIT("待审核"), PASSED("审核通过"), NOT_PASSED("审核未通过"), CANCELLED("审核取消"), REVIEWED("重新审核"),
    CHECKING("审核中");
    private final String status;

    CheckStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
