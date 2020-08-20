
 Nt_Import("Project-Test/js/area.js")

function main(arg){
	
	Nt_Control.Table([{name:'kege',gender:'1'},{name:'wuyihong',gender:'2'}],"Ô±¹¤±í");
}

function beforeSend (send){
	
	
	return send;
	
}
function afterReceive (send,recv){

	Nt_Ptr.println(recv)
	Nt_Control.Browser("http://www.baidu.com");
	return recv;
	
	
}


