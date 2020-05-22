
function main(data){
	
	Nt_Control.Table([{"name":"kege","age":22},{"name":"kege","age":22}]);

}

function beforeSend (send){
	
	
	return send;
	
}
function afterReceive (send,recv){

	Nt_Ptr.println(recv)
	Nt_Control.Browser(recv,false);
	return recv;
	
	
}


