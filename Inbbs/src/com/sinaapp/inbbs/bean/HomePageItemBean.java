package com.sinaapp.inbbs.bean;


public class HomePageItemBean {
	/*
		 "creattime": "1441335686",
		 "id": "1",
		 "image": "562dd0198f565.jpg",
		 "lasttime": "1451042161",
		 "node": "开发动态",
		 "nodeid": "5",
		 "review": "11",
		 "title": "NNOW轻论坛程序v0.2.0［测试版］",
		 "userid": "1",
		 "username": "XCAI"
	 */
	/**	发布时间 */
	public long creattime;
	/**	文章ID */
	public int id;
	/**	用户头像 */
	public String image;
	/**	最后回复时间 */
	public String lasttime;
	/**	文章所在板块 */
	public String node;
	/**	板块ID */
	public int nodeid;
	/**	回复数目 */
	public int review;
	/**	文章名称 */
	public String title;
	/**	用户ID */
	public int userid;
	/**	用户名 */
	public String username;
	@Override
	public String toString() {
		return "HomePageItemBean [creattime=" + creattime + ", id=" + id + ", image=" + image + ", lasttime="
				+ lasttime + ", node=" + node + ", nodeid=" + nodeid + ", review=" + review + ", title=" + title
				+ ", userid=" + userid + ", username=" + username + "]";
	}
	
	
	
	
	
}
