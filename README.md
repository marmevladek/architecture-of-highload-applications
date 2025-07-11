# Приложение для знакомств

# Требования к проекту
## 1.1 Максимальное количество пользователей, поддерживаемых в каждый момент времени
#### Ожидаемое количество одновременных сессий:
- До <b>10000</b> пользователей могут быть одновременно авторизованы и иметь активную сессию.
#### Количество полльзователей, активно взаимодействующих с приложением в реальном времени:
- До <b>1000</b> пользователей могут одновременно выполнять свайпы, отправлять/получать совпадения, загружать/обновлять профили.


## 1.2 Требуемая скорость обработки запросов
#### 1.2.1. Время отлика на запросы пользователей:
- Время обработки POST-запроса на свайп - не более 300 мс
- Время получения колоды - не более 200 мс
- Время получения профли - не более 150 мс

#### 1.2.2. Время обработки запросов на уровне БД и взаимодействие между сервисами:
- Запись свайпа в базу - < 50 мс
- Доступ к Redis для чтения/ записи колоды - < 20 мс
- Взаимодействие с RabbitMQ (отправка события совпадения) - < 10 мс

## 1.3. Объем хранимой информации
#### 1.3.1. Прогнозируемый объем данных на старте:
- <b>Профили:</b> ~10000 записей -> ~20 МБ
- <b>Свайпы:</b> ~100000 записей -> ~10-20 МБ
- <b>Колоды в Redis:</b> Хранятся на ~12 часов, общим объеймом до 50-100 МБ

#### 1.3.2. Прогнозируемый рост объема данных с течением времени:
<b>При росте пользователей до 1 млн:</b>
- Профили: до 200 МБ
- Свайпа до 2-5 ГБ
- Колоды в Redis: до 1-2 ГБ
Логи, телеграм-ссылки, изоьражения и другая информация могут добавить еще ~10-15 ГБ в течении года.

## Диаграмма прецедентов
<picture>
  <img src="assets/UseCaseDiagram.jpg">
</picture>

## Архитектурная схема
<picture>
  <img src="assets/arch.jpg">
</picture>

## Grafana / Prometheus
#### Dashboard - Spring Boot 2.1 System Monitor
<picture>
    <img src="assets/grafana3springboot.jpg">
</picture>


#### Dashboard - Cadvisor exporter
<picture>
    <img src="assets/grafana3cadvisor.jpg">
</picture>


## Tsung

- Первый тест:
  - В течение 15 минут постепенно будет сгенерировано до 12000 пользователей, каждый из которых совершает свайпы.
- Второй тест:
  - В течение 1 минуты создаётся до 3000 пользователей с интенсивностью 1000 пользователей в секунду.
  
- Третий тест:
  - В течение 10 минут создаётся до 20000 пользователей, которые совершают свайпы.
  - Нагрузка распределена по фазам:
    - Фаза 1 (2 минуты):
      -	Пользователи приходят со скоростью 800 в секунду, максимум — 15000.
    - Фаза 2 (3 минуты):
      - Скорость возрастает до 1200 в секунду, максимум — 20000 пользователей.

1) 
   `````docker-compose up -d --build`````
2) 
   `````docker-compose exec tsung tsung -f /tsung/config/{config}.xml start`````
3) `````docker-compose run --rm -w "/root/.tsung/log/{date}/"  --entrypoint "/usr/local/lib/tsung/bin/tsung_stats.pl" tsung --stats "tsung.log"`````

#### Report - low_rate_high_users
<picture>
    <img src="assets/tsung-4-1-report.jpg">
</picture>

#### Report - high_rate_low_users
<picture>
    <img src="assets/tsung-4-2-report.jpg">
</picture>

#### Report - peak_load
<picture>
    <img src="assets/tsung-4-3-report.jpg">
</picture>

# Масштабирование

### Вертикальное масштабирование
Swipe Service:
- 4 CPU
- 1024 MB RAM

<picture>
    <img src="assets/vs_4_1024.jpg">
</picture>

Результаты:
 - Максимальная скорость обработки запросов: 637.9 / sec
 - Средняя скорость обработки запросов: 142.59 / sec
 - Нагрузка на CPU: 68.29 %
 - Занято RAM: 2858.37 MB

### Горизонтальное масштабирование
Swipe Service:
- 2 реплики

<picture>
    <img src="assets/hs_2.jpg">
</picture>

Результаты:
- Максимальная скорость обработки запросов: 520.2 / sec
- Средняя скорость обработки запросов: 127.77 / sec
- Нагрузка на CPU: 95.84 %
- Занято RAM: 2432.21 MB

Swipe Service:
- 3 реплики

<picture>
    <img src="assets/hs_3.jpg">
</picture>

Результаты:
- Максимальная скорость обработки запросов: 413.8 / sec
- Средняя скорость обработки запросов: 129.80 / sec
- Нагрузка на CPU: 97.84 %
- Занято RAM: 1938.75 MB

### Комбинированное масштабирование
Swipe Service:
- вертикальное
  - 4 CPU
  - 1024 MB RAM
- горизонтальное
  - 2 реплики

<picture>
    <img src="assets/hs_2_vs_4_1024.jpg">
</picture>

Результаты:
- Максимальная скорость обработки запросов: 628.5 / sec
- Средняя скорость обработки запросов: 138.28 / sec
- Нагрузка на CPU: 84.77 %
- Занято RAM: 2506.98 MB

# Репликация и шардирование

<picture>
    <img src="assets/hs_2_vs_4_1024_rl_sh.jpg">
</picture>

Результаты:
- Максимальная скорость обработки запросов: 590.5 / sec
- Средняя скорость обработки запросов: 129.28 / sec
- Нагрузка на CPU: 87.30 %
- Занято RAM: 2442.69 MB

# NGINX

### Round-Robin

<picture>
    <img src="assets/rr_hs_2_vs_4_1024_rl_sh.jpg">
</picture>

Результаты:
 - Максимальная скорость обработки запросов: 568.3 / sec
 - Средняя скорость обработки запросов: 132.2 / sec
 - Нагрузка на CPU: 75.59 %
 - Занято RAM: 2403.05 MB

### Least-Connections

<picture>
    <img src="assets/lc_hs_2_vs_4_1024_rl_sh.jpg">
</picture>

Результаты:
- Максимальная скорость обработки запросов: 507.2 / sec
- Средняя скорость обработки запросов: 124.79 / sec
- Нагрузка на CPU: 80.69 %
- Занято RAM: 2501.53 MB

### Random

<picture>
    <img src="assets/random_hs_2_vs_4_1024_rl_sh.jpg">
</picture>

Результаты:
- Максимальная скорость обработки запросов: 511.7 / sec 
- Средняя скорость обработки запросов: 131.57 / sec
- Нагрузка на CPU: 88.00 %
- Занято RAM: 2411.86 MB

Проведя анализ всех тестирований, можно сделать вывод, что самой подходящей конфигурацией является:

- Replicas: 2
- CPU: 4
- RAM: 1024 MB
- NGINX: Round-Robin
- Replication & Sharding: для отказоустойчивости системы