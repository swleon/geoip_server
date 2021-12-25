
# GEO服务接口文档


**简介**:基于ip离线地址库的geo全球行政地区服务


**HOST**:http://localhost:8081


**联系人**:wuque


**Version**:1.0


**接口路径**:/v3/api-docs

**文档地址**：  
- http://localhost:8081/swagger-ui/index.html
- http://localhost:8081/doc.html


[TOC]


# geo-controller


## children


**接口地址**:`/api/geo/children`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|geonameId|geonameId|query|true|integer(int64)||
|lang|lang|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|GeoProvinceVO|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|adminCode1||string||
|adminName1||string||
|admincodes1Iso31662||string||
|countryCode||string||
|countryId||string||
|countryName||string||
|created||integer(int32)|integer(int32)|
|fcl||string||
|fclName||string||
|fcode||string||
|fcodeName||string||
|geoChildrenInfoList||array|GeoArea|
|&emsp;&emsp;adminCode1||string||
|&emsp;&emsp;adminName1||string||
|&emsp;&emsp;admincodes1Iso31662||string||
|&emsp;&emsp;countryCode||string||
|&emsp;&emsp;countryId||string||
|&emsp;&emsp;countryName||string||
|&emsp;&emsp;created||integer(int32)||
|&emsp;&emsp;fcl||string||
|&emsp;&emsp;fclName||string||
|&emsp;&emsp;fcode||string||
|&emsp;&emsp;fcodeName||string||
|&emsp;&emsp;geonameId||integer(int64)||
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;isDeleted||integer(int32)||
|&emsp;&emsp;lat||string||
|&emsp;&emsp;level||integer(int32)||
|&emsp;&emsp;lng||string||
|&emsp;&emsp;locales||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;pgeonameId||integer(int64)||
|&emsp;&emsp;population||integer(int64)||
|&emsp;&emsp;toponymName||string||
|&emsp;&emsp;updated||integer(int32)||
|geonameId||integer(int64)|integer(int64)|
|id||integer(int64)|integer(int64)|
|isDeleted||integer(int32)|integer(int32)|
|lat||string||
|level||integer(int32)|integer(int32)|
|lng||string||
|locales||string||
|name||string||
|pgeonameId||integer(int64)|integer(int64)|
|population||integer(int64)|integer(int64)|
|toponymName||string||
|totalResultsCount||integer(int64)|integer(int64)|
|updated||integer(int32)|integer(int32)|


**响应示例**:
```javascript
[
	{
		"adminCode1": "",
		"adminName1": "",
		"admincodes1Iso31662": "",
		"countryCode": "",
		"countryId": "",
		"countryName": "",
		"created": 0,
		"fcl": "",
		"fclName": "",
		"fcode": "",
		"fcodeName": "",
		"geoChildrenInfoList": [
			{
				"adminCode1": "",
				"adminName1": "",
				"admincodes1Iso31662": "",
				"countryCode": "",
				"countryId": "",
				"countryName": "",
				"created": 0,
				"fcl": "",
				"fclName": "",
				"fcode": "",
				"fcodeName": "",
				"geonameId": 0,
				"id": 0,
				"isDeleted": 0,
				"lat": "",
				"level": 0,
				"lng": "",
				"locales": "",
				"name": "",
				"pgeonameId": 0,
				"population": 0,
				"toponymName": "",
				"updated": 0
			}
		],
		"geonameId": 0,
		"id": 0,
		"isDeleted": 0,
		"lat": "",
		"level": 0,
		"lng": "",
		"locales": "",
		"name": "",
		"pgeonameId": 0,
		"population": 0,
		"toponymName": "",
		"totalResultsCount": 0,
		"updated": 0
	}
]
```


## continent


**接口地址**:`/api/geo/continent`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ContinentVO|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|continent||string||
|continentName||string||
|geonameId||integer(int64)|integer(int64)|


**响应示例**:
```javascript
[
	{
		"continent": "",
		"continentName": "",
		"geonameId": 0
	}
]
```


## country


**接口地址**:`/api/geo/country`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|lang|lang|query|false|string||
|search|search|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|GeoCountryVO|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|areaInSqKm||string||
|capital||string||
|capitalCN||string||
|continent||string||
|continentName||string||
|continentNameCN||string||
|countryCode||string||
|countryName||string||
|countryNameCN||string||
|currencyCode||string||
|east||number(double)|number(double)|
|fipsCode||string||
|geonameId||integer(int64)|integer(int64)|
|isoAlpha3||string||
|isoNumeric||string||
|languages||string||
|north||number(double)|number(double)|
|population||string||
|postalCodeFormat||string||
|south||number(double)|number(double)|
|west||number(double)|number(double)|


**响应示例**:
```javascript
[
	{
		"areaInSqKm": "",
		"capital": "",
		"capitalCN": "",
		"continent": "",
		"continentName": "",
		"continentNameCN": "",
		"countryCode": "",
		"countryName": "",
		"countryNameCN": "",
		"currencyCode": "",
		"east": 0,
		"fipsCode": "",
		"geonameId": 0,
		"isoAlpha3": "",
		"isoNumeric": "",
		"languages": "",
		"north": 0,
		"population": "",
		"postalCodeFormat": "",
		"south": 0,
		"west": 0
	}
]
```


## countryByGroup


**接口地址**:`/api/geo/countryByGroup`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|lang|lang|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## ip


**接口地址**:`/api/geo/ip`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ip|ip|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|IpInfo|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|cityGeonameId||integer(int32)|integer(int32)|
|cityName||string||
|countryCode||string||
|countryGeonameId||integer(int32)|integer(int32)|
|countryName||string||
|ip||string||
|latitude||number(double)|number(double)|
|longitude||number(double)|number(double)|
|postalCode||string||
|provinceCode||string||
|provinceGeonameId||integer(int32)|integer(int32)|
|provinceName||string||


**响应示例**:
```javascript
{
	"cityGeonameId": 0,
	"cityName": "",
	"countryCode": "",
	"countryGeonameId": 0,
	"countryName": "",
	"ip": "",
	"latitude": 0,
	"longitude": 0,
	"postalCode": "",
	"provinceCode": "",
	"provinceGeonameId": 0,
	"provinceName": ""
}
```


## sync


**接口地址**:`/api/geo/sync`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|lang|lang|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResponseEntity|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|body||object||
|statusCode|可用值:ACCEPTED,ALREADY_REPORTED,BAD_GATEWAY,BAD_REQUEST,BANDWIDTH_LIMIT_EXCEEDED,CHECKPOINT,CONFLICT,CONTINUE,CREATED,DESTINATION_LOCKED,EXPECTATION_FAILED,FAILED_DEPENDENCY,FORBIDDEN,FOUND,GATEWAY_TIMEOUT,GONE,HTTP_VERSION_NOT_SUPPORTED,IM_USED,INSUFFICIENT_SPACE_ON_RESOURCE,INSUFFICIENT_STORAGE,INTERNAL_SERVER_ERROR,I_AM_A_TEAPOT,LENGTH_REQUIRED,LOCKED,LOOP_DETECTED,METHOD_FAILURE,METHOD_NOT_ALLOWED,MOVED_PERMANENTLY,MOVED_TEMPORARILY,MULTIPLE_CHOICES,MULTI_STATUS,NETWORK_AUTHENTICATION_REQUIRED,NON_AUTHORITATIVE_INFORMATION,NOT_ACCEPTABLE,NOT_EXTENDED,NOT_FOUND,NOT_IMPLEMENTED,NOT_MODIFIED,NO_CONTENT,OK,PARTIAL_CONTENT,PAYLOAD_TOO_LARGE,PAYMENT_REQUIRED,PERMANENT_REDIRECT,PRECONDITION_FAILED,PRECONDITION_REQUIRED,PROCESSING,PROXY_AUTHENTICATION_REQUIRED,REQUESTED_RANGE_NOT_SATISFIABLE,REQUEST_ENTITY_TOO_LARGE,REQUEST_HEADER_FIELDS_TOO_LARGE,REQUEST_TIMEOUT,REQUEST_URI_TOO_LONG,RESET_CONTENT,SEE_OTHER,SERVICE_UNAVAILABLE,SWITCHING_PROTOCOLS,TEMPORARY_REDIRECT,TOO_EARLY,TOO_MANY_REQUESTS,UNAUTHORIZED,UNAVAILABLE_FOR_LEGAL_REASONS,UNPROCESSABLE_ENTITY,UNSUPPORTED_MEDIA_TYPE,UPGRADE_REQUIRED,URI_TOO_LONG,USE_PROXY,VARIANT_ALSO_NEGOTIATES|string||
|statusCodeValue||integer(int32)|integer(int32)|


**响应示例**:
```javascript
{
	"body": {},
	"statusCode": "",
	"statusCodeValue": 0
}
```
