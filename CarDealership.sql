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