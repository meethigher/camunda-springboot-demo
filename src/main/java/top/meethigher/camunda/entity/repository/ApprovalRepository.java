package top.meethigher.camunda.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.meethigher.camunda.entity.Approval;

/**
 * 审批信息dao
 *
 * @author chenchuancheng
 * @since 2022/11/23 16:34
 */
public interface ApprovalRepository extends JpaRepository<Approval, Integer> {

    /**
     * 通过请假编号和审批人查询审批信息(已加组合唯一约束，必查出来单条)
     *
     * @param leaveId  请假编号
     * @param approver 审批人
     * @return 审批信息
     */
    Approval findByLeaveIdAndApprover(Integer leaveId, String approver);
}
