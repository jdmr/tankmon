grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
        mavenCentral()

        // uncomment these to enable remote dependency resolution from public Maven repositories
        //mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        runtime 'mysql:mysql-connector-java:5.1.16'
        runtime 'postgresql:postgresql:9.0-801.jdbc4'
        runtime 'net.sf.opencsv:opencsv:2.1'
        compile "org.jadira.usertype:usertype.jodatime:1.9"
        compile "org.springframework.security:spring-security-crypto:3.1.0.RELEASE"
    }

    plugins {
        compile ":hibernate:$grailsVersion"
        compile ":joda-time:1.3.1"
        compile ":jquery:1.7.1"
        compile ":resources:1.1.6"
        //compile "org.quartz-scheduler:quartz:2.1.3"

        runtime ":twitter-bootstrap:2.0.1.17"
        runtime ":fields:1.0.1"
        runtime ":sendgrid:0.2"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":cloud-foundry:1.2.1"
        build ":tomcat:$grailsVersion"
    }
}
