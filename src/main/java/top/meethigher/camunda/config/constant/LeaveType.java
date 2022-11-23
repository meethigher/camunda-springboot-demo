package top.meethigher.camunda.config.constant;

/**
 * 请假类型
 *
 * @author chenchuancheng
 * @since 2022/11/23 14:26
 */
public enum LeaveType {

    /**
     * 年假
     */
    ANNUAL_LEAVE(0, "年假"),
    /**
     * 事假
     */
    ABSENT_LEAVE(1, "事假");


    private final int code;

    private final String name;

    LeaveType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
