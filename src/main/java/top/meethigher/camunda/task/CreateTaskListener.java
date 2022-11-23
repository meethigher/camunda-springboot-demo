package top.meethigher.camunda.task;


import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import top.meethigher.camunda.config.constant.UserType;
import top.meethigher.camunda.entity.LeaveAssigner;
import top.meethigher.camunda.entity.repository.LeaveAssignerRepository;

/**
 * 创建任务监听器
 * 注册上监听器，camunda自动处理，如果某个任务下个环节还有任务，会自动执行，如果没有，就不执行
 *
 * @author chenchuancheng
 * @since 2022/11/23 16:26
 */
@Component
@RequiredArgsConstructor
public class CreateTaskListener implements TaskListener {

    private final LeaveAssignerRepository leaveAssignerRepository;


    @Override
    public void notify(DelegateTask delegateTask) {
        //获取当前任务的标识
        String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
        UserType type = UserType.findByCode(taskDefinitionKey);
        //存储下一审批Task中的指定人
        LeaveAssigner assigner = new LeaveAssigner();
        assigner.setLeaveId((Integer) delegateTask.getVariable("leaveId"));
        assigner.setUserId(type.getCode());
        //入库存储下一审批信息
        leaveAssignerRepository.save(assigner);
        //存入流程
        delegateTask.setAssignee(type.getCode());
    }


}
