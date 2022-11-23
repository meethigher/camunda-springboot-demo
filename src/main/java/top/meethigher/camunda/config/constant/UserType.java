package top.meethigher.camunda.config.constant;

/**
 * 用户类型
 *
 * @author chenchuancheng
 * @since 2022/11/23 15:11
 */
public enum UserType {

    /**
     * 人事主管
     */
    HR("HR", "人事主管"),
    /**
     * 部门主管
     */
    DM("DM", "部门主管"),
    /**
     * 总经理
     */
    GM("GM", "总经理");

    private final String code;

    private final String name;


    UserType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static UserType findByCode(String code) {
        for (UserType type : UserType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return UserType.HR;
    }
}
