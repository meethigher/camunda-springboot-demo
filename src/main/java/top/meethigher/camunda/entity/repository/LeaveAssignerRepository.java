package top.meethigher.camunda.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.meethigher.camunda.entity.LeaveAssigner;
import top.meethigher.camunda.entity.unionkey.LeaveAssignerKey;

import java.util.List;

/**
 * @author chenchuancheng
 * @since 2022/11/24 9:51
 */
public interface LeaveAssignerRepository extends JpaRepository<LeaveAssigner, LeaveAssignerKey> {

    /**
     * 通过请假编号查询
     * 请假编号唯一约束
     *
     * @param leaveId 请假编号
     * @return 处于审批中的请假信息
     */
    LeaveAssigner findByLeaveId(Integer leaveId);


    /**
     * 通过指定人查询
     *
     * @param userId 用户编号
     * @return 处于审批中的请假信息
     */
    List<LeaveAssigner> findByUserId(String userId);
}
