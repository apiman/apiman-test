--
-- Authentication
--
CREATE TABLE qe_users (
    "username" character varying NOT NULL,
    "password" character varying NOT NULL,
    PRIMARY KEY ("username")
);

INSERT INTO "qe_users" VALUES ('admin', 'admin');
INSERT INTO "qe_users" VALUES ('user', 'user');
INSERT INTO "qe_users" VALUES ('nobody', 'nobody');
--
-- Authentication
--
CREATE TABLE qe_roles (
    "username" character varying NOT NULL,
    "role" character varying NOT NULL,
    PRIMARY KEY ("username", "role"),
    FOREIGN KEY ("username") REFERENCES qe_users ("username")
);

INSERT INTO "qe_roles" VALUES ('admin', 'admin');
INSERT INTO "qe_roles" VALUES ('admin', 'user');
INSERT INTO "qe_roles" VALUES ('user', 'user');


