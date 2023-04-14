package top.meethigher.camunda.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.meethigher.camunda.config.constant.LeaveState;
import top.meethigher.camunda.config.constant.LeaveType;

@Data
public class LeaveDTO {

    /**
     * 用户编号
     */
    @ApiModelProperty(example = "000001")
    private String userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(example = "孙悟空")
    private String userName;

    /**
     * 部门
     */
    @ApiModelProperty(example = "西天取经组")
    private String department;

    /**
     * 请假类型
     *
     * @see LeaveType
     */
    private LeaveType leaveType;

    /**
     * 请假天数
     */
    @ApiModelProperty(example = "365")
    private Integer days;

    /**
     * 请假理由
     */
    @ApiModelProperty(example = "打死白骨精，被唐僧要求放年假")
    private String reason;

}
