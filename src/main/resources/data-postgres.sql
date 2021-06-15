insert into patient (id, name, surname, email, password, address, phone_num, points, category) values (10, 'Filip', 'Volaric', 'filip.kresa@gmail.com', 'fickos123', 4, '0641345948', 15, 'GOLD');
insert into patient (id, name, surname, email, password, address, phone_num, points, category) values (11, 'Svetozar', 'Vulin', 'svetozar.vulin@gmail.com', 'sveta123', 1, '065123123', 12, 'GOLD');
insert into patient (id, name, surname, email, password, address, phone_num, points, category) values (12, 'Uros', 'Petric', 'uki.tricpe@gmail.com', 'urosplatinium', 3, '0651344891', 22, 'PLATINUM');
insert into patient (id, name, surname, email, password, address, phone_num, points, category) values (13, 'Veljko', 'Kosanovic', 'cika.ljave@gmail.com', 'ljave123', 2, '061156678', 9, 'SILVER');
insert into patient (id, name, surname, email, password, address, phone_num, points, category) values (14, 'Pera', 'Peric', 'pera.peric@gmail.com', 'pera123', 4, '022400400', 1, 'BRONZE');

insert into address (street, number, city, country, latitude, longitude) values ('Pinkijeva', 3, 'Ruma', 'Srbija', 45.00332144112418, 19.82307121321917);
insert into address (street, number, city, country, latitude, longitude) values ('27. Oktobra', 9, 'Ruma', 'Srbija', 45.01032716131725, 19.818894326712318);
insert into address (street, number, city, country, latitude, longitude) values ('Ustanicka', 67, 'Beograd', 'Srbija', 44.78682348847335, 20.480522684376773);
insert into address (street, number, city, country, latitude, longitude) values ('Krusevacka', 30, 'Beograd', 'Srbija', 44.78853815332539, 20.48372415369131);
insert into address (street, number, city, country, latitude, longitude) values ('Cara Dusana', 67, 'Novi Sad', 'Srbija', 45.24124651222663, 19.82543566904801);

insert into pharmacy (name, address_id, description, rating, pharmacist_price) values ('Apoteka1', 1, 'opis1', 7, 1000);
insert into pharmacy (name, address_id, description, rating, pharmacist_price) values ('Apoteka2', 2, 'opis2', 6, 2000);
insert into pharmacy (name, address_id, description, rating, pharmacist_price) values ('Apoteka3', 3, 'opis3', 9, 3000);

insert into medicine(name, type, specification) values ('Lek1', 1, 'neeekaaa speeeec');
insert into medicine(name, type, specification) values ('Lek2', 2, 'sdfsefs');
insert into medicine(name, type, specification) values ('Lek3', 2, 'uiyuoirtl');
insert into medicine(name, type, specification) values ('Lek4', 0, 'antibiotic 4');
insert into medicine(name, type, specification) values ('Lek5', 0, 'antibiotic 5');
insert into medicine(name, type, specification) values ('Lek6', 0, 'antibiotic 6');

insert into pharmacy_medicines (pharmacy_id, medicine_id, price, quantity, start_date, end_date) values (1, 1, 100, 100, 1621893600000, 1640386800000);
insert into pharmacy_medicines (pharmacy_id, medicine_id, price, quantity, start_date, end_date) values (1, 2, 120, 100, 1621893600000, 1640386800000);
insert into pharmacy_medicines (pharmacy_id, medicine_id, price, quantity, start_date, end_date) values (1, 3, 90, 100, 1621893600000, 1640386800000);
insert into pharmacy_medicines (pharmacy_id, medicine_id, price, quantity, start_date, end_date) values (2, 3, 70, 10, 1621893600000, 1640386800000);
insert into pharmacy_medicines (pharmacy_id, medicine_id, price, quantity, start_date, end_date) values (3, 3, 70, 6, 1621893600000, 1640386800000);
insert into pharmacy_medicines (pharmacy_id, medicine_id, price, quantity, start_date, end_date) values (3, 1, 200, 26, 1621893600000, 1640386800000);

insert into dermatologist (id, name, surname, email, password, address, phone_num) values (1, 'Mika', 'Mikic', 'mika22@gmail.com', 'mika123', 5, '011100100');
insert into dermatologist (id, name, surname, email, password, address, phone_num) values (2, 'Djuka', 'Djukic', 'djuka22@gmail.com', 'djuka123', 5, '011101101');
insert into dermatologist (id, name, surname, email, password, address, phone_num) values (3, 'Ivan', 'Djordjevic', 'iivan@gmail.com', 'ivan123', 5, '011102102');
insert into dermatologist (id, name, surname, email, password, address, phone_num) values (4, 'Nikola', 'Nikolic', 'nikolicaprikolica@gmail.com', 'nidza123', 5, '011103103');

insert into pharmacist (id, name, surname, email, password, address, phone_num, rating, pharmacy_id) values (5, 'Filip', 'Filipovic', 'filipfilip@gmail.com', 'fica123', 5, '011200200', 8, 3);
insert into pharmacist (id, name, surname, email, password, address, phone_num, rating, pharmacy_id) values (6, 'Djordje', 'Volas', 'djokica@gmail.com', 'djoka123', 5, '011201201', 7, 1);
insert into pharmacist (id, name, surname, email, password, address, phone_num, rating, pharmacy_id) values (7, 'Stefan', 'Volkovic', 'stefke@gmail.com', 'stefi123', 5, '011202202', 8, 1);
insert into pharmacist (id, name, surname, email, password, address, phone_num, rating, pharmacy_id) values (8, 'Marko', 'Markovic', 'markelof@gmail.com', 'mare123', 5, '011203203', 9, 2);
insert into pharmacist (id, name, surname, email, password, address, phone_num, rating, pharmacy_id) values (9, 'Sara', 'Neskovic', 'sara@gmail.com', 'chang3m3', 5, '0112032123', 10, 3);

insert into pharmacy_dermatologists (pharmacy_id, dermatologist_id) values (1,1);
insert into pharmacy_dermatologists (pharmacy_id, dermatologist_id) values (1,2);
insert into pharmacy_dermatologists (pharmacy_id, dermatologist_id) values (2,3);
insert into pharmacy_dermatologists (pharmacy_id, dermatologist_id) values (2,1);

insert into lab_admin (id, name, surname, email, password, address, phone_num, pharmacy_id) values (15, 'Lazar', 'Lazarevic', 'laske@gmail.com', 'laza123', 5, '011203203', 1);
insert into lab_admin (id, name, surname, email, password, address, phone_num, pharmacy_id) values (16, 'Mihajlo', 'Mihajlovic', 'mihajlomikimiki@gmail.com', 'miki123', 4, '022113113', 2);
insert into lab_admin (id, name, surname, email, password, address, phone_num, pharmacy_id) values (17, 'Paun', 'Paunovic', 'paun@gmail.com', 'paun123', 3, '022213213', 3);
insert into lab_admin (id, name, surname, email, password, address, phone_num, pharmacy_id) values (18, 'Zeljko', 'Zeljkovic', 'zelje@gmail.com', 'zelje123', 2, '022213213', 1);

insert into doctor_terms (doctor_id, pharmacy_id, start, finish) values (1, 1, '2021-06-25 15:50:00', '2021-06-25 17:00:00');
insert into doctor_terms (doctor_id, pharmacy_id, start, finish) values (1, 1, '2021-06-26 18:00:00', '2021-06-26 18:30:00');
insert into doctor_terms (doctor_id, pharmacy_id, start, finish) values (2, 1, '2021-06-19 16:00:00', '2021-06-19 17:00:00');
insert into doctor_terms (doctor_id, pharmacy_id, start, finish) values (1, 2, '2021-06-21 15:00:00', '2021-06-21 15:30:00');
insert into doctor_terms (doctor_id, pharmacy_id, start, finish) values (1, 2, '2021-06-28 15:00:00', '2021-06-28 16:00:00');

insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-04-20 16:00:00', '2021-04-20 16:30:00', 1, 2); 
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-04-16 17:00:00', '2021-04-16 18:00:00', 1, 2); 
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-03-20 16:00:00', '2021-03-20 16:30:00', 1, 2); 
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-04-16 18:40:00', '2021-04-16 19:30:00', 1, 2); 
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-06-07 16:00:00', '2021-06-07 16:30:00', 1, 0); 
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 2, '2021-04-27 15:00:00', '2021-04-27 15:40:00', 1, 2); 
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 2, '2021-04-28 11:00:00', '2021-04-28 11:45:00', 1, 2); 
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-04-21 08:40:00', '2021-04-21 09:45:00', 1, 2); 

insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (11, 3, '2021-05-05 16:00:00', '2021-05-05 17:00:00', 2, 2); 
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (12, 1, '2021-04-18 20:50:00', '2021-04-18 22:00:00', 1, 2); 
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 3, '2021-05-05 16:00:00', '2021-05-05 17:00:00', 2, 2);

insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-04-27 01:00:00', '2021-04-27 04:00:00', 1, 2);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (11, 1, '2021-04-27 16:00:00', '2021-04-27 17:00:00', 1, 2);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (11, 1, '2021-05-27 16:00:00', '2021-05-27 17:00:00', 1, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (12, 1, '2021-05-29 18:00:00', '2021-05-29 19:00:00', 2, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (11, 5, '2021-05-27 16:00:00', '2021-05-27 17:00:00', 3, 2);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (12, 5, '2021-05-29 18:00:00', '2021-05-29 19:00:00', 3, 2);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (13, 5, '2020-05-29 18:00:00', '2020-05-29 19:00:00', 1, 2);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (14, 5, '2020-06-29 18:00:00', '2020-06-29 19:00:00', 1, 2);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (13, 5, '2019-05-29 18:00:00', '2019-05-29 19:00:00', 1, 2);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (12, 5, '2021-11-29 18:00:00', '2021-11-29 19:00:00', 1, 2);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-05-26 8:30:00', '2021-05-26 9:30:00', 1, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-05-29 2:00:00', '2021-05-29 4:30:00', 1, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-05-31 22:00:00', '2021-05-31 23:59:00', 1, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-06-01 15:00:00', '2021-06-01 20:59:00', 1, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-06-15 15:00:00', '2021-06-15 20:59:00', 1, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-06-12 16:30:00', '2021-06-12 20:59:00', 1, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-06-15 15:00:00', '2021-06-15 20:59:00', 1, 0);

insert into patients_allergies (patient_id, medicine_id) values (10, 1);
insert into patients_allergies (patient_id, medicine_id) values (10, 2);
insert into patients_allergies (patient_id, medicine_id) values (12, 1);
insert into patients_allergies (patient_id, medicine_id) values (12, 2);

insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 1, 1, 1588456800000, 2, 200, 0);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 2, 3, 1587852000000, 1, 70, 0);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 1, 3, 1621029600000, 10, 70, 2);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (11, 1, 2, 1587852000000, 4, 480, 2);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 1, 1, 1626300000000, 18, 70, 2);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 1, 1, 1621461600000, 18, 100, 2);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 1, 1, 1621548000000, 18, 720, 2);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 1, 1, 1621893600000, 18, 370, 2);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 1, 1, 1622671200000, 18, 400, 2);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 1, 1, 1623967200000, 18, 510, 2);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 1, 1, 1632000000000, 18, 510, 0);
insert into reservations (patient_id, pharmacy_id, medicine_id, date, quantity, total_price, status) values (10, 3, 1, 1622725200000, 4, 510, 0);

insert into work_hours (doctor_id, pharmacy_id, start_time, finish_time) values (1, 1, '08:00:00', '21:00:00');
insert into work_hours (doctor_id, pharmacy_id, start_time, finish_time) values (2, 1, '14:00:00', '20:00:00');
insert into work_hours (doctor_id, pharmacy_id, start_time, finish_time) values (3, 2, '10:00:00', '16:00:00');
insert into work_hours (doctor_id, pharmacy_id, start_time, finish_time) values (4, 2, '16:00:00', '22:00:00');
insert into work_hours (doctor_id, pharmacy_id, start_time, finish_time) values (1, 3, '13:00:00', '20:00:00');
insert into work_hours (doctor_id, pharmacy_id, start_time, finish_time) values (5, 3, '14:00:00', '22:00:00');
insert into work_hours (doctor_id, pharmacy_id, start_time, finish_time) values (6, 1, '13:00:00', '21:00:00');

insert into ROLE (name) values ('ROLE_PATIENT');
insert into ROLE (name) values ('ROLE_DERMATOLOGIST');
insert into ROLE (name) values ('ROLE_PHARMACIST');
insert into ROLE (name) values ('ROLE_LAB_ADMIN');
insert into ROLE (name) values ('ROLE_HEAD_ADMIN');

insert into user_role (user_id, role_id) VALUES (1, 2);
insert into user_role (user_id, role_id) VALUES (2, 2);
insert into user_role (user_id, role_id) VALUES (3, 2);
insert into user_role (user_id, role_id) VALUES (4, 2);
insert into user_role (user_id, role_id) VALUES (5, 3);
insert into user_role (user_id, role_id) VALUES (6, 3);
insert into user_role (user_id, role_id) VALUES (7, 3);
insert into user_role (user_id, role_id) VALUES (8, 3);
insert into user_role (user_id, role_id) VALUES (9, 3);
insert into user_role (user_id, role_id) VALUES (10, 1);
insert into user_role (user_id, role_id) VALUES (11, 1);
insert into user_role (user_id, role_id) VALUES (12, 1);
insert into user_role (user_id, role_id) VALUES (13, 1);
insert into user_role (user_id, role_id) VALUES (14, 1);
insert into user_role (user_id, role_id) VALUES (15, 4);
insert into user_role (user_id, role_id) VALUES (16, 4);
insert into user_role (user_id, role_id) VALUES (17, 4);
insert into user_role (user_id, role_id) VALUES (18, 4);

insert into orders (pharmacy_id, deadline) values (1, 1619560800000);
insert into orders (pharmacy_id, deadline) values (1, 1620165600000);
insert into orders (pharmacy_id, deadline) values (2, 1622930400000);

insert into order_medicines (order_id, medicine_id, quantity) values (1, 1, 5);
insert into order_medicines (order_id, medicine_id, quantity) values (1, 2, 3);
insert into order_medicines (order_id, medicine_id, quantity) values (2, 3, 1);
insert into order_medicines (order_id, medicine_id, quantity) values (3, 1, 1);

insert into supplier_offers (supplier_id, order_id, price, deadline) values (1, 1, 1250, 1622325600000);
insert into supplier_offers (supplier_id, order_id, price, deadline) values (2, 1, 1000, 1622412000000);
insert into supplier_offers (supplier_id, order_id, price, deadline) values (3, 1, 1500, 1622584800000);

insert into vacation (finish, start, status, type, doctor_id) values ('2021-07-23', '2021-07-19', 0, 0, 1);
insert into vacation (finish, start, status, type, doctor_id) values ('2021-07-30', '2021-07-31', 0, 1, 1);

insert into demand_medicine (pharmacy_id, medicine_id, quantity) values (1, 1, 10);
insert into demand_medicine (pharmacy_id, medicine_id, quantity) values (1, 2, 15);
insert into demand_medicine (pharmacy_id, medicine_id, quantity) values (2, 3, 10);

insert into vacation (finish, start, status, type, doctor_id) values ('2021-07-23', '2021-07-19', 1, 0, 1);
insert into vacation (finish, start, status, type, doctor_id) values ('2021-07-31', '2021-07-30', 1, 1, 1);
insert into vacation (finish, start, status, type, doctor_id) values ('2021-12-30', '2021-12-22', 1, 0, 1);

insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (11, 1, '2021-05-19 13:00:00', '2021-05-19 17:00:00', 3, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (12, 1, '2021-05-19 13:30:00', '2021-05-19 19:00:00', 3, 0);
insert into visit (patient_id, doctor_id, start_time, finish_time, pharmacy_id, status) values (10, 1, '2021-05-26 8:30:00', '2021-05-26 10:30:00', 1, 0);

insert into penalty (date, patient_id) values ('2021-05-23', 10);
insert into penalty (date, patient_id) values ('2021-05-17', 10);
insert into penalty (date, patient_id) values ('2021-05-10', 11);

insert into ratings (mark, obj_id, patient_id, type) values (5, 1, 10, 1);
insert into ratings (mark, obj_id, patient_id, type) values (5, 1, 11, 1);
insert into ratings (mark, obj_id, patient_id, type) values (5, 1, 12, 1);
insert into ratings (mark, obj_id, patient_id, type) values (7, 1, 10, 2);
