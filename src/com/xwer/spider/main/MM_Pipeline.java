package com.xwer.spider.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xwer.spider.utils.DownLoadUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MM_Pipeline implements Pipeline {
	private Logger logger = Logger.getLogger(this.getClass());
	private String path;

	public MM_Pipeline() {
		setPath("/MM/");
	}

	public MM_Pipeline(String path) {
		setPath(path);
	}

	public void setPath(String path) {
		this.path = path;
	}

	// 处理下载的方法
	@Override
	public void process(ResultItems resultItems, Task task) {
		logger.info("到了process" + resultItems);
		String fileStorePath = this.path;
		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {

			if (entry.getValue() instanceof List) {
				
				List<String> list = new ArrayList<String>((List) entry.getValue());			
				for (int i = 1; i < list.size(); i++) {
					// 获取文件唯一名字
					String realname = DownLoadUtils.subFileName(list.get(i));
					String uuidname = DownLoadUtils.generateRandonFileName(realname);

					// 这里通过自己写的下载工具前抓取到的图片网址，并放在对应的文件中
					try {
						DownLoadUtils.download(list.get(i), uuidname, fileStorePath);
						logger.info("文件" + uuidname +"已经下载完毕");
					} catch (Exception e) {
						logger.warn("文件下载异常" + list.get(i));
						e.printStackTrace();
					}
				}
			}
			else {
				System.out.println(entry.getKey() + ":\t" + entry.getValue());
			}
		}

	}
}
