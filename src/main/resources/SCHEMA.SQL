create table t_scheduler_job (
                                 id serial not null,
                                 job_name varchar(255),
                                 job_group varchar(255),
                                 cron varchar(255),
                                 repeat_time BIGINT,
                                 is_cron BOOLEAN,
                                 primary key(id)
);

create table t_process (
                           id serial not null,
                           process_type INTEGER,
                           url varchar(255),
                           job_id BIGINT,
                           primary key(id),
                           CONSTRAINT fk_job FOREIGN KEY (job_id) REFERENCES t_scheduler_job(id)
);