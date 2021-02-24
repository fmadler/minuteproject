DELIMITER /
USE conference /       
drop PROCEDURE IF EXISTS registerAttendee//
CREATE PROCEDURE registerAttendee(
        IN address_street1 bigint(20),
        IN address_street2 bigint(20),
        IN countryId int(11),
        OUT attendeeId bigint(20)      
)
    BEGIN
        DECLARE addressId bigint(20);
        Insert into address (street1, street2, country_id) values (address_street1, address_street2, countryId);
        SET addressId = last_insert_id();
        SET attendeeId = addressId;
    END
//
drop PROCEDURE IF EXISTS registerAttendeeFull//
CREATE PROCEDURE registerAttendeeFull(
        IN firstName VARCHAR(255),
        IN lastName VARCHAR(255),
        IN email VARCHAR(255),
        IN conferenceId bigint(20),
        IN address_street1 bigint(20),
        IN address_street2 bigint(20),
        IN countryId int(11),
        OUT attendeeId bigint(20)
)
    BEGIN
       
        DECLARE addressId bigint(20);
        DECLARE conferenceMemberId bigint(20);
        START TRANSACTION;
        Insert into address (street1, street2, country_id) values (address_street1, address_street2, countryId);
        SET addressId = last_insert_id();
        Insert into conference_member (conference_id, first_name, last_name, email, address_id, status)
                values (conferenceId, firstName, lastName, email, addressId, 'ACTIVE');
        SET conferenceMemberId = last_insert_id();
        SET attendeeId = conferenceMemberId;
        COMMIT;
    END
//