package com.jfcore.elatis;

import java.io.File;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jfcore.elatis.pojo.QueryResult;
import com.jfcore.tools.PropertiesHelp;
 

 

public class HanderEngine implements InvocationHandler {
	
	
	static VelocityEngine ve=null;
	
 
	
	
	
	static HashMap<String,Element> selectNodeList = new HashMap<String,Element>();
	
	private static Logger logger = LoggerFactory.getLogger(HanderEngine.class);
	
	
	
	private Lock lock = new ReentrantLock();
	
	
	Class<?> _tClass;

	public HanderEngine(Class<?> tClass) {
		_tClass=tClass;
	}

	public static void main(String[] args) {
		
	

		// 取得velocity的模版内容, 模板内容来自字符传

		String content = "";
		content += "Welcome  $name  to Javayou.com! ";
		content += " today is  $date.";

		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();

		// 把数据填入上下文
		context.put("name", "javaboy2012");

		context.put("date", (new Date()).toString());

		// 输出流
		StringWriter writer = new StringWriter();

		// 转换输出

		ve.evaluate(context, writer, "", content); // 关键方法
		
		System.out.println(writer.toString());

	}
	
	String rendering(String script,VelocityContext context)
	{
		if(ve==null)
		{
			ve = new VelocityEngine();
			ve.init();
		}
		// 输出流
		StringWriter writer = new StringWriter();

		// 转换输出
		ve.evaluate(context, writer, "", script); // 关键方法
		
		return writer.toString();
	}

	
	
	
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		
		
		Element scriptNode = getScript(arg1);
		
		if(scriptNode==null)
		{
			return null;
		}
		
		VelocityContext context = new VelocityContext();
		
		
		Parameter[] parameters = arg1.getParameters();
 		
		for (int i = 0; i < parameters.length; i++) {			
			Parameter parameter = parameters[i];	
			context.put(parameter.getName(), arg2[i]);	
		}
		
		
		String script = rendering(scriptNode.getText(),context);
		
		
		String url = scriptNode.attributeValue("url");
		 
		String method = scriptNode.attributeValue("method");
		
		logger.info("方法:"+arg1.getName()+", url:"+url+", method:"+method+", 执行的脚本:"+script);
		
		
		
		String result=null;
		
		
		switch (method) {
		case "post":
			
			result = ElatisRequest.build(url).post(script);			
			break;
			
		case "get":
			result = ElatisRequest.build(url).get();
			break;
			
		case "put":
			ElatisRequest.build(url).put(script); 
			break;
			
		case "delete":
			ElatisRequest.build(url).delete();
			break;

		default:
			break;
		}
		

		
		return result;
	}




	private Element getScript(Method arg1) {
		String packageName = arg1.getDeclaringClass().getPackage().getName().replace(".", "/"); //"com/jms/service/search/dataAccess";
		
		String interfaceName = arg1.getDeclaringClass().getSimpleName();// "IFullSubjectSearch";
		
		String key = arg1.getDeclaringClass().getName()+"."+arg1.getName();
		
		Element script=null; 
		
		if(selectNodeList.containsKey(key))
		{
			script= selectNodeList.get(key);
		}
		
		 
		if(script == null)
		{

						
			URL url = this.getClass().getClassLoader().getResource(packageName+"/xmaps/"+interfaceName+"Mapper.xml");
			
			if(url==null)
			{
				try {
					String xmapsPath = PropertiesHelp.getApplicationConf("xmaps.path");				
					url= new URL("file:"+xmapsPath+"/"+interfaceName+"Mapper.xml");
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
			URL url1 =  this.getClass().getClassLoader().getResource("");
			
			logger.info("URL 根路径:"+url1);
			
			logger.info("加载URL:"+url+";"+packageName+"/xmaps/"+interfaceName+"Mapper.xml");
 
						
			File xmlFile = new File(url.getFile());
	        try {
	        		        	
	        	
	            // 创建SAXReader对象
	            SAXReader reader = new SAXReader();
	            // 读取XML文件
	            Document document = reader.read(xmlFile);
	            // 获取根元素
	            Element root = document.getRootElement();
	            // 现在可以使用root元素进行遍历和操作XML文件的内容
	            
	            
	            List<Element> tempNode =  root.elements("select");
	            if(tempNode==null)
	            {
	            	return null;
	            }
	            
	            lock.tryLock();
	            
	            for (Element element : tempNode) {
	            	
	            	 selectNodeList.put(arg1.getDeclaringClass().getName()+"."+element.attributeValue("id"), element);
				}
	 
	            
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	        finally {
	        	lock.unlock();
			}
			
		}
		
		if(selectNodeList.containsKey(key))
		{
			script= selectNodeList.get(key);
		}
		
		
		return script;
	}

}
