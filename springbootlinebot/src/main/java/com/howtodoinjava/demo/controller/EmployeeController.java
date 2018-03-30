package com.howtodoinjava.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.howtodoinjava.demo.model.Employee;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import retrofit2.Response;

@RestController
public class EmployeeController {
	
	private final String  channelAccessToken = "3hflPK8vhXAjFJaWd5bRdGkjjEVHCzDgWGzULBTcHmK4KwuFPmiwjRoY/7dvuzXd8YFMor68osBzAxlB7OOwPh8R0ThmxG5OkVoJ2oGT8CnmnTT0C0QKQps9/mxY/JimBSJ4IRucy921QW8qYFxIWAdB04t89/1O/w1cDnyilFU=";
	
	public  boolean reply = false;
	
	@RequestMapping("/test")
    public List<Employee> getEmployees() 
    {
		List<Employee> employeesList = new ArrayList<Employee>();
		employeesList.add(new Employee(1,"lokesh","gupta","howtodoinjava@gmail.com"));
		
		
		return employeesList;
    }
	
	@RequestMapping("/see")
    public String see(@RequestBody String body) throws IOException 
    {
		System.out.println(body);
		
		
		JSONObject jsnobject = new JSONObject(body);
		
		JSONArray jsonArray = jsnobject.getJSONArray("events");
		
		
	    for (int i = 0; i < jsonArray.length(); i++) {
	    	
	      
	       String replyToken = jsonArray.getJSONObject(i).get("replyToken").toString();
	       String msg =((JSONObject)(jsonArray.getJSONObject(i).get("message"))).get("text").toString();
	      
	       if("start".equals(msg)){
	    	   reply = true;
	       }else if("stop".equals(msg)){
	    	   reply = false;
	       }
	       
	       System.out.println("reply:"+reply);
	       
	       if(reply){
	    	   
	    	  /* if("IU".equals(msg)){
	    		   msg = "主管您好，我叫吳書溥，很高興有機會來 貴公司面試!!";
	    	   }*/
	    	   
	    	   System.out.println("msg:"+msg);
	    	   TextMessage textMessage = new TextMessage(msg);
			   ReplyMessage replyMessage = new ReplyMessage(
					     replyToken,
				        textMessage
				);
				Response<BotApiResponse> response =
				        LineMessagingServiceBuilder
				                .create(channelAccessToken)
				                .build()
				                .replyMessage(replyMessage)
				                .execute();
				System.out.println("reponse:"+response.code() + " " + response.message());	
	       }
	       	
	    }
		
		return "OK";
    }

}
