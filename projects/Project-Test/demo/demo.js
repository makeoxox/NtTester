//���Խű�ģ�壨��ں���ֻ��Ϊmain��

 Nt_Import("Project-Test/js/area.js")

function main (arg){
	
	var area = getArea("����ʡ�人�н������Ļ����110��");
	area = Nt_Json.stringify(area);
	Nt_Ptr.println(area);
	return arg;
	
}
