REM @echo off
REM java -cp "c:\program files\jade\lib\jade.jar" jade.Boot -name Agentville -gui
java -cp "c:\program files\jade\lib\jade.jar;.;.\target\agentville_jade_maven_archetype_test_MyAgent.jar" jade.Boot -gui
REM java -cp "c:\program files\jade\lib\jade.jar" jade.Boot -services jade.core.messaging.TopicManagementService;jade.core.event.NotificationService -gui
