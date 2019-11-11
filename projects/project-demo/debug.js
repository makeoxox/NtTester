
Nt_Import("projects/project-demo/js/sm4.js")

function main(arg){
		
	Nt_Db.connect("jdbc:mysql://192.168.1.100:3306/test","com.mysql.jdbc.Driver","root","142758");
        var data = Nt_Db.query("select * from student");
        var odata = Nt_Json.stringify(data);
	Nt_Ptr.println(odata);
	var sm4 = new SM4Util();
	sm4.secretKey="AABBCCDDEEFFGGHH";
	sm4.iv="IIJJKKLLMMNNOOPP";
	var ndata =sm4.encryptData_CBC(odata);
	Nt_Ptr.println(ndata);
	  
}
/**

Nt_Asyn.run(function(){
	
})
*/
