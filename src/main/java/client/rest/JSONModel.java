package client.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import util.IOUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JSONModel<T> {
    @JsonIgnore
    protected final static Logger STATIC_LOGGER = Logger.getLogger(JSONModel.class);
    @JsonIgnore
    public final Logger LOGGER = Logger.getLogger(this.getClass());

    protected static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper;
    }

    @JsonIgnore
    public JSONObject asJson() {
        try {
            return new JSONObject(getMapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            LOGGER.warn("can't make json from " + this);
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @JsonIgnore
    public JSONObject asJsonAllowNull() {
        ObjectMapper mapper = getMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
        try {
            return new JSONObject(mapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            LOGGER.warn("can't make json from " + this);
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @JsonIgnore
    public T makeMyFromJson(JSONObject jsonObject) {
        ObjectMapper mapper = getMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Class<T> currentClass = (Class<T>) this.getClass();
            return mapper.readValue(jsonObject.toString(), currentClass);
        } catch (IOException e) {
            LOGGER.warn("can't parse " + jsonObject);
            e.printStackTrace();
        }
        return null;
    }


    @JsonIgnore
    public static <T extends JSONModel> T makeMyFromJson(Class<T> currentClass, JSONObject jsonObject) {
        return makeMyFromJsonString(currentClass, jsonObject.toString());
    }

    @JsonIgnore
    public static <T> T makeMyFromJsonString(Class<T> currentClass, String jsonObjectString) {
        ObjectMapper mapper = getMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (jsonObjectString == null) {
            STATIC_LOGGER.warn("cant parse 'null' context");
            return null;
        }
        try {
            //STATIC_LOGGER.info("TARGET object class : " + currentClass);
            return mapper.readValue(jsonObjectString, currentClass);
        } catch (IOException e) {
            STATIC_LOGGER.warn("can't parse " + jsonObjectString);
            e.printStackTrace();
        }
        return null;
    }

    @JsonIgnore
    public static Map makeMapFromJsonString(String jsonObjectString) {
        ObjectMapper mapper = getMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (jsonObjectString == null) {
            STATIC_LOGGER.warn("cant parse 'null' context");
            return null;
        }
        try {
            //STATIC_LOGGER.info("TARGET object class : " + currentClass);
            return mapper.readValue(jsonObjectString, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (IOException e) {
            STATIC_LOGGER.warn("can't parse " + jsonObjectString);
            e.printStackTrace();
        }
        return null;
    }

    @JsonIgnore
    public static <T> List<T> makeMyFromJsonArrayString(Class<T> currentClass, String jsonObjectString) {
        ObjectMapper mapper = getMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (jsonObjectString == null) {
            STATIC_LOGGER.warn("cant parse 'null' context");
            return null;
        }
        try {
            //STATIC_LOGGER.info("TARGET object class : " + currentClass);
            List<T> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonObjectString);
            for (int i = 0; i < jsonArray.length(); i++) {
                T t = mapper.readValue(jsonArray.getJSONObject(i).toString(), currentClass);
                list.add(t);
            }

            return list;
        } catch (IOException e) {
            STATIC_LOGGER.warn("can't parse " + jsonObjectString);
            e.printStackTrace();
        }
        return null;
    }

    @JsonIgnore
    public T makeMyFromJsonString(String jsonObjectString) {
        ObjectMapper mapper = getMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Class<T> currentClass = (Class<T>) this.getClass();
            //  LOGGER.info("TRY to transform, TARGET object class : " + currentClass);
            return mapper.readValue(jsonObjectString, currentClass);
        } catch (IOException e) {
            LOGGER.warn("can't parse " + jsonObjectString + "for " + this.getClass());
            e.printStackTrace();
        }
        return null;
    }

    protected static <T extends JSONModel> T makeTmpContainer(Class<T> currentClass) {
        Constructor<T> constructor = null;
        try {
            constructor = currentClass.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        T object = null;
        try {
            object = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        assert object != null;
        return object;
    }

    public void saveToFile(String fileName) {
        IOUtils.saveTextToFile(new java.io.File(fileName), asJson().toString());
    }
}
