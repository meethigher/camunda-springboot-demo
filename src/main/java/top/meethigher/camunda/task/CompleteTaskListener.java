package top.meethigher.camunda.task;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import top.meethigher.camunda.config.constant.LeaveState;
import top.meethigher.camunda.config.constant.UserType;
import top.meethigher.camunda.entity.Approval;
import top.meethigher.camunda.entity.Leave;
import top.meethigher.camunda.entity.repository.ApprovalRepository;
import top.meethigher.camunda.entity.repository.LeaveRepository;

import java.util.Map;
import java.util.Optional;


/**
 * 完成任务监听器
 *
 * @author chenchuancheng
 * @since 2022/11/23 16:25
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CompleteTaskListener implements TaskListener {

    private final ApprovalRepository approvalRepository;

    private final LeaveRepository leaveRepository;


    @Override
    public void notify(DelegateTask delegateTask) {
        //获取指派人
        String assignee = delegateTask.getAssignee();
        Integer leaveId = Integer.valueOf(delegateTask.getExecution().getBusinessKey());
        Approval approval = approvalRepository.findByLeaveIdAndApprover(leaveId, assignee);
        LeaveState state = LeaveState.findByCode(approval.getResult());
        log.info("{} 的请假经过 {} 审批的结果 {}", leaveId, assignee, state);
        //处理审批结果
        Optional<Leave> optional = leaveRepository.findById(leaveId);
        if (!optional.isPresent()) {
            return;
        }
        Leave leave = optional.get();
        if (state.equals(LeaveState.UNAPPROVED)) {
            leave.setRejectedRemark(approval.getRemark());
            leave.setLeaveState(state.getCode());
            log.info("'{}' 请假经过 '{}' 审批的结果 '{}' 原因 '{}'", leaveId, assignee, state, leave.getRejectedRemark());
        } else {
            //如果是最后一环节的审批通过，更新最终状态
            if (assignee.equals(UserType.GM.getCode())) {
                leave.setLeaveState(state.getCode());
                log.info("'{}' 请假经过 '{}' 审批的结果 '{}'", leaveId, assignee, state);
            }
        }
        //将审批结果传给下一级
        Map<String, Object> map = delegateTask.getVariables();
        map.put("result", approval.getResult());
        delegateTask.setVariables(map);
    }
}
