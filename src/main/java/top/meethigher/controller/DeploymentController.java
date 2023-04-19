package top.meethigher.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenchuancheng
 * @since 2023/4/19 14:31
 */
@RestController
@Tag(name = "部署管理")
@RequestMapping("/deploy")
@RequiredArgsConstructor
public class DeploymentController {


}
