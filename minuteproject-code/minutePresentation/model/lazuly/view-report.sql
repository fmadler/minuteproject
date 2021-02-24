create view my_conference_cool_report
as
select cm.id as id,
cm.first_name,
cm.email,
cm.status,
a.street1 as address_street1,
a.street2 as address_street2,
co.iso_name as country_code,
c.name as conference_name,
c.start_date as conference_begin_date,
c.end_date as conference_end_date
from conference_member cm, conference c, address a, country co
where cm.conference_id = c.id
and cm.address_id = a.id
and a.id = co.id
;