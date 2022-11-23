package top.meethigher.camunda.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.meethigher.camunda.entity.Leave;

import java.util.List;
import java.util.Set;

/**
 * 请假信息dao
 *
 * @author chenchuancheng
 * @since 2022/11/23 15:56
 */
public interface LeaveRepository extends JpaRepository<Leave, Integer> {

    /**
     * 通过请假编号查询
     *
     * @param leaveIdSet 请假编号集合
     * @return 请假信息
     */
    List<Leave> findByLeaveIdIn(Set<Integer> leaveIdSet);
}
