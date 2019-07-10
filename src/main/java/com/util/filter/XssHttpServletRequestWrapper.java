package com.util.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	boolean isUpData = false;//判断是否是上传 上传忽略
	private final byte[] body;
	private HttpServletRequest servletRequestBuffer = null;
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
        String contentType = servletRequest.getContentType ();
        if (null != contentType) {
            isUpData =contentType.startsWith ("multipart");
        }
       
        servletRequestBuffer = servletRequest;
        if(!isUpData){
        	body = inputHandlers().getBytes();
        }else{
        	body = null;
        }
        
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values==null)  {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }
    
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    /**
     * 获取request的属性时，做xss过滤
     */
    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (null != value && value instanceof String) {
            value = cleanXSS((String) value);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }
    @Override
    public ServletInputStream getInputStream () throws IOException {
        if (isUpData){
            return super.getInputStream();
        }else{
        	final ByteArrayInputStream bais = new ByteArrayInputStream(body);  
            return new ServletInputStream() {  
            	
                @Override  
                public int read() throws IOException {  
                    return bais.read();  
                }

				@Override
				public boolean isFinished() {
					return false;
				}

				@Override
				public boolean isReady() {
					return false;
				}

				@Override
				public void setReadListener(ReadListener readListener) {}  
            };  
        }
    }
    
    /**
     * 用于获取post请求  payload格式请求数据
     * @return
     */
    public String inputHandlers(){
    	HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(servletRequestBuffer);
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = null;
	    InputStreamReader inputStreamReader=null;
	    ServletInputStream servletInputStream =null;
		try {
			servletInputStream =  httpServletRequestWrapper.getInputStream();
			inputStreamReader=new InputStreamReader (servletInputStream, Charset.forName("UTF-8"));
            reader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
		} catch (IOException e) {
			return "";
		}finally {
			try {
				if(servletInputStream!=null){
					servletInputStream.close();
				}
				if(inputStreamReader!=null){
					inputStreamReader.close();
				}
				if(reader!=null){
					reader.close();
				}
			} catch (IOException e) {
				
			}
		}
        return cleanXSS(sb.toString ());
    }

    private static String cleanXSS(String values) {
    	String value=values;
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll("=", "");
        return value;
    }
	/**
	 * 过滤特殊字符
	 */
	public  String filter(String value) {
		if (value == null) {
			return null;
		}
		StringBuffer result = new StringBuffer(value.length());
		for (int i = 0; i < value.length(); ++i) {
			switch (value.charAt(i)) {
			case '<':
				result.append("<");
				break;
			case '>':
				result.append(">");
				break;
			case '"':
				result.append("\"");
				break;
			case '\'':
				result.append("'");
				break;
			case '%':
				result.append("%");
				break;
			case ';':
				result.append(";");
				break;
			case '(':
				result.append("(");
				break;
			case ')':
				result.append(")");
				break;
			case '&':
				result.append("&");
				break;
			case '+':
				result.append("+");
				break;
			default:
				result.append(value.charAt(i));
				break;
			}
		}
		return result.toString();
	}

	
}
