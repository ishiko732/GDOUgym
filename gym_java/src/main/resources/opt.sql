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

## 设置时间为当前时间
ALTER TABLE Announcement
    MODIFY COLUMN createDate datetime NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE Announcement
    MODIFY COLUMN updateDate timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

# 创建虚表-取公告种类
create view Announcement_type as
    select distinct type
    from Announcement;

select *
from Announcement_type;

# 获取最新的公告
create view new_announcement as
    select aid,uid,type,content,createDate,updateDate
    from Announcement
    right join (select max(aid)as base_id,max(createDate) as createtime from Announcement group by type) as max_tmp on max_tmp.base_id=aid and max_tmp.createtime=createDate
    order by createDate,updateDate,aid;

# 取指定的最新公告
select aid,uid,type,content,createDate,updateDate
from Announcement
         right join (select max(aid)as base_id,max(createDate) as createtime from Announcement group by type) as max_tmp on max_tmp.base_id=aid and max_tmp.createtime=createDate
where type like '馆内罚款'
order by createDate,updateDate,aid;

# 取指定的公告历史记录(从最近到最久)
select *
from Announcement
where type like '馆内罚款'
order by createDate desc ,updateDate desc