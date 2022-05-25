# 二进制获取权限
select pname
from permission
join role as r on (pid & r.permissions)= pid
where r.id=1