﻿DROP TABLE IF EXISTS R;

CREATE TABLE R (
	a integer,
	b integer
);

INSERT INTO R VALUES (1, 1);
INSERT INTO R VALUES (1, NULL);
INSERT INTO R VALUES (10, 20);
INSERT INTO R VALUES (10, NULL);
INSERT INTO R VALUES (NULL, 20);
INSERT INTO R VALUES (NULL, NULL);

COMMIT;

SELECT * FROM R;