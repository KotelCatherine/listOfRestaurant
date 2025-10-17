-- Более сложный вариант с разными типами расписаний для таблицы restaurant_schedules

DO $$
    DECLARE
        restaurant_uuids uuid[] := ARRAY[
            '11111111-1111-1111-1111-111111111111',
            '22222222-2222-2222-2222-222222222222',
            '33333333-3333-3333-3333-333333333333',
            '44444444-4444-4444-4444-444444444444',
            '55555555-5555-5555-5555-555555555555',
            '66666666-6666-6666-6666-666666666666',
            '77777777-7777-7777-7777-777777777777',
            'b37b1f77-3cc6-4dfb-bc25-477f019dcbd0',
            'f91e9e36-39e1-4433-945d-ee3d6f4c1b99',
            '710e3a72-fc0a-4052-b1d6-1e0e1470be32',
            'cc19dc95-a2f2-48ac-ba16-6afa36a42cda'
            ];

        restaurant_uuid uuid;
        day_index integer;
        schedule_id uuid;
        open_t time;
        close_t time;
        closed_flag boolean;
        table_exists boolean;

    BEGIN
        -- Проверяем существование таблицы restaurant_schedules
        SELECT EXISTS (
            SELECT FROM information_schema.tables
            WHERE table_schema = 'public'
              AND table_name = 'restaurant_schedules'
        ) INTO table_exists;

        IF NOT table_exists THEN
            RAISE EXCEPTION 'Таблица restaurant_schedules не существует в схеме public';
        END IF;

        -- Очищаем существующие данные для этих ресторанов (опционально)
        DELETE FROM restaurant_schedules
        WHERE restaurant_id = ANY(restaurant_uuids);

        RAISE NOTICE 'Начинаем заполнение таблицы restaurant_schedules...';

        FOREACH restaurant_uuid IN ARRAY restaurant_uuids
            LOOP
                FOR day_index IN 1..7
                    LOOP
                        schedule_id := gen_random_uuid();

                        -- Разные типы расписаний в зависимости от дня недели
                        CASE day_index
                            WHEN 1 THEN -- Понедельник
                            open_t := '09:00'::time;
                            close_t := '21:00'::time;
                            closed_flag := false;

                            WHEN 2 THEN -- Вторник
                            open_t := '09:00'::time;
                            close_t := '21:00'::time;
                            closed_flag := false;

                            WHEN 3 THEN -- Среда
                            open_t := '09:00'::time;
                            close_t := '22:00'::time;
                            closed_flag := false;

                            WHEN 4 THEN -- Четверг
                            open_t := '09:00'::time;
                            close_t := '22:00'::time;
                            closed_flag := false;

                            WHEN 5 THEN -- Пятница
                            open_t := '09:00'::time;
                            close_t := '23:00'::time;
                            closed_flag := false;

                            WHEN 6 THEN -- Суббота
                            open_t := '10:00'::time;
                            close_t := '23:00'::time;
                            closed_flag := false;

                            WHEN 7 THEN -- Воскресенье
                            -- Некоторые рестораны закрыты по воскресеньям
                                IF random() < 0.3 THEN
                                    open_t := '00:00'::time;
                                    close_t := '00:00'::time;
                                    closed_flag := true;
                                ELSE
                                    open_t := '10:00'::time;
                                    close_t := '22:00'::time;
                                    closed_flag := false;
                                END IF;

                            END CASE;

                        -- Вставляем данные именно в таблицу restaurant_schedules
                        INSERT INTO restaurant_schedules (id, restaurant_id, day_of_week, open_time, close_time, is_closed)
                        VALUES (schedule_id, restaurant_uuid, day_index, open_t, close_t, closed_flag);

                    END LOOP;

                RAISE NOTICE 'Добавлено расписание для ресторана %', restaurant_uuid;
            END LOOP;

        RAISE NOTICE 'Расписания успешно созданы в таблице restaurant_schedules для всех ресторанов';

        -- Выводим статистику
        RAISE NOTICE 'Всего создано записей: %', (SELECT COUNT(*) FROM restaurant_schedules WHERE restaurant_id = ANY(restaurant_uuids));

    END $$;