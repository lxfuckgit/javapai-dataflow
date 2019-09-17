# 数据埋点
所谓“埋点”，是数据采集领域（尤其是用户行为数据采集领域）的术语，指的是针对特定用户行为或事件进行捕获、处理和发送的相关技术及其实施过程。

# 数据埋点的意义
即帮助定义和获取分析人员真正需要的业务数据及其附带信息。在不同场景下，业务人员关注的信息和角度可能不同。

我所能接触到项目中运用前端埋点的用途大致分为两类：
+ >运营统计
从产品及运营的角度，我们需要知道一个页面的pv、uv、停留时间、操作系统、用户设备、用户操作顺序等，帮助产品优化体验，精准运营。
+ >优化研发
在研发人员来看，增加埋点可以作为性能监控的一种方式。例如分析http请求时间，页面渲染速度等。

# UBT埋点接入说明
## 1、埋点形式
+ sdk方式  
```后续开发```
+ http方式  
```已完成```

## 2、接口说明
+ 接口协议：HTTP  
+ 提交方式：POST  
+ 内容格式：application/json  

## 3、接口列表
### 3.1、同步采集时间接口
```http://{hostname}:{port}/getCurrentTimestamp()```

### 3.2、UBT数据采集接口
```http://{hostname}:{port}/ubt/ubtEvent(Event event)```

```
{
	"appId": "myAppId",
	"sourceId": "myCookies",
	"action": "goin",
	"event": "home_view",
	......
	"properties": {
		"$os": "android",
		"$appVerson": "v1.1.0"
		......
	}
}
```

### 3.3、UBT数据批量采集接口
```http://{hostname}:{port}/ubt/ubtEventBatch(List<Event> events)```

```
[{
	"appId": "myAppId",
	"sourceId": "myCookies",
	"action": "goin",
	"event": "home_view",
	......
	"properties": {
		"$os": "android",
		"$appVerson": "v1.1.0"
		......
	}
}, {
	"appId": "myAppId",
	"sourceId": "myCookies",
	"action": "click",
	"event": "banner_click",
	......
	"properties": {
		"$os": "android",
		"$appVerson": "v1.1.0"
		......
	}
}]
```

## 4、UBT报文说明
### Event参数说明
字段名称                 | 字段类型     | 必填项 |  描述  
-|-|-|-
appId      | String |  Y  | 项目标识(自行约定) |
userId     | String |  N  | 用户登录标识(注册账号) |
sourceId   | String |  Y  | 数据来源标识(native取设备号，其它源取cookiesId)        |
action     | String |  Y  | 用户跟目标交互的行为；可选值:<br>点击：`click`<br>输入：`input`<br>跳进：`goin`<br>跳出：`goto`<br>滑动：`slider`<br> 登录：`login`<br>  登出：`logout`<br>   |
event      | String |  Y  | 目标的事件标识                |
timestamp  | Long   |  N  | 事件发生的实际时间戳(精确到毫秒)，如果不填默认为服务器时间辍;    |
properties | Map    |  N  | 预留属性(预留属性分为系统预留和业务预留)<br>系统预留可直接使用业务预留供接入方自定义        |

### 系统预留属性
|属性名                             |属性类型                     |属性说明|
|--|--|--|
| $ip          | String      |设备的IP|
| $os          | String      |操作系统|
| $token       | String      |通信令牌|
| $longitude   | String      |经度|
| $latitude    | String      |纬度|
| $network     | String      |网络类型|
| $appVersion   | String     |app版本号|
| $appChannel  | String      |app下载源|
| $marketChannel| String     |渠道源|

### 注意事项
+ 采集标识(appId)未注册时，数据会被无视。
+ 采集时间(timestamp)超过当前系统时间时,数据会被无视。


