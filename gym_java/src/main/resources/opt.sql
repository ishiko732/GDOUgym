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
order by createDate desc ,updateDate desc;


# 验证当前aid是否为最新的公告
select exists(select aid from new_announcement where aid = 6) as ret;

# 查询列名
SELECT
    COLUMN_NAME
FROM
    information_schema.COLUMNS
WHERE
        table_name = 'UserInfo';

## 赛事

## 查询赛事情况
select Competition.*,Ccheck.status as 'isCheck',exists(select id from Competition_cancel where cid=Competition.id) as 'isCancel'
from Competition
left join Competition_check Ccheck on Ccheck.cid=Competition.id;
# where (competition_time between '2022-06-4 14:00:00' and '2022-06-4 15:05:00') and (timestampadd(minute ,60,competition_time) between '2022-06-4 14:00:00' and '2022-06-5 15:05:00');

# 获取赛事时间段
create view competition_time as
select *,timestampadd(minute ,60,competition_time) as competition_end_time
from Competition;


# 根据cid查找裁判和公告
alter view referee_announcements as
select cid,Competition_field.id as cfId,fcId,Competition_field.uid,truename as judgment,Competition.name as competition_name,Fc.name as field_name,Competition_field.introduction,competition_time as starttime,timestampadd(minute ,60,competition_time) as endtime
from Competition_field
left join Competition on Competition_field.cid = Competition.id
left join UserInfo UI on Competition_field.uid = UI.uid
left join Field_check Fc on Competition_field.fcId = Fc.id
where Competition_field.uid is not null and timestampadd(minute ,60,competition_time) >= now()
order by cid,fcId;

# 根据cid查找对应的场地信息
alter view competition_field_time as
select Competition_field.*,fid,TIMESTAMP(Fd.date,Ta.start_time) as startTime,TIMESTAMP(Fd.date,Ta.end_time) as endTime,Fc.status as fieldStatus
from Competition_field
left join Order_item Oi on Competition_field.fcId = Oi.fcid
left join Time_arrange Ta on Oi.time_id = Ta.time_id
left join Field_date Fd on Ta.fdid = Fd.id
left join Field_check Fc on Competition_field.fcId = Fc.id;

# 根据赛事地点id查询器材
select Competition_equipment.*
from Competition_equipment;

# 根据uid查询审核信息
select Competition_check.*
from Competition_check
left join Competition C on C.id = Competition_check.cid
where C.uid=1;

# 获取用户名和id列表
select User.id,name,truename
from User
left join UserInfo  ui on uid=User.id
# where User.id = 1
order by User.id