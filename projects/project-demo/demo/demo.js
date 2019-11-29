
function beforeSend (send){
	
	
	return send;
	
}
function afterReceive (send,recv){

	
	Nt_Ptr.println(send)
	
	var data =Nt_Json.parse(recv)
	
	var base64 = data.result.base64_image;
	
	Nt_Control.Graph(base64);

	return data;
	
	
}


