create table tb_disease
(
    id          int auto_increment comment '名字id'
        primary key,
    name        varchar(63) not null comment '病症名',
    description text        not null comment '病症描述'
)
    comment '疾病表';

create table tb_drug
(
    id                   int          not null comment '药品id'
        primary key,
    name                 varchar(63)  not null comment '药物名称',
    expenseEach          int          not null comment '药物单价, 单位, 分',
    specification        varchar(63)  not null comment '药物规格',
    administrationRoute  varchar(63)  not null comment '给药途径',
    medicationSite       varchar(63)  not null comment '用药部位',
    medicationPrecaution varchar(255) not null comment '用药注意事项',
    guidance             text         not null comment '用药指导'
)
    comment '药品具体信息';

create table tb_family_relationship
(
    id          int auto_increment comment '主键id'
        primary key,
    name        varchar(63)  not null comment '名称, 英文',
    description varchar(255) not null comment '描述',
    close_level tinyint(1)   not null comment '是否是一级亲属, true for 一级, false for 二级'
)
    comment '权限表';

create table tb_follow_up
(
    id                         bigint       not null comment '随访表id'
        primary key,
    visit_doctor_id            bigint       not null comment '就诊号',
    allergies_improve          varchar(255) not null comment '过敏症改善情况',
    physical_development       varchar(255) not null comment '身体发育情况',
    adverse_drug_reaction      varchar(255) not null comment '药物不良反应',
    adverse_reaction_treatment varchar(255) not null comment '不良反应处理方法',
    constraint tb_follow_up_unique_visit_doctor_id
        unique (visit_doctor_id)
)
    comment '随访表';

create table tb_healthcare
(
    healthcare_id   bigint        not null comment '主键id'
        primary key,
    healthcare_code varchar(31)   not null comment '医保码, 比较复杂, 找不到权威的资料',
    type            varchar(63)   not null comment '医保类型',
    balance         int default 0 not null comment '医保余额',
    constraint tb_healthcare_healthcare_code_uindex
        unique (healthcare_code)
)
    comment '权限表';

create table tb_medical_department
(
    id                  int auto_increment comment '主键id'
        primary key,
    name                varchar(63)  not null comment '科室名',
    description         varchar(255) not null comment '描述',
    outer_department    int          null comment '外部科室, null for outermost',
    expense_every_visit int          not null comment '每次问诊产生的费用,单位,分',
    constraint tb_medical_department_outer_self_fk
        foreign key (outer_department) references tb_medical_department (id)
)
    comment '权限表';

create table tb_medical_provider_form
(
    id      int auto_increment comment '主键id'
        primary key,
    name    varchar(63)  not null comment '机构名',
    address varchar(255) not null comment '机构所在地址'
)
    comment '医疗提供机构表';

create table tb_patient
(
    id               bigint                  not null comment '患者id'
        primary key,
    phone            char(11)                not null comment '电话',
    name             varchar(63)             not null comment '姓名',
    sex              enum ('MALE', 'FEMALE') not null comment '性别',
    birth_date       date                    not null comment '出生日期',
    address          varchar(255)            not null comment '家庭地址',
    height           float                   not null comment '升高(m)',
    weight           float                   not null comment '体重(kg)',
    healthcare_id    bigint                  null comment '医保号',
    identity_card_id char(18)                not null comment '病患身份证号, char(11) 不可为null',
    constraint tb_patient_healthcare_id
        unique (healthcare_id),
    constraint tb_patient_identity_card_id
        unique (identity_card_id),
    constraint tb_patient_tb_healthcare_fk
        foreign key (healthcare_id) references tb_healthcare (healthcare_id)
)
    comment '患者表';

create table tb_family_history
(
    id                     bigint not null comment '主键id'
        primary key,
    patient_Id             bigint not null comment '病患, 关系的基',
    family_relationship_id int    not null comment '关系表id',
    disease_id             int    not null comment '病症id',
    constraint tb_family_history_disease_id_tb_disease_fk
        foreign key (disease_id) references tb_disease (id),
    constraint tb_family_history_patient_id_tb_patient_id_fk
        foreign key (patient_Id) references tb_patient (id),
    constraint tb_family_history_tb_family_relationship_fk
        foreign key (family_relationship_id) references tb_family_relationship (id)
)
    comment '家庭病史';

create index tb_family_history_patient_id_index
    on tb_family_history (patient_Id)
    comment '常常依据patientc';

create table tb_role
(
    id          int auto_increment comment '主键id'
        primary key,
    name        varchar(63)  not null comment '名称, 英文',
    description varchar(255) not null comment '描述'
)
    comment '权限表';

create table tb_medical_provider_job
(
    id                  int auto_increment comment '主键id'
        primary key,
    role_id             int         not null comment '权限',
    name                varchar(63) not null comment '职位名',
    expense_every_visit int         not null comment '每次问诊能拿到的费用',
    constraint tb_medical_provider_job_tb_role_fk
        foreign key (role_id) references tb_role (id)
)
    comment '医生职务表';

create table tb_medical_provider
(
    id               bigint auto_increment comment '主键id'
        primary key,
    name             varchar(63) not null comment '姓名',
    identity_card_id varchar(18) not null comment '身份证号',
    role_id          int         not null comment '权限',
    form_id          int         not null comment '机构id',
    department_id    int         not null comment '科室id',
    job_id           int         not null comment '职位id',
    constraint tb_medical_provider_tb_medical_department_fk
        foreign key (department_id) references tb_medical_department (id),
    constraint tb_medical_provider_tb_medical_provider_form_fk
        foreign key (form_id) references tb_medical_provider_form (id),
    constraint tb_medical_provider_tb_medical_provider_job_fk
        foreign key (job_id) references tb_medical_provider_job (id),
    constraint tb_medical_provider_tb_role_fk
        foreign key (role_id) references tb_role (id)
)
    comment '医疗提供者表, 考虑到业务比较复杂,现阶段无法 故在出现慢查询之后再进行索引建立';

create table tb_visit_doctor
(
    id                     bigint               not null comment '就诊号'
        primary key,
    patient_id             bigint               not null comment '患者id',
    medical_provider_id    bigint               not null comment '医疗提供者id',
    brief_description      varchar(255)         null comment '对该次会诊的简述',
    other_adjuvant_therapy varchar(255)         null comment '其他辅助治疗',
    visit_time             datetime             null comment '访问时间',
    total_expense          int                  null comment '该次问诊总价',
    paid                   tinyint(1) default 0 not null comment '是否已经支付本次问诊费用',
    followup_id            bigint               null comment '随访id',
    constraint tb_visit_doctor_tb_follow_up_fk
        foreign key (followup_id) references tb_follow_up (id),
    constraint tb_visit_doctor_tb_medical_department_fk
        foreign key (medical_provider_id) references tb_medical_provider (id),
    constraint tb_visit_doctor_tb_patient_fk
        foreign key (patient_id) references tb_patient (id)
)
    comment '问诊表';

create table tb_disease_diagnosis_intermediation
(
    visit_doctor_id bigint not null comment '会诊id',
    disease_id      int    not null comment '病症id',
    primary key (visit_doctor_id, disease_id),
    constraint tb_disease_diagnosis_intermediation_disease_id_tb_disease_id_fk
        foreign key (disease_id) references tb_disease (id),
    constraint tb_disease_diagnosis_intermediation_tb_visit_doctor_id_fk
        foreign key (visit_doctor_id) references tb_visit_doctor (id)
)
    comment '疾病诊断中间表';

create table tb_expenses_record
(
    id              bigint       not null comment '费用表主键id'
        primary key,
    visit_doctor_id bigint       not null comment '就诊号/就诊表',
    category        varchar(63)  not null comment '消费类别',
    description     varchar(255) not null comment '描述',
    amount          int          not null comment '金额,分',
    constraint tb_expenses_record_visit_doctor_id_tb_visit_doctor_id_fk
        foreign key (visit_doctor_id) references tb_visit_doctor (id)
)
    comment '费用记录表';

alter table tb_follow_up
    add constraint tb_follow_up_tb_visit_doctor_fk
        foreign key (visit_doctor_id) references tb_visit_doctor (id);

create table tb_specific_using_drug_record
(
    visit_doctor_id           bigint               not null comment '就诊号/就诊表id, 和 drug id 一起是联合主键',
    drug_id                   int                  not null comment '药品表id, 和 visit doctor id 一起是联合主键',
    count                     int                  not null comment '数量, 和钱的计算有关',
    patient_id                bigint               not null comment '患者表id(冗余, 为了提高效率), 方便患者查询当前需要用药',
    dosage_used               varchar(63)          null comment '使用剂量, 未知单位, 故字符串',
    day_time_used             int                  null comment '使用天数',
    frequency_used            varchar(63)          null comment '使用频率, 未知单位, 故字符串',
    treat_start               date                 not null comment '治疗开始时间',
    treat_end                 date                 not null comment '治疗结束时间',
    other_medication_guidance text                 null comment '其他用药指导',
    deleted                   tinyint(1) default 0 not null comment '逻辑删除',
    primary key (visit_doctor_id, drug_id),
    constraint tb_specific_using_drug_record_tb_drug_fk
        foreign key (drug_id) references tb_drug (id),
    constraint tb_specific_using_drug_record_tb_patient_fk
        foreign key (patient_id) references tb_patient (id),
    constraint tb_specific_using_drug_record_tb_visit_doctor_fk
        foreign key (visit_doctor_id) references tb_visit_doctor (id)
)
    comment '药物具体使用表';

create index tb_specific_using_drug_record_patient_id_index
    on tb_specific_using_drug_record (patient_id);

create index tb_specific_using_drug_record_treat_end_index
    on tb_specific_using_drug_record (treat_end)
    comment '可以查询有关日期范围内的药品';

create index tb_specific_using_drug_record_treat_start_index
    on tb_specific_using_drug_record (treat_start)
    comment '可以查询有关日期范围内的药品';

create index tb_specific_using_drug_record_visit_doctor_id_index
    on tb_specific_using_drug_record (visit_doctor_id);

create table tb_symptomatic_presentation
(
    id                   bigint                                not null comment '症状id'
        primary key,
    visit_doctor_id      bigint                                not null comment '就诊号/就诊表id',
    name                 char(11)                              not null comment '名称',
    severity             enum ('SLIGHT', 'MODERATE', 'SEVERE') not null comment '严重程度(enum-轻度/中度/重度)',
    frequency            varchar(63)                           not null comment '频率, 由于不知道单位, 故字符串',
    start_time           date                                  not null comment '开始时间',
    incentive            varchar(63)                           not null comment '诱因',
    environmental_factor varchar(63)                           not null comment '环境因素',
    sign_description     varchar(63)                           not null comment '体征描述',
    description          text                                  not null comment '描述',
    deleted              tinyint(1) default 0                  not null comment '逻辑删除',
    constraint tb_symptomatic_presentation_tb_visit_doctor_fk
        foreign key (visit_doctor_id) references tb_visit_doctor (id)
)
    comment '症状表现表';

create index tb_symptomatic_presentation_visit_doctor_id_index
    on tb_symptomatic_presentation (visit_doctor_id)
    comment '依据问诊查有关症状';

create table tb_user_patient_intermediation
(
    user_id    bigint not null,
    patient_id bigint not null,
    primary key (user_id, patient_id),
    constraint tb_user_patient_intermediation_tb_patient_fk
        foreign key (patient_id) references tb_patient (id),
    constraint tb_user_patient_intermediation_tb_visit_doctor_fk
        foreign key (user_id) references tb_visit_doctor (id)
)
    comment '用户和病患id的中间表, 用户可以持有多个病患, bing''hua';

create index tb_user_patient_intermediation_user_id_index
    on tb_user_patient_intermediation (user_id)
    comment '依据user_id查询可优化';

create index tb_visit_doctor_medical_provider_id_index
    on tb_visit_doctor (medical_provider_id)
    comment '医疗提供者id';

create index tb_visit_doctor_patient_id_index
    on tb_visit_doctor (patient_id)
    comment 'patient_id';

create index tb_visit_doctor_visit_time_index
    on tb_visit_doctor (visit_time)
    comment '用于时间范围的查询';

create table user_security
(
    id                 bigint                             not null comment '用户id'
        primary key,
    role_id            int                                not null comment '权限',
    phone              char(11)                           not null comment '电话号码, +86 only',
    password           char(60)                           not null comment '密码',
    name               varchar(63)                        not null comment '密码',
    create_time        datetime default CURRENT_TIMESTAMP not null,
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP invisible,
    identifier_card_id int                                null,
    constraint identifier_card_id
        unique (identifier_card_id),
    constraint user_security_phone
        unique (phone),
    constraint user_security_tb_role_fk
        foreign key (role_id) references tb_role (id)
)
    comment '用户鉴权表';


