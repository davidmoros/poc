FROM sonarqube:7.6-community

COPY assets/sonarqube-community-branch-plugin-1.0.2.jar /opt/sonarqube/extensions/plugins

#RUN /opt/sonarqube/bin/run.sh && curl -v -u admin:admin "http://localhost:9000/api/qualityprofiles/restore" --form backup="@assets/Telefonica way (T1 2018).xml"