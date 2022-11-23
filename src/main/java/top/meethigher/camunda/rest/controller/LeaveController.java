package top.meethigher.camunda.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import top.meethigher.camunda.config.constant.LeaveState;
import top.meethigher.camunda.entity.Approval;
import top.meethigher.camunda.entity.Leave;
import top.meethigher.camunda.entity.LeaveAssigner;
import top.meethigher.camunda.entity.repository.ApprovalRepository;
import top.meethigher.camunda.entity.repository.LeaveAssignerRepository;
import top.meethigher.camunda.entity.repository.LeaveRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 请假接口
 *
 * @author chenchuancheng
 * @since 2022/11/23 16:08
 */
@Api(tags = "请假")
@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
@Slf4j
public class LeaveController {


    /**
     * 该值与.bpmn里面的process id="leave_process"内部的值对应
     */
    private static final String PROCESS_KEY = "leave_process";

    private final RuntimeService runtimeService;

    private final LeaveRepository leaveRepository;

    private final ApprovalRepository approvalRepository;

    private final LeaveAssignerRepository leaveAssignerRepository;

    private final TaskService taskService;

    /**
     * 提交一个请假申请
     * 触发一个请假流程的创建
     * <p>
     * json如下：
     * {
     * "days": 30,
     * "department": "西天取经小组",
     * "leaveType": 0,
     * "reason": "打死白骨精，被唐僧要求放年假",
     * "userId": "000001",
     * "userName": "孙悟空"
     * }
     *
     * @param leave 请假信息
     */
    @ApiOperation("提交请假")
    @PostMapping("/apply")
    @Transactional(rollbackFor = Exception.class)
    public Integer apply(@RequestBody Leave leave) {
        leave.setLeaveState(LeaveState.APPROVING.getCode());
        leaveRepository.save(leave);
        //开启流程
        //runtimeService.startProcessInstanceByKey(PROCESS_ID, JSONUtils.object2Map(leave));
        //上面的做法,相当于下面的做法+top.meethigher.camunda.task.StartLeaveDelegate，一步到位。此处只是为了理解
        runtimeService.startProcessInstanceByKey(PROCESS_KEY, leave.getLeaveId().toString());
        return leave.getLeaveId();
    }

    /**
     * 查询请假信息
     *
     * @param leaveId 请假编号
     * @return 请假信息
     */
    @ApiOperation("通过编号查询请假")
    @GetMapping("/queryLeave/{leaveId}")
    public Leave queryLeave(@PathVariable("leaveId") Integer leaveId) {
        Optional<Leave> optional = leaveRepository.findById(leaveId);
        return optional.orElse(null);
    }

    /**
     * 查询当前用户的审批任务
     *
     * @param userId 用户编号
     * @return 属于该用户的审批任务
     */
    @ApiOperation("查询属于当前人的审批信息")
    @GetMapping("/queryApproval/{userId}")
    public List<Leave> queryApproval(@PathVariable("userId") String userId) {
        List<Leave> list = new LinkedList<>();
        List<LeaveAssigner> assignerList = leaveAssignerRepository.findByUserId(userId);
        if (ObjectUtils.isEmpty(assignerList)) {
            return list;
        }
        Set<Integer> leaveIdSet = assignerList.stream().map(LeaveAssigner::getLeaveId).collect(Collectors.toSet());
        list = leaveRepository.findByLeaveIdIn(leaveIdSet);
        return list;
    }

    @ApiOperation("提交审批意见")
    @PostMapping("/submitApproval")
    @Transactional(rollbackFor = Exception.class)
    public void submitApproval(@RequestBody Approval approval) {
        //查询流程中任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(PROCESS_KEY)
                .processInstanceBusinessKey(String.valueOf(approval.getLeaveId()))
//                .processVariableValueEquals("leaveId",approval.getLeaveId())
                .singleResult();
        String assignee = task.getAssignee();
        if (!assignee.equals(approval.getApprover())) {
            log.error("失败,无权限审批");
            return;
        }
        String taskId = task.getId();
        //存储当前人审批结果
        approvalRepository.save(approval);
        //删除属于当前人的审批信息
        LeaveAssigner assigner = new LeaveAssigner();
        assigner.setLeaveId(approval.getLeaveId());
        assigner.setUserId(approval.getApprover());
        leaveAssignerRepository.delete(assigner);
        //在事务里面执行先删再增，实际会变成先增再删，调用flush可解决。[springdata+jpa service一个方法同一事物中执行delete和insert_今天做一条翻身的咸鱼的博客-CSDN博客_jpa service](https://blog.csdn.net/qq_33434415/article/details/102610605)
        leaveAssignerRepository.flush();
        //完成任务
        taskService.complete(taskId);
    }
}
