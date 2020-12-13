
insert into ACCOUNT (EMAIL, PASSWORD, NOM, PRENOM, AGE) values ('admin@domain.com', 'secret','ADMIN','Mme',49);
insert into ACCOUNT (EMAIL, PASSWORD, NOM, PRENOM, AGE) values ('customer@domain.com', 'secret','CUSTOMER','Mr',29);
insert into ACCOUNT (EMAIL, PASSWORD, NOM, PRENOM, AGE) values ('user@domain.com', 'secret','USER','Mr',88);

insert into ROLE (NAME) values ('ROLE_CUSTOMER');
insert into ROLE (NAME) values ('ROLE_USER');



insert into account_roles (account_id , roles_id) values (1,1);
insert into account_roles (account_id , roles_id) values (1,2);
insert into account_roles (account_id , roles_id) values (2,1);
insert into account_roles (account_id , roles_id) values (3,2);





