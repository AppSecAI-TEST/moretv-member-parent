package cn.whaley.member.base.manager;


/**
 * 支付接口
 * 
 * @author tangzc 2016年5月27日
 * 
 */
public class TencentPayManage {/*

	*//**
	 * 会员退货
	 * @param payMethod
	 * @param orderNo
	 * @param amount
	 * @param goodsNo
	 * @return
	 * @throws Exception
	 *//*
	public static CpReturnBill clearVip(final String vuid, final String vippkg) {
		// 重试腾讯退货3次
		ExceptionRetryBase<CpReturnBill> retryWork = new ExceptionRetryBase<CpReturnBill>() {

			@Override
			protected CpReturnBill tryWork() throws Exception {
				HttpHelper httpHandle = new HttpHelper();
				httpHandle.setConnectTimeout(5000);
				httpHandle.setReadTimeout(5000);
				CpReturnBill cpReturnBill = null;
				String access_token = ExternalManage.getAccessToken();
				String url = PropertiyHelp.getContextProperty("TENCENT_SERVER")
						+ "/clear_vip?version=1&format=json&access_token=" + access_token + "&vuid=" + vuid + "&vippkg="
						+ vippkg;
				LogHelper.getLogger().info("clearVip_request,uri:" + url);

				String res = httpHandle.doGet(url);
				LogHelper.getLogger().info("clearVip_response,response" + res + ",uri:" + url);

				JSONObject clearVipJson = JSONObject.fromObject(res);

				JSONObject result = clearVipJson.getJSONObject("result");

				if ("0".equals(result.getString("ret"))) {
					JSONObject data = clearVipJson.getJSONObject("data");
					cpReturnBill = new CpReturnBill();
					cpReturnBill.setRollbackTime(data.getInt("rollback_time"));
					cpReturnBill.setOrderId(data.getString("order_id"));
					cpReturnBill.setOttid(data.getString("ottid"));
					cpReturnBill.setStart(new Date(data.getLong("start") * 1000));
					cpReturnBill.setEnd(new Date(data.getLong("end") * 1000));
				}
				return cpReturnBill;
			}

		};
		boolean result = retryWork.tryWorkUnderException();
		if (result) {
			return retryWork.getLastResult();
		}

		return null;
    }
*/}
