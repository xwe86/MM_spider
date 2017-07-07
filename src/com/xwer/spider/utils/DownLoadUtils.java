package com.xwer.spider.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * 下载相关的工具类
 * @author Administrator
 *
 */
public class DownLoadUtils {
	
		/**
		 * 下载图片工具
		 * @param urlString	图片链接地址
		 * @param filename	图片的文件名字
		 * @param savePath	图片保存的路径
		 * @throws Exception
		 */
	    public static void download(String urlString, String filename,String savePath) throws Exception {
	        // 构造URL
	        URL url = new URL(urlString);
	        // 打开连接
	        URLConnection con = url.openConnection();
	        //设置请求头
	        con.addRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
	        //设置请求超时为5s
	        con.setConnectTimeout(5*1000);
	        // 输入流
	        InputStream is = con.getInputStream();

	        // 1K的数据缓冲
	        byte[] bs = new byte[1024];
	        // 读取到的数据长度
	        int len;
	        // 输出的文件流
	        File sf=new File(savePath);
	        if(!sf.exists()){
	            sf.mkdirs();
	        }
	        OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
	        // 开始读取
	        while ((len = is.read(bs)) != -1) {
	            os.write(bs, 0, len);
	        }
	        // 完毕，关闭所有链接
	        os.close();
	        is.close();
	    }
	    
     /**
	 * 截取真实文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String subFileName(String fileName) {
		// 查找最后一个 \出现位置
		int index = fileName.lastIndexOf("\\");
		if (index == -1) {
			return fileName;
		}
		return fileName.substring(index + 1);
	}

	/**
	 * 获得随机UUID文件名
	 * @param fileName
	 * @return
	 */
	public static String generateRandonFileName(String fileName) {
		// 获得扩展名
		String ext = fileName.substring(fileName.lastIndexOf("."));
		return UUID.randomUUID().toString().replace("-", "") + ext;
	}

	/**
	 *  获得hashcode生成二级目录
	 * @param uuidFileName
	 * @return
	 */
	public static String generateRandomDir(String uuidFileName) {
		int hashCode = uuidFileName.hashCode();
		// 一级目录
		int d1 = hashCode & 0xf;
		// 二级目录
		int d2 = (hashCode >> 4) & 0xf;
		return "/" + d1 + "/" + d2;
	}
	
}
