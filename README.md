# EC-management project

## Developed by Scrum team (group2)
###### Scrum master:
	- Phan Tiến Tùng (tungptseven): product owner, product tester
###### Scrum members:
	- Nguyễn Đức Anh (ducanhz135): product tester
	- Đàm Cao Sơn (DamCaoSon): programmer, designer
 	- Nguyễn Hải Nam (dfChicken): programmer, designer
	- Nguyễn Văn Ái (ainguyenkaka): programmer, unit tester

### Resource URLs
- Repository URL: https://github.com/EnterpriseWebSoftwareDevelopmentSEM7-G2/ec-management 
- Screencast URL: http://rebrand.ly/ewsd_g2_cw_kaka 
- Project management: https://ainguyenkaka.visualstudio.com/EnterpriseWebSoftwareDevelopmentSEM7/_dashboards 
- Site URL: http://ecmsystem.ga/
- Username and password: {username} – {password}
	- Student role: student – 1234
	- Manager role: manager – 1234
	- Coordinator role: coordinator – 1234
  	- Admin role: admin - 1234

## Project UML
![alt tag](https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/images%2Ffinal_uml.png?alt=media&token=d86cd4f5-b270-477f-b476-623ba6434904)

## Project ERD
![alt tag](https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/images%2Ffinal_erd.png?alt=media&token=c2fa3e2e-14a0-4b81-9d4a-d3a5f51142c2)

## Project setup
1. Git clone
```
git clone https://github.com/EnterpriseWebSoftwareDevelopmentSEM7-G2/ec-management.git
```
2. Build and test
```
mvn package
```
3. Install mysql and run query for granting user's permission
```
CREATE USER 'ecm'@'localhost' IDENTIFIED BY 'ecm';
GRANT ALL PRIVILEGES ON *.* TO 'ecm'@'localhost'
```
4. Run tomcat web server
```
mvn spring-boot:run
```
## Scrum Log
https://www.gitbook.com/book/tungptseven/scrum-log/details

## Product backlog
https://ainguyenkaka.visualstudio.com/EnterpriseWebSoftwareDevelopmentSEM7/_backlogs?level=Backlog%20items&showParents=false&_a=backlog


