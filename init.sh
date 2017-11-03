#!/bin/bash
sudo ln -sf /home/newadmin/IdeaProjects/Interview/nginx.conf  /etc/nginx/sites-enabled/default
sudo /etc/init.d/nginx restart
sudo /opt/apache-tomcat-8.5.20/bin/startup.sh
sudo ln -sf /home/newadmin/IdeaProjects/Interview/build/libs/Interview.war  /opt/apache-tomcat-8.5.20/webapps/Interview.war



