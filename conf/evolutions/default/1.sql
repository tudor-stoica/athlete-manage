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
  created_at                    timestamp,
  updated_at                    timestamp,
  team_id                       integer,
  student_id                    integer,
  points                        integer not null,
  ofsaa                         boolean,
  paid                          boolean,
  constraint uq_spot_team_id_student_id unique (team_id,student_id),

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
  division                      varchar(255),
  gender                        varchar(255),
  sport                         varchar(255),
  season                        varchar(255),
  school_year                   timestamp,
  banquet_info                  varchar(255),
  coaches                       array,
  default_points                integer,
  constraint pk_team primary key (id)
);

alter table spot add constraint fk_spot_team_id foreign key (team_id) references team (id) on delete restrict on update restrict;
create index ix_spot_team_id on spot (team_id);

alter table spot add constraint fk_spot_student_id foreign key (student_id) references student (id) on delete restrict on update restrict;
create index ix_spot_student_id on spot (student_id);


# --- !Downs

alter table spot drop constraint if exists fk_spot_team_id;
drop index if exists ix_spot_team_id;

alter table spot drop constraint if exists fk_spot_student_id;
drop index if exists ix_spot_student_id;

drop table if exists admin;

drop table if exists sport;

drop table if exists spot;

drop table if exists student;

drop table if exists team;

