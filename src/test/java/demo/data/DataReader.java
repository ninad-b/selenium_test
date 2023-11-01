package demo.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	public List<HashMap<String, Object>> getJsonDataToMap() throws IOException
	{
		String jsonStringContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"/SeleniumFrameworkDesign/src/test/java/demo/data/demoProjectData.json"), 
															StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, Object>> mapDataList = mapper.readValue(jsonStringContent, new TypeReference<List<HashMap<String, Object>>>() {});
		
		return mapDataList;
	}
}
