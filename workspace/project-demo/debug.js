
function main(arg){
	
	
	$Asyn.run(function(){
		$Db.connect("jdbc:mysql://192.168.1.100:3306/test","com.mysql.jdbc.Driver","root","142758");
		$Db.query("select * from student",function(data){
			$Ptr.println($Json.stringify(data))
		})
		
	})
	return null;
}
