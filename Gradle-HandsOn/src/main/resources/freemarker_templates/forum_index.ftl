<!DOCTYPE HTML>
<html lang="en">
<head>
  <title>SimpleForum <#if postsResource??>Posts Resource<#else>Index Resource</#if></title>
</head>
<body>
	<table height="80" border="0" width="100%">
	
	<tr><td align="left" valign="middle" height="80"><a href="https://www.tugraz.at/en/institutes/iti/home/">
	<font size="1">TU Graz Institute of Technical Informatics | Smart Services Course, Summer 2018</font><br/>
	<img src="/webresources/images/tugraz-logo.gif" width="100" alt="TU Graz Institute of Technical Informatics">	
	</a></td>
	
	<td align="right" valign="middle" height="80"><a href="http://www.pro2future.at/start-en/">
	<font size="1">Pro2Future | Cognitive Products</font><br/>
	<img src="/webresources/images/pro2future-logo.png" width="100" alt="Pro2Future GmbH">	
	</a></td></tr>
	
	</table>
	
	<table width="80%" align="center" border="0">
		
		<tr height="40" width="100%"/>
	
		<tr>
			<td align="center" height="80" colspan="10">
				<font size="6">This is <b>SimpleForum<#if postsResource??>/posts</#if></b>!</font><br/>
				
				<font size="4">
				<#if postsResource??>
					Showing all posts... go <a href="/">back</a> to index.
				<#else>
					Showing 5 newest posts... go to <a href="/posts">/posts</a> to see all of them!
				</#if>
				</font><br/>
								
				<font size="1">Number of Posts: ${postsCount}</font></br>	
				<font size="1">Server Time: ${serverTime}</font><br/>
				<!-- <font size="1"><a href="webresources/apidoc/">API Documentation</a></font> -->
			</td>
		</tr>
		
		<tr height="50" width="100%"/>
				
		<#if latestPosts??>
			<tr width="100%" align="center"><td>

			<table border="0">
			<#list latestPosts as post>
				<tr align="center">
					<td><b>${post.author}</b>: ${post.message} <font size="1">(${post.pubdate})</font></td>
				</tr>
			</#list>
			</table>
			
			</td></tr>
		<#else>
			<tr width="100%" align="center"><td>No posts received yet...</td></tr>
		</#if>		
		
	</table>
</body>
</html>
