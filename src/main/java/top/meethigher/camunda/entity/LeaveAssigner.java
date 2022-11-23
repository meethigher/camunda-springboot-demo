package top.meethigher.camunda.entity;

import lombok.Data;
import top.meethigher.camunda.entity.unionkey.LeaveAssignerKey;

import javax.persistence.*;

/**
 * 审批中的请假信息对应的当前环节的处理人
 * 若最后任务删除，审批中的数据也应一并删除
 *
 * @author chenchuancheng
 * @since 2022/11/24 9:06
 */
@Entity
@Data
@IdClass(LeaveAssignerKey.class)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "leaveId")})//唯一约束，目前的流程不能一个请假好几个人同时审批，顺序的
public class LeaveAssigner {

    @Id
    private Integer leaveId;

    @Id
    private String userId;
}
