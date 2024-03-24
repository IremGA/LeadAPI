
    create sequence Car_SEQ start with 1 increment by 50;

    create sequence Lead_SEQ start with 1 increment by 50;

    create sequence LeadDeviceSpecification_SEQ start with 1 increment by 50;

    create sequence LeadOffer_SEQ start with 1 increment by 50;

    create table Car (
        kilometer numeric(38,0),
        id bigint not null,
        brand varchar(255),
        creation_year varchar(255),
        location varchar(255),
        model varchar(255),
        style varchar(255),
        primary key (id)
    );

    create table Lead (
        createdAt timestamp(6) with time zone,
        id bigint not null,
        respondBefore timestamp(6) with time zone,
        leadStatus varchar(20) check (leadStatus in ('ACTIVE','OFFER_READY','OFFER_SENT_TO_LEAD','FAILED','SUSPENDED','INACTIVE')),
        paymentPlan varchar(20) check (paymentPlan in ('BANK_TRANSFER','CASH','BUY_NOW_PAY_LATER_COMPANY','BUY_NOW_PAY_LATER_BANK')),
        first_name varchar(50) not null,
        last_name varchar(50) not null,
        comment varchar(255),
        e_mail varchar(255) not null,
        occupation varchar(255),
        primary key (id)
    );

    create table LeadDeviceSpecification (
        id bigint not null,
        lead_id_fk bigint,
        updatedAt timestamp(6) with time zone,
        ld_spec_priority varchar(20) check (ld_spec_priority in ('MUST_HAVE','NICE_TO_HAVE')),
        ld_spec_type varchar(20) check (ld_spec_type in ('TRANSMISSION','TRIM','TIRE')),
        comment varchar(500),
        primary key (id)
    );

    create table LeadOffer (
        priceAmount numeric(38,2),
        car_id bigint unique,
        createdAt timestamp(6) with time zone,
        id bigint not null,
        lead_id_fk bigint,
        currency varchar(20) check (currency in ('USD','EUR','GBP')),
        offerStatus varchar(20) check (offerStatus in ('ACTIVE','ACCEPTED_BY_LEAD','REJECTED_BY_LEAD','WAITING','INACTIVE')),
        primary key (id)
    );

    alter table if exists LeadDeviceSpecification 
       add constraint FKe29vqkeiyc1uue2h3sqb6v2jm 
       foreign key (lead_id_fk) 
       references Lead;

    alter table if exists LeadOffer 
       add constraint FKbf2idcv4qqgply5i6r32wp2xy 
       foreign key (car_id) 
       references Car;

    alter table if exists LeadOffer 
       add constraint FK3j5do29ayi0n3kef1ndi2flip 
       foreign key (lead_id_fk) 
       references Lead;
