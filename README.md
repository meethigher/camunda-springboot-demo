整个camunda的绘图、具体使用，参照上一篇文章[Camunda工作流引擎简记](https://meethigher.top/blog/2022/camunda-quick-start/)。

**h2-rest-api版本**

1.)  部署流程

* enable-duplicate-filtering：true表示禁止部署同名流程，false表示允许部署同名流程

```sh
curl -X POST -F "deployment-name=部署名称" -F "deployment-source=部署来源" -F "enable-duplicate-filtering=true" -F "文件名1=@文件1" -F "文件名n=@文件n" http://localhost:8080/engine-rest/deployment/create
```

返回结果示例

```json
{
	"links": [{
		"method": "GET",
		"href": "http://10.0.0.1:8080/engine-rest/deployment/c94dfbba-dfe1-11ed-9419-c0b5d7a4ecb9",
		"rel": "self"
	}],
	"id": "c94dfbba-dfe1-11ed-9419-c0b5d7a4ecb9",
	"name": "部署名称",
	"source": "部署来源",
	"deploymentTime": "2023-04-21T09:13:55.624+0800",
	"tenantId": null,
	"deployedProcessDefinitions": {
		"leave_process_no_table:1:c968afae-dfe1-11ed-9419-c0b5d7a4ecb9": {
			"id": "leave_process_no_table:1:c968afae-dfe1-11ed-9419-c0b5d7a4ecb9",
			"key": "leave_process_no_table",
			"category": "http://bpmn.io/schema/bpmn",
			"description": null,
			"name": null,
			"version": 1,
			"resource": "leave_process_no_table.bpmn",
			"deploymentId": "c94dfbba-dfe1-11ed-9419-c0b5d7a4ecb9",
			"diagram": null,
			"suspended": false,
			"tenantId": null,
			"versionTag": null,
			"historyTimeToLive": null,
			"startableInTasklist": true
		}
	},
	"deployedCaseDefinitions": null,
	"deployedDecisionDefinitions": null,
	"deployedDecisionRequirementsDefinitions": null
}
```

2.) 查询已定义流程(需登录)

* firstResult：开始数据的序号
* maxResults：由开始数据往后查询的数量
* latest：是否只展示最新版本

```sh
curl -X GET http://localhost:8080/camunda/api/engine/engine/default/process-definition?firstResult=0&maxResults=15&latest=true
```

3.) 发起一个流程(需登录)

```sh
curl -X POST -H "Content-Type:application/json"  --data @test.json http://localhost:8080/camunda/api/engine/engine/default/process-definition/leave_process_no_table:1:5c9b1b55-de8c-11ed-89df-c0b5d7a4ecb9/submit-form
```

test.json内容

```json
{"businessKey":"111","variables":{"111":{"value":111,"type":"Integer"}}}
```

