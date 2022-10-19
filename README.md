# database
数据库文档生成
表名	blob
列名	中文说明	数据类型（精度范围）	空/非空	约束条件
appreciation	赞赏是否开启（0：未开启；1：开启）	int(10)	Y	
commentable	评论（0：未开启；1：开启）	int(10)	Y	
content	内容	text	Y	
createTime		datetime	Y	
firstPicture	图片地址	varchar(50)	Y	
id	主键	int(10)		
key		text	Y	
num	随机码	varchar(255)	Y	
property	属性（原创，转载，翻译）	varchar(20)	Y	
published	发布（0：未开启；1：开启）	varchar(10)	Y	
recommend	推荐（0：未开启；1：开启）	int(10)	Y	
shareStatement	转载声明是否开启（0：未开启；1：开启）	int(10)	Y	
title	标题	varchar(255)	Y	
type	博客类型	varchar(20)	Y	
updateTime		datetime	Y	
views	浏览次数	int(10)	Y	
备注	

5.02 表名:
表名	blob_friend
列名	中文说明	数据类型（精度范围）	空/非空	约束条件
create_time		datetime	Y	
flag	1，添加，0，没添加	varchar(10)	Y	
friend_description		varchar(100)	Y	
friend_img_link		varchar(100)	Y	
friend_link		varchar(100)	Y	
friend_name		varchar(100)	Y	
id		int(11)		
备注	

5.03 表名:
表名	blob_message
列名	中文说明	数据类型（精度范围）	空/非空	约束条件
blob_like_count	博客喜欢量	int(100)	Y	
blob_run_time	运行天数	int(100)	Y	
blob_title		varchar(100)	Y	
blob_view_count	博客浏览量	int(100)	Y	
id		int(11)		
备注	

5.04 表名:
表名	blob_tag_link
列名	中文说明	数据类型（精度范围）	空/非空	约束条件
blob_id	博客id	varchar(10)	Y	
id		int(11)		
tag_name	标签id	varchar(20)	Y	
备注	

5.05 表名:
表名	blob_user
列名	中文说明	数据类型（精度范围）	空/非空	约束条件
blob_id	博客id	varchar(11)	Y	
id		int(11)		
user_id	用户id	varchar(11)	Y	
备注	
