package org.heart.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class JsonUtils {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(JsonUtils.class);

    /** The object mapper. */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String LOG_ERROR_MSG = "convert failed {}";

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
    }

    private JsonUtils() {
    }

    /**
     * Convert to json.
     *
     * @param object
     *            the object
     * @return the string
     * @throws JsonProcessingException 
     */
    public static String convertToJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException|RuntimeException e) {
            LOGGER.error(LOG_ERROR_MSG, e);
            return "";
        }
    }

    public static <T> T json2Bean(String json, Class<T> clazz)  {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException|RuntimeException e) {
            LOGGER.error(LOG_ERROR_MSG, e);
            return null;
        }
    }

    public static <T> List<T> json2List(String json, Class<T> clazz){
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> lst;
        try {
            lst = objectMapper.readValue(json, javaType);
        } catch (IOException | RuntimeException e) {
            LOGGER.error(LOG_ERROR_MSG, e);
            return Collections.emptyList();
        }
        return lst;
    }

    /**
     * 获取泛型的Collection Type
     * 
     * @param collectionClass
     *            泛型的Collection
     * @param elementClasses
     *            元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass,
            Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(
                collectionClass, elementClasses);
    }

}
