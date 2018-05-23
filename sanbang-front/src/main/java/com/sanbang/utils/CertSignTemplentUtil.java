package com.sanbang.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CertSignTemplentUtil {

	private static Logger log = Logger.getLogger(CertSignTemplentUtil.class.getName());

	private Configuration configuration = null;

	String producePdfPath = "";

	public CertSignTemplentUtil() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}

	/**
	 * 生成pdf
	 * 
	 * @param params
	 * @param tempUrl
	 * @param htmlUrl
	 * @param pdFUrl
	 * @return
	 */
	public String processPdf(Map<String, Object> params, String templPath, String ftlName, String htmlPath,
			String pdfPath, String fontPath) {
		try {
			configuration.setDirectoryForTemplateLoading(new File(templPath));
			Template temp = configuration.getTemplate(ftlName);
			File htmlFile = new File(htmlPath);
			if (!htmlFile.exists()) {
				htmlFile.createNewFile();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(htmlPath)), "utf-8"));
			temp.process(params, out);
			out.flush();
			String url = htmlFile.toURI().toURL().toString();
			OutputStream os = new FileOutputStream(pdfPath);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(url);
			// 解决中文问题
			ITextFontResolver fontResolver = renderer.getFontResolver();
			fontResolver.addFont(fontPath + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			renderer.layout();
			renderer.createPDF(os);
			os.close();
		} catch (Exception e) {
			log.info(e.toString());
			return "";
		}
		return pdfPath;
	}

	
	public static void main(String[] args) {
		CertSignTemplentUtil cert=new CertSignTemplentUtil();
		Map<String, Object> map=new HashMap<>();
		map.put("customerName", "龙飞公司");
		map.put("orderAmount", "aaa");
		map.put("AcapAmount", "bb");
		cert.processPdf(map, "E:/", "jybtz.ftl", "D:/caigou.html", "D:/cc.pdf", "D:/fonts/");
//		cert.processPdf(map, "E:/templent/", "xiaoshou.ftl", "D:/bb.html", "D:/bb.pdf", "D:/fonts/");
	}
}
