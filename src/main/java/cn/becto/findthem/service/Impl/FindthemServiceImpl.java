package cn.becto.findthem.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.becto.findthem.pojo.Answer;
import cn.becto.findthem.pojo.Author;
import cn.becto.findthem.pojo.FindthemResult;
import cn.becto.findthem.pojo.ResultData;
import cn.becto.findthem.service.FindthemService;
import cn.becto.findthem.utils.StringReplaceUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class FindthemServiceImpl implements FindthemService {
	@Autowired
	private JedisPool pool;
	@Autowired
	private HttpSolrServer solrServer;
	@Value("${ANSWER_PAGE_URL}")
	private String ANSWER_PAGE_URL;

	@Override
	public FindthemResult syncData() throws Exception {
		// 获取存储在redis里的上一次更新的截止时间点
		Jedis jedis = pool.getResource();
		String last_sync = jedis.get("findthem:last_sync");
		long last_sync_long;
		if (last_sync == null || "".equals(last_sync)) {
			last_sync_long = 1542290139L;
		} else {
			last_sync_long = Long.parseLong(last_sync);
		}
		//声明一个变量，用来存储本次更新开始的时间断点
		long updated_time_record = last_sync_long;
		
		// 抓取数据
		CloseableHttpClient client = HttpClients.createDefault();

		int offset = 0;
		Random random = new Random();
		
		while (true) {
			Thread.sleep(random.nextInt(10) * 200);
			boolean flag = false;
			HttpGet httpGet = new HttpGet(
					ANSWER_PAGE_URL + "/answers?include=content&limit=20&offset=" + offset
							+ "&sort_by=updated");
			offset += 20;
			httpGet.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			httpGet.setConfig(requestConfig);
			
			CloseableHttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine());
			
			//获取到抓取的json字符串数据并转换成pojo对象
			String resultJson = EntityUtils.toString(entity, "utf-8");
			if (resultJson == null || "".equals(resultJson)) {
				return FindthemResult.ok(200, "已无新数据", null);
			}
			
			ResultData resultData = JSON.parseObject(resultJson, ResultData.class);
			//获取本次获得的20条数据
			List<Answer> answerList = resultData.getData();
			//记录本次更新开始的时间断点，随后存储在 redis的findthem:last_sync中
			if (offset <= 20) {
				updated_time_record = answerList.get(0).getUpdated_time();
			}
			for (Answer answer : answerList) {
				//判断上次更新截止时间点，如果小于等于之，则结束更新。
				if (last_sync_long > answer.getUpdated_time()) {
					flag = true;
					break;
				}
				//更新solr索引
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", answer.getId());
				document.addField("answer_name", answer.getAuthor().getName());
				document.addField("answer_token", answer.getAuthor().getUrl_token());
				document.addField("answer_updated", answer.getUpdated_time());
				document.addField("answer_content", answer.getContent());
				solrServer.add(document);
			}
			//结束while循环
			solrServer.commit();
			if (flag) {
				//存入新的更新断点
				jedis.set("findthem:last_sync", updated_time_record + "");
				//提交索引库更新
				solrServer.commit();
				break;
			}
		}
		return FindthemResult.ok(200, "已无新数据", null);
	}

	@Override
	public FindthemResult findthemByKeyword(String keyword, Integer page) throws Exception {
		SolrQuery query = new SolrQuery();
		query.set("q", "answer_content:" + keyword);
		//每页固定显示5条记录
		query.setRows(5);
		query.setStart(5 * (page - 1));
		query.addSort("answer_updated", ORDER.desc);
		
		QueryResponse response = solrServer.query(query);
		//结果封装到ResultData对象中
		ResultData resultData = new ResultData();
		List<Answer> answerList = new ArrayList<Answer>();
		SolrDocumentList results = response.getResults();
		for (SolrDocument solrDocument : results) {
			Answer answer = new Answer();
			Author author = new Author();
			author.setName(solrDocument.get("answer_name").toString());
			author.setUrl_token(solrDocument.get("answer_token").toString());
			answer.setId(solrDocument.get("id").toString());
			answer.setUpdated_time(Long.parseLong(solrDocument.get("answer_updated").toString()));
			
			//图片连接的修复
			String contentString = solrDocument.get("answer_content").toString();
			String fixedContentString = StringReplaceUtils.imgSrcFix(contentString);
			
			answer.setContent(fixedContentString);
			answer.setAuthor(author);
			answerList.add(answer);
		}
		resultData.setData(answerList);
		return FindthemResult.ok(200, "查找完毕", resultData);
	}

}
