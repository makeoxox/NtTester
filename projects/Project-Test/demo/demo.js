//调试脚本模板（入口函数只能为main）

 Nt_Import("Project-Test/js/area.js")

function main (arg){
	
	var area = getArea("湖北省武汉市江夏区文化大道110号");
	area = Nt_Json.stringify(area);
	Nt_Ptr.println(area);
	return arg;
	
}
