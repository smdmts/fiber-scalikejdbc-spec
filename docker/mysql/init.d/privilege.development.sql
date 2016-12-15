grant all privileges on *.* to 'root'@'%' with grant option;
grant all privileges on *.* to 'root'@'localhost' with grant option;

SET PASSWORD FOR root@'%'=password('');