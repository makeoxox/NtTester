
Nt_Import("projects/project-demo/js/sm4.js")
Nt_Import("projects/project-demo/js/md5.js")

function main(arg){
	
	var data = [{name:"kege",age:25},
		    {name:"egek",age:24},
		    {name:"kgee",age:26},
		    {name:"keeg",age:27}];
	
	Nt_Control.Table(data);
	  
}

/**

Nt_Asyn.run(function(){

	
	var sm4 = new SM4Util();
	
	sm4.secretKey="AABBCCDDEEFFGGHH";
	
	var ndata =sm4.encryptData_ECB(arg);
	
	Nt_Ptr.println("---------");
	Nt_Ptr.println(ndata);
	Nt_Ptr.println("---------");
	
})
*/

