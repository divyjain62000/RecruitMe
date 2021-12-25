alter table student 
drop column secondary_mobile_number,
drop column secondary_email;

alter table student 
add secondary_mobile_number varchar(10),
add secondary_email varchar(50);