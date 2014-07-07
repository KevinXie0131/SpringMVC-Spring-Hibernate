package rml.util;

import org.codehaus.jackson.map.ObjectMapper;

public class JacksonJsonUtil {

	private static ObjectMapper mapper;

	/*
	 * get ObjectMapper instance
	 * 
	 * @param createNew：true，new instance；false, existing mapper instance
	 * 
	 * @return
	 */
	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {

		if (createNew) {
			return new ObjectMapper();
		} else if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	/**
	 * 
	 *  java object => json string
	 * 
	 * @param obj: Java object      
	 * 
	 * @return json string
	 * 
	 * @throws Exception
	 */

	public static String beanToJson(Object obj) throws Exception {

		try {
			ObjectMapper objectMapper = getMapperInstance(false);
			String json = objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * 
	 * java object => json string
	 * 
	 * @param obj: Java object      
	 * 
	 * @param createNew：true，new instance；false, existing mapper instance
	 * 
	 * @return json string
	 * 
	 * @throws Exception
	 */

	public static String beanToJson(Object obj, Boolean createNew)
			throws Exception {

		try {
			ObjectMapper objectMapper = getMapperInstance(createNew);
			String json = objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * 
	 * json string => java object 
	 * 
	 * @param json: string
	 * 
	 * @param cls: Java class
	 *           
	 * @return
	 * 
	 * @throws Exception
	 */

	public static Object jsonToBean(String json, Class<?> cls) throws Exception {

		try {
			ObjectMapper objectMapper = getMapperInstance(false);
			Object vo = objectMapper.readValue(json, cls);
			return vo;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * 
	 * json string => java object 
	 * 
	 * @param json: string
	 * 
	 * @param cls: Java class
	 * 
	 * @param createNew：true，new instance；false, existing mapper instance
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public static Object jsonToBean(String json, Class<?> cls, Boolean createNew)
			throws Exception {

		try {
			ObjectMapper objectMapper = getMapperInstance(createNew);
			Object vo = objectMapper.readValue(json, cls);
			return vo;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

}
