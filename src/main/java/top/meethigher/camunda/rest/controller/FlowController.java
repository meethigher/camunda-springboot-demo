package top.meethigher.camunda.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.meethigher.camunda.entity.Leave;
import top.meethigher.camunda.entity.repository.LeaveRepository;
import top.meethigher.camunda.rest.converter.LeaveConverter;
import top.meethigher.camunda.rest.dto.LeaveDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chenchuancheng
 * @since 2023/4/14 17:17
 */
@Api(tags = "流程")
@RestController
@RequestMapping("/flow")
@RequiredArgsConstructor
@Slf4j
public class FlowController {

    private final RepositoryService repositoryService;

    private final LeaveRepository leaveRepository;

    private final RuntimeService runtimeService;

    private static final List<String> list = Collections.synchronizedList(new ArrayList<>());


    @PostMapping("/deploy-bpmn")
    @ApiOperation("上传并创建工作流")
    public String deployBpmn(@RequestParam("file") MultipartFile file, @RequestParam("processKey") String processKey) {
        try {
            File convertedFile = convertMultipartFileToFile(file);

            Deployment deployment = repositoryService.createDeployment()
                    .addInputStream(convertedFile.getName(), new FileInputStream(convertedFile))
                    .deploy();

            // Return success message
            list.add(processKey);
            return "BPMN file deployed successfully with deployment id: " + deployment.getId();
        } catch (Exception e) {
            // Return error message
            return "Error deploying BPMN file: " + e.getMessage();
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(System.getProperty("user.dir").replace("\\", "/") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
            fos.flush();
        }
        return convertedFile;
    }


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
     */
    @ApiOperation("提交请假")
    @PostMapping("/apply")
    @Transactional(rollbackFor = Exception.class)
    public Integer apply(LeaveDTO leaveDTO) {
        Leave leave = LeaveConverter.toLeave(leaveDTO);
        leaveRepository.save(leave);
        //开启流程
        //runtimeService.startProcessInstanceByKey(PROCESS_ID, JSONUtils.object2Map(leave));
        //上面的做法,相当于下面的做法+top.meethigher.camunda.task.StartLeaveDelegate，一步到位。此处只是为了理解
        runtimeService.startProcessInstanceByKey(list.get(0), leave.getLeaveId().toString());
        return leave.getLeaveId();
    }
}
