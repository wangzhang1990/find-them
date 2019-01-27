package cn.becto.findthem.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import cn.becto.findthem.pojo.FindthemResult;
import cn.becto.findthem.pojo.ResultData;

public interface FindthemService {

	FindthemResult syncData() throws Exception;

	FindthemResult findthemByKeyword(String keyword, Integer page) throws Exception;


	
}
