<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">
	var centerTabs;
	var tabsMenu;
	$(function() {
		
		centerTabs = $('#centerTabs').tabs({
			fit : true,
			border : false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			}
		});
		
		tabsMenu = $('#tabsMenu').menu({
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('type');

				if (type === 'refresh') {
					refreshTab(curTabTitle);
					return;
				}

				if (type === 'close') {
					var t = centerTabs.tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						centerTabs.tabs('close', curTabTitle);
					}
					return;
				}

				var allTabs = centerTabs.tabs('tabs');
				var closeTabsTitle = [];

				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
						closeTabsTitle.push(opt.title);
					} else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});

				for ( var i = 0; i < closeTabsTitle.length; i++) {
					centerTabs.tabs('close', closeTabsTitle[i]);
				}
			}
		});
		
	});
	
	function addTab(node) {
		if (centerTabs.tabs('exists', node.text)) {
			centerTabs.tabs('select', node.text);
		} else {
			if (node.attributes.url && node.attributes.url.length > 0) {			
				centerTabs.tabs('add', {
					title : node.text,
					closable : true,
					iconCls : node.iconCls,
					content : '<iframe src="${pageContext.request.contextPath}' + node.attributes.url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(node.text);
						}
					} ]
				});
			} 
		}
	}
	
	function refreshTab(title) {
		var tab = centerTabs.tabs('getTab', title);
		centerTabs.tabs('update', {
			tab : tab,
			options : tab.panel('options')
		});
	}
</script>
<div id="centerTabs">
	<div title="Home" data-options="border:false" style="overflow: hidden;">
		<div style="margin-left:10px;margin-top:5px;line-height:20px;"><strong style="color:blue;">Frameworks and Tools</strong>
			<ul style="list-style:none;margin-left:-20px;margin-top:0px;">
				<li>Java&IDE: JDK6 MyEclipse2013</li>
				<li>Backend:  SpringMVC3.2.8 Spring3.2.8 Hibernate4.2.11 (Configured by annotation)</li>
				<li>Frontend: JQuery-EasyUI1.3.1 Javascript JQuery Ajax (Client-side and server-side communicated by JSON)</li>
				<li>Database: Oracle10g</li>
				<li>Web Server: Tomcat 7</li>
				<li>Build Tool: Maven</li>
				<li>Linux：CentoOS 6.4</li>
				<li>Other: Druid JUnit Log4j Jackson FastJson JFreeChart POI</li>
			</ul>
		</div>
		<hr/>
		<div style="margin-left:10px;margin-top:0px;line-height:20px;"><strong style="color:blue;">System Features</strong>
			<ul style="list-style:none;margin-left:-20px;margin-top:0px;">	
				<li>Migrate the application of Struts2.3+Spring3+Hibernate4 from Struts2 to SpringMVC</li>
				<li>Convert database connection pool from C3P0 to Druid</li>
				<br/>
				<li>Management of user, role, authority, equipment and document</li>		
				<li>Export equipment list to Excel file using POI</li>
				<li>Chart display of user statistics using JFreeChart</li>
				<li>User access logs based on Spring AOP</li>
			</ul>
		</div>
		<hr/>
		<div style="margin-left:10px;margin-top:0px;line-height:20px;"><strong style="color:blue;">About</strong>
			<ul style="list-style:none;margin-left:-20px;margin-top:0px;">
				<li><a href='https://github.com/ZhibingXie'>Github link</a></li>
				<li><a href='http://ca.linkedin.com/in/zhibingxie'>Linkedin link</a></li>
			</ul>
		</div>
		<hr/>
		<div style="margin-left:10px;margin-top:0px;line-height:20px;"><strong style="color:blue;">Contact</strong>
			<ul style="list-style:none;margin-left:-20px;margin-top:0px;">
				<li>Zhibing Xie (Downtown, Toronto, Ontario, Canada)</li>
				<li>Email: zhibing.x@gmail.com</li>
			</ul>
		</div>
		<hr/>	
	</div>
</div>
<div id="tabsMenu" style="width: 120px;display:none;">
	<div type="refresh">Refresh</div>
	<div class="menu-sep"></div>
	<div type="close">Close</div>
	<div type="closeOther">Close other</div>
	<div type="closeAll">Close all</div>
</div>