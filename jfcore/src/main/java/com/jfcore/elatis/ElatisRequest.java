package com.jfcore.elatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.jfcore.elatis.pojo.QueryResult;
import com.jfcore.orm.MybatisUtils;
import com.jfcore.tools.PropertiesHelp;

import kafka.utils.json.JsonObject;

public class ElatisRequest {
	
	
	private static Logger logger = LoggerFactory.getLogger(ElatisRequest.class);
	
	public static void main(String[] args) {
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		String str = ElatisRequest.build("myfile_index/myfile_type/KFQZFooBX-HE2IaJOHHb")
				//.post("com.jms.service.search.elatis.ISearch.searchKey", paramMap, String.class);
				.get();
		
		System.out.println(str);
		
	}

	static String user = null;
	// PropertiesHelp.getApplicationConf("elatis.user");             //"elastic";

	static String password = null;
	// PropertiesHelp.getApplicationConf("elatis.password");    //"123456";

	static String baseUrl = null;
	// PropertiesHelp.getApplicationConf("elatis.password");   //"http://192.168.30.169:9200";
	
 

	RestTemplate restTemplate = null;

	String _url = null;

	public static ElatisRequest build(String url) {
		
		try {
			
			user = PropertiesHelp.getApplicationConf("elatis.user");             

			password = PropertiesHelp.getApplicationConf("elatis.password");    

			baseUrl = PropertiesHelp.getApplicationConf("elatis.baseUrl"); 
				
			
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		
		ElatisRequest httpRequest = new ElatisRequest();

		RestTemplateBuilder builder = new RestTemplateBuilder();
		httpRequest.restTemplate = builder.basicAuthentication(user, password).build();

		httpRequest._url = baseUrl + "/" + url;
		
		return httpRequest;

	}

	private synchronized static <T> HttpEntity<T> getRequestBody(T requestStr) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		return new HttpEntity<>(requestStr, headers);
	}

	public String post(String json) {

		logger.info("执行post："+this._url);
		ResponseEntity<String> res =null;
		try {
			res = restTemplate.postForEntity(this._url, getRequestBody(json), String.class); 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		logger.info("完成post："+this._url);
		
		return res.getBody();

	}
 

	public String get() {

		logger.info("执行get："+this._url);
		
		ResponseEntity<String> res = restTemplate.getForEntity(this._url, String.class);

		String t =  res.getBody();
		
		logger.info("完成get："+this._url);
		
		return t;

	}

	public void put(String json) {

		logger.info("执行put："+this._url);
		
		restTemplate.put(this._url, getRequestBody(json));

		logger.info("完成put："+this._url);
	}
 

	public void delete() {
		
		logger.info("执行delete："+this._url);
		
		restTemplate.delete(this._url);
		
		logger.info("完成delete："+this._url);

	}

}
