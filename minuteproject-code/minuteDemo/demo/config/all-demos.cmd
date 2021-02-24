@rem standard
echo "standard generations"
call demo-fitnesse.cmd
del ..\..\sample\hsql\petshop.lck
call demo-hibernate.cmd
del ..\..\sample\hsql\petshop.lck
call demo-ibatis.cmd
del ..\..\sample\hsql\petshop.lck
call demo-jpa.cmd
del ..\..\sample\hsql\petshop.lck
call demo-last-features-openxava.cmd
del ..\..\sample\hsql\petshop.lck
call demo-JPA2.cmd
del ..\..\sample\hsql\petshop.lck
call demo-JPA2-BSLA.cmd
del ..\..\sample\hsql\petshop.lck
call demo-last-features-spring-hibernate.cmd
del ..\..\sample\hsql\petshop.lck
call demo-last-features-spring-jpa.cmd
del ..\..\sample\hsql\petshop.lck

@rem incubator generations
call demo-JOOQ.cmd
@rem call demo-JOOQ-tech.cmd
del ..\..\sample\hsql\petshop.lck
call demo-REST-JEE.cmd
del ..\..\sample\hsql\petshop.lck
call demo-REST-CXF-Spring.cmd
del ..\..\sample\hsql\petshop.lck
call demo-REST-CXF-JEE.cmd
del ..\..\sample\hsql\petshop.lck
call demo-REST-SpringMVC.cmd
del ..\..\sample\hsql\petshop.lck
call demo-WS-JEE.cmd
del ..\..\sample\hsql\petshop.lck
call demo-JSF-primefaces.cmd
del ..\..\sample\hsql\petshop.lck
call demo-JSF-Spring-primefaces.cmd
del ..\..\sample\hsql\petshop.lck
call demo-draft-features-vaadin-spring-hibernate.cmd
del ..\..\sample\hsql\petshop.lck
call demo-last-features-roo.cmd
del ..\..\sample\hsql\petshop.lck
call demo-last-features-grails.cmd
del ..\..\sample\hsql\petshop.lck
call demo-last-features-play.cmd
del ..\..\sample\hsql\petshop.lck
call demo-solr.cmd
del ..\..\sample\hsql\petshop.lck
call demo-ADF.cmd
del ..\..\sample\hsql\petshop.lck
call demo-vaadin-spring.cmd
del ..\..\sample\hsql\petshop.lck

