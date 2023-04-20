package top.meethigher.external;


import org.camunda.bpm.client.ExternalTaskClient;

import java.awt.*;
import java.net.URI;

/**
 * 超时订阅
 * <a href="https://blog.csdn.net/qq_38137096/article/details/120490007">解决 camunda 定时器执行慢于设定事件_camunda 定时不准确_低头行走的少年的博客-CSDN博客</a>
 * @author chenchuancheng
 * @since 2023/4/20 11:45
 */
public class TimeoutReminder {

    private final static String TOPIC = "timeoutReminder";


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
                        Desktop.getDesktop().browse(new URI("https://meethigher.top?userName=" + userName + "&days=" + amount));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 完成任务
                    externalTaskService.complete(externalTask);
                })).open();
    }
}
