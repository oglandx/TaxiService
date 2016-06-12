CREATE TABLE "User" (
  id          SERIAL NOT NULL,
  firstname  varchar(255),
  lastname   varchar(255),
  middlename varchar(255),
  birthdate  date,
  email      varchar(255) NOT NULL,
  pass       varchar(255) NOT NULL,
  karma      int4,
  CONSTRAINT id
  PRIMARY KEY (id)
);

CREATE TABLE Operator (
  id       SERIAL NOT NULL,
  user_id int4 NOT NULL,
  PRIMARY KEY (id)
);
ALTER TABLE Operator ADD CONSTRAINT FKOperator393579 FOREIGN KEY (user_id) REFERENCES "User" (id);

CREATE TABLE Passenger (
  id       SERIAL NOT NULL,
  user_id int4 NOT NULL,
  PRIMARY KEY (id)
);
ALTER TABLE Passenger ADD CONSTRAINT FKPassenger674465 FOREIGN KEY (user_id) REFERENCES "User" (id);

CREATE TABLE Driver (
  id       SERIAL NOT NULL,
  user_id int4 NOT NULL,
  PRIMARY KEY (id)
);
ALTER TABLE Driver ADD CONSTRAINT FKDriver826707 FOREIGN KEY (user_id) REFERENCES "User" (id);

CREATE TABLE Rate (
  id          SERIAL NOT NULL,
  costperkm  numeric(19, 0),
  costpermin numeric(19, 0),
  PRIMARY KEY (id)
);

CREATE TABLE Payment (
  id       SERIAL NOT NULL,
  drivekm int4,
  waitmin int4,
  rate_id int4 NOT NULL,
  PRIMARY KEY (id)
);
ALTER TABLE Payment ADD CONSTRAINT FKPayment981090 FOREIGN KEY (rate_id) REFERENCES Rate (id);

CREATE TABLE Address (
  id        SERIAL NOT NULL,
  city     varchar(255),
  street   varchar(255),
  building varchar(255),
  PRIMARY KEY (id)
);

CREATE TABLE "Order" (
  id            SERIAL NOT NULL,
  creationTime timestamp,
  status       varchar(100),
  address_id   int4 NOT NULL,
  payment_id   int4 NOT NULL,
  passenger_id int4,
  driver_id    int4,
  PRIMARY KEY (id));
ALTER TABLE "Order" ADD CONSTRAINT FKOrder475505 FOREIGN KEY (address_id) REFERENCES Address (id);
ALTER TABLE "Order" ADD CONSTRAINT FKOrder651484 FOREIGN KEY (payment_id) REFERENCES Payment (id);
ALTER TABLE "Order" ADD CONSTRAINT FKOrder752832 FOREIGN KEY (passenger_id) REFERENCES Passenger (id);
ALTER TABLE "Order" ADD CONSTRAINT FKOrder473824 FOREIGN KEY (driver_id) REFERENCES Driver (id);

ALTER TABLE rate ADD COLUMN freeminutes INT4;

ALTER TABLE payment RENAME COLUMN drivekm to distance;

ALTER TABLE driver ADD COLUMN "status" VARCHAR(100);