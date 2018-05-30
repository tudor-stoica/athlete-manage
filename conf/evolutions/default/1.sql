# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admin (
  id                            integer auto_increment not null,
  created_at                    timestamp,
  updated_at                    timestamp,
  first_name                    varchar(255),
  last_name                     varchar(255),
  email                         varchar(255),
  constraint pk_admin primary key (id)
);

create table sport (
  id                            integer auto_increment not null,
  created_at                    timestamp,
  updated_at                    timestamp,
  name                          varchar(255),
  point_value                   integer,
  constraint pk_sport primary key (id)
);

create table spot (
  id                            integer auto_increment not null,
  student_id                    integer not null,
  created_at                    timestamp,
  updated_at                    timestamp,
  points                        integer not null,
  constraint pk_spot primary key (id)
);

create table student (
  id                            integer auto_increment not null,
  created_at                    timestamp,
  updated_at                    timestamp,
  first_name                    varchar(255),
  last_name                     varchar(255),
  email                         varchar(255),
  student_number                integer not null,
  oen                           integer not null,
  grade                         integer not null,
  sex                           varchar(255),
  constraint pk_student primary key (id)
);

create table team (
  id                            integer auto_increment not null,
  created_at                    timestamp,
  updated_at                    timestamp,
  school_year                   timestamp,
  constraint pk_team primary key (id)
);

alter table spot add constraint fk_spot_student_id foreign key (student_id) references student (id) on delete restrict on update restrict;
create index ix_spot_student_id on spot (student_id);


# --- !Downs

alter table spot drop constraint if exists fk_spot_student_id;
drop index if exists ix_spot_student_id;

drop table if exists admin;

drop table if exists sport;

drop table if exists spot;

drop table if exists student;

drop table if exists team;

