package top.meethigher.camunda.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * 审批结果
 *
 * @author chenchuancheng
 * @since 2022/11/23 16:31
 */
@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {
        "leaveId", "approver"
})})//唯一约束
public class Approval {

    /**
     * 审批信息编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer approvalId;

    /**
     * 请假信息编号
     */
    @ApiModelProperty(example = "1")
    private Integer leaveId;

    /**
     * 审批人
     */
    @ApiModelProperty(example = "HR")
    private String approver;


    /**
     * 审批结果
     *
     * @see top.meethigher.camunda.config.constant.LeaveState
     */
    @ApiModelProperty(example = "1")
    private Integer result;

    /**
     * 审批备注
     */
    @ApiModelProperty(example = "我来审批")
    private String remark;
}
