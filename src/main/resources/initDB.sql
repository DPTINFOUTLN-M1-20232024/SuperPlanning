DROP TABLE IF EXISTS DOCUMENT CASCADE ;
create table DOCUMENT
(
    ID              LONG auto_increment,
    DISCRIMINATOR   VARCHAR,
    BORROWER_ID     LONG,
    TITLE           VARCHAR,
    AUTHOR_NAME     VARCHAR,
    DESIGNER_NAME   VARCHAR,
    SERIAL_NUMBER   INT,
    THEME           VARCHAR
);

alter table DOCUMENT
    add constraint DOCUMENT_PK
        primary key(ID);

create unique index DOCUMENT_ID_UINDEX
    on DOCUMENT(ID);

DROP TABLE IF EXISTS LAPTOP CASCADE ;
create table LAPTOP
(
    ID          LONG auto_increment,
    BRAND       VARCHAR,
    OS_NAME     VARCHAR,
    BORROWER_ID LONG
);

create unique index LAPTOP_ID_UINDEX
    on LAPTOP (ID);

alter table LAPTOP
    add constraint LAPTOP_PK
        primary key (ID);


DROP TABLE IF EXISTS MEMBER CASCADE ;
CREATE TABLE MEMBER
(
    ID          LONG AUTO_INCREMENT,
    LASTNAME    VARCHAR,
    FIRSTNAME   VARCHAR,
    STATUS_NAME VARCHAR
);

CREATE UNIQUE INDEX MEMBER_ID_UINDEX
    ON MEMBER(ID);

ALTER TABLE MEMBER
    ADD CONSTRAINT MEMBER_PK
        PRIMARY KEY (ID);