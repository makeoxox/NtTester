 Nt_Db.connect("jdbc:mysql://192.168.1.100:3306/test","com.mysql.jdbc.Driver","root","142758");
           Nt_Db.query("select * from student",function(data){
           	var odata = Nt_Json.stringify(data);
		Nt_Ptr.println(odata);
		var ndata = str_md5(odata)
		Nt_Ptr.println(ndata);
	   })
	