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
			<h3>接口名：${file.interfaceName} 接口类型：${file.interfaceType}
				接口方向：${file.interfaceVector}</h3>
		</div>
		<br />
		<div style="text-align: center;">
			<table width="85%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<th width='100px'>序号</th>
					<th>域名</th>
					<th>域字典</th>
					<th>上级域字典</th>
					<th>必输</th>
					<th>长度</th>
					<th>备注</th>
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