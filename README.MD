REST Application 
Jax-RS: Jersey 2
JAXB unmarshlling
deploy on WildFly
MySQL DB
========================

For liquibase migrate process use MVN Command line:

mvn liquibase:update

File with changeSet's (named db.changelog.xml), you can find it project directory "\liquibase"
=========================
Before deploying project you must configure some parameters
in pom.xml file you can find section <properties>

set the 
<wildfly-hostname>
<wildfly-port>
<wildfly-username>
<wildfly-password>

To deploy this project you can chosee one of this ways:
1. From the wildfly interface page 
   http://wildfly-server:9990/console/index.html
   TAB > Deployments > Upload deployment
   Choose war file from project directory "/target/distance.war"
   
   
2. or Run deploy From mvn command line     
   
   "mvn clean install wildfly:deploy"
   
=========================

In MySQL DB persist russian letters, so, for correct testing application you need to configure Mysql server config "my.ini"


[mysqld]
...
....
.....
character-set-server=utf8
collation-server=utf8_unicode_ci


If you create you own tables, you must use option COLLATE='utf8_general_ci'
to provide Russian names of cities.

Example:
CREATE TABLE `city` (
 .....
)
COLLATE='utf8_general_ci'


=========================
In project directory "to_upload" you can find files
Cities.xml
Distances.xml

Which files you can use to upload additional data to Database from REST Application interface (HTML upload <Form>).

PS 
Third api endpoint of test-task.
=========================

Additional Information

You can check coordinates of cities here
https://dateandtime.info/ru/citycoordinates.php?id=1850147

"Crow flight" distance calculator online
https://www.distance.to/Samara,Samarskaya-oblast',RUS/Paris,%C3%8Ele-de-France,FRA

Formula "crow flight"
http://www.codecodex.com/wiki/Calculate_distance_between_two_points_on_a_globe#Java
=========================