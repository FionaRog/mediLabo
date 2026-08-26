INSERT INTO patients (firstname, lastname, date_of_birth, gender, address, telephone)
SELECT 'Test', 'TestNone', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'
    WHERE NOT EXISTS (
    SELECT 1
    FROM patients
    WHERE firstname = 'Test'
      AND lastname = 'TestNone'
      AND date_of_birth = '1966-12-31'
);

INSERT INTO patients (firstname, lastname, date_of_birth, gender, address, telephone)
SELECT 'Test', 'TestBorderline', '1945-06-24', 'M', '2 High St', '200-333-4444'
    WHERE NOT EXISTS (
    SELECT 1
    FROM patients
    WHERE firstname = 'Test'
      AND lastname = 'TestBorderline'
      AND date_of_birth = '1945-06-24'
);

INSERT INTO patients (firstname, lastname, date_of_birth, gender, address, telephone)
SELECT 'Test', 'TestInDanger', '2004-06-18', 'M', '3 Club Road', '300-444-5555'
    WHERE NOT EXISTS (
    SELECT 1
    FROM patients
    WHERE firstname = 'Test'
      AND lastname = 'TestInDanger'
      AND date_of_birth = '2004-06-18'
);

INSERT INTO patients (firstname, lastname, date_of_birth, gender, address, telephone)
SELECT 'Test', 'TestEarlyOnset', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666'
    WHERE NOT EXISTS (
    SELECT 1
    FROM patients
    WHERE firstname = 'Test'
      AND lastname = 'TestEarlyOnset'
      AND date_of_birth = '2002-06-28'
);