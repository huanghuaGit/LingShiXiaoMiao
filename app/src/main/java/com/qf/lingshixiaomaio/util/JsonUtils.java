package com.qf.lingshixiaomaio.util;

import android.util.Log;

import com.qf.lingshixiaomaio.model.CommentsEntity;
import com.qf.lingshixiaomaio.model.HomeBrandsSpecialEntity;
import com.qf.lingshixiaomaio.model.HomeCenterBrandsEntity;
import com.qf.lingshixiaomaio.model.HomeDropDownBrandsEntity;
import com.qf.lingshixiaomaio.model.HomeHeadEntity;
import com.qf.lingshixiaomaio.model.HomeTabEntity;
import com.qf.lingshixiaomaio.model.HomeTabSelectEntity;
import com.qf.lingshixiaomaio.model.Infos;
import com.qf.lingshixiaomaio.model.ItemDetailEntity;
import com.qf.lingshixiaomaio.model.ItemListEntity;
import com.qf.lingshixiaomaio.model.Subject_Detail;
import com.qf.lingshixiaomaio.model.Subject_Detail_Goodses;
import com.qf.lingshixiaomaio.model.Subject_Info;
import com.qf.lingshixiaomaio.model.Subject_Top;
import com.qf.lingshixiaomaio.model.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
	// 解析首页ViewPager数据
	public static List<HomeHeadEntity> parseHomeViewPagerJson(
			JSONObject response) {
		List<HomeHeadEntity> list = new ArrayList<HomeHeadEntity>();
		try {
			JSONArray array = response.getJSONObject("data").getJSONArray(
					"topics");
			for (int i = 0; i < array.length(); i++) {
				JSONObject sub_obj = array.getJSONObject(i);
				int id = sub_obj.getInt("id");
				JSONObject img_obj = sub_obj.getJSONObject("img");
				String img_url = img_obj.getString("img_url");
				JSONObject action_obj = sub_obj.getJSONObject("action");
				String info = action_obj.getString("info");
				int type = action_obj.getInt("type");
				HomeHeadEntity headEntity = new HomeHeadEntity(id, img_url,
						info, type);
				list.add(headEntity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 解析首页品牌的JSON数据
	public static List<HomeCenterBrandsEntity> parseHomeCenterBrandsJson(
			JSONObject response) {
		List<HomeCenterBrandsEntity> list = new ArrayList<HomeCenterBrandsEntity>();
		try {
			String code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject data_obj = response.getJSONObject("data");
				JSONArray array = data_obj.getJSONArray("brands");
				for (int i = 0; i < array.length(); i++) {
					JSONObject sub_obj = array.getJSONObject(i);
					int id = sub_obj.getInt("id");
					String title = sub_obj.getString("title");
					String discount = sub_obj.getString("discount");
					long time = sub_obj.getLong("time");
					JSONObject img_obj = sub_obj.getJSONObject("img");
					String img_url = img_obj.getString("img_url");
					HomeCenterBrandsEntity centerBrandsEntity = new HomeCenterBrandsEntity(
							id, title, img_url, discount, time);
					list.add(centerBrandsEntity);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析首页中间导航兰的JSON数据
	public static List<HomeTabEntity> parseHomeTabJson(JSONObject response,
			List<HomeTabEntity> home_tab_list) {
		try {
			String code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject data_obj = response.getJSONObject("data");
				JSONArray array = data_obj.getJSONArray("classifies");
				for (int i = 0; i < array.length(); i++) {
					JSONObject sub_obj = array.getJSONObject(i);
					int id = sub_obj.getInt("id");
					String title = sub_obj.getString("title");
					String desc = sub_obj.getString("desc");
					JSONObject img_obj = sub_obj.getJSONObject("img");
					String img_url = img_obj.getString("img_url");
					HomeTabEntity homeTabEntity = new HomeTabEntity(id, title,
							desc, img_url);
					home_tab_list.add(homeTabEntity);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return home_tab_list;
	}

	// 解析首页中BrandsSpecial部分数据
	public static List<HomeBrandsSpecialEntity> parseHomeBrandsSpecialJson(
			JSONObject response, List<HomeBrandsSpecialEntity> list) {
		try {
			String code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject data_obj = response.getJSONObject("data");
				JSONArray array = data_obj.getJSONArray("specials");
				for (int i = 0; i < array.length(); i++) {
					JSONObject sub_obj = array.getJSONObject(i);
					int id = sub_obj.getInt("id");
					JSONObject img_obj = sub_obj.getJSONObject("img");
					String img_url = img_obj.getString("img_url");
					JSONObject action_obj = sub_obj.getJSONObject("action");
					int type = action_obj.getInt("type");
					String info = action_obj.getString("info");

					HomeBrandsSpecialEntity brandsSpecialEntity = new HomeBrandsSpecialEntity(
							id, img_url, info, type);
					list.add(brandsSpecialEntity);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 解析首页下拉出现的数据的JSON数据
	public static List<HomeDropDownBrandsEntity> parseDropDownHomeBrandsJson(
			JSONObject response, List<HomeDropDownBrandsEntity> list) {
		String code;
		try {
			code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject data_obj = response.getJSONObject("data");
				JSONArray array = data_obj.getJSONArray("items");
				for (int i = 0; i < array.length(); i++) {
					JSONObject sub_obj = array.getJSONObject(i);
					int id = sub_obj.getInt("id");
					String title = sub_obj.getString("title");
					int type = sub_obj.getInt("type");
					int guide_type = sub_obj.getInt("guide_type");
					JSONObject price_obj = sub_obj.getJSONObject("price");
					double current = price_obj.getDouble("current");
					double prime = price_obj.getDouble("prime");
					JSONObject img_obj = sub_obj.getJSONObject("img");
					String img_url = img_obj.getString("img_url");
					long time = sub_obj.getLong("time");
					HomeDropDownBrandsEntity homeDropDownBrandsEntity = new HomeDropDownBrandsEntity(
							id, title, img_url, time, type, current, prime,
							guide_type);
					list.add(homeDropDownBrandsEntity);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 解析点击进去的商品列表的JSON数据

	public static List<ItemListEntity> parseItemListJson(JSONObject response) {
		List<ItemListEntity> list = new ArrayList<ItemListEntity>();
		String code;
		try {
			code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject data_obj = response.getJSONObject("data");
				JSONArray array = data_obj.getJSONArray("items");
				for (int i = 0; i < array.length(); i++) {
					JSONObject sub_obj = array.getJSONObject(i);
					int id = sub_obj.getInt("id");
					String title = sub_obj.getString("title");
					int sold_num = sub_obj.getInt("sold_num");
					JSONObject img_obj = sub_obj.getJSONObject("img");
					String img_url = img_obj.getString("img_url");
					JSONObject price_obj = sub_obj.getJSONObject("price");
					double current = price_obj.getDouble("current");
					double prime = price_obj.getDouble("prime");
					JSONObject tag_obj = sub_obj.getJSONObject("tag");
					String tag_title = tag_obj.getString("title");
					int fav_num = sub_obj.getInt("fav_num");
					String desc = sub_obj.getString("desc");
					long time = sub_obj.getLong("time");
					ItemListEntity itemListEntity = new ItemListEntity(id,
							title, sold_num, img_url, current, prime,
							tag_title, fav_num, desc, time);
					list.add(itemListEntity);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析首页brandsView点击进去的商品列表的JSON数据
	public static List<ItemListEntity> parseCenterBrandsViewListJson(
			JSONObject response) {
		List<ItemListEntity> list = new ArrayList<ItemListEntity>();
		String code;
		try {
			code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject data_obj = response.getJSONObject("data");
				JSONArray array = data_obj.getJSONArray("goodses");
				for (int i = 0; i < array.length(); i++) {
					JSONObject sub_obj = array.getJSONObject(i);
					int id = sub_obj.getInt("id");
					String title = sub_obj.getString("title");
					int sold_num = sub_obj.getInt("sold_num");
					JSONObject img_obj = sub_obj.getJSONObject("img");
					String img_url = img_obj.getString("img_url");
					JSONObject price_obj = sub_obj.getJSONObject("price");
					double current = price_obj.getDouble("current");
					double prime = price_obj.getDouble("prime");
					JSONObject tag_obj = sub_obj.getJSONObject("tag");
					String tag_title = tag_obj.getString("title");
					int fav_num = sub_obj.getInt("fav_num");
					String desc = sub_obj.getString("desc");
					long time = sub_obj.getLong("time");
					ItemListEntity itemListEntity = new ItemListEntity(id,
							title, sold_num, img_url, current, prime,
							tag_title, fav_num, desc, time);
					list.add(itemListEntity);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 解析tabView里商品选择器的数据
	public static List<HomeTabSelectEntity> parseHomeTabSelectEntityJson(
			JSONObject response) {
		List<HomeTabSelectEntity> list = new ArrayList<HomeTabSelectEntity>();
		String code;
		try {
			code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject data_obj = response.getJSONObject("data");
				JSONArray array = data_obj.getJSONArray("items");
				for (int i = 0; i < array.length(); i++) {
					JSONObject sub_obj = array.getJSONObject(i);
					int id = sub_obj.getInt("id");
					String title = sub_obj.getString("title");
					HomeTabSelectEntity homeTabSelectEntity = new HomeTabSelectEntity(
							id, title);
					list.add(homeTabSelectEntity);
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 解析专题头部
	 * 
	 * @param json
	 * @return List<Subject_Top>
	 */
	public static List<Subject_Top> parseSubjectTopJson(String json) {
		List<Subject_Top> list = new ArrayList<Subject_Top>();
		try {
			JSONObject jsonobj = new JSONObject(json);
			String code = jsonobj.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject dataobj = jsonobj.getJSONObject("data");
				JSONArray arrayitems = dataobj.getJSONArray("items");
				for (int i = 0; i < arrayitems.length(); i++) {
					JSONObject objitmes = arrayitems.getJSONObject(i);
					int id = objitmes.getInt("id");
					JSONObject objimg = objitmes.getJSONObject("img");
					String img_url = objimg.getString("img_url");
					int img_w = objimg.getInt("img_w");
					int img_h = objimg.getInt("img_h");
					JSONObject objaction = objitmes.getJSONObject("action");
					int type = objaction.getInt("type");
					String info = objaction.getString("info");
					Subject_Top sub = new Subject_Top(id, img_url, img_w,
							img_h, type, info);
					list.add(sub);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 解析专题list
	 * 
	 * @param json
	 * @return
	 */
	public static List<Subject_Info> parseSubjectInfoJson(String json) {
		List<Subject_Info> list = new ArrayList<Subject_Info>();
		try {
			JSONObject jsonobj = new JSONObject(json);
			String code = jsonobj.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject dataobj = jsonobj.getJSONObject("data");
				JSONArray arrayitems = dataobj.getJSONArray("items");
				for (int i = 0; i < arrayitems.length(); i++) {
					JSONObject objitmes = arrayitems.getJSONObject(i);
					int id = objitmes.getInt("id");
					String desc = objitmes.getString("desc");
					String title = objitmes.getString("title");
					int hotindex = objitmes.getInt("hotindex");
					int share_num = objitmes.getInt("share_num");
					JSONObject objimg = objitmes.getJSONObject("img");
					String img_url = objimg.getString("img_url");
					int img_w = objimg.getInt("img_w");
					int img_h = objimg.getInt("img_h");
					Subject_Info sub = new Subject_Info(id, desc, title,
							img_url, img_w, img_h, hotindex, share_num);
					list.add(sub);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 解析专题详情
	 * 
	 * @param json
	 * @return
	 */
	public static List<Subject_Detail> parseSubjectDetialJson(String json) {
		List<Subject_Detail> list_detail = new ArrayList<Subject_Detail>();
		try {
			JSONObject jsonobj = new JSONObject(json);
			String code = jsonobj.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject dataobj = jsonobj.getJSONObject("data");
				JSONObject objsub = dataobj.getJSONObject("subject");
				int id = objsub.getInt("id");
				String desc = objsub.getString("desc");
				String title = objsub.getString("title");
				int hotindex = objsub.getInt("hotindex");
				int share_num = objsub.getInt("share_num");
				JSONObject objimg = objsub.getJSONObject("img");
				String img_url = objimg.getString("img_url");
				Subject_Detail sub = new Subject_Detail(img_url, id, title,
						hotindex, desc, share_num);
				Log.i("list_detail", sub.toString());
				list_detail.add(sub);
				Log.i("list_detail", list_detail.size() + "");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list_detail;
	}

	/**
	 * 解析专题详情web
	 * 
	 * @param json
	 * @return
	 */
	public static String parseSubjectDetialWebJson(String json) {
		String web_url = null;
		try {
			JSONObject jsonobj = new JSONObject(json);
			String code = jsonobj.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject dataobj = jsonobj.getJSONObject("data");
				web_url = dataobj.getString("web_url");
				return web_url;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return web_url;
	}

	/**
	 * 解析专题详情商品
	 * 
	 * @param json
	 * @return
	 */
	public static List<Subject_Detail_Goodses> parseSubjectDetialGoodJson(
			String json) {
		List<Subject_Detail_Goodses> list_detail_goods = new ArrayList<Subject_Detail_Goodses>();
		try {
			JSONObject jsonobj = new JSONObject(json);
			String code = jsonobj.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject dataobj = jsonobj.getJSONObject("data");
				JSONArray arrgood = dataobj.getJSONArray("goodses");
				for (int i = 0; i < arrgood.length(); i++) {
					JSONObject objarr = arrgood.getJSONObject(i);
					int id = objarr.getInt("id");
					String title = objarr.getString("title");
					int type = objarr.getInt("type");
					int guide_type = objarr.getInt("guide_type");
					int status = objarr.getInt("status");
					int sold_num = objarr.getInt("sold_num");
					int surplus_num = objarr.getInt("surplus_num");
					int freight = objarr.getInt("freight");
					int time = objarr.getInt("time");
					int fav_num = objarr.getInt("fav_num");
					String desc = objarr.getString("desc");
					int sub_classify_id = objarr.getInt("sub_classify_id");
					JSONObject objprice = objarr.getJSONObject("price");
					double current = objprice.getDouble("current");
					double prime = objprice.getDouble("prime");
					JSONArray arrpos = objarr.getJSONArray("posters");
					String img_url = arrpos.getJSONObject(0).getString(
							"img_url");
					Subject_Detail_Goodses sub = new Subject_Detail_Goodses(
							surplus_num, sub_classify_id, freight, img_url,
							title, type, sold_num, fav_num, guide_type,
							current, prime, id, time, status, desc);
					list_detail_goods.add(sub);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list_detail_goods;
	}

	/**
	 * 解析美味详情头布局
	 * 
	 * @param
	 * @return
	 */
	public static List<ItemDetailEntity> parseItemDetailHeadJson(
			JSONObject response) {
		List<ItemDetailEntity> list = new ArrayList<ItemDetailEntity>();
		String code;
		try {
			code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject sub_obj = response.getJSONObject("data");
				int id = sub_obj.getInt("id");
				String title = sub_obj.getString("title");
				int type = sub_obj.getInt("type");
				int guide_type = sub_obj.getInt("guide_type");
				int sold_num = sub_obj.getInt("sold_num");
				int surplus_num = sub_obj.getInt("surplus_num");
				JSONObject price_obj = sub_obj.getJSONObject("price");
				double current = price_obj.getDouble("current");
				double prime = price_obj.getDouble("prime");
				long server_time = sub_obj.getLong("server_time");

				JSONArray tags_array = sub_obj.getJSONArray("tags");
				List<Tags> tag_list = new ArrayList<Tags>();
				for (int i = 0; i < tags_array.length(); i++) {
					JSONObject tag_obj = tags_array.getJSONObject(i);
					String tab_title = tag_obj.getString("title");
					String tab_color = tag_obj.getString("color");
					Tags tags = new Tags(tab_title, tab_color);
					tag_list.add(tags);
				}

				JSONArray img_array = sub_obj.getJSONArray("main_imgs");
				List<String> image_list = new ArrayList<String>();
				for (int i = 0; i < img_array.length(); i++) {
					JSONObject img_obj = img_array.getJSONObject(i);
					String img_url = img_obj.getString("img_url");
					image_list.add(img_url);
				}

				/*
				 * //底部优惠部分 String icon_title = null; String activity_title =
				 * null; int action_type = 0; String action_info= null;
				 * 
				 * if(type==2&&guide_type==0){ JSONObject activity_obj =
				 * sub_obj.getJSONObject("activity"); icon_title =
				 * activity_obj.getString("icon_title"); activity_title =
				 * activity_obj.getString("title"); JSONObject action_obj =
				 * activity_obj.getJSONObject("action"); action_type =
				 * action_obj.getInt("type"); action_info =
				 * action_obj.getString("info"); } ItemDetailEntity
				 * itemDetailEntity = new ItemDetailEntity(id, title, type,
				 * guide_type, sold_num, surplus_num, current, prime,
				 * server_time, tag_list, image_list, icon_title,
				 * activity_title, action_type, action_info);
				 */

				ItemDetailEntity itemDetailEntity = new ItemDetailEntity(id,
						title, type, guide_type, sold_num, surplus_num,
						current, prime, server_time, tag_list, image_list);

				list.add(itemDetailEntity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 解析美味详情中间图片
	 * 
	 * @param ZL
	 * @return
	 */
	public static String parseItemDetailCenterWebViewJson(JSONObject response) {
		String details = null;
		try {
			String code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject sub_obj = response.getJSONObject("data");
				details = sub_obj.getString("details");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return details;
	}

	/**
	 * 解析美味详情评论布局
	 * 
	 * @param ZL
	 * @return
	 */
	public static List<ItemDetailEntity> parseItemDetailCommentsJson(
			JSONObject response) {
		List<ItemDetailEntity> list = new ArrayList<ItemDetailEntity>();
		try {
			String code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject sub_obj = response.getJSONObject("data");
				JSONObject comments_obj = sub_obj.getJSONObject("comments");
				int total_num = comments_obj.getInt("total_num");

				JSONArray comments_array = comments_obj
						.getJSONArray("comments");
				List<CommentsEntity> list_Comments = new ArrayList<CommentsEntity>();
				for (int i = 0; i < comments_array.length(); i++) {
					JSONObject obj = comments_array.getJSONObject(i);
					int comments_id = obj.getInt("id");
					JSONObject avatar_obj = obj.getJSONObject("avatar");
					String comments_img_url = avatar_obj.getString("img_url");
					String nickname = obj.getString("nickname");
					String content = obj.getString("content");

					CommentsEntity comments = new CommentsEntity(comments_id,
							comments_img_url, nickname, content);
					list_Comments.add(comments);
				}
				ItemDetailEntity itemDetailEntity = new ItemDetailEntity(
						total_num, list_Comments);
				list.add(itemDetailEntity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 解析美味详情美味信息
	 * 
	 * @param ZL
	 * @return
	 */
	public static List<ItemDetailEntity> parseItemDetailInfoJson(
			JSONObject response) {
		List<ItemDetailEntity> list = new ArrayList<ItemDetailEntity>();
		try {
			String code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject sub_obj = response.getJSONObject("data");
				int type = sub_obj.getInt("type");
				int guide_type = sub_obj.getInt("guide_type");
				JSONObject arguments_obj = sub_obj.getJSONObject("arguments");
				String arguments_title = arguments_obj.getString("title");
				JSONArray infos_array = arguments_obj.getJSONArray("infos");

				List<Infos> info_list = new ArrayList<Infos>();
				for (int i = 0; i < infos_array.length(); i++) {
					JSONObject infos_obj = infos_array.getJSONObject(i);
					String arguments_key = infos_obj.getString("key");
					String arguments_value = infos_obj.getString("value");
					Infos infos = new Infos(arguments_key, arguments_value);
					info_list.add(infos);
				}
				ItemDetailEntity itemDetailEntity = new ItemDetailEntity(type,
						guide_type, arguments_title, info_list);
				list.add(itemDetailEntity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 解析美味详情底部猜你喜欢数据
	 * 
	 * @param ZL
	 * @return
	 */
	public static List<ItemListEntity> parseItemDetailGuessLoveJson(
			JSONObject response) {
		List<ItemListEntity> list = new ArrayList<ItemListEntity>();
		String code;
		try {
			code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject data_obj = response.getJSONObject("data");
				JSONArray array = data_obj.getJSONArray("guess_love");
				for (int i = 0; i < array.length(); i++) {
					JSONObject sub_obj = array.getJSONObject(i);
					int id = sub_obj.getInt("id");
					String title = sub_obj.getString("title");

					int type = sub_obj.getInt("type");
					int guide_type = sub_obj.getInt("guide_type");

					int sold_num = sub_obj.getInt("sold_num");
					JSONObject img_obj = sub_obj.getJSONObject("img");
					String img_url = img_obj.getString("img_url");
					JSONObject price_obj = sub_obj.getJSONObject("price");
					double current = price_obj.getDouble("current");
					double prime = price_obj.getDouble("prime");
					JSONObject tag_obj = sub_obj.getJSONObject("tag");
					String tag_title = tag_obj.getString("title");
					int fav_num = sub_obj.getInt("fav_num");
					String desc = sub_obj.getString("desc");
					long time = sub_obj.getLong("time");
					ItemListEntity itemListEntity = new ItemListEntity(id,
							title, sold_num, img_url, current, prime,
							tag_title, fav_num, desc, time, type, guide_type);
					list.add(itemListEntity);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 解析美味详情更多评论布局
	 * 
	 * @param ZL
	 * @return
	 */
	public static List<ItemDetailEntity> parseItemDetailMoreCommentsJson(
			JSONObject response) {
		List<ItemDetailEntity> list = new ArrayList<ItemDetailEntity>();
		try {
			String code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject sub_obj = response.getJSONObject("data");
				int total_num = sub_obj.getInt("total_num");

				JSONArray comments_array = sub_obj.getJSONArray("comments");
				List<CommentsEntity> list_Comments = new ArrayList<CommentsEntity>();
				for (int i = 0; i < comments_array.length(); i++) {
					JSONObject obj = comments_array.getJSONObject(i);
					int comments_id = obj.getInt("id");
					String comments_img_url = obj.getString("avatar");
					String nickname = obj.getString("nickname");
					String content = obj.getString("content");

					CommentsEntity comments = new CommentsEntity(comments_id,
							comments_img_url, nickname, content);
					list_Comments.add(comments);
				}
				ItemDetailEntity itemDetailEntity = new ItemDetailEntity(
						total_num, list_Comments);
				list.add(itemDetailEntity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 解析打折商品列表
	 * 
	 * @param ZL
	 * @return
	 */
	public static List<ItemListEntity> parseDiscountItemListJson(
			JSONObject response) {
		List<ItemListEntity> list = new ArrayList<ItemListEntity>();
		String code;
		try {
			code = response.getString("rs_code");
			if (code.equals("1000")) {
				JSONObject data_obj = response.getJSONObject("data");
				JSONArray array = data_obj.getJSONArray("items");
				for (int i = 0; i < array.length(); i++) {
					JSONObject sub_obj = array.getJSONObject(i);
					int id = sub_obj.getInt("id");
					String title = sub_obj.getString("title");
					int status = sub_obj.getInt("status");
					int sold_num = sub_obj.getInt("sold_num");
					JSONObject img_obj = sub_obj.getJSONObject("img");
					String img_url = img_obj.getString("img_url");
					JSONObject price_obj = sub_obj.getJSONObject("price");
					double current = price_obj.getDouble("current");
					double prime = price_obj.getDouble("prime");
					JSONObject tag_obj = sub_obj.getJSONObject("tag");
					String tag_title = tag_obj.getString("title");
					int fav_num = sub_obj.getInt("fav_num");
					long time = sub_obj.getLong("time");
					ItemListEntity itemListEntity = new ItemListEntity(id,
							title, sold_num, img_url, current, prime,
							tag_title, fav_num, time, status);
					list.add(itemListEntity);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}