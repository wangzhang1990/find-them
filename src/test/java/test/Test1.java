package test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.alibaba.fastjson.JSON;

import cn.becto.findthem.pojo.ResultData;
import redis.clients.jedis.Jedis;

public class Test1 {
	
	@Test
	public void demo1() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://www.zhihu.com/api/v4/questions/275359100/answers?include=content&limit=20&offset=" + 0 + "&sort_by=updated");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		httpGet.setConfig(requestConfig);
		
		CloseableHttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		System.out.println(response.getStatusLine());
		
		String resultJson = EntityUtils.toString(entity, "utf-8");
		
		System.out.println(resultJson);
		
		ResultData parseObject = JSON.parseObject(resultJson, ResultData.class);
		System.out.println(parseObject);
		
		
		response.close();
		client.close();
	}
	
	@Test
	public void demo2() {
		Jedis jedis = new Jedis("192.168.194.133", 6379);
		jedis.auth("123456");
		for (int i = 2; i < 500; i++) {
			String string = jedis.get("findthem:" + i);
			if (string.contains("西安")) {
				System.out.println(string);
			}
		}
	}
}
