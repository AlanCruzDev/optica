package com.bolsaideas.springboot.backend.optica.procesador;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper<T>{

  public static <T> Object ConvertJsonObject(Map<String, Object> data, TypeReference<T> type) throws IOException{
    ObjectMapper mapper = new ObjectMapper();
    return mapper.convertValue(data.get("#result-set-1"), type);
  }
}
