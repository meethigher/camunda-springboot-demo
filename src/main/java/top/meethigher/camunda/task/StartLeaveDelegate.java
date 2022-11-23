package top.meethigher.camunda.task;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import top.meethigher.camunda.entity.Leave;
import top.meethigher.camunda.entity.repository.LeaveRepository;
import top.meethigher.camunda.utils.JSONUtils;

import java.util.Map;
import java.util.Optional;

/**
 * 开启一个请假流程
 *
 * @author chenchuancheng
 * @since 2022/11/23 16:28
 */
@Component
@RequiredArgsConstructor
public class StartLeaveDelegate implements JavaDelegate {

    private final LeaveRepository leaveRepository;


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String businessKey = execution.getBusinessKey();
        Integer leaveId = Integer.valueOf(businessKey);
        Optional<Leave> optional = leaveRepository.findById(leaveId);
        if (optional.isPresent()) {
            Leave leave = optional.get();
            Map<String, Object> map = JSONUtils.object2Map(leave);
            execution.setVariables(map);
        }
    }

}
