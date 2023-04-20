package top.meethigher.external;

import org.camunda.bpm.client.ExternalTaskClient;

import java.awt.*;
import java.net.URI;

/**
 * 定时提醒订阅
 *
 * @author chenchuancheng
 * @since 2023/4/20 14:53
 */
public class ScheduledFixedReminder {
    private final static String TOPIC = "scheduledFixedReminder";


    public void run() {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000) // 长轮询超时时间
                .build();

        // 订阅指定的外部任务
        client.subscribe(TOPIC)
                .lockDuration(1000)
                .handler(((externalTask, externalTaskService) -> {
                    // 获取流程变量
                    String userName = (String) externalTask.getVariable("userName");
                    Integer amount = (Integer) externalTask.getVariable("days");
                    System.out.println("userName--->" + userName + "  amount-->" + amount);
                    try {
                        Desktop.getDesktop().browse(new URI("https://meethigher.top/cloud?userName=" + userName + "&days=" + amount));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 完成任务
                    externalTaskService.complete(externalTask);
                })).open();
    }
}
