package com.xwer.spider.main;

import java.util.regex.Pattern;

import org.junit.Test;



import us.codecraft.webmagic.Spider;

public class MM_test {
	public static void main(String[] args) {
		//图片的存放路径,PiPline需要用到
		String fileStorePath = "D:\\webmagic\\test";
		//过滤网页的正则  http://www.meizitu.com/a/more_1.html
		String urlPattern = "http://www.meizitu.com/[a-z]/[0-9]{1,4}.html";
		//自定义的解析器
		MM_Processor mmSprider = new MM_Processor("http://www.meizitu.com/", urlPattern);

		//启动爬虫
		Spider.create(mmSprider).addUrl("http://www.meizitu.com/").addPipeline(new MM_Pipeline(fileStorePath))
				.thread(3).run();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void tess(){
		String info="<a href='http://www.meizitu.com/a/5536.html' target='_blank'><img src='http://mm.howkuai.com/wp-content/uploads/2017a/05/05/01.jpg' alt='真正的素颜美女，就是养眼！看完' width='64'height='64'>";
		String ss="http://mm.howkuai.com/wp-content/uploads/20[0-9]{2}[a-z]/[0-9]{2}/[0-9]{2}/[0-9]{1,4}.jpg";
		String imgRegex3 = "http://mm[^>].+\\.jpg";
		Pattern p = Pattern.compile(ss);
		java.util.regex.Matcher m = p.matcher(info);
		for(;m.find();){
			System.out.println(m.group());
		}
		System.out.println("-------");
	}

}
