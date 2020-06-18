--liquibase formatted sql

--changeset kateshaidurova:region_schemes
CREATE TABLE public.region_schemes (
  id              BIGINT     NOT NULL PRIMARY KEY,
  list_schemes_id BIGINT     NOT NULL REFERENCES list_schemes (id),
  country_code    VARCHAR(2) NOT NULL,
  code            TEXT       NOT NULL,
  UNIQUE (list_schemes_id, country_code, code)
);

--changeset kateshaidurova:region_schemes_filling_data context:!test
INSERT INTO public.region_schemes(id, list_schemes_id, country_code, code)
VALUES (1, 2 /* CUATM */, 'MD', '0101000'),
       (2, 2 /* CUATM */, 'MD', '0301000'),
       (3, 2 /* CUATM */, 'MD', '0501000'),
       (4, 2 /* CUATM */, 'MD', '1000000'),
       (5, 2 /* CUATM */, 'MD', '1200000'),
       (6, 2 /* CUATM */, 'MD', '1400000'),
       (7, 2 /* CUATM */, 'MD', '1700000'),
       (8, 2 /* CUATM */, 'MD', '2100000'),
       (9, 2 /* CUATM */, 'MD', '2500000'),
       (10, 2 /* CUATM */, 'MD', '2700000'),
       (11, 2 /* CUATM */, 'MD', '2900000'),
       (12, 2 /* CUATM */, 'MD', '3100000'),
       (13, 2 /* CUATM */, 'MD', '3400000'),
       (14, 2 /* CUATM */, 'MD', '3600000'),
       (15, 2 /* CUATM */, 'MD', '3800000'),
       (16, 2 /* CUATM */, 'MD', '4100000'),
       (17, 2 /* CUATM */, 'MD', '4300000'),
       (18, 2 /* CUATM */, 'MD', '4500000'),
       (19, 2 /* CUATM */, 'MD', '4800000'),
       (20, 2 /* CUATM */, 'MD', '5300000'),
       (21, 2 /* CUATM */, 'MD', '5500000'),
       (22, 2 /* CUATM */, 'MD', '5700000'),
       (23, 2 /* CUATM */, 'MD', '6000000'),
       (24, 2 /* CUATM */, 'MD', '6200000'),
       (25, 2 /* CUATM */, 'MD', '6400000'),
       (26, 2 /* CUATM */, 'MD', '6700000'),
       (27, 2 /* CUATM */, 'MD', '7100000'),
       (28, 2 /* CUATM */, 'MD', '7400000'),
       (29, 2 /* CUATM */, 'MD', '7800000'),
       (30, 2 /* CUATM */, 'MD', '8000000'),
       (31, 2 /* CUATM */, 'MD', '8300000'),
       (32, 2 /* CUATM */, 'MD', '8500000'),
       (33, 2 /* CUATM */, 'MD', '8700000'),
       (34, 2 /* CUATM */, 'MD', '8900000'),
       (35, 2 /* CUATM */, 'MD', '9200000'),
       (36, 2 /* CUATM */, 'MD', '9600000'),
       (37, 2 /* CUATM */, 'MD', '9800000');


--changeset kateshaidurova:region_schemes_i18n
CREATE TABLE public.region_schemes_i18n (
  id                         BIGINT     NOT NULL PRIMARY KEY,
  region_scheme_id BIGINT           NOT NULL REFERENCES region_schemes (id),
  language_code              VARCHAR(2) NOT NULL,
  description                TEXT       NOT NULL,
  UNIQUE (region_scheme_id, language_code)
);

--changeset kateshaidurova:region_schemes_i18n_filling_data context:!test
INSERT INTO public.region_schemes_i18n(id, region_scheme_id, language_code, description)
VALUES (1, 1, 'EN', 'mun.Chisinau'),
       (2, 2, 'EN', 'mun.Balti'),
       (3, 3, 'EN', 'mun.Bender'),
       (4, 4, 'EN', 'Anenii Noi'),
       (5, 5, 'EN', 'Basarabeasca'),
       (6, 6, 'EN', 'Briceni'),
       (7, 7, 'EN', 'Cahul'),
       (8, 8, 'EN', 'Cantemir'),
       (9, 9, 'EN', 'Calarasi'),
       (10, 10, 'EN', 'Causeni'),
       (11, 11, 'EN', 'Cimislia'),
       (12, 12, 'EN', 'Criuleni'),
       (13, 13, 'EN', 'Donduseni'),
       (14, 14, 'EN', 'Drochia'),
       (15, 15, 'EN', 'Dubasari'),
       (16, 16, 'EN', 'Edinet'),
       (17, 17, 'EN', 'Falesti'),
       (18, 18, 'EN', 'Floresti'),
       (19, 19, 'EN', 'Glodeni'),
       (20, 20, 'EN', 'Hincesti'),
       (21, 21, 'EN', 'Ialoveni'),
       (22, 22, 'EN', 'Leova'),
       (23, 23, 'EN', 'Nisporeni'),
       (24, 24, 'EN', 'Ocnita'),
       (25, 25, 'EN', 'Orhei'),
       (26, 26, 'EN', 'Rezina'),
       (27, 27, 'EN', 'Riscani'),
       (28, 28, 'EN', 'Singerei'),
       (29, 29, 'EN', 'Soroca'),
       (30, 30, 'EN', 'Straseni'),
       (31, 31, 'EN', 'Soldanesti'),
       (32, 32, 'EN', 'Stefan Voda'),
       (33, 33, 'EN', 'Taraclia'),
       (34, 34, 'EN', 'Telenesti'),
       (35, 35, 'EN', 'Ungheni'),
       (36, 36, 'EN', 'Gagauzia'),
       (37, 37, 'EN', 'UATSN'),
       (38, 1, 'RO', 'mun.Chişinău'),
       (39, 2, 'RO', 'mun.Bălţi'),
       (40, 3, 'RO', 'mun.Bender'),
       (41, 4, 'RO', 'Anenii Noi'),
       (42, 5, 'RO', 'Basarabeasca'),
       (43, 6, 'RO', 'Briceni'),
       (44, 7, 'RO', 'Cahul'),
       (45, 8, 'RO', 'Cantemir'),
       (46, 9, 'RO', 'Călăraşi'),
       (47, 10, 'RO', 'Căuşeni'),
       (48, 11, 'RO', 'Cimişlia'),
       (49, 12, 'RO', 'Criuleni'),
       (50, 13, 'RO', 'Donduşeni'),
       (51, 14, 'RO', 'Drochia'),
       (52, 15, 'RO', 'Dubăsari'),
       (53, 16, 'RO', 'Edineț'),
       (54, 17, 'RO', 'Făleşti'),
       (55, 18, 'RO', 'Floreşti'),
       (56, 19, 'RO', 'Glodeni'),
       (57, 20, 'RO', 'Hînceşti'),
       (58, 21, 'RO', 'Ialoveni'),
       (59, 22, 'RO', 'Leova'),
       (60, 23, 'RO', 'Nisporeni'),
       (61, 24, 'RO', 'Ocniţa'),
       (62, 25, 'RO', 'Orhei'),
       (63, 26, 'RO', 'Rezina'),
       (64, 27, 'RO', 'Rîşcani'),
       (65, 28, 'RO', 'Sîngerei'),
       (66, 29, 'RO', 'Soroca'),
       (67, 30, 'RO', 'Străşeni'),
       (68, 31, 'RO', 'Şoldăneşti'),
       (69, 32, 'RO', 'Ştefan Vodă'),
       (70, 33, 'RO', 'Taraclia'),
       (71, 34, 'RO', 'Teleneşti'),
       (72, 35, 'RO', 'Ungheni'),
       (73, 36, 'RO', 'UTA Găgăuzia'),
       (74, 37, 'RO', 'UATSN'),
       (75, 1, 'RU', 'мун.Кишинэу'),
       (76, 2, 'RU', 'мун.Бэлць'),
       (77, 3, 'RU', 'мун.Бендер'),
       (78, 4, 'RU', 'Анений Ной'),
       (79, 5, 'RU', 'Басарабяскa'),
       (80, 6, 'RU', 'Бричень'),
       (81, 7, 'RU', 'Кахул'),
       (82, 8, 'RU', 'Кантемир'),
       (83, 9, 'RU', 'Кэлэрашь'),
       (84, 10, 'RU', 'Кэушень'),
       (85, 11, 'RU', 'Чимишлия'),
       (86, 12, 'RU', 'Криулень'),
       (87, 13, 'RU', 'Дондушень'),
       (88, 14, 'RU', 'Дрокия'),
       (89, 15, 'RU', 'Дубэсарь'),
       (90, 16, 'RU', 'Единец'),
       (91, 17, 'RU', 'Фэлешть'),
       (92, 18, 'RU', 'Флорешть'),
       (93, 19, 'RU', 'Глодень'),
       (94, 20, 'RU', 'Хынчешть'),
       (95, 21, 'RU', 'Яловень'),
       (96, 22, 'RU', 'Леова'),
       (97, 23, 'RU', 'Ниспорень'),
       (98, 24, 'RU', 'Окница'),
       (99, 25, 'RU', 'Орхей'),
       (100, 26, 'RU', 'Резина'),
       (101, 27, 'RU', 'Рышкань'),
       (102, 28, 'RU', 'Сынджерей'),
       (103, 29, 'RU', 'Сорока'),
       (104, 30, 'RU', 'Стрэшень'),
       (105, 31, 'RU', 'Шолдэнешть'),
       (106, 32, 'RU', 'Штефан Водэ'),
       (107, 33, 'RU', 'Тараклия'),
       (108, 34, 'RU', 'Теленешть'),
       (109, 35, 'RU', 'Унгень'),
       (110, 36, 'RU', 'АТО Гагаузия'),
       (111, 37, 'RU', 'АТЕЛД');
