package com.sanbang.vo.goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_goods_cartography;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_goods_photo;
import com.sanbang.bean.ezs_quality;
import com.sanbang.bean.ezs_user;

/**
 * 商品
 * 
 * @author 刘恒福
 *
 */
public class GoodsVo {

	private Long id;

	private Date lastModifyDate;// 商品更新日期，即最近一次修改商品信息的日期

	private String good_no;// 商品编号

	private String name;// 商品名称

	private BigDecimal price;// 商品单价
	
	private BigDecimal saleprice;// 商品单价

	private int validity;// 商品有效期

	private double inventory;// 商品库存量

	private ezs_area area;// 商品库存区县

	private String addess;// 库存所在地

	private String seo_description;// 商品SEO描述

	private String keyword;// 商品搜索关键字

	private ezs_dict util;// 单位

	private boolean recommend;// 是否推荐

	private Date recommend_time;// 推荐日期

	private int click;// 商品点击量

	private int collect;// 商品收藏量

	/**
	 * 1.下架 <br/>
	 * 2.正常上架<br/>
	 * 3.定制中的商品<br/>
	 * <br/>
	 * add By YanL
	 */
	private int status;// 商品状态1.下架 2.正常上架

	private ezs_dict logistics;// 物流方式

	private ezs_dict supply;// 供应情况

	private ezs_dict color;// 颜色

	private ezs_area region;// 地区

	private ezs_dict form;// 形态

	private ezs_dict classOfFinish;// 加工级别

	private String source;// 来源

	private String purpose;// 商品用途

	private String density;// 商品密度

	private String cantilever;// 悬臂梁缺口冲击

	private String lipolysis;// 溶脂

	private String ash;// 灰分

	private String freely;// j简支梁渠口冲击

	private String water;// 水分

	private String tensile;// 拉伸强度

	private String crack;// 断裂伸长率

	private String bending;// 弯曲强度

	private String flexural;// 弯曲模量

	private String burning;// 燃烧等级

	private boolean protection;// 是否环保

	private String content;// 描述

	private double cncl_num;// 样品库存

	private ezs_user user;// 发布人

	private ezs_goods_class goodClass;// 商品分类

	private List<ezs_quality> quality;// 质检

	private boolean memberLook;// 是否会员查看

	private int goods_salenum;// 销量数

	private boolean good_self;// 是否自营商品

	private boolean lockStatus;// 锁定：true b'1'，解锁:false b'0000'标识

	private Long goods_main_photo_id;

	// 商品图片
	private List<ezs_accessory> mainphoto;

	// 商品图片
	private List<ezs_goods_photo> goods_photos;

	// 制作过程
	private List<ezs_goods_cartography> cartographys;
	private Date addTime;

	private Boolean deleteStatus;

	private Long area_id;

	private Long classOfFinish_id;

	private Long color_id;

	private Long form_id;

	private Long goodClass_id;

	private Long logistics_id;

	private Long quality_id;

	private Long region_id;

	private Long supply_id;

	private Long user_id;

	private Long util_id;

	private String addess2;
	
	private long allcount;//总评价
	
	private int highp;//好评率
	
	private int collected;
	
	private String path;
	
    private String areaName;
    
    private ezs_goods_class gclass;

	public Long getId() {
		return id;
	}

	public ezs_goods_class getGclass() {
		return gclass;
	}

	public void setGclass(ezs_goods_class gclass) {
		this.gclass = gclass;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getGood_no() {
		return good_no;
	}

	public void setGood_no(String good_no) {
		this.good_no = good_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	public BigDecimal getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public double getInventory() {
		return inventory;
	}

	public void setInventory(double inventory) {
		this.inventory = inventory;
	}

	public ezs_area getArea() {
		return area;
	}

	public void setArea(ezs_area area) {
		this.area = area;
	}

	public String getAddess() {
		return addess;
	}

	public void setAddess(String addess) {
		this.addess = addess;
	}

	public String getSeo_description() {
		return seo_description;
	}

	public void setSeo_description(String seo_description) {
		this.seo_description = seo_description;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ezs_dict getUtil() {
		return util;
	}

	public void setUtil(ezs_dict util) {
		this.util = util;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public Date getRecommend_time() {
		return recommend_time;
	}

	public void setRecommend_time(Date recommend_time) {
		this.recommend_time = recommend_time;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public int getCollect() {
		return collect;
	}

	public void setCollect(int collect) {
		this.collect = collect;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ezs_dict getLogistics() {
		return logistics;
	}

	public void setLogistics(ezs_dict logistics) {
		this.logistics = logistics;
	}

	public ezs_dict getSupply() {
		return supply;
	}

	public void setSupply(ezs_dict supply) {
		this.supply = supply;
	}

	public ezs_dict getColor() {
		return color;
	}

	public void setColor(ezs_dict color) {
		this.color = color;
	}

	public ezs_area getRegion() {
		return region;
	}

	public void setRegion(ezs_area region) {
		this.region = region;
	}

	public ezs_dict getForm() {
		return form;
	}

	public void setForm(ezs_dict form) {
		this.form = form;
	}

	public ezs_dict getClassOfFinish() {
		return classOfFinish;
	}

	public void setClassOfFinish(ezs_dict classOfFinish) {
		this.classOfFinish = classOfFinish;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public String getCantilever() {
		return cantilever;
	}

	public void setCantilever(String cantilever) {
		this.cantilever = cantilever;
	}

	public String getLipolysis() {
		return lipolysis;
	}

	public void setLipolysis(String lipolysis) {
		this.lipolysis = lipolysis;
	}

	public String getAsh() {
		return ash;
	}

	public void setAsh(String ash) {
		this.ash = ash;
	}

	public String getFreely() {
		return freely;
	}

	public void setFreely(String freely) {
		this.freely = freely;
	}

	public String getWater() {
		return water;
	}

	public void setWater(String water) {
		this.water = water;
	}

	public String getTensile() {
		return tensile;
	}

	public void setTensile(String tensile) {
		this.tensile = tensile;
	}

	public String getCrack() {
		return crack;
	}

	public void setCrack(String crack) {
		this.crack = crack;
	}

	public String getBending() {
		return bending;
	}

	public void setBending(String bending) {
		this.bending = bending;
	}

	public String getFlexural() {
		return flexural;
	}

	public void setFlexural(String flexural) {
		this.flexural = flexural;
	}

	public String getBurning() {
		return burning;
	}

	public void setBurning(String burning) {
		this.burning = burning;
	}

	public boolean isProtection() {
		return protection;
	}

	public void setProtection(boolean protection) {
		this.protection = protection;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getCncl_num() {
		return cncl_num;
	}

	public void setCncl_num(double cncl_num) {
		this.cncl_num = cncl_num;
	}

	public ezs_user getUser() {
		return user;
	}

	public void setUser(ezs_user user) {
		this.user = user;
	}

	public ezs_goods_class getGoodClass() {
		return goodClass;
	}

	public void setGoodClass(ezs_goods_class goodClass) {
		this.goodClass = goodClass;
	}

	public List<ezs_quality> getQuality() {
		return quality;
	}

	public void setQuality(List<ezs_quality> quality) {
		this.quality = quality;
	}

	public boolean isMemberLook() {
		return memberLook;
	}

	public void setMemberLook(boolean memberLook) {
		this.memberLook = memberLook;
	}

	public int getGoods_salenum() {
		return goods_salenum;
	}

	public void setGoods_salenum(int goods_salenum) {
		this.goods_salenum = goods_salenum;
	}

	public boolean isGood_self() {
		return good_self;
	}

	public void setGood_self(boolean good_self) {
		this.good_self = good_self;
	}

	public boolean isLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(boolean lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Long getGoods_main_photo_id() {
		return goods_main_photo_id;
	}

	public void setGoods_main_photo_id(Long goods_main_photo_id) {
		this.goods_main_photo_id = goods_main_photo_id;
	}

	public List<ezs_accessory> getMainphoto() {
		return mainphoto;
	}

	public void setMainphoto(List<ezs_accessory> mainphoto) {
		this.mainphoto = mainphoto;
	}

	public List<ezs_goods_photo> getGoods_photos() {
		return goods_photos;
	}

	public void setGoods_photos(List<ezs_goods_photo> goods_photos) {
		this.goods_photos = goods_photos;
	}

	public List<ezs_goods_cartography> getCartographys() {
		return cartographys;
	}

	public void setCartographys(List<ezs_goods_cartography> cartographys) {
		this.cartographys = cartographys;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Boolean getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Long getArea_id() {
		return area_id;
	}

	public void setArea_id(Long area_id) {
		this.area_id = area_id;
	}

	public Long getClassOfFinish_id() {
		return classOfFinish_id;
	}

	public void setClassOfFinish_id(Long classOfFinish_id) {
		this.classOfFinish_id = classOfFinish_id;
	}

	public Long getColor_id() {
		return color_id;
	}

	public void setColor_id(Long color_id) {
		this.color_id = color_id;
	}

	public Long getForm_id() {
		return form_id;
	}

	public void setForm_id(Long form_id) {
		this.form_id = form_id;
	}

	public Long getGoodClass_id() {
		return goodClass_id;
	}

	public void setGoodClass_id(Long goodClass_id) {
		this.goodClass_id = goodClass_id;
	}

	public Long getLogistics_id() {
		return logistics_id;
	}

	public void setLogistics_id(Long logistics_id) {
		this.logistics_id = logistics_id;
	}

	public Long getQuality_id() {
		return quality_id;
	}

	public void setQuality_id(Long quality_id) {
		this.quality_id = quality_id;
	}

	public Long getRegion_id() {
		return region_id;
	}

	public void setRegion_id(Long region_id) {
		this.region_id = region_id;
	}

	public Long getSupply_id() {
		return supply_id;
	}

	public void setSupply_id(Long supply_id) {
		this.supply_id = supply_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getUtil_id() {
		return util_id;
	}

	public void setUtil_id(Long util_id) {
		this.util_id = util_id;
	}

	public String getAddess2() {
		return addess2;
	}

	public void setAddess2(String addess2) {
		this.addess2 = addess2;
	}
	public long getAllcount() {
		return allcount;
	}

	public void setAllcount(long allcount) {
		this.allcount = allcount;
	}

	public int getHighp() {
		return highp;
	}

	public void setHighp(int highp) {
		this.highp = highp;
	}

	public int getCollected() {
		return collected;
	}

	public void setCollected(int collected) {
		this.collected = collected;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public GoodsVo() {
	}

}
