# 二进制获取权限
select r.id,role,group_concat(pname) as permissions,count(pname) as cnt
from permission
join role as r on (pid & r.permissions)= pid
group by r.id;

# 创建虚表
create view role_permissions as
    select r.id,role,group_concat(pname) as permissions,count(pname) as cnt
    from permission
             join role as r on (pid & r.permissions)= pid
    group by r.id;