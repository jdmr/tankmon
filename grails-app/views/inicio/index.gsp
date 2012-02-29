<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap"/>
		<title>Inicio</title>
	</head>

	<body>
        <content tag="nav">
        <ul class="nav">
            <li class="active"><a href="${createLink(uri: '/')}"><g:message code='default.home.label' /></a></li>
            <li><a href="${createLink(uri: '/empresa')}"><g:message code='empresa.list.label' /></a></li>
            <li><a href="${createLink(uri: '/usuario')}"><g:message code='usuario.list.label' /></a></li>
        </ul>
        </content>
		<div class="row-fluid">
			<section id="main" class="span12">

				<div class="hero-unit">
					<h1>Bienvenidos a TANKMON</h1>

					<p>Monitoreo de tanques... lorem ipsum etc, etc.</p>
					
					<p>This is a demo of how to reskin Grails dynamic scaffolding pages. I've
					used The <a href="http://freeside.co/grails-fields">Fields plugin</a> for customizing
					form rendering and the <a href="https://github.com/groovydev/twitter-bootstrap-grails-plugin">Twitter
					Bootstrap Resources plugin</a> to provide the CSS resources. Beyond that it&apos;s a
					bare Grails app using dynamically scaffolded controllers and views.</p>
				</div>
					
				<div class="row-fluid">
					
					<div class="span4">
						<h2>Try It</h2>
						<p>This demo app includes a couple of controllers to show off its features.</p>
						<ul class="nav nav-list">
							<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
								<li><g:link controller="${c.logicalPropertyName}">${c.naturalName}</g:link></li>
							</g:each>
						</ul>
					</div>

					<div class="span4">
						<h2>Install It</h2>
						<p>To install this look &amp; feel into your Grails app you will need to:</p>
						<p>Add the following plugins to your <em>BuildConfig.groovy</em>:</p>
						<pre>runtime ':twitter-bootstrap:${applicationContext.getBean('pluginManager').getGrailsPlugin('twitter-bootstrap').version}'
runtime ':fields:${applicationContext.getBean('pluginManager').getGrailsPlugin('fields').version}'</pre>
						<p>Copy the following files to your project:</p>
						<pre>src/templates/scaffolding/*
web-app/css/scaffolding.css
grails-app/conf/ScaffoldingResources.groovy
grails-app/taglib/**/*
grails-app/views/index.gsp
grails-app/views/layouts/bootstrap.gsp
grails-app/views/_fields/default/_field.gsp</pre>
					</div>
					
					<div class="span4">
						<h2>Fork It</h2>
						<p>You can download, fork &amp; raise issues on this project on <a href="https://github.com/robfletcher/twitter-bootstrap-scaffolding">GitHub</a>.</p>
					</div>

				</div>

			</section>
		</div>
		
		<a href="http://github.com/robfletcher/twitter-bootstrap-scaffolding"><img id="github-ribbon" src="https://a248.e.akamai.net/assets.github.com/img/e6bef7a091f5f3138b8cd40bc3e114258dd68ddf/687474703a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f7265645f6161303030302e706e67" alt="Fork me on GitHub"></a>
	</body>
</html>
