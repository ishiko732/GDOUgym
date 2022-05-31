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

# 创建虚表--用户-角色-权限表
create view user_role_permissions as
    select User.id as uid ,name,rp.id as rid,rp.permissions,rp.cnt
    from User
    join (select r.id,role,group_concat(pname) as permissions,count(pname) as cnt
              from permission
                       join role as r on (pid & r.permissions)= pid
              group by r.id) rp on User.role_id = rp.id;

# 创建虚表--用户-信息表
create view user_info as
    select UserInfo.*,password,role_id
    from User,UserInfo
    where User.id=UserInfo.uid;
