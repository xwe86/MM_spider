package com.xwer.spider.main;

import java.util.List;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

public class MM_Processor implements PageProcessor {
	private Logger logger = Logger.getLogger(this.getClass());
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(5).setSleepTime(1000);
	

	// 网页匹配规则
	private String urlPattern;

	public MM_Processor(String startUrl, String urlPattern) {
		// 设置所属域
		this.site = Site.me().setDomain(UrlUtils.getDomain(startUrl));
		this.urlPattern = urlPattern;
	}

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		
		site.setUserAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
		
		String imgRegex1 = "http://www.meizitu.com/wp-content/uploads/20[0-9]{2}[a-z]/[0-9]{2,4}/[0-9]{2,4}/[0-9]{1,5}.jpg";
		//图片抓取规则
		String imgRegex2 = "http://mm.howkuai.com/wp-content/uploads/20[0-9]{2}[a-z]/[0-9]{2,4}/[0-9]{2,4}/[a-zA-Z_0-9]{1,25}.jpg";
		String imgRegex3 = "http://mm.howkuai.com/wp-content/uploads/20[0-9]{2}[a-z]/[0-9]{2}/[0-9]{2}/[0-9]{1,4}.jpg";

		// 获取目标链接 例如 http://www.meizitu.com/a/5535.html
		 List<String> requests = page.getHtml().links().regex(urlPattern).all();
		 logger.info("获取到的目标链接是: "+requests);
		 logger.info("添加链接( "+requests.size()+" )条到集合");
		page.addTargetRequests(requests);
		logger.info("队列中存储的链接数是: "+page.getResultItems().getAll().size());
		
		// 图片的title,标题名称,用于设定文件夹的名称 
		//这个没有实现
		 String imgHostFileName = page.getHtml().xpath("//div[@class='metaRight']/h2/text()").toString();
		logger.info("获取的标题是"+imgHostFileName);
		
		List<String> listProcess = page.getHtml().regex(imgRegex2).all();
		List<String> list2 = page.getHtml().regex(imgRegex3).all();
		listProcess.addAll(list2);
		logger.info("存入的图片地址: "+listProcess);

		// 此处将标题一并抓取，之后提取出来作为文件名
		listProcess.add(0, imgHostFileName);
		//将获取到的页面的数据放到resultItems集合中(map)
		page.putField("img", listProcess);
	
	}

	@Override
	public Site getSite() {

		return site;
	}
	
	

}
