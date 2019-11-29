//调试脚本模板（入口函数只能为main）

function main (arg){
	
	var file = new Nt_File("C:/Users/Administrator/Desktop/demo.xml");
	var xmlStr = file.read("gbk");
	Nt_Xml.parse(xmlStr,"gbk");
	var base64 =Nt_Xml.document.getRootElement().element("result").element("root").element("info").element("photo").getText();
	Nt_Control.Graph(base64);
	
	return arg;
	
}
