package com.qf.lingshixiaomaio.util;

public interface Constants {

	public interface URL {
		// 首页顶部——好吃到爆接口
		public static final String URL_HOMEPAGE_HEAD = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151027233240&sig=40c42c9da4f28b0e&wssig=34a35a9883b976f1&os_type=3&version=13&channel_name=baidu&srv=2201";
		// 首页下拉数据接口
		public static final String URL_HOMEPAGE_DROPDOWN = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151028020355&sig=114cae8a552b5309&wssig=b6e270550cdbf3a2&os_type=3&version=13&channel_name=baidu&srv=2206&since_id=0&pg_cur=%d&pg_size=%d";
		// 首页ViewPager商品列表
		public static final String URL_HOME_VIEWPAGE_ITEM_LIST = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151027235339&sig=965e6b422e82b0e2&wssig=fe9b88014010e5a3&os_type=3&version=13&channel_name=baidu&srv=2407&pg_cur=%d&pg_size=20&subject_id=%s&since_id=0";
		// 首页tabView商品列表
		public static final String URL_HOME_TABVIEW_ITEM_LIST = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151027234857&sig=f46c38ded977b70c&wssig=f09c8a36e0c6d72d&os_type=3&version=13&channel_name=baidu&srv=2406&pg_cur=%d&pg_size=20&sub_id=%d&parent_id=%d&since_id=0";
		// 首页tabView商品列表选择器
		public static final String URL_HOME_TABVIEW_ITEM_SELECT = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151028064805&sig=33292d9e9313cd21&wssig=257caa907f20d866&os_type=3&version=13&channel_name=baidu&srv=2402&classify_id=%d";
		// 首页中间brandsView商品列表
		public static final String URL_HOME_BRANDSVIEW_ITEM_LIST = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151027235631&sig=71c2afa4b67e39c2&wssig=533b2112e9c288ea&os_type=3&version=13&channel_name=baidu&srv=2205&brand_id=%d&pg_cur=%d&pg_size=20&since_id=0";
		// 首页brandsSpecialView的商品列表
		public static final String URL_HOME_BRANDSPECIALVIEW_ITEM_LIST = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151028015900&sig=f6f2936467a9d9f1&wssig=3449935ab9f8a1ef&os_type=3&version=13&channel_name=baidu&srv=2407&pg_cur=%d&pg_size=20&subject_id=%s&since_id=0";
		// 特卖首页页面
		public static final String URL_SALE = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151028070646&sig=7e47ff2066b5a954&wssig=8dbc7c3a7fbed164&os_type=3&version=13&channel_name=baidu&srv=2301&since_id=0&pg_cur=%d&pg_size=20";

		// 专题
		public static final String SUBJECT_TOP = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151028071107&sig=a23fc4c3108c1540&wssig=149c1f90738dcdee&os_type=3&version=13&channel_name=baidu&srv=2401";

		// 专题内容
		public static final String SUBJECT_INFO = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=92648&tms=20151029022756&sig=0cc26c69cb79b855&wssig=e97bc1bbbc360ea2&os_type=3&version=13&channel_name=baidu&srv=2405&pg_cur=%d&pg_size=20&since_id=0";
		// 专题详情
		public static final String SUBJECT_DETAIL = "http://ds.lingshi.cccwei.com/api.php?apptype=0&srv=2404&cid=10002&uid=0&tms=20150721190147&sig=8c35f5a024148111&wssig=308efe4382a088e0&os_type=1&version=7&subject_id=%d";
		public static final String SUBJECT_DETAIL_WEB = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=0&tms=20151106080916&sig=3d1bbd97043f30f0&wssig=1befa315924d34c2&os_type=3&version=13&channel_name=baidu&srv=2408&subject_id=%d";
		// 专题列表
		public static final String SUBJECT_LIST = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=92648&tms=20151101040732&sig=8aa2c6a591e90154&wssig=ee806adda127d7d7&os_type=3&version=13&channel_name=baidu&srv=2403&pg_cur=1&pg_size=20&special_id=%d&since_id=0";

		// 搜索
		public static final String SEARCH = "http://api.ds.lingshi.cccwei.com/?cid=1792&uid=92648&tms=20151028084030&sig=dc887df14e06be7e&wssig=7be56ab53d235cfe&os_type=3&version=13&channel_name=baidu&srv=2204&since_id=0&pg_cur=1&pg_size=20&keyword=%s";

		// 美味详情页
		public static final String URL_ITEM_DETIALS = "http://ds.lingshi.cccwei.com/api.php?apptype=0&srv=2502&cid=10002&uid=0&tms=20150713151836&sig=e9f75a3bdae950ea&wssig=9de6f856100a21ab&os_type=3&version=13&opt=1&add_id=8&goods_id=%d";

		// 美味详情里面更多评论的页面
		public static final String URL_MORE_COMMENTS = "http://ds.lingshi.cccwei.com/api.php?apptype=0&srv=2503&cid=10002&uid=0&tms=20150721190147&sig=8c35f5a024148111&wssig=308efe4382a088e0&os_type=1&goods_id=%d&version=7&pg_cur=1&pg_size=20";

		// 优惠的商品列表
		public static final String URL_DISCOUNT_ITEM_LIST = "http://api.ds.lingshi.cccwei.com/?cid=473492&uid=0&tms=20151106155425&sig=864169ce19f00c7c&wssig=f45521c680ea0ed3&os_type=3&version=13&channel_name=baidu&srv=2909&supplier_id=8&since_id=0&pg_cur=%d&pg_size=20";
	}
}
