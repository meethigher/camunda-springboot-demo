package top.meethigher.camunda.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.meethigher.camunda.config.constant.LeaveState;
import top.meethigher.camunda.config.constant.UserType;

@Data
public class ApprovalDTO {

    /**
     * 请假信息编号
     */
    @ApiModelProperty(example = "1")
    private Integer leaveId;

    /**
     * 审批人
     */
    private UserType approver;


    /**
     * 审批结果
     *
     * @see top.meethigher.camunda.config.constant.LeaveState
     */
    private LeaveState result;

    /**
     * 审批备注
     */
    @ApiModelProperty(example = "我来审批")
    private String remark;
}
