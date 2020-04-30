package com.javapai.dataflow.collector.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javapai.dataflow.collector.utils.DateUtil;


@RestController
public class ExportController {
	/**
	 * index 名称
	 */
	private static final String INDEX_NAME = "ubt_event";

	/**
	 * type 名称
	 */
	private static final String TYPE_NAME = "ubt";
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	static SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS" );
	
	static String queryJson = "{\n" +
	        "    \"query\": {\n" +
	        "        \"match\": {\n" +
	        "            \"appId\": \"app_haitao\"\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";
	
	
	
//	public static void export1() throws IOException {
//		String resultString = "";
//		
//		// get data from es(low rest api)
//		RestClient restClient = RestClient.builder(new HttpHost("47.103.139.145", 9200, "http")).build();
//		String endPoint = "/" + INDEX_NAME + "/" + TYPE_NAME + "/_search";
//		Request request = new Request("POST", endPoint);
//		request.setJsonEntity(queryJson);//json 参数
//		Response response = restClient.performRequest(request);
//		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
//			System.out.println(resultString);
//		}
//		
////		// set data to excel
//		// 创建HSSFWorkbook对象
//		XSSFWorkbook wb = new XSSFWorkbook();
//		// 创建HSSFSheet对象
//		XSSFSheet sheet = wb.createSheet("ubt数据导出");
//		// 创建HSSFRow对象
//		XSSFRow row = sheet.createRow(0);
//		String[] cellTitle = {"AppId","数据来源","用户标识","采集时间","事件名称","交互动作","采集内容"};
//		for (int i = 0; i < cellTitle.length; i++) {
//			row.createCell(i).setCellValue(cellTitle[i]);
//		}
//		
////		JsonFactory factory = new JsonFactory();
////		JsonParser parser = factory.createParser(resultString);
//		JsonNode rootNode = objectMapper.readTree(resultString).get("hits");
//		System.out.println("------>查询结果：" + rootNode.get("total").asText());
//		rootNode.get("hits").forEach(action -> {
//			System.out.println("------>appId:" + action.get("_source").get("appId").asText());
//			System.out.println("------>sourceId:" + action.get("_source").get("sourceId").asText());
//			System.out.println("------>userId:" + action.get("_source").get("originalId").asText());
//			System.out.println("------>timestamp:" + action.get("_source").get("timestamp").asText());
//			System.out.println("------>event:" + action.get("_source").get("event").asText());
//			System.out.println("------>action:" + action.get("_source").get("action").asText());
//			System.out.println("------>properties:" + action.get("_source").get("properties").toString());
//		});
//		
////		for (int i = 0; i < rootNode.get("hits").size(); i++) {
////			XSSFRow temp = sheet.createRow(i + 1);
////			temp.createCell(0).setCellValue(rootNode.get("hits").get(i).get("_source").get("appId").asText());
////			temp.createCell(1).setCellValue(rootNode.get("hits").get("_source").get("sourceId").asText());
////			temp.createCell(2).setCellValue(rootNode.get("hits").get("_source").get("originalId").asText());
////			temp.createCell(3).setCellValue(rootNode.get("hits").get("_source").get("timestamp").asText());
////			temp.createCell(4).setCellValue(rootNode.get("hits").get("_source").get("event").asText());
////			temp.createCell(5).setCellValue(rootNode.get("hits").get("_source").get("action").asText());
////			temp.createCell(6).setCellValue(rootNode.get("hits").get("_source").get("properties").asText());
////		}
////		
////		// 输出Excel文件
////		FileOutputStream output = new FileOutputStream("d:\\workbook.xls");
////		wb.write(output);
////		output.flush();
//	}
	
	public static void export2() throws IOException {
		Settings settings = Settings.builder().put("cluster.name","my-application").build();
		TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName("47.103.139.145"), 9300));
		
		//3.查询类型(match_all)
//		QueryBuilder qBuilder = QueryBuilders.matchAllQuery();
//		QueryBuilder qBuilder = QueryBuilders.queryStringQuery("appId:app_langhuabai");
//		QueryBuilder qBuilder = QueryBuilders.queryStringQuery("appId:app_haitao");
//		QueryBuilder qBuilder = QueryBuilders.queryStringQuery("appId:app_haitao_kaiye");
//		QueryBuilder qBuilder = QueryBuilders.queryStringQuery("appId:app_wbyouxuan");
//		QueryBuilder qBuilder = QueryBuilders.queryStringQuery("appId:app_huodongye");
		//多条件查询
		BoolQueryBuilder qBuilder = QueryBuilders.boolQuery();
		qBuilder.must(QueryBuilders.matchQuery("appId", "app_langhuabai"));
//		qBuilder.must(QueryBuilders.matchQuery("event", "ht_home "));
		qBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte(DateUtil.convertTimeToLong("2020-03-27 00:00:00")).lte(DateUtil.convertTimeToLong("2020-03-29 23:59:59")));
		
		SearchResponse sResponse = client.prepareSearch("ubt_event")
				.setQuery(qBuilder)
				.setSize(10000) //实际返回的数量为size*index的主分片个数（在ES 5.x版本中，返回的数据量就是参数中指定的数据量）
				.setScroll(TimeValue.timeValueMinutes(1))
				.execute().actionGet();
		
		Workbook wb = getTargetSheet();
		
		int page = 0;
		do {
			// 读取结果集数据
			System.out.println("第" + (page+1) + "次打印数据：");
			SearchHits hits = sResponse.getHits();
			for (int i = 0; i < hits.getHits().length; i++) {
				Row temp = wb.getSheetAt(0).createRow(page * 100 + i + 1);
				System.out.println(hits.getAt(i).getSourceAsString());
				Map<String, Object> map = hits.getAt(i).getSourceAsMap();// 将获取的值转换成map的形式
				temp.createCell(0).setCellValue(map.get("appId").toString());
				temp.createCell(1).setCellValue(map.get("sourceId").toString());
				temp.createCell(2).setCellValue(String.valueOf(map.get("originalId")));
				temp.createCell(3).setCellValue(sdf.format(new java.util.Date(Long.valueOf(map.get("timestamp").toString()))));
				temp.createCell(4).setCellValue(map.get("event").toString());
				temp.createCell(5).setCellValue(map.get("action").toString());
				temp.createCell(6).setCellValue(map.get("properties").toString());
			}
			page++;
			// 将scorllId循环传递
			sResponse = client.prepareSearchScroll(sResponse.getScrollId()).setScroll(TimeValue.timeValueMinutes(1)).execute().actionGet();

			// 当searchHits的数组为空的时候结束循环，至此数据全部读取完毕
		} while (sResponse.getHits().getHits().length != 0);
		
		// 输出Excel文件
		FileOutputStream output = new FileOutputStream("d:\\workbook-1.xlsx");
		wb.write(output);
		output.flush();
	}
	
	
	@GetMapping("export3")
	public static void export3() throws IOException {
		Settings settings = Settings.builder().put("cluster.name","my-application").build();
		TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName("47.103.139.145"), 9300));
		
		//多条件查询
		BoolQueryBuilder qBuilder = QueryBuilders.boolQuery();
		qBuilder.filter(QueryBuilders.matchQuery("appId", "APP_FenQi"));
		qBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte(DateUtil.convertTimeToLong("2019-11-28 00:00:00")).lte(DateUtil.convertTimeToLong("2019-12-05 16:59:59")));
		
		SearchResponse sResponse = client.prepareSearch("ubt_event").setQuery(qBuilder)
				.setSize(10000) //实际返回的数量为size*index的主分片个数（在ES 5.x版本中，返回的数据量就是参数中指定的数据量）
				.setScroll(TimeValue.timeValueMinutes(1))
				.execute().actionGet();
		
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://172.32.10.202:3306/xxx" +
                    "?useUnicode=true&characterEncoding=GBK&useServerPrepStmts=false&rewriteBatchedStatements=true","dev","dev");
            connection.setAutoCommit(false); //设置手动提交
            //预编译sql对象,只编译一回
			PreparedStatement ps = connection.prepareStatement("insert into event (appId,sourceId,userId,timestamp,event,action,properties) values(?,?,?,?,?,?,?)");
            
			
			int page = 0;
			do {
				// 读取结果集数据
				System.out.println("第" + (page+1) + "次打印数据：");
				SearchHits hits = sResponse.getHits();
				for (int i = 0; i < hits.getHits().length; i++) {
					System.out.println(hits.getAt(i).getSourceAsString());
					Map<String, Object> map = hits.getAt(i).getSourceAsMap();// 将获取的值转换成map的形式
					ps.setString(1, map.get("appId").toString());
					ps.setString(2, map.get("sourceId").toString());
					ps.setString(3, String.valueOf(map.get("originalId")));
					ps.setString(4, sdf.format(new java.util.Date(Long.valueOf(map.get("timestamp").toString()))));
					ps.setString(5, map.get("event").toString());
					ps.setString(6,map.get("action").toString());
					ps.setString(7,String.valueOf(map.get("properties")));
//					if(map.get("userId").equals("20210095") && map.get("sourceId").equals("CBE79584-CFB4-4E48-A42E-74D720A812A0")) {
//						System.out.println(String.valueOf(map.get("properties")));
//						ps.setString(7,"");
//					}else {
//						ps.setString(7,String.valueOf(map.get("properties")));
//					}
					ps.addBatch();
				}
				
	            ps.executeBatch();//提交批处理
	            connection.commit();//执行
	            
				page++;
				// 将scorllId循环传递
				sResponse = client.prepareSearchScroll(sResponse.getScrollId()).setScroll(TimeValue.timeValueMinutes(1)).execute().actionGet();
				
				// 当searchHits的数组为空的时候结束循环，至此数据全部读取完毕
			} while (sResponse.getHits().getHits().length != 0);
			
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		System.out.println("finished!");
		
	}

	public static void main(String[] args) throws IOException {
//		export1();
		export2();
		
//		export3();
	}
	
	public static Workbook getTargetSheet() {
		// 创建HSSFWorkbook对象
		Workbook wb = new XSSFWorkbook();// SXSSFWorkbook
		// 创建HSSFSheet对象
		Sheet sheet = wb.createSheet("ubt数据导出");
		// 创建HSSFRow对象
		Row row = sheet.createRow(0);
		String[] cellTitle = { "AppId", "数据来源", "用户标识", "采集时间", "事件名称", "交互动作", "采集内容" };
		for (int i = 0; i < cellTitle.length; i++) {
			row.createCell(i).setCellValue(cellTitle[i]);
		}
		
		return wb;
	};
	
}
