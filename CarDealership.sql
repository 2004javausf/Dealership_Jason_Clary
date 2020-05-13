CREATE OR REPLACE PROCEDURE RejectOtherPendingOffers(userID NUMBER, vehicleID NUMBER)
IS
BEGIN
    DELETE FROM OFFERS
    WHERE USERS_ID != userID;
    UPDATE VEHICLES
    SET STATUS_ID = 2
    WHERE VEHICLES.ID = vehicleID;
COMMIT;
END;
/

create or replace PROCEDURE SelectAllOffers(c1 in out SYS_REFCURSOR)
IS
BEGIN
OPEN c1 FOR
SELECT FIRSTNAME, LASTNAME, COLOR, YEAR, 
(SELECT MAKE
FROM VEHICLES
JOIN vehiclebrand ON vehiclebrand.id = vehicles.brand_id
WHERE VEHICLES.ID = offers.vehicles_id) AS MAKE,
(SELECT MODEL
FROM VEHICLES
JOIN vehiclebrand ON vehiclebrand.id = vehicles.brand_id
WHERE VEHICLES.ID = offers.vehicles_id) AS MODEL,
PRICE, DOWNPAYMENT, PAYMENTS, 
ROUND(((PRICE - DOWNPAYMENT) / PAYMENTS),2) AS PAYAMOUNT
FROM OFFERS
JOIN users ON USERS.ID = offers.users_id
JOIN VEHICLES ON VEHICLES.ID = offers.vehicles_id;
DBMS_SQL.RETURN_RESULT(c1);
COMMIT;
END;


variable c1 refcursor;
execute selectalloffers(:c1);

SELECT FIRSTNAME, LASTNAME, COLOR, YEAR, 
(SELECT MAKE
FROM VEHICLES
JOIN vehiclebrand ON vehiclebrand.id = vehicles.brand_id
WHERE VEHICLES.ID = offers.vehicles_id) AS MAKE,
(SELECT MODEL
FROM VEHICLES
JOIN vehiclebrand ON vehiclebrand.id = vehicles.brand_id
WHERE VEHICLES.ID = offers.vehicles_id) AS MODEL,
PRICE, DOWNPAYMENT, PAYMENTS, 
ROUND(((PRICE - DOWNPAYMENT) / PAYMENTS),2) AS PAYAMOUNT
FROM OFFERS
JOIN users ON USERS.ID = offers.users_id
JOIN VEHICLES ON VEHICLES.ID = offers.vehicles_id
WHERE USERS.USERNAME = 'username' AND (SELECT AVAILABILITY FROM VEHICLES
JOIN STATUS ON STATUS.ID = VEHICLES.STATUS_ID
WHERE VEHICLES.ID = offers.vehicles_id) = 'Not Available';

SELECT ID, MODEL
FROM VEHICLEBRAND
WHERE MAKE = 'Ford';

Insert into VEHICLES 
values(VEHICLES_SEQ.NEXTVAL + 10,
1001
, 2
, 'green'
, 2000
, 1);