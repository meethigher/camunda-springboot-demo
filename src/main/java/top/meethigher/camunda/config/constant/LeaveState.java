package top.meethigher.camunda.config.constant;

import org.omg.CORBA.UNSUPPORTED_POLICY;

/**
 * 请假状态
 *
 * @author chenchuancheng
 * @since 2022/11/24 9:55
 */
public enum LeaveState {


    APPROVING(0, "审批中"),
    APPROVED(1, "通过"),
    UNAPPROVED(2, "未通过");

    private final int code;

    private final String name;

    LeaveState(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public static LeaveState findByCode(Integer code) {
        for (LeaveState state : LeaveState.values()) {
            if (code.equals(state.code)) {
                return state;
            }
        }
        return UNAPPROVED;
    }
}
