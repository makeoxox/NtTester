
 Nt_Import("Project-Test/js/area.js")

function main(data){
	

}

function beforeSend (send){
	
	
	return send;
	
}
function afterReceive (send,recv){

	Nt_Ptr.println(recv)
	Nt_Control.Browser(recv,false);
	return recv;
	
	
}


