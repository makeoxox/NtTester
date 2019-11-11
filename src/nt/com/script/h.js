
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

//导入函数
function Nt_Import(js){
	try{
		AbstractMessageScriptManager.engine.eval(new InputStreamReader(new FileInputStream(new File(js))));
	}catch(e){
		Nt_Ptr.println(e)
	}
}

// 异步对象
var Nt_Asyn = {
	run : function(func){
		new Thread(new Runnable(){
			run : function(){
				try{
					func();
				}	catch (e){
					Nt_Ptr.println(e);
				}
			}
		}).start();
	}
}

// 打印对象
var Nt_Ptr = {
	println : function(content){
		ConsoleTextArea.AppendMessageOnCurrentConsole(content);
	},
	print : function(content){
		ConsoleTextArea.PrintMessageOnCurrentConsole(content);
	}
}

// 网络对象


// 数据库对象
 var Nt_Db = {
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
			 Nt_Ptr.println(e)
		 }
			
	},
	query : function (sql,success){
		 if(this.jdbc==null){
			 Nt_Ptr.println("未连接数据库")
			 return null;
		 }
		 try{
			 	var list =this.jdbc.queryForList(sql);
			 }catch(e){
				 Nt_Ptr.println(e)
				 return;
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
			Nt_Ptr.println("未连接数据库")
			 return;
		 }
		 try{
			    this.jdbc.update(sql);	
			}catch(e){
				Nt_Ptr.println(e)
			}
			 
	} 
 }
	
 // 数据展现对象
var Nt_Control={
	 Table  : function(title,data){
		Platform.runLater(new Runnable() {
		    run : function(){
		    	try{
		    		
		    	}catch(e){
		    		Nt_Ptr.println(e);
		    	}
		    }
		});
	},
	List : function(title,data){
		Platform.runLater(new Runnable() {
		    run : function(){
		    	try{
		    		
		    	}catch(e){
		    		Nt_Ptr.println(e);
		    	}
		    }
		});
	}
}
 
 
 // Json对象
 var Nt_Json = {
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
 var Nt_File = function(path){
	 
	 this.file=new File(path);
	 
	 this.name= this.file.getName();
	 
	 this.absolutePath= this.file.getAbsolutePath();
	 
 } 
 
 
 Nt_File.prototype.createNewFile = function(){
	 this.file.createNewFile();
 }
 
 Nt_File.prototype.delete = function(){
	 this.file.delete();
 }
 
 Nt_File.prototype.exists = function(){
	 return  this.file.exists();
 }

 Nt_File.prototype.getParent = function(){
	 return  this.file.getParent();
 }

 Nt_File.prototype.isFile = function(){
	 return  this.file.isFile();
 }
 
 Nt_File.prototype.isDirectory = function(){
	 return  this.file.isDirectory();
 }
 
 Nt_File.prototype.listFiles = function(){
	 
		var fileArray =this.file.listFiles();
		var arr = [];
		for each(var f in fileArray){
			arr.push(new $File(f.getAbsolutePath()));
		}
		
	 return	 arr;
 }
 
 Nt_File.prototype.listFileNames = function(){
	 var fileArray =this.file.listFiles();
		var arr = [];
		for each(var f in fileArray){
			arr.push(f.getName());
		}
		
	 return	 arr;
 }
 
 
 Nt_File.prototype.read = function(decode){
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
 
 Nt_File.prototype.write = function(content,encode,overwrite){
		if(encode==null)encode="utf-8";
		if(overwrite==null)overwrite=true;
		var  wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file,overwrite),encode));
		wr.write(content);
		wr.flush();
		wr.close();
 }

 