package top.meethigher.camunda.rest.converter;

import org.springframework.beans.BeanUtils;
import top.meethigher.camunda.entity.Leave;
import top.meethigher.camunda.rest.dto.LeaveDTO;

public class LeaveConverter {

    public static Leave toLeave(LeaveDTO leaveDTO) {
        Leave leave = new Leave();
        BeanUtils.copyProperties(leaveDTO, leave);
        leave.setLeaveType(leaveDTO.getLeaveType().getCode());
        return leave;
    }
}
