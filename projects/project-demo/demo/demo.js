
 Nt_Import("Project-Test/js/area.js")

function main(arg){
	
	
}

function beforeSend (send){
	
	
	return send;
	
}
function afterReceive (send,recv){

	Nt_Ptr.println(recv)
	Nt_Control.Browser("http://www.baidu.com");
	return recv;
	
	
}


