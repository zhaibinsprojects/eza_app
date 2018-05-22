package com.sanbang.upload.sevice.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.sanbang.bean.ezs_user;
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.userauth.AuthImageVo;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

	// 日志
	private static Logger log = Logger.getLogger(FileUploadServiceImpl.class);

	// 上下文地址
	@Value("${consparam.imgs.baseurl}")
	public String BASEURL;

	// 基础路径path
	@Value("${consparam.imgs.basepath}")
	public String BASEPATH;

	// 临时路径
	@Value("${consparam.imgs.tempsavepath}")
	public String TEMPSAVEPATH;

	// 真实路径标识
	@Value("${consparam.imgs.truepathflag}")
	public String TRUEPATHFLAG;

	// 临时路径标识
	@Value("${consparam.imgs.tempathflag}")
	public String TEMPATHFLAG;

	// 支持的图片格式
	@Value("${consparam.imgs.formats}")
	public String formats;

	// 支持的图片格式数组
	public String[] format;
	
	// rediskey有效期
	@Value("${consparam.redis.redisuserkeyexpir}")
	private String redisuserkeyexpir;

	@PostConstruct
	public void initMethod() {
		format = StringUtils.split(formats, ",");
	}

	private static final String IMAGEENDPATH = "-100.jpg";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                

	/**
	 * 上传图片到临时路径 width 和 height都为0代表 不检查图片长宽 width 和 height都不为0代表检查长宽 传多少 限制
	 * 图片宽和长就是多少 size是当前图片的大小 单位是字节 返回 json串 code为000代表操作成功
	 */
	public Map<String, Object> uploadFile(HttpServletRequest request, int width, int height, long size)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("上传图片文件");
		String path = null;
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			// 拼接目录
			StringBuilder ym = this.dateUtil(0).append(this.dateUtil(1)).append(File.separator);
			StringBuilder d = this.dateUtil(2).append(File.separator);
			StringBuilder hms = this.dateUtil(3).append("-").append(this.dateUtil(4)).append("-")
					.append(this.dateUtil(5)).append("-").append(this.dateUtil(6)).append("-").append(this.dateUtil(7));
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					boolean b = sizeInfo(file, size);
					if (b) {
						b = formatPic(file.getInputStream());
						if (b) {
							// 判断图片长宽
							if (width != 0 && height != 0) {
								if (filterImgPixel(file.getInputStream(), width, height)) {
									// 判断是否存在年月文件夹，没有就创建
									saveFileMethod(ym, d, hms, file, path, map);
								} else {
									map.put("message", "请上传小于" + width + "*" + height + "尺寸的图片");
									map.put("code", "888");
									map.put("pixel", "图片长宽不适合");
								}
							} else if (width == 0 && height == 0) {
								// 判断是否存在年月文件夹，没有就创建
								saveFileMethod(ym, d, hms, file, path, map);
							} else {
								map.put("code", "999");
								map.put("message", "操作异常");
							}
						} else {
							map.put("message", "图片格式错误,请不要改动图片后缀名字 或者 上传符合规定的图片文件");
							map.put("code", "888");
							map.put("picformat", "图片格式错误,请不要改动图片后缀名字 或者 上传符合规定的图片文件");
						}
					} else {
						map.put("message", "图片过大，请上传不超过：" + size / 1024 + "kb的图片");
						map.put("code", "888");
						map.put("picsize", "图片过大，请上传不超过：" + size / 1024 + "kb的图片");
					}
				}
			}
		} else {
			map.put("message", "没有上传图片");
			map.put("code", "888");
			map.put("pic", "没有上传图片");
		}
		log.info("上传图片文件结果:code=" + map.get("code") + " &message=" + map.get("message"));
		return map;
	}

	/**
	 * 上传图片到临时路径 width 和 height都为0代表 不检查图片长宽 width 和 height都不为0代表检查长宽 传多少 限制
	 * 图片宽和长就是多少 size是当前图片的大小 单位是字节 返回 json串 code为000代表操作成功
	 */
	public Map<String, Object> uploadFileformall(HttpServletRequest request, int width, int height, long size)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("上传图片文件");
		String path = null;
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			// 拼接目录
			StringBuilder ym = this.dateUtil(0).append(this.dateUtil(1)).append(File.separator);
			StringBuilder d = this.dateUtil(2).append(File.separator);
			StringBuilder hms = this.dateUtil(3).append("-").append(this.dateUtil(4)).append("-")
					.append(this.dateUtil(5)).append("-").append(this.dateUtil(6)).append("-").append(this.dateUtil(7));
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					boolean b = sizeInfo(file, size);
					if (b) {
						b = formatPic(file.getInputStream());
						if (b) {
							// 判断图片长宽
							if (width != 0 && height != 0) {
								if (filterImgLess(file.getInputStream(), width, height)) {
									// 判断是否存在年月文件夹，没有就创建
									saveFileMethodformall(ym, d, hms, file, path, map);
								} else {
									map.put("message", "请上传大于" + width + "*" + height + "尺寸的图片");
									map.put("code", "888");
									map.put("pixel", "图片长宽不适合");
								}
							} else if (width == 0 && height == 0) {
								// 判断是否存在年月文件夹，没有就创建
								saveFileMethodformall(ym, d, hms, file, path, map);
							} else {
								map.put("code", "999");
								map.put("message", "操作异常");
							}
						} else {
							map.put("message", "图片格式错误,请不要改动图片后缀名字 或者 上传符合规定的图片文件");
							map.put("code", "888");
							map.put("picformat", "图片格式错误,请不要改动图片后缀名字 或者 上传符合规定的图片文件");
						}
					} else {
						map.put("message", "图片过大，请上传不超过：" + size / 1024 + "kb的图片");
						map.put("code", "888");
						map.put("picsize", "图片过大，请上传不超过：" + size / 1024 + "kb的图片");
					}
				}
			}
		} else {
			map.put("message", "没有上传图片");
			map.put("code", "888");
			map.put("pic", "没有上传图片");
		}
		log.info("上传图片文件结果:code=" + map.get("code") + " &message=" + map.get("message"));
		return map;
	}

	/**
	 * 上传图片到临时路径 width 和 height都为0代表 不检查图片长宽 width 和 height都不为0代表检查长宽 传多少 限制
	 * 图片宽和长就是多少 size是当前图片的大小 单位是字节 返回 json串 code为000代表操作成功
	 */
	public Map<String, Object> uploadFileforshop(HttpServletRequest request, int width, int height, long size,
			String type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("上传图片文件");
		String path = null;
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			// 拼接目录
			StringBuilder ym = this.dateUtil(0).append(this.dateUtil(1)).append(File.separator);
			StringBuilder d = this.dateUtil(2).append(File.separator);
			StringBuilder hms = this.dateUtil(3).append("-").append(this.dateUtil(4)).append("-")
					.append(this.dateUtil(5)).append("-").append(this.dateUtil(6)).append("-").append(this.dateUtil(7));
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					boolean b = sizeInfo(file, size);
					if (b) {
						b = formatPic(file.getInputStream());
						if (b) {
							// 判断图片长宽
							if (width != 0 && height != 0) {
								if (filterImgLessforshop(file.getInputStream(), width, height, type)) {
									// 判断是否存在年月文件夹，没有就创建
									saveFileMethodformall(ym, d, hms, file, path, map);
								} else {
									map.put("message", "请上传合适尺寸的图片");
									map.put("code", "888");
									map.put("pixel", "图片长宽不适合");
								}
							} else if (width == 0 && height == 0) {
								// 判断是否存在年月文件夹，没有就创建
								saveFileMethodformall(ym, d, hms, file, path, map);
							} else {
								map.put("code", "999");
								map.put("message", "操作异常");
							}
						} else {
							map.put("message", "图片格式错误,请不要改动图片后缀名字 或者 上传符合规定的图片文件");
							map.put("code", "888");
							map.put("picformat", "图片格式错误,请不要改动图片后缀名字 或者 上传符合规定的图片文件");
						}
					} else {
						map.put("message", "图片过大，请上传不超过：" + size / 1024 + "kb的图片");
						map.put("code", "888");
						map.put("picsize", "图片过大，请上传不超过：" + size / 1024 + "kb的图片");
					}
				}
			}
		} else {
			map.put("message", "没有上传图片");
			map.put("code", "888");
			map.put("pic", "没有上传图片");
		}
		log.info("上传图片文件结果:code=" + map.get("code") + " &message=" + map.get("message"));
		return map;
	}

	public boolean sizeInfo(MultipartFile file, long size) {
		long fileSize = file.getSize();
		if (fileSize <= size) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 上传图片到临时路径 width 和 height都为0代表 不检查图片长宽 width 和 height都不为0代表检查长宽 传多少 限制
	 * 图片宽和长就是多少 size是当前图片的大小 单位是字节 返回 json串 code为000代表操作成功
	 */
	public Map<String, Object> uploadFilenomark(HttpServletRequest request, int width, int height, long size,
			String type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("上传图片文件");
		String path = null;
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			// 拼接目录
			StringBuilder ym = this.dateUtil(0).append(this.dateUtil(1)).append(File.separator);
			StringBuilder d = this.dateUtil(2).append(File.separator);
			StringBuilder hms = this.dateUtil(3).append("-").append(this.dateUtil(4)).append("-")
					.append(this.dateUtil(5)).append("-").append(this.dateUtil(6)).append("-").append(this.dateUtil(7));
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					boolean b = sizeInfo(file, size);
					if (b) {
						b = formatPic(file.getInputStream());
						if (b) {
							// 判断图片长宽
							if (width != 0 && height != 0) {
								if (filterImgLessforshop(file.getInputStream(), width, height, type)) {
									// 判断是否存在年月文件夹，没有就创建
									saveFileMethod(ym, d, hms, file, path, map);
								} else {
									map.put("message", "请上传合适尺寸的图片");
									map.put("code", "888");
									map.put("pixel", "图片长宽不适合");
								}
							} else if (width == 0 && height == 0) {
								// 判断是否存在年月文件夹，没有就创建
								saveFileMethod(ym, d, hms, file, path, map);
							} else {
								map.put("code", "999");
								map.put("message", "操作异常");
							}
						} else {
							map.put("message", "图片格式错误,请不要改动图片后缀名字 或者 上传符合规定的图片文件");
							map.put("code", "888");
							map.put("picformat", "图片格式错误,请不要改动图片后缀名字 或者 上传符合规定的图片文件");
						}
					} else {
						map.put("message", "图片过大，请上传不超过：" + size / 1024 + "kb的图片");
						map.put("code", "888");
						map.put("picsize", "图片过大，请上传不超过：" + size / 1024 + "kb的图片");
					}
				}
			}
		} else {
			map.put("message", "没有上传图片");
			map.put("code", "888");
			map.put("pic", "没有上传图片");
		}
		log.info("上传图片文件结果:code=" + map.get("code") + " &message=" + map.get("message"));
		return map;
	}

	/**
	 * 封装相同方法
	 * 
	 * @param ym
	 * @param d
	 * @param hms
	 * @param file
	 * @param path
	 * @param map
	 * @throws Exception
	 */
	public void saveFileMethod(StringBuilder ym, StringBuilder d, StringBuilder hms, MultipartFile file, String path,
			Map<String, Object> map) throws Exception {
		isExist(TEMPSAVEPATH + ym);
		isExist(TEMPSAVEPATH + ym + d);
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		path = TEMPSAVEPATH + ym + d + hms + suffix;
		// 上传
		// file.getInputStream()
		file.transferTo(new File(path));
		String url = this.getUrl(path, BASEURL, TEMPATHFLAG);

		map.put("code", "000");
		map.put("message", "操作成功");
		map.put("url", url);
	}

	/**
	 * 封装相同方法
	 * 
	 * @param ym
	 * @param d
	 * @param hms
	 * @param file
	 * @param path
	 * @param map
	 * @throws Exception
	 */
	public void saveFileMethodformall(StringBuilder ym, StringBuilder d, StringBuilder hms, MultipartFile file,
			String path, Map<String, Object> map) throws Exception {
		isExist(TEMPSAVEPATH + ym);
		isExist(TEMPSAVEPATH + ym + d);
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		path = TEMPSAVEPATH + ym + d + hms + suffix;
		// 上传

		Tools.mark(file, path, suffix.substring(1, suffix.length()), Color.white, "易再生网 www.ezaisheng.com");
		// file.transferTo(new File(path));
		String url = this.getUrl(path, BASEURL, TEMPATHFLAG);

		map.put("code", "000");
		map.put("message", "操作成功");
		map.put("url", url);
	}

	
	public boolean filterImgPixel(InputStream file, int standWidth, int standHeight) throws IOException {
		BufferedImage bi = null;
		int width = 0;
		int height = 0;
		bi = ImageIO.read(file);
		width = bi.getWidth();
		height = bi.getHeight();
		// System.out.println("尺寸："+width+" "+height);
		if (width <= standWidth && height <= standHeight) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断图片宽高是否小于定义宽度
	 * 
	 * @param file
	 * @param standWidth
	 * @param standHeight
	 * @return
	 * @throws IOException
	 */
	public boolean filterImgLess(InputStream file, int standWidth, int standHeight) throws IOException {
		BufferedImage bi = null;
		int width = 0;
		int height = 0;
		bi = ImageIO.read(file);
		width = bi.getWidth();
		height = bi.getHeight();
		// System.out.println("尺寸："+width+" "+height);
		if (width >= standWidth && height >= standHeight) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断图片宽高是否小于定义宽度
	 * 
	 * @param file
	 * @param standWidth
	 * @param standHeight
	 * @param size
	 *            1 等于 2 大于等于 3 小于等于 为空 默认为小于等于
	 * @return
	 * @throws IOException
	 */
	public boolean filterImgLessforshop(InputStream file, int standWidth, int standHeight, String type)
			throws IOException {
		BufferedImage bi = null;
		int width = 0;
		int height = 0;
		bi = ImageIO.read(file);
		width = bi.getWidth();
		height = bi.getHeight();
		// System.out.println("尺寸："+width+" "+height);
		if (Tools.isEmpty(type) || "3".equals(type)) {
			if (width <= standWidth && height <= standHeight) {
				return true;
			} else {
				return false;
			}
		} else if ("2".equals(type)) {
			if (width >= standWidth && height >= standHeight) {
				return true;
			} else {
				return false;
			}
		} else if ("1".equals(type)) {
			if (width == standWidth && height == standHeight) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

	public boolean formatPic(InputStream is) throws Exception {
		ImageInputStream iis = ImageIO.createImageInputStream(is);
		Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
		int flag = 0;
		if (!iter.hasNext()) {
			return false;
		}
		ImageReader reader = iter.next();

		System.out.println("Format: " + reader.getFormatName());
		for (int i = 0; i < format.length; i++) {
			if (reader.getFormatName().equalsIgnoreCase(format[i])) {
				flag = 1;
				break;
			}
		}

		// close stream
		iis.close();
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}

	public StringBuilder dateUtil(int type) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		int param = 0;
		StringBuilder sbd = new StringBuilder();
		switch (type) {
		case 0:// 年
			param = c.get(Calendar.YEAR);
			break;
		case 1:// 月
			param = c.get(Calendar.MONTH) + 1;
			break;
		case 2:// 日
			param = c.get(Calendar.DAY_OF_MONTH);
			break;
		case 3:// 时
			param = c.get(Calendar.HOUR_OF_DAY);
			break;
		case 4:// 分
			param = c.get(Calendar.MINUTE);
			break;
		case 5:// 秒
			param = c.get(Calendar.SECOND);
			break;
		case 6:// 2位随机整数
			Random rand = new Random();
			param = rand.nextInt(91) + 9;
			if (param == 9) {
				param = 10;
			}
			break;
		case 7:// 5位随机整数
			Random rand2 = new Random();
			param = (int) (rand2.nextDouble() * (100000 - 10000) + 10000);
			break;
		}
		if (param < 10) {
			sbd.append("0").append(param);
		} else {
			sbd.append(param);
		}
		return sbd;
	}

	/**
	 * 不存在就创建该文件夹
	 * 
	 * @param path
	 */
	public void isExist(String path) {
		File file = new File(path);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
		file = null;
	}

	/**
	 * 将物理路径转换成url
	 * 
	 * @param path
	 * @param baseurl
	 *            基础地址
	 * @param flag
	 * @return
	 */
	public String getUrl(String path, String baseurl, String flag) throws Exception {
		return baseurl + path.substring(path.indexOf(flag));
	}

	/**
	 * 将url转换成物理路径
	 * 
	 * @param basepath
	 *            基础路径
	 * @param url
	 *            图片url
	 * @param flag
	 *            转换路径标识字符串 例如
	 *            localhost:8080/website/userImgs/tempath/2061/sdkfjk.jpg 到
	 *            e:/pics/tempath/2061/sdkfjk.jpg 的转换 标识符是 tempath 返回生成的路径字符串
	 * @return
	 */
	public String getPath(String basepath, String url, String flag) throws Exception {
		// BASEPATH
		return basepath + url.substring(url.indexOf(flag));
	}

	public void download(String fileName, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + "aa.jpg");
		try {
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "download";// 这个download目录为啥建立在classes下的
			// InputStream inputStream = new FileInputStream(new File(path
			// + File.separator + fileName));
			InputStream inputStream = new FileInputStream(new File("d:/aa.png"));

			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			// 这里主要关闭。
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
	}

	public static void main(String[] args) throws Exception {
		FileUploadServiceImpl fsi = new FileUploadServiceImpl();
		System.out.println(fsi.getUrl("e:/adsf/tempath/2061/sdkfjk.jpg", fsi.BASEURL, fsi.TEMPATHFLAG));
		System.out.println(
				fsi.getPath(fsi.BASEPATH, "localhost:8080/website/userImgs/tempath/2061/sdkfjk.jpg", fsi.TEMPATHFLAG));
	}

	@Override
	public Result tempsaveimg(String type, String picurl, HttpServletRequest request) {
		Result result = Result.success();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}

		ezs_user authupi = RedisUserSession.getAuthUserInfo(request);
		
		AuthImageVo authimg = new AuthImageVo();
		authimg.setImgcode(type);
		authimg.setImgurl(picurl);

		List<AuthImageVo> list = authupi.getAuthimg();
		
		if(list==null){
			list=new ArrayList<>();
		}
		// 执照法人资质处理
		if (!type.equals("OTHER_QUALIFICATIONS")) {
			if (null != list && list.size() > 0) {
				int index = -1;
				for (AuthImageVo authImageVo : list) {
					if (type.equals(authImageVo.getImgcode())) {
						authImageVo.setImgurl(picurl);
						index = 1;
					}
				}
				if (index == -1) {
					list.add(authimg);
				}
				;
			} else {
				list.add(authimg);
			}
		} else {
			// 其他资质处理
			if (type.equals("OTHER_QUALIFICATIONS")) {
				String name=request.getParameter("name");
				String usetime=request.getParameter("usetime");
				
				if(Tools.isEmpty(name)){
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setMsg("资质名称不能为空");
					return result;
				}
				if(Tools.isEmpty(usetime)){
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setMsg("有效日期不能为空");
					return result;
				}
				authimg.setName(name);
				try {
					authimg.setUsetime(sdf.parse(usetime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				list.add(authimg);
			}
		}
		
		
		
		authupi.setAuthimg(list);
		
		//认证授权图片处理
		authPicCash(list, authupi);
		//认证授权图片处理
		
		boolean res = RedisUserSession.updateUserInfo(authupi.getAuthkey(), authupi,
				Long.parseLong(redisuserkeyexpir));

		if (res) {
			result.setSuccess(true);
			result.setMsg("保存成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setObj(new HashMap<>().put("imgurl", authimg.getImgurl()));
		} else {
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}
	
	
	/**
	 * 认证授权图片处理
	 * @param list
	 */
	private void authPicCash(List<AuthImageVo> list,ezs_user authupi){
		
		//企业授权
		if(!authupi.isAuthorfilestate()){
			int i=0;
			for (AuthImageVo authImageVo : list) {
				if(authImageVo.getImgcode().equals("LETTER_OF_AUTHORIZATION")
						||authImageVo.getImgcode().equals("LICENSEE_IDCARD")){
				i++;	
				}
			}
			if(i==2){
				authupi.setAuthorfilestate(true);
			}
		}
		
		//1.企业账号，2.个体户
		int account=authupi.getEzs_store().getAccountType();
		if(1==account){
			authupi.setAuthimgstate(false);
			//企业授权
			if(!authupi.isAuthimgstate()){
				int i=0;
				for (AuthImageVo authImageVo : list) {
					if(authImageVo.getImgcode().equals("BUSLIC")
							||authImageVo.getImgcode().equals("ACCOUNT_OPENING_LICENSE")
							||authImageVo.getImgcode().equals("IDCARD_FONT")
							||authImageVo.getImgcode().equals("IDCARD_BACK")){
					i++;	
					}
				}
				if(i==4){
					authupi.setAuthimgstate(true);
				}
			}
		}else{
			authupi.setAuthimgstate(false);
			//企业授权
			if(!authupi.isAuthimgstate()){
				int i=0;
				for (AuthImageVo authImageVo : list) {
					if(authImageVo.getImgcode().equals("LETTER_OF_AUTHORIZATION")
							||authImageVo.getImgcode().equals("LICENSEE_IDCARD")){
					i++;	
					}
				}
				if(i==4){
					authupi.setAuthimgstate(true);
				}
			}
		}
		
		
	}

}
