INSERT INTO diplom.validation_rules (name, rule)
VALUES ('FIRST_NAME', '([a-zA-Z])*');
INSERT INTO validation_rules (name, rule)
VALUES ('LAST_NAME', '([a-zA-Z])*');
INSERT INTO validation_rules (name, rule)
VALUES ('PASSWORD', '^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?!.*(\d)\1)(?=.*[^a-zA-Z\d]).{8,}$');
INSERT INTO validation_rules (name, rule)
VALUES ('PASSWORD_CONFIRMATION', '^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?!.*(\d)\1)(?=.*[^a-zA-Z\d]).{8,}$');
INSERT INTO validation_rules (name, rule)
VALUES ('USERNAME', '^[a-zA-Z0-9_]{8,20}$');
INSERT INTO validation_rules (name, rule)
VALUES ('PHONE_NUMBER', '^\+[1-9]{1}[0-9]{3,14}$');
INSERT INTO validation_rules (name, rule)
VALUES ('EMAIL', '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$');
INSERT INTO validation_rules (name, rule)
VALUES ('IS_ACTIVE_CHAT', 'null');
INSERT INTO validation_rules (name, rule)
VALUES ('STATUS', 'null');