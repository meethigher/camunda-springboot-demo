package top.meethigher.camunda.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.meethigher.camunda.config.constant.LeaveType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 请假信息
 *
 * @author chenchuancheng
 * @since 2022/11/23 14:23
 */
@Entity
@Data
public class Leave {

    /**
     * 请假编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveId;

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
    @ApiModelProperty(example = "0")
    private Integer leaveType;

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

    /**
     * 请假状态
     */
    private int leaveState;

    /**
     * 请假拒绝回执
     */
    private String rejectedRemark;

}
