
load("nashorn:mozilla_compat.js");

importPackage("nt.com.util")
importPackage("nt.com.view.init")
importPackage("nt.com.script.javascript");
importPackage("nt.com.script.javascript.view")
importPackage("nt.com.network")
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

// ���뺯�� ,��Ŀ��·��
function Nt_Import(js){
	try{
		AbstractMessageScriptManager.engine.eval(new InputStreamReader(new FileInputStream(new File("projects/"+js))));
	}catch(e){
		Nt_Ptr.println(e)
	}
}

// �첽����
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



// �����ӡ����
var Nt_Ptr = {
	println : function(content){
		ConsoleTextArea.AppendMessageOnCurrentConsole(content);
	},
	print : function(content){
		ConsoleTextArea.PrintMessageOnCurrentConsole(content);
	}
}

// �������
var Nt_Net ={
		tcp : {
			send : function(msg,ip,port){
				try{
					var bts = new BioTcpSingle();
					return bts.send(msg,ip,port);
				 }catch(e){
					 Nt_Ptr.println(e)
				 }
			},
			sendNoRecv: function(msg,ip,port){
				try{
					var bts = new BioTcpSingle();
					bts.sendNoRecv(msg,ip,port);
				 }catch(e){
					 Nt_Ptr.println(e)
				 }
			}
		},
		http: {
			post : function(url,msg,code,contentType){
				if(code==null)code="utf-8";
				if(contentType==null)contentType="text/plain"
				try{
					var hs = new HttpSingle();
					return hs.sendByPost(new URL(url),msg,code,contentType);
				 }catch(e){
					 Nt_Ptr.println(e)
				 }
			},
			get : function(url,code){
				if(code==null)code="utf-8";
				try{
					var hs = new HttpSingle();
					return hs.sendByGet(new URL(url),code);
				 }catch(e){
					 Nt_Ptr.println(e)
				 }
			}
		}
}


// ���ݿ����
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
			 Nt_Ptr.println("δ�������ݿ�")
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
			Nt_Ptr.println("δ�������ݿ�")
			 return;
		 }
		 try{
			    this.jdbc.update(sql);	
			}catch(e){
				Nt_Ptr.println(e)
			}
			 
	} 
 }
	
 // ����չ�ֶ���
var Nt_Control={
	 Table  : function(NativeArray,title){
		Platform.runLater(new Runnable() {
		    run : function(){
		    	try{
		    		var list = new ArrayList();
		    		for(i=0;i<NativeArray.length;i++){
		    			var map = NativeArray[i];
		    			var dataMap = new HashMap();
		    			for(key in map){
		    				dataMap.put(key,map[key]);
		    			}
		    			list.add(dataMap);
		    		}
		    		if(title==null)title="table"
		    		new Table(list,title);
		    	}catch(e){
		    		Nt_Ptr.println(e);
		    	}
		    }
		});
	},
	Graph : function(Base64Img,title){
		Platform.runLater(new Runnable() {
		    run : function(){
		    	try{
		    		if(title==null)title="graph"
		    		new Graph(Base64Img,title)
		    	}catch(e){
		    		Nt_Ptr.println(e);
		    	}
		    }
		});
	},
	Browser : function(content,contentType,title){
		Platform.runLater(new Runnable() {
		    run : function(){
		    	try{
		    		if(title==null)title="";
		    		if(contentType==undefined)contentType=null;
		    		new Browser(content,contentType,title);
		    	}catch(e){
		    		Nt_Ptr.println(e);
		    	}
		    }
		});
	}
}
 
 
 // Json����
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

// Xml����ʹ�ò��ѣ�����
/*var Nt_Xml = {
		document : null,
		parse : function(xmlStr,encode){
			this.document = XmlParser.getDocByString(xmlStr,encode);
		},
		format : function(xmlStr,encode){
		 return 	XmlParser.convertFormatXMLStr(xmlStr,encode);
		}
}*/
 
 // �ļ�������
 var Nt_File = function(path){
	 
	try{
		this.file=new File(path);
		 
		 this.name= this.file.getName();
		 
		 this.absolutePath= this.file.getAbsolutePath();
		
	}catch(e){
		Nt_Ptr.println(e);
	}
	 
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
	 
	 try{
		 	var fileArray =this.file.listFiles();
			var arr = [];
			for ( f in fileArray ){
				arr.push(new Nt_File(fileArray[f].getAbsolutePath()));
			}
		 return	 arr;
			
		}catch(e){
			Nt_Ptr.println(e);
		}
		
 }
 
 Nt_File.prototype.listFileNames = function(){
	 
	 try{
		 	var fileArray =this.file.listFiles();
			var arr = [];
			for ( f in fileArray){
				arr.push(fileArray[f].getName());
			}
		 return	 arr;
			
		}catch(e){
			Nt_Ptr.println(e);
		}
 }
 
 
 Nt_File.prototype.read = function(decode){
	 
	 try{
		 if(decode==null)decode="utf-8";
		 var content =Utils.ReadFiletoString(this.file,decode);
		 return	 content.substring(0,content.length-1);
		}catch(e){
			Nt_Ptr.println(e);
		}
		
 }
 
 Nt_File.prototype.write = function(content,encode,overwrite){
	 
	 try{
		if(encode==null)encode="utf-8";
		if(overwrite==null)overwrite=true;
		Utils.WriteStringtoFile(content,overwrite,this.file,encode);
		}catch(e){
			Nt_Ptr.println(e);
		}
		
 }

 