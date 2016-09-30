package me.niccolomattei.api.telegram.utils;

import me.niccolomattei.api.telegram.Bot;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RequestUtility {

	private HttpURLConnection conn;
	private DataOutputStream out;
	private List<RequestParam> requestParams;

	public RequestUtility(String rurl) throws IOException {
		URL url = new URL(rurl);

		conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", Bot.USER_AGENT);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		out = new DataOutputStream(conn.getOutputStream());
		requestParams = new ArrayList<>();
	}

	public void addParameter(String prmName, Object value) throws IOException {
		requestParams.add(new RequestParam(prmName, URLEncoder.encode(String.valueOf(value), "UTF-8")));
	}

	public void addParameters(Map<String, Object> map) throws IOException {
		for (String keys : map.keySet()) {
			out.writeBytes(keys + "=" + map.get(keys).toString());
		}
	}

	public String close() throws IOException {
		out.write(getParamString().getBytes("UTF-8"));
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		String isA = "";
		if (is != null) {
			isA = Utils.getStringFromInputStream(is);
		}
		return isA;
	}
	
	private String getParamString() {
		Iterator<RequestParam> iterator = requestParams.iterator();
		StringBuilder builder = new StringBuilder();
		while(iterator.hasNext()) {
			RequestParam next = iterator.next();
			builder.append(next.getKey() + "=" + next.getValue());
			if(iterator.hasNext()) {
				builder.append("&");
			}
		}
		return builder.toString();
	}

}
