package com.silgan.inventory.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {

	public static String convertListToJson(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
        String arrayToJson = "";
        if(null!=object) {
        	try {
    			arrayToJson = objectMapper.writeValueAsString(object);
    			return arrayToJson;
    		} catch (JsonProcessingException e) {
    			e.printStackTrace();
    		}
        }		
		return arrayToJson;
	}
	
	public static Object convertJsonToObject(String jsonStr) {
		ObjectMapper objectMapper = new ObjectMapper();
        Object object = new Object();
        if(null!=jsonStr) {
			try {
				object = objectMapper.readValue(jsonStr, Object.class);
				return object;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
        }
		return object;
	}
	
	public static Map convertJsonMap(String jsonStr) {
		ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> map = new HashMap<String,String>();
        if(null!=jsonStr) {
			try {
				map = objectMapper.readValue(jsonStr, Map.class);
				return map;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
        }
		return map;
	}
	
	
	public static String generateSerialNum(String dcCode,Integer dcSeq) {
		String dcStr = String.format("%06d",dcSeq);  
		return dcCode+dcStr;
	}	 
}
