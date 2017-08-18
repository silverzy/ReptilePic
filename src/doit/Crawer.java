package doit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;  
  
/** 
 * @author Administrator 
 * 爬取百度图片，百度图片加载方式：异步加载，瀑布流式 
 */  
public class Crawer  
{  
    private static int page=-1;   
    private static final String ENCODE="UTF-8";//编码  
    private static Lock lock=new ReentrantLock();  
    /** 
     * 将中文转换成URL编码 
     * @param keyword     
     */  
    public static String  encode(String keyword) throws UnsupportedEncodingException   
    {  
        return new String(URLEncoder.encode(keyword,ENCODE).getBytes());  
    }  
      
    public static void main(String[] args) throws UnsupportedEncodingException   
    {  
        String name="赵丽颖";  
        String username = TransPinYin.getpinyin(name);
        final String downloadPath="D:\\Facesku\\"+username+"\\";  
        
        final String keyword=encode(name);  
       for(int i=0;i<50;i++)  
        {  
            new Thread("Thread--"+i){  
                public void run()  
                {  
                    downloadPic(keyword,downloadPath);  
                }  
                  
            }.start();  
        }         
       
    }  
    public static void downloadPic(String keyword,String downloadPath)  
    {      
        File path=new File(downloadPath);  
        if(!path.exists())  
            path.mkdirs();  
        while(!Thread.interrupted())  
        {  
           lock.lock();  
           try{  
               page++;  
           }finally{  
               lock.unlock();  
           }             
           String url="http://image.baidu.com/search/avatarjson?tn=resultjsonavatarnew&ie=utf-8&word="+keyword+"&cg=star&pn="+page*30+"&rn=30&itg=0&z=0&fr=&width=&height=&lm=-1&ic=0&s=0&st=-1&gsm="+Integer.toHexString(page*30);  
           String html=null;  
           try {  
            html=getHtml(url);  
            List<String> listUrl=getImgeUrl(html);  
                        
            download(listUrl,downloadPath);  
           
        } catch (Exception e) {           
            e.printStackTrace();  
        }     
      }  
    }  
    /** 
     * 获取url页面的内容 
     * @param url 
     * @return 
     * @throws Exception 
     */  
    public static String getHtml(String url) throws Exception  
    {  
        URL uri=new URL(url);  
        StringBuffer sb=new StringBuffer();  
        URLConnection connection=uri.openConnection();  
        InputStream in=connection.getInputStream();  
        byte[] buf=new byte[1024];    
        int length=0;  
        while((length=in.read(buf,0,buf.length))>0)  
            sb.append(new String(buf,ENCODE));  
        in.close();       
        return sb.toString();     
    }  
    /** 
     * 获取图片URL 
     * @param html 
     * @return  
     */  
    public static List<String> getImgeUrl(String html)  
    {  
    	//int i=0;
        List<String> listUrl=new ArrayList<String>();  
        String reg = "objURL\":\"http://.+?\\.(png|PNG|jpg|JPG|JPEG|jpeg)";
        
        //String reg = "objURL\":\"http://.+?\\.jpg";  
        Matcher matcher=Pattern.compile(reg).matcher(html);  
      
        while(matcher.find())  
        {      	
        	 
        	//String[] data = matcher.group().split("\\s+");
        	 
                listUrl.add(matcher.group().substring(9));  
        	 
        }
            return listUrl;  
    }  
    
    
    public static void download(List<String> listUrl,String downloadPath)  
    {         
        for(String  url:listUrl)  
        {  
        	
            String imageName=url.substring(url.lastIndexOf("/")+1,url.length());  
            
           
            try {  
                URL uri=new URL(url);  
                //URLConnection connection=uri.openConnection();  
                //HttpURLConnection httpCon=(HttpURLConnection)connection;  
                //InputStream in=httpCon.getInputStream();  
                InputStream in=uri.openStream();                   
                String file=downloadPath+imageName;  
                FileOutputStream fo=new FileOutputStream(new File(file));  
                byte[] buf=new byte[1024];  
                int length=0;  
                while((length=in.read(buf,0,buf.length))!=-1)  
                    fo.write(buf, 0, length);  
               System.out.println(Thread.currentThread().getName()+url+"下载完成！");  
                in.close();  
                fo.close();  
            } catch (FileNotFoundException e1) {                  
                System.out.println("无法下载图片！");  
            } catch(IOException e2) {  
                System.out.println("发生IO异常！");  
            }  
        }  
    }  
}  