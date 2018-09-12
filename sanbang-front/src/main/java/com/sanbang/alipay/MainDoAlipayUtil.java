package com.sanbang.alipay;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.net.util.Base64;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

public class MainDoAlipayUtil {
	// 新易appId
	private static final String APP_ID = "2018081561035437";
	// 新易私钥
	public static final String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDLLPUe7MbR85If5BDRM7cs8/RbEs8lWy42pJvauHHrom1Fi7eefdW7CbuimPiyF2MlcUawvWhOv5NdExlqg+/Ow4oW3xIxXthPAz8t573U5bk1EgavqH/t13bTY4NnY7pHeProcQC7Gj99rHh5YIlJlqSH+42dtaXCWwUFwPlqJ+L78GCRsUBe/ZExUJZF9iBSlIaLk7WK3iVdem3Xf6DvTWdDfInpCIObb3109PR6/GUCdZVt4jJtgy0iahTiDSeEXaPvzWiyAo2G3KuJADEqnqIwi6CH5Gp3ZyMDvzrOUJrXg/48mDGyX8fOH4yNvi2/rx4WizgkpgZT3YOZCJudAgMBAAECggEBAMBnDKGCfC1qSWCl6z8RiE+rniV4xG2N0U+xxl3z+P25zVzkmLggVfxPlT6/OhP8jclxYg4Q7+xTA0xKv/DcjIXzKpvKF0JlnNYGLxhbsqsf+KnHLkm+eZdUSZSZUmDgUhFJAeKI0LZefu2WHGhlAN2NKLYVg+BrmlNZOoJeYlMhTdIu9shAFQkyiAUIGl7BXjxYPVKc2kB8gH6gNmh6OikgasF0PXCRRjIPjvNHGv0cBgG63zeU9IiXwwwBgPDrPjBnzm9o8xODoqW3OctnD2tTjRLSnloKlWpidEhFqGhTX+fYTZ9lo97ulA8yJKzrYnE2dlZVBfyqdW3IFsZHEMECgYEA8ngKnXP7BzPwfyvhF6JH+UjbWM4/DW4gAK6j8osBmEhGdV4P1/eVN+L+Yf3qrxMgM3Ne+VElmIDYg5GtgUeRQiohGOQLhECPmzS0xDvvKzTC3xrzSORNKhFlrbbqUjLyTTrv2w1t1KWsdL0EUju65WVP53FtmzWnrE2auv/3ZrUCgYEA1oOQdIG+2OmMn78hTi9GxznPP5BcN9waMuz9L30IF4E5XkZIFCdkmYb3/NJdhvMYqP4WAPCKsH1VjO5k/h11D3xZeMacJ9vV73gbBPLCsBpcIKTauUf26yyDZfXx4y24kloxCNw9y3iU2pRHD0KcNC4EZr8Sfa8+0tVwwewcSkkCgYEAmigN/6tUh3DTT9dTBhasEebrZlvCpMRGXoiqPbN5MRuKiGZkRlfnrB+KwjyQr/zF9VA9qt+Xuoz4mzXMjSw2Q03LuyqJ0+zEINZys2yzk9G4r+ZPlSFpmfxzm+12rworGUUGaEvyb0diDNp729iT6/LsyWZJXGvRje/NF2VUIx0CgYAq6UqeY83/qkidNCi/cSmhdOkGeCRacEc1ZL8JHuPdf9YwC7MjhPXU2HEHPDXFZx/Jvno8WeIEiC3y8UV2qAHgxSlIxcI7Hvje3JHbHYzgmYVQamnuony8cr0eSmLG5UCE1lH0ycn6x/ZO+1ZzsQl6TrJGs3ZJeiMBHf1ebZMDMQKBgQCG9qFuyR9s1R77Yl6HmQ6A0AF8Hx6h+Dk8w9/ueQfIL8Ekug2zDtCBxdSQdix/sqEfxKGz5lnk3GYoY5Cp+8eYFjfBAiis8vYCl3d1amtI7obFw7YQBTYLWItPUkQ18Z0mzhumMVKWs9eLDkX4yFoo+BQL9r0EIQpudSP3+hWIcQ==";
	// 新易公钥
	public static final String APP_PUBLIC_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDLLPUe7MbR85If5BDRM7cs8/RbEs8lWy42pJvauHHrom1Fi7eefdW7CbuimPiyF2MlcUawvWhOv5NdExlqg+/Ow4oW3xIxXthPAz8t573U5bk1EgavqH/t13bTY4NnY7pHeProcQC7Gj99rHh5YIlJlqSH+42dtaXCWwUFwPlqJ+L78GCRsUBe/ZExUJZF9iBSlIaLk7WK3iVdem3Xf6DvTWdDfInpCIObb3109PR6/GUCdZVt4jJtgy0iahTiDSeEXaPvzWiyAo2G3KuJADEqnqIwi6CH5Gp3ZyMDvzrOUJrXg/48mDGyX8fOH4yNvi2/rx4WizgkpgZT3YOZCJudAgMBAAECggEBAMBnDKGCfC1qSWCl6z8RiE+rniV4xG2N0U+xxl3z+P25zVzkmLggVfxPlT6/OhP8jclxYg4Q7+xTA0xKv/DcjIXzKpvKF0JlnNYGLxhbsqsf+KnHLkm+eZdUSZSZUmDgUhFJAeKI0LZefu2WHGhlAN2NKLYVg+BrmlNZOoJeYlMhTdIu9shAFQkyiAUIGl7BXjxYPVKc2kB8gH6gNmh6OikgasF0PXCRRjIPjvNHGv0cBgG63zeU9IiXwwwBgPDrPjBnzm9o8xODoqW3OctnD2tTjRLSnloKlWpidEhFqGhTX+fYTZ9lo97ulA8yJKzrYnE2dlZVBfyqdW3IFsZHEMECgYEA8ngKnXP7BzPwfyvhF6JH+UjbWM4/DW4gAK6j8osBmEhGdV4P1/eVN+L+Yf3qrxMgM3Ne+VElmIDYg5GtgUeRQiohGOQLhECPmzS0xDvvKzTC3xrzSORNKhFlrbbqUjLyTTrv2w1t1KWsdL0EUju65WVP53FtmzWnrE2auv/3ZrUCgYEA1oOQdIG+2OmMn78hTi9GxznPP5BcN9waMuz9L30IF4E5XkZIFCdkmYb3/NJdhvMYqP4WAPCKsH1VjO5k/h11D3xZeMacJ9vV73gbBPLCsBpcIKTauUf26yyDZfXx4y24kloxCNw9y3iU2pRHD0KcNC4EZr8Sfa8+0tVwwewcSkkCgYEAmigN/6tUh3DTT9dTBhasEebrZlvCpMRGXoiqPbN5MRuKiGZkRlfnrB+KwjyQr/zF9VA9qt+Xuoz4mzXMjSw2Q03LuyqJ0+zEINZys2yzk9G4r+ZPlSFpmfxzm+12rworGUUGaEvyb0diDNp729iT6/LsyWZJXGvRje/NF2VUIx0CgYAq6UqeY83/qkidNCi/cSmhdOkGeCRacEc1ZL8JHuPdf9YwC7MjhPXU2HEHPDXFZx/Jvno8WeIEiC3y8UV2qAHgxSlIxcI7Hvje3JHbHYzgmYVQamnuony8cr0eSmLG5UCE1lH0ycn6x/ZO+1ZzsQl6TrJGs3ZJeiMBHf1ebZMDMQKBgQCG9qFuyR9s1R77Yl6HmQ6A0AF8Hx6h+Dk8w9/ueQfIL8Ekug2zDtCBxdSQdix/sqEfxKGz5lnk3GYoY5Cp+8eYFjfBAiis8vYCl3d1amtI7obFw7YQBTYLWItPUkQ18Z0mzhumMVKWs9eLDkX4yFoo+BQL9r0EIQpudSP3+hWIcQ==";
	// 阿里公钥
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk5XVz4Bt0BqxjmUASWwYZ9pak3SXf3t4yKFSlFq7KCWCMnYxyOmB9tFhW5+gvngHykFwfqEpUbNWJiWhQXptOVJl4rpMPe/23xt9sFwS3zE7u9VOfDOeEN5TkGxFELcL0xlUWXmQfG/lpSXpGnhBuZsraCO1xFgPymXsjlMBYpMnN5QGmj/FoiUePaN+vS7qjj1ruNmQyGl+ZfiF/XDDr4igwoug6S79qkIWTyogmtiZYOxic1UasuPqVEGGYNzGvQlvbJ8WO+3WcupdVlt/d+hkh+YQNjm+gYEgTMj9xDce0gF1gO3y4Frq1NCTVsgOreu5d9ULuZFoTcgs8+NUNQIDAQAB";
	public static final String CHARSET = "UTF-8";
	// 阿里服务地址
	private static final String ALIPAY_SERVERURL = "https://openapi.alipay.com/gateway.do";
	private static final String DATA_TYPE = "json";
	private static final String SIGN_TYPE = "RSA2";

	/**
	 * 
	 * @param title     对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
	 * @param subject   商品的标题/交易标题/订单标题/订单关键字等
	 * @param OrderNo   商户网站唯一订单号
	 * @param payAmount 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
	 * @param NotifyURl 支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
	 * @param result
	 * @return
	 */
	public static Result DoPayAli(String title, String subject, String OrderNo, BigDecimal payAmount, String NotifyURl,
			Result result) {
		Map<String, Object> map = new HashMap<>();
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_SERVERURL, APP_ID, APP_PRIVATE_KEY, DATA_TYPE,
				CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(title);// 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
		model.setSubject(subject);// 商品的标题/交易标题/订单标题/订单关键字等
		model.setOutTradeNo(OrderNo);// 商户网站唯一订单号
		model.setTimeoutExpress("30m");// 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
										// 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
		model.setTotalAmount(String.valueOf(payAmount));// 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
		model.setProductCode("QUICK_MSECURITY_PAY");// 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
		request.setBizModel(model);
		request.setNotifyUrl(NotifyURl);// 支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			if (response.isSuccess()) {
				result.setMsg("请求alipay成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setSuccess(true);
				map.put("reqstr", response.getBody());
				result.setObj(map);
			} else {
				result.setMsg("请求alipay失败");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setObj(map);
			}

		} catch (AlipayApiException e) {
			e.printStackTrace();
			result.setMsg("请求alipay失败");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setObj(map);
		}
		return result;
	}

	
	public static void encryptAndSign(String bizContent) throws AlipayApiException {
        
       /* String responseContent = AlipaySignature.encryptAndSign(bizContent, ALIPAY_PUBLIC_KEY, APP_PRIVATE_KEY,
            "UTF-8", true, true);
        System.out.println(responseContent);*/
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("biz_content", AlipaySignature.rsaEncrypt(bizContent, ALIPAY_PUBLIC_KEY, "UTF-8"));
        params.put("charset", "UTF-8");
        params.put("service", "alipay.mobile.public.message.notify");
        params.put("sign_type", "RSA");
        params.put("sign", AlipaySignature.rsaSign(params, APP_PRIVATE_KEY, "UTF-8"));

        // 验签&解密
        String resultContent = AlipaySignature.checkSignAndDecrypt(params, ALIPAY_PUBLIC_KEY, APP_PRIVATE_KEY,
            true, true);

        System.out.println(resultContent);

    }
	
   
	public static String getFormatParams(Map<String,String> params){
	    List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(params.entrySet());
	    Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
	        public int compare(Map.Entry<String, String> arg0, Map.Entry<String, String> arg1) {
	            return (arg0.getKey()).compareTo(arg1.getKey());
	        }
	    });
	    String ret = "";

	    for (Map.Entry<String, String> entry : infoIds) {
	        ret += entry.getKey();
	        ret += "=";
	        ret += URLDecoder.decode(entry.getValue());
	        ret += "&";
	    }
	    ret = ret.substring(0, ret.length() - 1);
	    return ret;
	}
	
	
	public static Map<String, String>  getCheckSignFormatParams(Map<String,String> params){
		
		Map<String, String>  chahe=new HashMap<>();
		for (Entry<String, String> c : params.entrySet()) {
			chahe.put(c.getKey(), URLDecoder.decode(Base64.decodeBase64(c.getValue()).toString()));
		}
		return chahe;
	}
	
	
	public static void main(String[] args) {
		/*Result result = Result.failure();
		result = DoPayAli("test", "miaoshu", "hq123456789", new BigDecimal("0.01"),
				"http://test.ezaisheng.com/website/certSign/forout/signContractFast.do", result);
		System.out.println(result.toString());*/
		String bizContent="%7B%22body%22%3A%22%E6%88%91%E7%9A%84%E8%AE%A2%E9%98%85%E6%94%AF%E4%BB%98%E7%A1%AE%E8%AE%A4%22%2C%22out_trade_no%22%3A%22HQ1536167129761102%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%BB%B7%E6%A0%BC%E8%A1%8C%E6%83%85%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%221620.00%22%7D";
		//String bizContent="<?xml version=\"1.0\" encoding=\"UTF-8\"?><alipay><response>QEmvbqZvLtVjisrBidsIdIS2uVnRzrVELQ5982dCTai1frKqxWDo2I+Wl+aoLV4zCCIy0tVfuOTT25XD8aVUAuvDBT177NMk9353jcHAoz2rxM+1Dw+GiiOwpQxtG1EiTkCD1UGVplJETz7ozrisfsd/8YrYHW5MF4viihbuO9fMSvlO/75c3KTZoZPqjlcpsYc1Rus55nHflSBbLPLMVFm3yTff9B2HVi+DdxEsgl8Ap+v+4MdJw3HbTMJDJnW6XMy/jeMNfFio756O+XSdmn6FbMA/CU3zMM455Um0gKyaxVyUA5fh5C4P+H+SW/8dWFKimiB1I8tZPgsDocfk7RLreSG9EUhcoXlaR9MWAtjBaC5mc+JV+k/CSoDQDD3BrRo6cBSe7ZgL9Lax586JwViN0EmEFDE1aIRgzvrBBvzbNnQ4Resby7yM8YTbVxq7uCoSzM5+E0/z/dWiU/ENuI4rlUGqprGirjWnr0fFkJEvw1OjFYZJ6asIrgDrQjD6CETtVn9AFY9zvFg+WIK49p7J7StN32KU+vXNo0rOY0xcp8iBoQWdDr862bNUvm8lwZ1FtD8Tfv/bOeiWzPt50NgGtBneXnp5Xt1jZ00tlvGcaqeBZB0ugWAODch2EzdvwG1XMrr7GQM3PfMmFYp3wM4+BFCD9L3d9XoaFxbD0f4IZKQlMFfLRyO/8reuzZ9OsbyYMr2QKEOqR7CHarTrOaiv3eJb0Xm5tW1M+NIKyfzrzYnB4uqOXq9PjOObbJ+Kk/rAo8y7YHlh2out4lb0JRL83tgdh3/H9QiX8UqZJeGeDOxWcZSdUB6DCrnjzz22YeRZ8OLRrX1r8amPOAFkTF5ALQkJKdXe1oBltA1GKXJuFlwtoQ4Z4zgZ5OtPebfThlbCo4i8LQPIeUw/SsHWdv4dM9UPN70t/omK2Hhj2Cjwte5gCv833sMmiSEudHGHcLTdIqzHGoz8d0YXjpMJieZXtE6RGPZa2Wx2bcsBT0+rG3AWTJUaRLP4d4zhlVR8</response><encryption_type>RSA</encryption_type><sign>dY+IOyyS9uq+uqwFYiYA32116yrC4NHOXu6vdBrDlERPOK4+fSV014VcHxIgiZb2MxKqKlH2vqP+khqmQ1RzfnmmFLT4sXYjF7nJtE69L1lPbpFvWbXP+bmVJ2ySZ5FF32pqOA2riPZ/FiGaJw4MgXCjRfu5aF+SiohK2dGR3YtmZYf7mmqrfqmZVJfEZYXp3s/tzpULGIJ9IiV/0iImcMW08HU4q49JrML7A5KhfurpG9CNG4+kwddp14HHm+sNqKlQhHBAiuSsqiKaNiPs8AcCcP3Pj31oP4cDBfdYlbqcFGQmOu/FU/3vQT5YGy7YMmotFFNiKy3lrBIAhneDaw==</sign><sign_type>RSA</sign_type></alipay>";
		try {
			encryptAndSign(bizContent);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
