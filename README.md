整个camunda的绘图、具体使用，参照上一篇文章[Camunda工作流引擎简记](https://meethigher.top/blog/2022/camunda-quick-start/)。

[源码地址](https://github.com/meethigher/camunda-springboot-demo)，相关技术储备如下

1. springboot--v2.5.4
2. jpa--v2.5.4
3. postgresql--v11
4. camunda--v7.16.0

整个过程不需要建表，所见即所得！这就是JPA面向对象编程的好处，这也是我认为JPA优于Mybatis的地方。

虽然我两种都用，但应用实际demo，JPA的速度远超Mybatis。

# 一、展示效果

1.) 启动项目

![基于springboot+jpa+camunda实现简单的请假审批流程](https://meethigher.top/blog/2022/camunda-practice/image-20221124143906458.png)

2.) 访问`http://localhost:9999/`，账号密码均为demo，可以找到最终流程图。

![基于springboot+jpa+camunda实现简单的请假审批流程](https://meethigher.top/blog/2022/camunda-practice/image-20221124144055173.png)

3.) 访问`http://localhost:9999/swagger-ui/index.html`，调用接口，体验整个流程。

![基于springboot+jpa+camunda实现简单的请假审批流程](https://meethigher.top/blog/2022/camunda-practice/image-20221124144225583.png)

# 二、思路借鉴

1. [xiaojing5576/workflow: 基于camunda实现的工作流设计](https://github.com/xiaojing5576/workflow)
2. [FuriousPws002/camunda-bpm-spring-boot-example: Spring Boot and database integration camunda bpmn](https://github.com/FuriousPws002/camunda-bpm-spring-boot-example)

3. [camunda-run: camunda工作流集成](https://gitee.com/heshaobing/camunda-run)
4. [蓝花梗/wf-camunda - 码云 - 开源中国](https://gitee.com/lanhuageng/wf-camunda)