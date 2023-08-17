package com.salpreh.jvalidator.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataLoader {

  public static String SCHEMA_1 = "/schemas/schema1.json";
  public static String DATA_1_WITH_CUSTOM = "/data/data1-withCustom.json";
  public static String DATA_1_WITHOUT_CUSTOM = "/data/data1-withoutCustom.json";

  public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static Map<String, Object> loadData(String path) {
    try {
      return OBJECT_MAPPER.readValue(DataLoader.class.getResourceAsStream(path), Map.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
