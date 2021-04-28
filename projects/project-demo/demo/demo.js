
 Nt_Import("Project-Test/js/area.js")

function main(arg){

	Nt_Db.connect("jdbc:mysql://101.231.192.13:8867/jxyh","com.mysql.jdbc.Driver","jxbf_ams","jxbf_ams");
	
	var data = Nt_Db.query("select * from t_plan_resource_plan_relate  ");
	
	Nt_Control.Table(data,"资源管理岗计划关联表");
	
}

function beforeSend (send){
	
	
	return send;
	
}
function afterReceive (send,recv){

	Nt_Ptr.println(recv)
	Nt_Control.Browser("http://www.baidu.com");
	return recv;
}


