
load("nashorn:mozilla_compat.js");

importPackage("nt.com.script")
importPackage("nt.com.util")
importPackage("nt.com.view.init")
importPackage("nt.com.script");
importPackage("org.apache.commons.dbcp");
importPackage("org.springframework.jdbc.core");
importPackage("java.lang");
importPackage("java.util");
importPackage("java.util.concurrent")
importPackage("javax.sql");
importPackage("java.net");
importPackage("java.io");
importPackage("nt.com.view.init");
importPackage("javafx.application");


// 异步对象
var $Asyn = {
	run : function(func){
		new Thread(new Runnable(){
			run : function(){
				func();
			}
		}).start();
	}
}

// 打印对象
var $Ptr = {
	println : function(content){
		ConsoleTextArea.AppendMessageOnCurrentConsole(content);
	},
	print : function(content){
		ConsoleTextArea.PrintMessageOnCurrentConsole(content);
	}
}

// 网络对象


// 数据库对象
 var $Db = {
	 jdbc : null,
	 connect : function(url,driver,username,password){
		 try{
			 var prop = new Properties();
				prop.setProperty("url", url);
				prop.setProperty("driverClassName", driver);
				prop.setProperty("username", username);
				prop.setProperty("password", password);
				var bs = BasicDataSourceFactory.createDataSource(prop);
				this.jdbc =new JdbcTemplate(bs);
		 }catch(e){
			 ConsoleTextArea.AppendMessageOnCurrentConsole(e)
		 }
			
	},
	query : function (sql,success){
		 if(this.jdbc==null){
			 ConsoleTextArea.AppendMessageOnCurrentConsole("未连接数据库");
			 return null;
		 }
		 try{
			 	var list =this.jdbc.queryForList(sql);
			 }catch(e){
				 ConsoleTextArea.AppendMessageOnCurrentConsole(e)
			 }
			var josnStr= JsonParser.arrayStringify(list)
			var nativeArray = JSON.parse(josnStr)
			if(success!=null){
				success(nativeArray);
			}	
			return nativeArray;
	},
	execute : function (sql){
		if(this.jdbc==null){
			 ConsoleTextArea.AppendMessageOnCurrentConsole("未连接数据库")
			 return;
		 }
		 try{
			    this.jdbc.update(sql);	
			}catch(e){
				ConsoleTextArea.AppendMessageOnCurrentConsole(e)
			}
			 
	} 
 }
	
 // 数据展现对象
var $Control={
	 Table  : function(title,data){
		Platform.runLater(new Runnable() {
		    run : function(){
		    	
		    }
		});
	},
	List : function(title,data){
		Platform.runLater(new Runnable() {
		    run : function(){
		    	
		    }
		});
	}
}
 
 
 // Json对象
 var $Json = {
	 parse:function(jsonStr){
		return JSON.parse(jsonStr) ;
	 },
	 stringify:function(Obj){
		return JSON.stringify(Obj) ;
	 },
	 json2xml:function(jsonStr,root){
		return JsonParser.JsonToXml(jsonStr,root);
	 },
	 xml2json:function(xmlStr){
		return JsonParser.XmlToJson(xmlStr);
	 }
 }
 
 // 文件操作类
 var $File = function(path){
	 
	 this.file=new File(path);
	 
	 this.name= this.file.getName();
	 
	 this.absolutePath= this.file.getAbsolutePath();
	 
 } 
 
 
 $File.prototype.createNewFile = function(){
	 this.file.createNewFile();
 }
 
 $File.prototype.delete = function(){
	 this.file.delete();
 }
 
 $File.prototype.exists = function(){
	 return  this.file.exists();
 }

 $File.prototype.getParent = function(){
	 return  this.file.getParent();
 }

 $File.prototype.isFile = function(){
	 return  this.file.isFile();
 }
 
 $File.prototype.isDirectory = function(){
	 return  this.file.isDirectory();
 }
 
 $File.prototype.listFiles = function(){
	 
		var fileArray =this.file.listFiles();
		var arr = [];
		for each(var f in fileArray){
			arr.push(new $File(f.getAbsolutePath()));
		}
		
	 return	 arr;
 }
 
 $File.prototype.listFileNames = function(){
	 var fileArray =this.file.listFiles();
		var arr = [];
		for each(var f in fileArray){
			arr.push(f.getName());
		}
		
	 return	 arr;
 }
 
 
 $File.prototype.read = function(decode){
		if(decode==null)decode="utf-8";
		var  br = new BufferedReader(new InputStreamReader(new FileInputStream(this.file),decode));
		var line = "";
		var content  = "";
		while((line = br.readLine())!=null){
			content += line+"\n";
		}
		br.close();
	 return	 content.substring(0,content.length-1);
 }
 
 $File.prototype.write = function(content,encode,overwrite){
		if(encode==null)encode="utf-8";
		if(overwrite==null)overwrite=true;
		var  wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file,overwrite),encode));
		wr.write(content);
		wr.flush();
		wr.close();
 }

 