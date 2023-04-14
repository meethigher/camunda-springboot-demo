package top.meethigher.camunda.rest.converter;

import top.meethigher.camunda.entity.Approval;
import top.meethigher.camunda.rest.dto.ApprovalDTO;

public class ApprovalConverter {

    public static Approval toApproval(ApprovalDTO approvalDTO) {
        Approval approval = new Approval();
        approval.setApprover(approvalDTO.getApprover().getCode());
        approval.setLeaveId(approvalDTO.getLeaveId());
        approval.setResult(approvalDTO.getResult().getCode());
        approval.setRemark(approvalDTO.getRemark());
        return approval;
    }
}
