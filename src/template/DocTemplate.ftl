<!DOCTYPE html>
<html>
<head>
<meta charset="GBK">
<title>Insert title here</title>
</head>
<body>
	<div style="text-align: center;">
		<h1>${projectName}</h1>
	</div>
	<br />
	<#list files as file>
		<div style="text-align: left; margin-left: 100;">
			<h3>�ӿ�����${file.interfaceName} �ӿ����ͣ�${file.interfaceType}
				�ӿڷ���${file.interfaceVector}</h3>
		</div>
		<br />
		<div style="text-align: center;">
			<table width="85%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<th width='100px'>���</th>
					<th>����</th>
					<th>���ֵ�</th>
					<th>�ϼ����ֵ�</th>
					<th>����</th>
					<th>����</th>
					<th>��ע</th>
				</tr>
				<#list file.props as prop>
					<tr style="text-align: center;">
						<td>${prop_index}</td>
						<td>${prop.field}</td>
						<td>${prop.fieldDict}</td>
						<td>${prop.superFieldDict}</td>
						<td>${prop.isMustFill}</td>
						<td>${prop.length}</td>
						<td>${prop.remark}</td>
					</tr>
				</#list>
			</table>
		</div>
	</#list>

</body>
</html>