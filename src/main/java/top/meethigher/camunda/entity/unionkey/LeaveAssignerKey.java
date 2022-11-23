package top.meethigher.camunda.entity.unionkey;

import java.io.Serializable;
import java.util.Objects;

/**
 * 联合主键
 *
 * @author chenchuancheng
 * @since 2022/11/24 9:11
 */
public class LeaveAssignerKey implements Serializable {

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 请假编号
     */
    private Integer leaveId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, leaveId);
    }

    @Override
    public boolean equals(Object key) {
        if (this == key) {
            return true;
        }
        if (key == null || getClass() != key.getClass()) {
            return false;
        }
        LeaveAssignerKey that = (LeaveAssignerKey) key;

        return Objects.equals(this.userId, that.userId) &&
                Objects.equals(this.leaveId, that.leaveId);
    }
}
