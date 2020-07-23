package com.saltlux.shkim.stock;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Stock{
	String url;
	String companyValue;
	String currentValue;
	String startValue;
	public Stock() {
		companyValue = "304100";
		url = "https://polling.finance.naver.com/api/realtime.nhn?query=SERVICE_ITEM:" + companyValue;
	}
	public String getJsonFromUrl() throws IOException {
		Document doc = Jsoup.connect(this.url).get();
		return doc.text();
	}
	public String getNewValue(String json) {
		JsonParser jsonParser = new JsonParser();
		JsonObject parsing = (JsonObject) jsonParser.parse(json);
		JsonObject result = (JsonObject) parsing.get("result");
		JsonArray areas = (JsonArray) result.get("areas");
		JsonObject area = (JsonObject) areas.get(0);
		JsonArray datas = (JsonArray) area.get("datas");
		JsonObject data = (JsonObject) datas.get(0);
		JsonElement nv = data.get("nv");
		return nv.getAsString();
	}
	public void updateValues(String json) {
		JsonParser jsonParser = new JsonParser();
		JsonObject parsing = (JsonObject) jsonParser.parse(json);
		JsonObject result = (JsonObject) parsing.get("result");
		JsonArray areas = (JsonArray) result.get("areas");
		JsonObject area = (JsonObject) areas.get(0);
		JsonArray datas = (JsonArray) area.get("datas");
		JsonObject data = (JsonObject) datas.get(0);
		JsonElement nv = data.get("nv");
		JsonElement sv = data.get("sv");
		this.currentValue = nv.getAsString();
		this.startValue = sv.getAsString();	
	}
	public static void main(String[] args) throws IOException {
		Stock test = new Stock();
		String result = test.getJsonFromUrl();
		System.out.println(result);
//		System.out.println(getNewValue(result));
	}
	
}